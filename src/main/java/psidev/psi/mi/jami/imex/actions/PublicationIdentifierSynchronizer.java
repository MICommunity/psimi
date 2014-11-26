package psidev.psi.mi.jami.imex.actions;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.ImexCentralClient;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Publication;

/**
 * Interface for synchronizing publication identifiers with IMEx central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/12</pre>
 */

public interface PublicationIdentifierSynchronizer {
    public static String INTERNAL = "jint";

    /**
     * It will look at all identifiers attached to the record in IMEx central and see if the publication identifier is also attached to the
     * IMEx record
     * @param pubId
     * @param imexPublication
     * @return true if the publication identifier is in IMEx central, false otherwise
     */
    public boolean isPublicationIdentifierInSyncWithImexCentral(String pubId, String source, ImexPublication imexPublication) throws BridgeFailedException;

    /**
     * Update the IMEx record in case intact publication has a valid pubmed, unassigned or doi identifier that is not in IMEx central.
     * It will not update the publication and it is possible that it will not update the IMEx record if the pubmed or doi identifier is different in IMEx central.
     * The publication record in IMEx central should have a valid IMEx id.
     * @param publication
     * @param imexPublication
     * @throws psidev.psi.mi.jami.enricher.exception.EnricherException : if the pubmed/doi identifier is different from the one in IMEx central
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException : if record not found, idnetifier s not recognized or IMEx central is not responding
     */
    public void synchronizePublicationIdentifier(Publication publication, Publication imexPublication) throws EnricherException, BridgeFailedException;

    public ImexCentralClient getImexCentralClient();
}
