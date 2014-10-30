package uk.ac.ebi.intact.jami.imex.actions;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;

/**
 * Interface for registering a publication in IMEx central and collect a publication record in IMEx central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/12</pre>
 */

public interface ImexCentralPublicationRegister {

    /**
     * Retrieve an existing record in IMEx central matching the publication identifier (pubmed, doi, jint or imex).
     * Returns null if we cannot find a record in IMEx central for this publication identifier.
     * @param publicationId
     * @param source
     * @return
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException if it is not possible to retrieve any publications with this identifier
     */
    public Publication getExistingPublicationInImexCentral(String publicationId, String source) throws BridgeFailedException;

    /**
     * Register a publication in IMEx central which is not existing in IMEx central. Can only register publications having valid pubmed id
     * imex ids or doi numbers.
     * @param publication
     * @return  the record in IMEx central which have been created, Null if it could not create a record in IMEx central
     * @throws BridgeFailedException if it is not possible to create a new record for this publication (may already exists, publication identifier not recognized, etc.)
     */
    public Publication registerPublicationInImexCentral(Publication publication) throws BridgeFailedException;

    public ImexCentralClient getImexCentralClient();

    public void setImexCentralClient(ImexCentralClient imexClient);
}
