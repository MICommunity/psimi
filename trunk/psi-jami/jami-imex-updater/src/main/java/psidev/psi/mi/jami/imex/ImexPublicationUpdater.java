package psidev.psi.mi.jami.imex;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.ImexCentralClient;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.full.FullPublicationEnricher;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.imex.actions.PublicationAdminGroupSynchronizer;
import psidev.psi.mi.jami.imex.actions.PublicationIdentifierSynchronizer;
import psidev.psi.mi.jami.imex.actions.PublicationStatusSynchronizer;
import psidev.psi.mi.jami.imex.listener.PublicationImexEnricherListener;

import java.util.Collection;
import java.util.Iterator;

/**
 * This enricher will update a publication having IMEx id and synchronize it with IMEx central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/14</pre>
 */

public class ImexPublicationUpdater extends FullPublicationEnricher{

    private PublicationAdminGroupSynchronizer adminGroupSynchronizer;
    private PublicationStatusSynchronizer statusSynchronizer;
    private PublicationIdentifierSynchronizer identifierSynchronizer;

    public static String IMEX_SECONDARY_MI = "MI:0952";
    public static String IMEX_SECONDARY = "imex secondary";
    public static String FULL_COVERAGE_MI = "MI:0957";
    public static String FULL_COVERAGE = "full coverage";
    public static String IMEX_CURATION_MI = "MI:0959";
    public static String IMEX_CURATION = "imex curation";
    public static String CURATION_DEPTH_MI = "MI:0955";
    public static String CURATION_DEPTH = "curation depth";
    public static String FULL_COVERAGE_TEXT = "Only protein-protein interactions";

    public ImexPublicationUpdater(ImexCentralClient fetcher) {
        super(fetcher);
    }

