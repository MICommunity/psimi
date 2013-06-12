package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:50
 */
public interface ProteinFetcher {

    /**
     * Takes a string identifier and forms an appropriate search.
     * It then returns a collection of proteins which match that identifier.
     * Returns null if the search was completed but found no results.
     *
     * @param identifier    The identifier to search for.
     * @return  The proteins which match the search term.
     * @throws BridgeFailedException    A problem has been encountered when contacting the service
     * @throws BadSearchTermException   A problem has been encountered with the term which is being used to search
     * @throws BadResultException       A problem was encountered with the results returned by the service
     */
    public Collection<Protein> getProteinsByIdentifier(String identifier)
            throws BridgeFailedException, BadResultException, BadSearchTermException;

    public String getService();

}
