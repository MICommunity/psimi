package uk.ac.ebi.intact.jami.imex.actions.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;
import uk.ac.ebi.intact.jami.bridges.imex.extension.ImexPublication;
import uk.ac.ebi.intact.jami.imex.actions.PublicationIdentifierSynchronizer;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Class which update identifiers in IMEx central if a submitted publication has been published in PubMed
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/12</pre>
 */

public class PublicationIdentifierSynchronizerImpl implements PublicationIdentifierSynchronizer {
    private static final Log log = LogFactory.getLog(PublicationIdentifierSynchronizerImpl.class);

    private ImexCentralClient imexCentral;

    public PublicationIdentifierSynchronizerImpl(ImexCentralClient client){
        if (client == null){
            throw new IllegalArgumentException("The IMEx central client cannot be null");
        }
        this.imexCentral = client;
    }

    public boolean isPublicationIdentifierInSyncWithImexCentral(String pubId, String source, ImexPublication imexPublication) throws BridgeFailedException {

        Collection<Xref> imexIdentifiers = imexPublication.getIdentifiers();

        // no existing identifiers in IMEx central for this record.
        if (imexIdentifiers.isEmpty()){

            ImexPublication existingPub = (ImexPublication)imexCentral.fetchByIdentifier(pubId, source);

            // the publication found in IMEx central is the same as the one found in IntAct so the identifiers are in sync
            return areIdenticalPublications(imexPublication, existingPub);
        }
        // existing identifiers
        else {
            // we have a pmid in IMEx central so we should have a pmid in IntAct
            for (Xref id : imexIdentifiers){
                if (XrefUtils.doesXrefHaveDatabaseAndId(id, null, source, pubId)){
                    return true;
                }
            }
        }

        // check if identifier is not in IMEx central
        ImexPublication existingPub = (ImexPublication)imexCentral.fetchByIdentifier(pubId, source);

        // the publication found in IMEx central is the same as the one found in IntAct so the identifiers are in sync
        return areIdenticalPublications(imexPublication, existingPub);
    }