    @Override
    protected void processCurationDepth(Publication publicationToEnrich, Publication fetched) {
        if (publicationToEnrich.getCurationDepth() != CurationDepth.IMEx
                && publicationToEnrich.getImexId() != null){
            CurationDepth oldDepth = publicationToEnrich.getCurationDepth();
            publicationToEnrich.setCurationDepth(CurationDepth.IMEx);

            if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener)getPublicationEnricherListener()).onCurationDepthUpdated(publicationToEnrich, oldDepth);
            }
        }
        if (publicationToEnrich.getSource() != null && getAdminGroupSynchronizer() != null && fetched instanceof ImexPublication){
            try {
                getAdminGroupSynchronizer().synchronizePublicationAdminGroup(publicationToEnrich, (ImexPublication)fetched);
                if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                    ((PublicationImexEnricherListener)getPublicationEnricherListener()).onImexAdminGroupUpdated(publicationToEnrich, fetched.getSource());
                }
            } catch (BridgeFailedException e) {
                getPublicationEnricherListener().onEnrichmentError(publicationToEnrich, "Cannot update the admin group of publication "+publicationToEnrich+" in IMEx central", e);
            }
        }
    }

    @Override
    protected void processReleasedDate(Publication publicationToEnrich, Publication fetched) {
        if (publicationToEnrich.getReleasedDate() != null && getStatusSynchronizer() != null && fetched instanceof ImexPublication){
            try {
                getStatusSynchronizer().synchronizePublicationStatusWithImexCentral(publicationToEnrich, (ImexPublication)fetched);
                if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                    ((PublicationImexEnricherListener)getPublicationEnricherListener()).onImexStatusUpdated(publicationToEnrich,
                            ((ImexPublication) fetched).getStatus());
                }
            } catch (BridgeFailedException e) {
                getPublicationEnricherListener().onEnrichmentError(publicationToEnrich, "Cannot update the status of publication " + publicationToEnrich + " in IMEx central", e);
            }
        }
    }

    @Override
    protected void processXrefs(Publication publicationToEnrich, Publication fetched) {
        // nothing to do here
    }

    @Override
    protected void processAnnotations(Publication publicationToEnrich, Publication fetched) {
        // update publication annotations if necessary
        boolean hasImexCuration = false;
        boolean hasFullCoverage = false;
        boolean hasCurationDepth = false;
        Iterator<Annotation> annotIterator = publicationToEnrich.getAnnotations().iterator();
        while (annotIterator.hasNext()){
            Annotation ann = annotIterator.next();

            // full coverage annot
            if (AnnotationUtils.doesAnnotationHaveTopic(ann, FULL_COVERAGE_MI, FULL_COVERAGE)){

                // annotation is a duplicate, we delete it
                if (hasFullCoverage){
                    annotIterator.remove();
                    getPublicationEnricherListener().onRemovedAnnotation(publicationToEnrich, ann);
                }
                // first time we see a full coverage, if not the same text, we update it
                else if (ann.getValue() == null || !ann.getValue().equals(FULL_COVERAGE_TEXT)){
                    hasFullCoverage = true;

                    ann.setValue(FULL_COVERAGE_TEXT);
                    getPublicationEnricherListener().onAddedAnnotation(publicationToEnrich, ann);
                }
                // we found full coverage with same annotation text
                else {
                    hasFullCoverage = true;
                }
            }
            // imex curation annot
            else if (AnnotationUtils.doesAnnotationHaveTopic(ann, IMEX_CURATION_MI, IMEX_CURATION)){

                // annotation is a duplicate, we delete it
                if (hasImexCuration){
                    annotIterator.remove();
                    getPublicationEnricherListener().onRemovedAnnotation(publicationToEnrich, ann);
                }
                // we found imex curation
                else {
                    hasImexCuration = true;
                }
            }
        }

        if (!hasFullCoverage){
            Annotation ann = AnnotationUtils.createAnnotation(FULL_COVERAGE, FULL_COVERAGE_MI, FULL_COVERAGE_TEXT);
            publicationToEnrich.getAnnotations().add(ann);
            getPublicationEnricherListener().onAddedAnnotation(publicationToEnrich, ann);

        }

        if (!hasImexCuration){
            Annotation ann = AnnotationUtils.createAnnotation(IMEX_CURATION, IMEX_CURATION_MI, IMEX_CURATION);
            publicationToEnrich.getAnnotations().add(ann);
            getPublicationEnricherListener().onAddedAnnotation(publicationToEnrich, ann);
        }
    }

    @Override
    protected void processJournal(Publication publicationToEnrich, Publication fetched) {
        // nothing to do
    }

    @Override
    protected void processPublicationTitle(Publication publicationToEnrich, Publication fetched) {
        // nothing to do
    }

    @Override
    protected void processPublicationDate(Publication publicationToEnrich, Publication fetched) {
        // nothing to do
    }

    @Override
    protected void processAuthors(Publication publicationToEnrich, Publication fetched) {
        // nothing to do
    }

    @Override
    protected void processIdentifiers(Publication publicationToEnrich, Publication fetched) {
        String pubId = publicationToEnrich.getPubmedId() != null ? publicationToEnrich.getPubmedId() : publicationToEnrich.getDoi();
        String source = publicationToEnrich.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
        if (pubId == null && !publicationToEnrich.getIdentifiers().isEmpty()) {
            Xref id = publicationToEnrich.getXrefs().iterator().next();
            source = id.getDatabase().getShortName();
            pubId = id.getId();
        }

        // if the publication identifier is not in sync with IMEx central,
        // try to synchronize it first but does not update the publication
        try {
            if (fetched instanceof ImexPublication &&
                    !getIdentifierSynchronizer().isPublicationIdentifierInSyncWithImexCentral(pubId, source, (ImexPublication)fetched)){
                getIdentifierSynchronizer().synchronizePublicationIdentifier(publicationToEnrich, fetched);
                if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                    ((PublicationImexEnricherListener)getPublicationEnricherListener()).onImexPublicationIdentifierSynchronized(publicationToEnrich);
                }
            }
        } catch (BridgeFailedException e) {
            getPublicationEnricherListener().onEnrichmentError(publicationToEnrich, "Cannot update the identifier of publication " + publicationToEnrich + " in IMEx central", e);
        } catch (EnricherException e) {
            getPublicationEnricherListener().onEnrichmentError(publicationToEnrich, "Cannot update the identifier of publication " + publicationToEnrich + " in IMEx central", e);
        }
    }


    @Override
    public ImexPublication find(Publication publicationToEnrich) throws EnricherException {
        String imex = collectAndCleanUpImexPrimaryReferenceFrom(publicationToEnrich);

        if(imex != null){
            return fetchImexPublication(imex, Xref.IMEX);
        }
        else{
            if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener)getPublicationEnricherListener()).onMissingImexId(publicationToEnrich);
            }
            else{
                getPublicationEnricherListener().onEnrichmentError(publicationToEnrich, "The publication does not have a single IMEx identifier and cannot be updated", null);
            }
        }

        return null;
    }

    protected String collectAndCleanUpImexPrimaryReferenceFrom(Publication publication) {

        Collection<Xref> imexPrimaryRefs = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(publication.getXrefs(),
                Xref.IMEX_MI,
                Xref.IMEX,
                Xref.IMEX_PRIMARY_MI,
                Xref.IMEX_PRIMARY);

        if (imexPrimaryRefs.isEmpty()){
            return null;
        }
        else if (imexPrimaryRefs.size() == 1){
            return imexPrimaryRefs.iterator().next().getId();
        }
        // we may have conflict of imex-primary
        else{
            // collect all imex references having same id
            Collection<Xref> imexRefs = XrefUtils.collectAllXrefsHavingDatabaseAndId(imexPrimaryRefs, Xref.IMEX_MI, Xref.IMEX, publication.getImexId());

            // remove duplicated references
            if (imexRefs.size() == imexPrimaryRefs.size()){
                Iterator<Xref> refIterator = imexRefs.iterator();
                // keep first
                Xref mainRef = refIterator.next();
                while(refIterator.hasNext()){
                    publication.getXrefs().remove(refIterator.next());
                }

                return mainRef.getId();
            }
            // we have imex conflicting xrefs
            else if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener)getPublicationEnricherListener()).onImexIdConflicts(publication, imexPrimaryRefs);
            }
            else{
                getPublicationEnricherListener().onEnrichmentError(publication, "The publication has several IMEx identifiers and cannot be updated", null);
            }
        }

        return null;
    }

    public PublicationAdminGroupSynchronizer getAdminGroupSynchronizer() {
        return adminGroupSynchronizer;
    }

    public void setAdminGroupSynchronizer(PublicationAdminGroupSynchronizer adminGroupSynchronizer) {
        this.adminGroupSynchronizer = adminGroupSynchronizer;
    }

    public PublicationStatusSynchronizer getStatusSynchronizer() {
        return statusSynchronizer;
    }

    public void setStatusSynchronizer(PublicationStatusSynchronizer statusSynchronizer) {
        this.statusSynchronizer = statusSynchronizer;
    }

    public PublicationIdentifierSynchronizer getIdentifierSynchronizer() {
        return identifierSynchronizer;
    }

    public void setIdentifierSynchronizer(PublicationIdentifierSynchronizer identifierSynchronizer) {
        this.identifierSynchronizer = identifierSynchronizer;
    }

    @Override
    protected void onEnrichedVersionNotFound(Publication publicationToEnrich) throws EnricherException {
        if(getPublicationEnricherListener() instanceof PublicationImexEnricherListener)
            ((PublicationImexEnricherListener)getPublicationEnricherListener()).onImexIdNotRecognized(publicationToEnrich, publicationToEnrich.getImexId());
        else{
            super.onEnrichedVersionNotFound(publicationToEnrich);
        }
    }

    private ImexPublication fetchImexPublication(String id, String db) throws EnricherException {
        try {
            return (ImexPublication)getPublicationFetcher().fetchByIdentifier(id, db);
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < getRetryCount()){
                try {
                    return (ImexPublication)getPublicationFetcher().fetchByIdentifier(id, db);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ getRetryCount() +" times to fetch the Publication but cannot connect to the fetcher.", e);
        }
    }
}


