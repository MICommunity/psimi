package psidev.psi.mi.jami.bridges.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultOntologyTerm;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class OntologyOLSFetcher
        implements CvTermFetcher<OntologyTerm> , OntologyTermFetcher{

    protected final Logger log = LoggerFactory.getLogger(OntologyOLSFetcher.class.getName());
    private OlsFetcher olsFetcher;
    private Query queryService;

    public OntologyOLSFetcher() throws BridgeFailedException {
        try{
            queryService = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            queryService = null;
            throw new BridgeFailedException(e);
        }
        olsFetcher = new OlsFetcher();
    }



    public OntologyTerm findDirectChildren(String termIdentifier , CvTerm ontologyDatabase, OntologyTerm ontologyTermFetched)
            throws BridgeFailedException {

        try{
            HashMap<String, String> children = queryService.getTermChildren(
                    termIdentifier, null, 1, null);

            //log.info("term "+ontologyTermFetched.toString()+" found children "+children.size());
            for(Map.Entry<String,String> entry: children.entrySet()){
                OntologyTerm child = getCvTermByIdentifier(entry.getKey() , ontologyDatabase , true, false);
                if(child == null) throw new IllegalArgumentException("Null parents from known identifier.");
                ontologyTermFetched.getChildren().add(child);
            }
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
        return ontologyTermFetched;
    }

    public OntologyTerm findDirectParents(String termIdentifier , CvTerm ontologyDatabase, OntologyTerm ontologyTermFetched)
            throws BridgeFailedException {
        try{
            HashMap<String, String> parents = queryService.getTermParents(
                    termIdentifier , null);

            //log.info("term "+ontologyTermFetched.toString()+" found parents "+parents.size());

            for(Map.Entry<String,String> entry: parents.entrySet()){
                OntologyTerm parent = getCvTermByIdentifier(entry.getKey() , ontologyDatabase , false, true);
                if(parent == null) throw new IllegalArgumentException("Null parents from known identifier.");
                ontologyTermFetched.getParents().add(parent);
            }
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
        return ontologyTermFetched;
    }


    /**
     *
     * @param termIdentifier
     * @param ontologyDatabaseName
     * @param fetchChildren
     * @param fetchParents
     * @return
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName,
                                              boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabaseName));

        if(ontologyTermFetched == null) return null;

        CvTerm ontologyDatabase = ontologyTermFetched.getIdentifiers().iterator().next().getDatabase();

        if(fetchChildren) findDirectChildren(termIdentifier , ontologyDatabase, ontologyTermFetched);

        if(fetchParents)findDirectParents(termIdentifier , ontologyDatabase, ontologyTermFetched);

        return ontologyTermFetched;
    }

    /**
     * Finds a cvTerm
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabaseName, false, false);
    }


    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase,
                                              boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {
        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabase));

        if(ontologyTermFetched == null) return null;

        if(fetchChildren) findDirectChildren(termIdentifier , ontologyDatabase, ontologyTermFetched);

        if(fetchParents)findDirectParents(termIdentifier , ontologyDatabase, ontologyTermFetched);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabase , false, false);
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName,
                                             boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByExactName(searchName, ontologyDatabaseName));

        if(ontologyTermFetched == null) return null;

        Xref identifier = ontologyTermFetched.getIdentifiers().iterator().next();

        if(fetchChildren) findDirectChildren(identifier.getId() , identifier.getDatabase(), ontologyTermFetched);

        if(fetchParents)findDirectParents(identifier.getId()  , identifier.getDatabase(), ontologyTermFetched);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByExactName(searchName, ontologyDatabaseName, false, false);
    }

    public OntologyTerm getCvTermByExactName(String searchName, boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByExactName(searchName));

        if(ontologyTermFetched == null) return null;

        Xref identifier = ontologyTermFetched.getIdentifiers().iterator().next();

        if(fetchChildren) findDirectChildren(identifier.getId() , identifier.getDatabase(), ontologyTermFetched);

        if(fetchParents)findDirectParents(identifier.getId()  , identifier.getDatabase(), ontologyTermFetched);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByExactName(String searchName)
            throws BridgeFailedException {

        return getCvTermByExactName(searchName, false, false);
    }







    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }




    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyNames(HashMap<String, String> identifiers,
                                                                             boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyCvTerms(HashMap<String, CvTerm> identifiers,
                                                                               boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(HashMap<String, String> termNames,
                                                           boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames,
                                                           boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }







    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyNames(HashMap<String, String> identifiers)
            throws BridgeFailedException {
        return null;
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyCvTerms(HashMap<String, CvTerm> identifiers)
            throws BridgeFailedException {
        return null;
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(HashMap<String, String> termNames)
            throws BridgeFailedException {
        return null;
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames)
            throws BridgeFailedException {
        return null;
    }

    public OntologyTerm getOntologyTermFromCvTerm(CvTerm cvterm){
        if (cvterm == null) return null;
        OntologyTerm ontologyTerm = new DefaultOntologyTerm(cvterm.getShortName());
        ontologyTerm.setFullName( cvterm.getFullName());
        ontologyTerm.getIdentifiers().addAll(cvterm.getIdentifiers());
        ontologyTerm.getSynonyms().addAll(cvterm.getSynonyms());
        return ontologyTerm;

    }

}
