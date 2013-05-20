package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 16:34
 */
public interface CvTermFetcher {

    /**
     * Identifies and initiates the CvTerm using its identifier.
     * If the identifier can not be accessed, returns null.
     *
     * @param identifier    The identifier to search by
     * @param ontology
     * @return A fully enriched CvTerm
     * @throws BridgeFailedException    Thrown if there are problems with the connection
     */
    public CvTerm getCvTermByID(String identifier, String ontology)
            throws BridgeFailedException;

    /**
     * Identifies and initiates a CvTerm using its name.
     * The identifier is retrieved using the search by name method in the bridge.
     * If the identifier is null, does not begin with its database identifier (eg, MI:999)
     * or multiple identifiers are found, this method returns null.
     *
     * @param name      A name to search for.
     * @param ontology    The identifier for the database to search. Can be null.
     * @return  A cvTerm found, using the name.
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByName(String name, String ontology)
            throws BridgeFailedException;

}
