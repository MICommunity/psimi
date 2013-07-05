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
 * Finds ontology terms in the Ontology Lookup Service
 * as well as having options to recursively find parents and or children.
 *
 * Implements CvTermFetcher, returning the results as OntologyTerms.
 * Further to this, the OntologyTermFetcher is implemented to include methods for finding children and parents.
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
     * Finds all the leaf children and then their parents using the first identifier of the provided term as a starting point.
     * <p>
     * Finds the identifiers of all leaf children.
     * Then finds the OntologyTerm for these identifiers and all parents.
     * If the provided term has no identifiers, an exception will be thrown.
     *
     * @param ontologyTerm  A complete ontologyTerm to find children for
     * @return      A collection of all the deepest children
     * @throws BridgeFailedException
     */
    public Collection<OntologyTerm> findAllParentsOfDeepestChildren(OntologyTerm ontologyTerm)
            throws BridgeFailedException {

        if(ontologyTerm == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(!ontologyTerm.getIdentifiers().iterator().hasNext()) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        Xref identity = ontologyTerm.getIdentifiers().iterator().next();

        return findAllParentsOfDeepestChildren(ontologyTerm , identity);
    }

    public Collection<OntologyTerm> findAllParentsOfDeepestChildren(OntologyTerm ontologyTerm , Xref identifier)
            throws BridgeFailedException {

        if(ontologyTerm == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(identifier == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        Collection<String> leafIDs = new ArrayList<String>();
        findLeafIds(leafIDs , identifier.getId());

        Collection<OntologyTerm> leafOntologyTerms = new ArrayList<OntologyTerm>();

        for(String leaf : leafIDs){
            leafOntologyTerms.add(getCvTermByIdentifier(leaf , identifier.getDatabase() , 0 , -1));
        }

        return leafOntologyTerms;
    }

    /**
     * Follows the children of a resolved ontology term to find all the leaves.
     */
    private void findLeafIds(Collection<String> leafIDs , String termIdentifier) throws BridgeFailedException {
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


    protected void findParents(OntologyTerm ontologyTermNeedingParents , Xref identity , int depth)
            throws BridgeFailedException {

        if( depth > 0 ) depth --;

        try{
            HashMap<String, String> parents = queryService.getTermParents(
                    identity.getId() , null);
            for(Map.Entry<String,String> entry: parents.entrySet()){
                OntologyTerm parent = getCvTermByIdentifier(entry.getKey() , identity.getDatabase() , 0 , depth);
                if(parent == null) throw new IllegalArgumentException("Null parent from known identifier.");
                ontologyTermNeedingParents.getParents().add(parent);
            }
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
    }

    /**
     * Finds all the children of the OntologyTerm, searching for their children recursively.
     * If the
     * @param ontologyTermNeedingChildren   An ontologyTerm to find the children for
     * @param identity  The identifier to use for this ontology term.
     * @throws BridgeFailedException
     */
    protected void findChildren(OntologyTerm ontologyTermNeedingChildren , Xref identity , int depth)
            throws BridgeFailedException {
        if(!ontologyTermNeedingChildren.getChildren().isEmpty())
            log.warn("Adding to a term which has children already " +
                    "("+ontologyTermNeedingChildren.getChildren()+")");

        if( depth > 0 ) depth --;
        Map<String, String> children = getChildrenIDs(identity.getId());
        for(Map.Entry<String,String> entry: children.entrySet()){
            OntologyTerm child = getCvTermByIdentifier(entry.getKey() , identity.getDatabase() , depth , 0);
            if(child == null) throw new IllegalArgumentException("Null parent from known identifier.");
            ontologyTermNeedingChildren.getChildren().add(child);
        }
    }

    //=======================================
    // Find with Relations

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database name.
     * If children or parents are selected it will recursively find them.
     * If both are selected, all parents of the deepest children will be found.
     *
     * @param termIdentifier        The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                              0 if no children should be found, -1 if the depth should be infinite
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                              0 if no parents should be found, -1 if the depth should be infinite
     * @return          A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName,
                                              int childrenDepth, int parentDepth)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabaseName));

        if(ontologyTermFetched == null) return null;
        Xref identity = ontologyTermFetched.getIdentifiers().iterator().next();

        if(childrenDepth == -1 || 0 < childrenDepth ) findChildren(ontologyTermFetched , identity , childrenDepth);
        if(parentDepth == -1 || 0 < parentDepth ) findParents(ontologyTermFetched , identity , parentDepth);
        if(childrenDepth == -1 && parentDepth == 1) findAllParentsOfDeepestChildren(ontologyTermFetched , identity);

        return ontologyTermFetched;
    }

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database CvTerm.
     * If children or parents are selected it will recursively find them.
     * If both are selected, all parents of the deepest children will be found.
     *
     * @param termIdentifier
     * @param ontologyDatabase
     * @param childrenDepth     Flag to note the depth of children that should be found.
     *                              0 if no children should be found, -1 if the depth should be infinite
     * @param parentDepth       Flag to note the depth of parents that should be found.
     *                              0 if no parents should be found, -1 if the depth should be infinite
     * @return
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase,
                                              int childrenDepth, int parentDepth)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByIdentifier(termIdentifier, ontologyDatabase));

        Xref identity = ontologyTermFetched.getIdentifiers().iterator().next();

        if(childrenDepth == -1 || 0 < childrenDepth ) findChildren(ontologyTermFetched , identity , childrenDepth);
        if(parentDepth == -1 || 0 < parentDepth ) findParents(ontologyTermFetched , identity , parentDepth);
        if(childrenDepth == -1 && parentDepth == 1) findAllParentsOfDeepestChildren(ontologyTermFetched , identity);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName,
                                             int childrenDepth, int parentDepth)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByExactName(searchName, ontologyDatabaseName));

        if(ontologyTermFetched == null) return null;

        Xref identity = ontologyTermFetched.getIdentifiers().iterator().next();

        if(childrenDepth == -1 || 0 < childrenDepth ) findChildren(ontologyTermFetched , identity , childrenDepth);
        if(parentDepth == -1 || 0 < parentDepth ) findParents(ontologyTermFetched , identity , parentDepth);
        if(childrenDepth == -1 && parentDepth == 1) findAllParentsOfDeepestChildren(ontologyTermFetched , identity);

        return ontologyTermFetched;
    }

    public OntologyTerm getCvTermByExactName(String searchName, int childrenDepth, int parentDepth)
            throws BridgeFailedException {

        OntologyTerm ontologyTermFetched = getOntologyTermFromCvTerm(
                olsFetcher.getCvTermByExactName(searchName));

        if(ontologyTermFetched == null) return null;

        Xref identity = ontologyTermFetched.getIdentifiers().iterator().next();

        if(childrenDepth == -1 || 0 < childrenDepth ) findChildren(ontologyTermFetched , identity , childrenDepth);
        if(parentDepth == -1 || 0 < parentDepth ) findParents(ontologyTermFetched , identity , parentDepth);
        if(childrenDepth == -1 && parentDepth == 1) findAllParentsOfDeepestChildren(ontologyTermFetched , identity);

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
        return getCvTermByIdentifier(termIdentifier, ontologyDatabaseName, 0, 0);
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabase , 0, 0);
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByExactName(searchName, ontologyDatabaseName, 0, 0);
    }

    public OntologyTerm getCvTermByExactName(String searchName)
            throws BridgeFailedException {
        return getCvTermByExactName(searchName, 0, 0);
    }

    //================================
    // Multi' Methods with Relations

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> identifiers, String ontologyDatabaseName,
                                                            int childrenDepth, int parentDepth)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> identifiers, CvTerm ontologyDatabase,
                                                            int childrenDepth, int parentDepth)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName,
                                                           int childrenDepth, int parentDepth)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames,
                                                           int childrenDepth, int parentDepth)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    //================================
    // Multi' Methods without Relations


    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> identifiers, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermsByIdentifiers(identifiers, ontologyDatabaseName , 0 , 0 );
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> identifiers, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return getCvTermsByIdentifiers(identifiers, ontologyDatabase , 0 , 0 );
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermsByExactNames(searchNames, ontologyDatabaseName , 0 , 0 );
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames)
            throws BridgeFailedException {
        return getCvTermsByExactNames(searchNames , 0 , 0 );
    }


    ///////////////////////////////
    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException {
        return null;
    }

    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Converts a CvTerm into an ontologyTerm
     * @param cvterm    A cvTerm to convert
     * @return          The ontologyTerm of the cvTerm
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
