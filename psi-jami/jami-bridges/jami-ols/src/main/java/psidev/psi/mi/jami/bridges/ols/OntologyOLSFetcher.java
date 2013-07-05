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
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class OntologyOLSFetcher
        implements CvTermFetcher<OntologyTerm> , OntologyTermFetcher{

    protected static final Logger log = LoggerFactory.getLogger(OntologyOLSFetcher.class.getName());
    private OlsFetcher olsFetcher;
    protected Query queryService;

    public OntologyOLSFetcher() throws BridgeFailedException {
        try{
            queryService = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            queryService = null;
            throw new BridgeFailedException(e);
        }
        olsFetcher = new OlsFetcher();
    }



    //=======================================
    // Find Relations



    /**
     * Finds all the leaf children and then their parents non-redundantly.
     * <p>
     * Finds the identifiers of all leaf children.
     * Then finds the OntologyTerm for these identifiers and all parents.
     *
     *
     * @param ontologyTerm  A complete ontology term
     * @return
     * @throws BridgeFailedException
     */
    public Collection<OntologyTerm> findAllParentsOfDeepestChildren(OntologyTerm ontologyTerm) throws BridgeFailedException {
        if(ontologyTerm == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(!ontologyTerm.getIdentifiers().iterator().hasNext()) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        Xref identity = ontologyTerm.getIdentifiers().iterator().next();

        return findAllParentsOfDeepestChildren(ontologyTerm , identity);
    }

    public Collection<OntologyTerm> findAllParentsOfDeepestChildren(OntologyTerm ontologyTerm , Xref identifier) throws BridgeFailedException {
        if(ontologyTerm == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(identifier == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        Collection<String> leafIDs = new ArrayList<String>();
        findLeafIds(leafIDs , identifier.getId());

        Collection<OntologyTerm> leafOntologyTerms = new ArrayList<OntologyTerm>();

        for(String leaf : leafIDs){
            leafOntologyTerms.add(getCvTermByIdentifier(leaf , identifier.getDatabase() , false , true));
        }

        return leafOntologyTerms;
    }

    /**
     * Follows the children of a resolved ontology term to find all the leaves.
     */
    public void findLeafIds(Collection<String> leafIDs , String termIdentifier) throws BridgeFailedException {

        Map<String,String> childrenIds = getChildrenIDs(termIdentifier);
        if(childrenIds.isEmpty()) leafIDs.add(termIdentifier);
        else{
            for(String childID : childrenIds.keySet()){
                findLeafIds(leafIDs, childID);
            }
        }
    }

    public Map<String , String> getChildrenIDs(String termIdentifier) throws BridgeFailedException {
        Map<String,String> childrenIDs;
        try{
            childrenIDs = queryService.getTermChildren(termIdentifier, null, 1, null);
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
        return childrenIDs;
    }

    /**
     * Checks the
     * @param termIdentifier
     * @param ontologyDatabase
     * @param ontologyTermNeedingParents
     * @throws BridgeFailedException
     */
    public void findParents(String termIdentifier , CvTerm ontologyDatabase,
                            OntologyTerm ontologyTermNeedingParents )
            throws BridgeFailedException {

        try{
            HashMap<String, String> parents = queryService.getTermParents(
                    termIdentifier , null);
            for(Map.Entry<String,String> entry: parents.entrySet()){
                OntologyTerm parent = getCvTermByIdentifier(entry.getKey() , ontologyDatabase, false , true);
                if(parent == null) throw new IllegalArgumentException("Null parent from known identifier.");
                ontologyTermNeedingParents.getParents().add(parent);
            }
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
    }


    /**
     * Adds all the children to an ontologyTerm.
     * If the term already has children, they will be cleared and reloaded.
     * Children are searched for in the cache first, then the service.
     */
    public void findChildren(String termIdentifier , CvTerm ontologyDatabase,
                             OntologyTerm ontologyTermNeedingChildren )
            throws BridgeFailedException {
        Map<String, String> children = getChildrenIDs(termIdentifier);
        for(Map.Entry<String,String> entry: children.entrySet()){
            OntologyTerm child = getCvTermByIdentifier(entry.getKey() , ontologyDatabase, true , false);
            if(child == null) throw new IllegalArgumentException("Null parent from known identifier.");
            ontologyTermNeedingChildren.getChildren().add(child);
        }
    }

    //=======================================
    // Find with Relations

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database name.
     * If children or parents are selected it will recursively find them until the
     * @param termIdentifier        The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param fetchChildren         Flag to note that children should be found
     * @param fetchParents          Flag to note that parents should be found
     * @return          A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName,
                                              boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabaseName));

        if(ontologyTermFetched == null) return null;
        CvTerm ontologyDatabase = ontologyTermFetched.getIdentifiers().iterator().next().getDatabase();

        if(fetchChildren && fetchParents) findAllParentsOfDeepestChildren(ontologyTermFetched);
        else if(fetchChildren) findChildren(termIdentifier , ontologyDatabase, ontologyTermFetched);
        else if(fetchParents) findParents(termIdentifier , ontologyDatabase, ontologyTermFetched);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase,
                                              boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabase));

        if(fetchChildren && fetchParents) findAllParentsOfDeepestChildren(ontologyTermFetched);
        else if(fetchChildren) findChildren(termIdentifier , ontologyDatabase, ontologyTermFetched);
        else if(fetchParents) findParents(termIdentifier , ontologyDatabase, ontologyTermFetched);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName,
                                             boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByExactName(searchName, ontologyDatabaseName));

        if(ontologyTermFetched == null) return null;

        Xref identifier = ontologyTermFetched.getIdentifiers().iterator().next();

        if(fetchChildren && fetchParents) findAllParentsOfDeepestChildren(ontologyTermFetched);
        else if(fetchChildren) findChildren(identifier.getId() , identifier.getDatabase(), ontologyTermFetched);
        else if(fetchParents)findParents(identifier.getId()  , identifier.getDatabase(), ontologyTermFetched);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByExactName(String searchName, boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByExactName(searchName));

        if(ontologyTermFetched == null) return null;

        Xref identifier = ontologyTermFetched.getIdentifiers().iterator().next();

        if(fetchChildren && fetchParents) findAllParentsOfDeepestChildren(ontologyTermFetched);
        else if(fetchChildren) findChildren(identifier.getId() , identifier.getDatabase(), ontologyTermFetched);
        else if(fetchParents) findParents(identifier.getId()  , identifier.getDatabase(), ontologyTermFetched);

        return ontologyTermFetched;
    }

    //===============================
    // Find without Relations

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database name.
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabaseName, false, false);
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabase , false, false);
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByExactName(searchName, ontologyDatabaseName, false, false);
    }

    public OntologyTerm getCvTermByExactName(String searchName)
            throws BridgeFailedException {
        return getCvTermByExactName(searchName, false, false);
    }

    //================================
    // Multi' Methods with Relations

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


    //================================
    // Multi' Methods without Relations


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


    ///////////////////////////////
    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }





    /**
     * Converts a CvTerm into an ontologyTerm
     * @param cvterm    A cvTerm to convert
     * @return          The ontologyterm of the cvTerm
     */
    public static OntologyTerm getOntologyTermFromCvTerm(CvTerm cvterm){
        if (cvterm == null) return null;
        OntologyTerm ontologyTerm = new DefaultOntologyTerm(cvterm.getShortName());
        ontologyTerm.setFullName( cvterm.getFullName());
        ontologyTerm.getIdentifiers().addAll(cvterm.getIdentifiers());
        ontologyTerm.getSynonyms().addAll(cvterm.getSynonyms());
        return ontologyTerm;
    }

}
