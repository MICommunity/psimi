package uk.ac.ebi.intact.jami.imex.actions.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;
import uk.ac.ebi.intact.jami.bridges.imex.Operation;
import uk.ac.ebi.intact.jami.bridges.imex.extension.ImexPublication;
import uk.ac.ebi.intact.jami.imex.actions.PublicationAdminGroupSynchronizer;

import java.util.List;

/**
 * This class is for synchronizing the admin group of a publication in imex central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/03/12</pre>
 */

public class PublicationAdminGroupSynchronizerImpl implements PublicationAdminGroupSynchronizer{

    private static final Log log = LogFactory.getLog(PublicationAdminGroupSynchronizerImpl.class);
    private ImexCentralClient imexCentral;

    public PublicationAdminGroupSynchronizerImpl(ImexCentralClient client){
        if (client == null){
            throw new IllegalArgumentException("The IMEx central client cannot be null");
        }
        this.imexCentral = client;
    }

    public void synchronizePublicationAdminGroup(Publication publication, ImexPublication imexPublication) throws BridgeFailedException {

        List<Source> sources = imexPublication.getSources();
        String pubId = publication.getPubmedId() != null ? publication.getPubmedId() : publication.getDoi();
        String source = publication.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
        if (pubId == null && !publication.getIdentifiers().isEmpty()){
            Xref id = publication.getXrefs().iterator().next();
            source = id.getDatabase().getShortName();
            pubId = id.getId();
        }

        // add other database admin group if it exists
        Source institution = publication.getSource();
        if (source == null){
            return;
        }

        if (!containsAdminGroup(sources, institution)){
            imexCentral.updatePublicationAdminGroup( pubId, source, Operation.ADD, institution.getShortName().toUpperCase() );
            log.info("Added other publication admin group : " + institution);
        }
    }

    private boolean containsAdminGroup(List<Source> adminGroupList, Source group){

        if (!adminGroupList.isEmpty()){
            for (Source source : adminGroupList){
                if (DefaultCvTermComparator.areEquals(source, group)){
                    return true;
                }
            }
        }

        return false;
    }

    public ImexCentralClient getImexCentralClient() {
        return imexCentral;
    }

}
