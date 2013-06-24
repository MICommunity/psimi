package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 */
public interface CvTermFetcher{

    public CvTerm getCvTermByIdentifier(String identifier, String ontologyName)
            throws BridgeFailedException;

    /**
     * Identifies and initiates the CvTerm using its identifier.
     * If the identifier can not be accessed, returns null.
     *
     * @param identifier    The identifier to search by
     * @param database
     * @return A fully enriched CvTerm
     * @throws BridgeFailedException    Thrown if there are problems with the connection
     */
    public CvTerm getCvTermByIdentifier(String identifier, CvTerm database)
            throws BridgeFailedException;

    /**
     * Identifies and initiates a CvTerm using its name.
     * The identifier is retrieved using the search by exact name method in the bridge.
     * If the identifier is null, or multiple identifiers are found, throws exceptions.
     *
     * @param searchName
     * @param ontologyName
     * @return
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByExactName(String searchName, String ontologyName)
            throws BridgeFailedException;

    public CvTerm getCvTermByExactName(String searchName)
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
    public Collection<CvTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException;

    public Collection<CvTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException;

}
