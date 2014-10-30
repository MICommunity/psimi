package uk.ac.ebi.intact.jami.imex.actions;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;
import uk.ac.ebi.intact.jami.bridges.imex.extension.ImexPublication;

/**
 * Interface for synchronizing admin group of a publication in IMEx central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/12</pre>
 */

public interface PublicationAdminGroupSynchronizer {

    /**
     * Update IMEx central and synchronize the publication ADMIN group. It can only be applied on publications having a valid pubmed identifier, doi number, jint identifier or IMEx identifier.
     * it will add the institution.
     * admin group
     * @param publication
     * @param imexPublication
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException if does not have a valid ADMIn group and if IMEx central is not available or the publication id is not recognized
     */
    public void synchronizePublicationAdminGroup(Publication publication, ImexPublication imexPublication) throws BridgeFailedException;

    public ImexCentralClient getImexCentralClient();

    public void setImexCentralClient(ImexCentralClient imexClient);
}
