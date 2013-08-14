package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collection;

/**
 * Finds ontology terms in the Ontology Lookup Service
 * as well as having options to recursively find parents and or children.
 *
 * Extends CvTermFetcher with OntologyTerms.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public interface OntologyTermFetcher extends CvTermFetcher<OntologyTerm>{

    //===========

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database name,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param termIdentifier        The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database cvTerm,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param termIdentifier        The identifier for the CvTerm to fetch.
     * @param ontologyDatabase      The ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    /**
     * Finds an ontologyTerm using an exact name and an ontology database name,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param searchName            The short or full name of the term
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    /**
     * Finds an ontologyTerm using the exact name,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * If the term identifier which is found is not MI or MOD, the method will return null.
     *
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param searchName        The identifier for the CvTerm to fetch.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByExactName(String searchName , int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    //===========


    /**
     * Finds ontologyTerms using termIdentifiers and an ontology database name,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param termIdentifiers       The identifiers for the CvTerms to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    /**
     * Finds ontologyTerms using termIdentifiers and an ontology database,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param termIdentifiers       The identifiers for the CvTerms to fetch.
     * @param ontologyDatabase      The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    /**
     * Finds ontologyTerms using full or short names and an ontology database name,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param searchNames           The identifiers for the CvTerms to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName,  int childrenDepth, int parentDepth)
            throws BridgeFailedException;



    /**
     * Finds ontologyTerms using full or short names and an ontology database name,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * If the term identifier which is found is not MI or MOD, the method will return null.
     *
     * For the childrenDepth and parentDepth, a value of -1 will add children and parents recursively until an edge is reached.
     * A value of 0 will not add any children or parents. Any other positive number will add that many levels of children and parents.
     * If both the childrenDepth and parentDepth are flagged at -1, the children will be found first,
     * and then all the parents of those children will be found, regardless of whether the original search term was on that branch.
     *
     * @param searchNames           The identifiers for the CvTerms to fetch.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                                  0 if no children should be found, -1 if the depth will be infinite.
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                                  0 if no parents should be found, -1 if the depth will be infinite.
     * @return                      A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames,  int childrenDepth, int parentDepth)
            throws BridgeFailedException;
}
