package uk.ac.ebi.intact.jami.imex.actions;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;
import uk.ac.ebi.intact.jami.bridges.imex.PublicationStatus;
import uk.ac.ebi.intact.jami.bridges.imex.extension.ImexPublication;

/**
 * interface for synchronizing publication status with IMEx central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/12</pre>
 */

public interface PublicationStatusSynchronizer {

    /**
     * Synchronize publication status with IMEx central and update the IMEx central record if necessary.
     * @param intactPublication
     * @param imexPublication
     * @throws edu.ucla.mbi.imex.icentral.ws.ImexCentralFault_Exception is status not recognized or no records could be found or IMEx central is not responding
     */
    public void synchronizePublicationStatusWithImexCentral(Publication intactPublication, ImexPublication imexPublication) throws BridgeFailedException;

    /**
     *
     * @param publication
     * @return the imex central publication status that is matching the status of the publication
     */
    public PublicationStatus getPublicationStatus(Publication publication);

    public ImexCentralClient getImexCentralClient();

    /**
     * Synchronize publication status with IMEx central and update the IMEx central record if necessary.
     * @param publication
     * @param imexPublication
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException is status not recognized or no records could be found or IMEx central is not responding
     */
    public void discardPublicationInImexCentral(Publication publication, ImexPublication imexPublication) throws BridgeFailedException;
}
