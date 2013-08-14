package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;

import java.util.Collection;

/**
 * The interface for fetching a publication record.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 29/07/13
 */
public interface PublicationFetcher {

    /**
     * Uses the PubMed identifier to search for a publication and return a completed record.
     * @param identifier    The identifier of the publication to search for.
     * @return              A completed record for the publication or null if no publication could be found.
     * @throws BridgeFailedException
     */
    public Publication getPublicationByIdentifier(String identifier) throws BridgeFailedException;

}
