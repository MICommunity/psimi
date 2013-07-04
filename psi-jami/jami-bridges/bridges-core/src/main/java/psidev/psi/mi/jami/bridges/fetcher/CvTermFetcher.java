package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 */
public interface CvTermFetcher<C extends CvTerm>{

    /**
     * Uses the identifier and the name of the database to search for a complete form of the cvTerm.
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return  A full cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public C getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException;

    /**
     * Uses the identifier and a cvTerm denoting the database to search to fetch a complete from of the term.
     * @param termIdentifier     The identifier for the CvTerm to fetch
     * @param ontologyDatabase  The cvTerm of the ontology to search for.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public C getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException;

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * @param searchName    A full or short name for the term to be searched for.
     * @param ontologyDatabaseName  The ontology to search for the term in.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public C getCvTermByExactName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException;


    public C getCvTermByExactName(String searchName)
            throws BridgeFailedException;

    /**
     * Identifies and initiates a CvTerm using its name.
     * A fuzzy search can also be used by setting @link{useFuzzySearch} to true.
     * This will extend to search possibilities to partial matches if no exact matches can be found.
     * @param searchName
     * @param databaseName
     * @return
     * @throws BridgeFailedException
     */
    public Collection<C> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException;

    public Collection<C> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException;


    //------


    /**
     * Uses the identifier and the name of the database to search for a complete form of the cvTerm.
     * @param identifiers    The identifier for the CvTerm to fetch and the corresponding ontology database name.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<C> getCvTermsByIdentifiersWithOntologyNames(HashMap<String,String> identifiers)
            throws BridgeFailedException;

    /**
     * Uses the identifier and a cvTerm denoting the database to search to fetch a complete from of the term.
     * @param identifiers     The identifier for the CvTerm to fetch and the corresponding cvTerm for the ontology.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<C> getCvTermsByIdentifiersWithOntologyCvTerms(HashMap<String,CvTerm> identifiers)
            throws BridgeFailedException;

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * @param termNames    A full or short name for the term to be searched for and the ontology.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<C> getCvTermsByExactNames(HashMap<String,String> termNames)
            throws BridgeFailedException;


    public Collection<C> getCvTermsByExactNames(Collection<String> searchNames)
            throws BridgeFailedException;

    /**
     * Identifies and initiates a CvTerm using its name.
     * A fuzzy search can also be used by setting @link{useFuzzySearch} to true.
     * This will extend to search possibilities to partial matches if no exact matches can be found.
     * @param searchName
     * @param databaseName
     * @return
     * @throws BridgeFailedException
     */
    /*public Collection<CvTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException;

    public Collection<CvTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException; */

}
