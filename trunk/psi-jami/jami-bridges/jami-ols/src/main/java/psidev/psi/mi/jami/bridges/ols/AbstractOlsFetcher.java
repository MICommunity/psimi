package psidev.psi.mi.jami.bridges.ols;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for OlsFetcher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/08/13</pre>
 */

public abstract class AbstractOlsFetcher<T extends CvTerm> implements CvTermFetcher<T> {

    private Query queryService;
    private Map<String,String> dbMap = new HashMap<String, String>();

    public AbstractOlsFetcher() throws BridgeFailedException {
        try{
            queryService = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            queryService = null;
            throw new BridgeFailedException(e);
        }
        initialiseDbMap();
    }

    private void initialiseDbMap(){
        dbMap.put("psi-mi", "MI");
        dbMap.put("psi-mod", "MOD");
        dbMap.put("psi-par", "PAR");
        dbMap.put("go","GO");
    }

    public T getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {

        // TODO
        // TODO 1) query ols which returns full name.
        // TODO 2) if no results, return null
        // TODO 3) if a result, call instantiateCvTerm with provided fullname and create identity xref
        return instantiateCvTerm();
    }

    public T getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        if(! ontologyDatabase.getMIIdentifier().equals(CvTerm.PSI_MI_MI))
            return null;
        LazyCvTerm cvTerm = new LazyCvTerm(termIdentifier);
        if(cvTerm.getMIIdentifier() == null)
            return null;
        return cvTerm;
    }

    public T getCvTermByExactName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {

        LazyCvTerm cvTerm = new LazyCvTerm(searchName , ontologyDatabaseName, true);
        if(cvTerm.getFullName() == null)
            return null;
        return cvTerm;
    }

    public T getCvTermByExactName(String searchName) throws BridgeFailedException {
        LazyCvTerm cvTerm = new LazyCvTerm(searchName , true);
        if(cvTerm.getMIIdentifier() == null)
            return null;
        return cvTerm;
    }

    public Collection<T> getCvTermByInexactName(String searchName, String databaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermByInexactName(String searchName, CvTerm database) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByExactNames(Collection<String> searchNames) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected abstract T instantiateCvTerm(String termName, Xref identity);
}