    public void synchronizePublicationIdentifier(Publication publication, Publication imexPublication) throws EnricherException, BridgeFailedException {

        String pubId = publication.getPubmedId() != null ? publication.getPubmedId() : publication.getDoi();
        String source = publication.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
        if (pubId == null && !publication.getIdentifiers().isEmpty()){
            Xref id = publication.getXrefs().iterator().next();
            source = INTERNAL;
            pubId = id.getId();
        }

        // if the publication id is not in IMEx central, we need to synchronize
        Collection<Xref> imexIdentifiers = imexPublication.getIdentifiers();

        // no existing (pubmed) identifiers in IMEx central for this record
        if (imexIdentifiers == null || (imexIdentifiers != null && imexIdentifiers.isEmpty())){

            // the identifier in Intact is not registered in IMEx central (check done by isPublicationIdentifierInSyncWithImexCentral) and the record has a valid IMEx id so we can update the record
            if (imexPublication.getImexId() != null){
                imexPublication = imexCentral.updatePublicationIdentifier(imexPublication.getImexId(), Xref.IMEX, pubId, source);
                log.info("Updated identifier " + pubId + " for the IMEx accession " + imexPublication.getImexId());
            }
            log.error("Cannot updated identifier " + pubId + " because no valid IMEx accession");
        }
        // existing identifiers
        else {
            String pubmed = imexPublication.getPubmedId();
            String doi = imexPublication.getDoi();
            String internal = imexIdentifiers.isEmpty() ? null : imexIdentifiers.iterator().next().getId();

            // boolean to know if publication id is a valid pubmed id
            boolean hasPubmed = Pattern.matches(ImexCentralClient.pubmed_regexp.toString(), pubId);

            // there is a pubmed identifier in IMEx central and we have a pubmed identifier
            if (pubmed != null && pubmed.length() > 0 && hasPubmed){
                // pubmed id not in sync with IMEx central, we have a conflict
                if (!pubId.equalsIgnoreCase(pubmed)){
                    throw new EnricherException("pubmed id conflict with IMEx central : imex = " + imexPublication.getImexId() + ", current pubmed = " + pubId + ", imex pubmed id = " + pubmed);
                }
            }
            // there is a DOI identifier in IMEx central and we have a potential DOI identifier
            else if (doi != null && doi.length() > 0 && !hasPubmed && publication.getDoi() != null){
                // doi id not in sync with IMEx central, we have a conflict
                if (!publication.getDoi().equalsIgnoreCase(doi)){
                    throw new EnricherException("DOI conflict with IMEx central : imex = " + imexPublication.getImexId() + ", current doi = " + pubId + ", imex doi = " + pubmed);
                }
            }
            // there is an internal identifier in IMEx central and we have a unassigned identifier
            else if (internal != null && internal.length() > 0){
                // internal id not in sync with IMEx central, we have a conflict
                if (!pubId.equalsIgnoreCase(internal)){
                    throw new EnricherException("Internal identifier conflict with IMEx central : imex = " + imexPublication.getImexId() + ", current internal identifier = " + pubId + ", imex internal identifier = " + pubmed);
                }
            }
            // the IMEx record does not have any pubmed id but intact does have a valid Pubmed id, we need to update the record
            else if ((pubmed == null || (pubmed != null && pubmed.length() == 0)) && hasPubmed){
                imexPublication = imexCentral.updatePublicationIdentifier(imexPublication.getImexId(), Xref.IMEX, pubId, source);
                log.info("Updated identifier " + pubId + " for the IMEx accession " + imexPublication.getImexId());
            }
            // the imex record does not have any DOI numbers but we do have a publication id which is not pubmed or unassigned, we need to update the record
            else if ((doi == null || (doi != null && doi.length() == 0)) && !hasPubmed && publication.getDoi() != null){
                imexPublication = imexCentral.updatePublicationIdentifier(imexPublication.getImexId(), Xref.IMEX, pubId, source);
                log.info("Updated identifier " + pubId + " for the IMEx accession " + imexPublication.getImexId());
            }
            // the imex record does not have any internal identifiers but we do have a publication id which is unassigned, we need to update the record
            else if ((internal == null || (internal != null && internal.length() == 0))){
                imexPublication = imexCentral.updatePublicationIdentifier(imexPublication.getImexId(), Xref.IMEX, pubId, source);
                log.info("Updated identifier " + pubId + " for the IMEx accession " + imexPublication.getImexId());
            }

            // pubmed in imex central, this is an error
            if (pubmed != null && pubmed.length() > 0 && !hasPubmed){
                throw new EnricherException("Publication has a valid pubmed id in IMEx central but not in the current publication : imex = " + imexPublication.getImexId() + ", current publication identifier = " + pubId + ", imex pubmed id = " + pubmed);
            }
        }
    }

    public boolean areIdenticalPublications(ImexPublication p1, ImexPublication p2){

        if ((p1 == null && p2 != null) || (p1 != null && p2 == null)){
            return false;
        }
        else if (p1 != null && p2 != null){

            if (p1.getImexId() != null && !p1.getImexId().equals(p2.getImexId())){
                return false;
            }
            else if (p2.getImexId() != null ){
                return false;
            }

            if (!CollectionUtils.isEqualCollection(p1.getAuthors(), p2.getAuthors())){
                return false;
            }

            if (!CollectionUtils.isEqualCollection(p1.getIdentifiers(), p2.getIdentifiers())){
                return false;
            }

            if (p1.getOwner() != null && !p1.getOwner().equals(p2.getOwner())){
                return false;
            }
            else if (p2.getOwner() != null ){
                return false;
            }

            if (p1.getPaperAbstract() != null && !p1.getPaperAbstract().equals(p2.getPaperAbstract())){
                return false;
            }
            else if (p2.getPaperAbstract() != null ){
                return false;
            }

            if (p1.getStatus() != null && !p1.getStatus().equals(p2.getStatus())){
                return false;
            }
            else if (p2.getStatus() != null ){
                return false;
            }

            if (p1.getTitle() != null && !p1.getTitle().equals(p2.getTitle())){
                return false;
            }
            else if (p2.getTitle() != null ){
                return false;
            }
        }

        return true;
    }

    public ImexCentralClient getImexCentralClient() {
        return imexCentral;
    }
}
