package psidev.psi.mi.jami.bridges.ols;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Abstract class for OlsCvTermFetcher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/08/13</pre>
 */

public abstract class AbstractOlsFetcher<T extends CvTerm> implements CvTermFetcher<T> {

    protected Query queryService;
    protected Map<String,String> dbMap = new HashMap<String, String>();

    public AbstractOlsFetcher() throws BridgeFailedException {
        try{
            queryService = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            queryService = null;
            throw new BridgeFailedException("Cannot create OLS fetcher.", e);
        }
        initialiseDbMap();
    }

    private void initialiseDbMap(){
        dbMap.put("psi-mi", "MI");
        dbMap.put("psi-mod", "MOD");
        dbMap.put("psi-par", "PAR");
        dbMap.put("go", "GO");
    }

    private Xref createXref(String identifier, String  miOntologyName){
        if (CvTerm.PSI_MI.equalsIgnoreCase(miOntologyName)){
            return XrefUtils.createPsiMiIdentity(identifier);
        } else if(CvTerm.PSI_MOD.equalsIgnoreCase(miOntologyName)) {
            return XrefUtils.createPsiModIdentity(identifier);
        } else {
            return XrefUtils.createIdentityXref(miOntologyName , identifier);
        }
    }

    public T fetchCvTermByIdentifier(String termIdentifier, String miOntologyName) throws BridgeFailedException {

        if(termIdentifier == null || termIdentifier.isEmpty())
            throw new IllegalArgumentException("Can not search for an identifier without a value.");
        if(miOntologyName == null || miOntologyName.isEmpty())
            throw new IllegalArgumentException("Can not search for an identifier in an ontology without a value.");

        String olsOntologyName = null;
        if(dbMap.containsKey(miOntologyName))
            olsOntologyName = dbMap.get(miOntologyName);
        String fullName = null;

        // 1) query ols which returns full name.
        try{
             fullName = queryService.getTermById(termIdentifier , olsOntologyName);
        } catch (RemoteException e) {
            throw new BridgeFailedException("Failed to query in OLS fetcher.", e);
        }

        // 2) if no results, return null
        if (fullName == null || fullName.equals(termIdentifier))
            return null;

        // 3) if a result, call instantiateCvTerm with provided fullName and create identity xref
        return instantiateCvTerm(fullName , createXref(termIdentifier , miOntologyName), olsOntologyName);
    }

    public T fetchCvTermByIdentifier(String termIdentifier, CvTerm ontologyCvTerm) throws BridgeFailedException {

        if(termIdentifier == null || termIdentifier.isEmpty())
            throw new IllegalArgumentException("Can not search for an identifier without a value.");
        if(ontologyCvTerm == null)
            throw new IllegalArgumentException("Can not search for an identifier in an ontology without a value.");

        String olsOntologyName = null;
        if(dbMap.containsKey(ontologyCvTerm.getShortName()))
            olsOntologyName = dbMap.get(ontologyCvTerm.getShortName());
        String fullName = null;

        // 1) query ols which returns full name.
        try{
            fullName = queryService.getTermById(termIdentifier , null);
        } catch (RemoteException e) {
            throw new BridgeFailedException("Failed to query in OLS fetcher.", e);
        }

        // 2) if no results, return null
        if (fullName == null || fullName.equals(termIdentifier))
            return null;

        // 3) if a result, call instantiateCvTerm with provided fullName and create identity xref
        return instantiateCvTerm(fullName , new DefaultXref(ontologyCvTerm , termIdentifier), olsOntologyName);
    }

    public T fetchCvTermByName(String searchName, String miOntologyName) throws BridgeFailedException {

        if(searchName == null || searchName.isEmpty())
            throw new IllegalArgumentException("Can not search for an identifier without a value.");

        String olsOntologyName = null;
        if(dbMap.containsKey(miOntologyName))
            olsOntologyName = dbMap.get(miOntologyName);

        // 1) query ols which returns full name.
        HashMap<String,String> termNamesMap;
        try{
            termNamesMap = queryService.getTermsByExactName(searchName, olsOntologyName);
        }catch (RemoteException e) {
            throw new BridgeFailedException("Failed to query in OLS fetcher.",e);
        }

        // 2) if no results, return null
        if(termNamesMap.size() != 1)
            return null;

        Map.Entry<String, String> entry = termNamesMap.entrySet().iterator().next();
        String fullName = entry.getValue();

        // 3) if a result, call instantiateCvTerm with provided fullName and create identity xref
        return instantiateCvTerm(fullName , createXref(entry.getKey() , miOntologyName), olsOntologyName);
    }

    public Collection<T> fetchCvTermByName(String searchName) throws BridgeFailedException {
        if(searchName == null || searchName.isEmpty())
            throw new IllegalArgumentException("Can not search for an identifier without a value.");

        // 1) query ols which returns full name.
        HashMap<String,String> termNamesMap;
        try{
            termNamesMap = queryService.getTermsByExactName(searchName, null);
        }catch (RemoteException e) {
            throw new BridgeFailedException("Failed to query in OLS fetcher.",e);
        }

        // 2) if no results, return null
        if(termNamesMap.isEmpty())
            return Collections.EMPTY_LIST;

        Collection<T> results = new ArrayList<T>(termNamesMap.size());

        for (Map.Entry<String, String> entry : termNamesMap.entrySet()){
            String fullName = entry.getValue();

            // 3) if a result, call instantiateCvTerm with provided fullName and create identity xref
            results.add(instantiateCvTerm(fullName, createXref(entry.getKey(), "unknown"), null));
        }

        return results;
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, String miOntologyName)
            throws BridgeFailedException {
        if (termIdentifiers == null){
            throw new IllegalArgumentException("The term identifiers cannot be null.");
        }

        Collection<T> results = new ArrayList<T>(termIdentifiers.size());
        for (String id : termIdentifiers){
            T element = fetchCvTermByIdentifier(id, miOntologyName);
            if (element != null){
                results.add(element);
            }
        }
        return results;
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        if (termIdentifiers == null){
            throw new IllegalArgumentException("The term identifiers cannot be null.");
        }

        Collection<T> results = new ArrayList<T>(termIdentifiers.size());
        for (String id : termIdentifiers){
            T element = fetchCvTermByIdentifier(id, ontologyDatabase);
            if (element != null){
                results.add(element);
            }
        }
        return results;
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames, String miOntologyName)
            throws BridgeFailedException {
        if (searchNames == null){
            throw new IllegalArgumentException("The term identifiers cannot be null.");
        }

        Collection<T> results = new ArrayList<T>(searchNames.size());
        for (String id : searchNames){
            T element = fetchCvTermByName(id, miOntologyName);
            if (element != null){
                results.add(element);
            }
        }
        return results;
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames)
            throws BridgeFailedException {
        if (searchNames == null){
            throw new IllegalArgumentException("The term identifiers cannot be null.");
        }

        Collection<T> results = new ArrayList<T>(searchNames.size());
        for (String id : searchNames){
            results.addAll(fetchCvTermByName(id));

        }
        return results;
    }

    protected abstract T instantiateCvTerm(String termName, Xref identity, String olsOntologyName);
}
