package psidev.psi.mi.jami.imex.actions.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.ImexCentralClient;
import psidev.psi.mi.jami.bridges.imex.PublicationStatus;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.imex.actions.PublicationStatusSynchronizer;

/**
 * It updates and synchronize the publications status if it is released (has a released date)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/03/12</pre>
 */

public class PublicationStatusSynchronizerImpl implements PublicationStatusSynchronizer{

    private static final Log log = LogFactory.getLog(PublicationStatusSynchronizerImpl.class);
    private ImexCentralClient imexCentral;

    public PublicationStatusSynchronizerImpl(ImexCentralClient client){
        if (client == null){
            throw new IllegalArgumentException("The IMEx central client cannot be null");
        }
        this.imexCentral = client;
    }

    public void synchronizePublicationStatusWithImexCentral(Publication publication, ImexPublication imexPublication) throws BridgeFailedException {
        PublicationStatus imexStatus = imexPublication.getStatus();

        PublicationStatus newStatus = getPublicationStatus(publication);
        if (newStatus == null){
           return;
        }
        if (imexStatus == null || (imexStatus != null && !newStatus.equals(imexStatus))){
            String pubId = publication.getPubmedId() != null ? publication.getPubmedId() : publication.getDoi();
            String source = publication.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
            if (pubId == null && !publication.getIdentifiers().isEmpty()){
                Xref id = publication.getXrefs().iterator().next();
                source = id.getDatabase().getShortName();
                pubId = id.getId();
            }
            imexCentral.updatePublicationStatus( pubId, source, newStatus );
            log.info("Updating imex status to " + newStatus.toString());
        }
    }

    public PublicationStatus getPublicationStatus( Publication publication ) {
        // IMEx central has currently the following publication states available:
        // NEW / RESERVED / INPROGRESS / RELEASED / DISCARDED / INCOMPLETE / PROCESSED

        if (publication.getReleasedDate() != null){
             return PublicationStatus.RELEASED;
        }
        return null;
    }

    public void discardPublicationInImexCentral(Publication publication, ImexPublication imexPublication) throws BridgeFailedException {
        PublicationStatus imexStatus = imexPublication.getStatus();

        if (imexStatus == null || (imexStatus != null && !PublicationStatus.DISCARDED.equals(imexStatus))){
            String pubId = publication.getPubmedId() != null ? publication.getPubmedId() : publication.getDoi();
            String source = publication.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
            if (pubId == null && !publication.getIdentifiers().isEmpty()){
                Xref id = publication.getXrefs().iterator().next();
                source = id.getDatabase().getShortName();
                pubId = id.getId();
            }
            imexCentral.updatePublicationStatus( pubId, source, PublicationStatus.DISCARDED );
            log.info("Updating imex status to DISCARDED");
        }
    }

    public ImexCentralClient getImexCentralClient() {
        return imexCentral;
    }

}
