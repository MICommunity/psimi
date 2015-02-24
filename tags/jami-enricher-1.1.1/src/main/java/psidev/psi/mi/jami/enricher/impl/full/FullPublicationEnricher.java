package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalPublicationEnricher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;

/**
 * Provides full enrichment of Publication.
 *
 * - enrich minimal properties as described in MinimalPublicationEnricher
 * - enrich publication title. It will only enrich the title if it is not already set in the publication to enrich. It will not
 * override any existing publication title
 * - enrich publication journal. It will only enrich the journal if it is not already set in the publication to enrich. It will not
 * override any existing journal
 * - enrich xrefs (imex, etc.) of a publication. It will use DefaultXrefComparator to compare identifiers and add missing xrefs without
 * removing any existing xrefs.
 * - enrich annotations of a publication. It will use DefaultAnnotationComparator to compare annotations and add missing annotations without
 * removing any existing annotations.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class FullPublicationEnricher extends MinimalPublicationEnricher {

    public FullPublicationEnricher(PublicationFetcher fetcher) {
        super(fetcher);
    }

    /**
     * The strategy for the enrichment of the publication.
     * This methods can be overwritten to change the behaviour of the enrichment.
     * @param publicationToEnrich   The publication which is being enriched.
     */
    @Override
    protected void processOtherProperties(Publication publicationToEnrich, Publication fetched) throws EnricherException {

        // == TITLE ==================================================================
        processPublicationTitle(publicationToEnrich, fetched);

        // == JOURNAL ===================================================================
        processJournal(publicationToEnrich, fetched);

        // == RELEASE date ===================================================================
        processReleasedDate(publicationToEnrich, fetched);

        // == CURATION depth ===================================================================
        processCurationDepth(publicationToEnrich, fetched);

        // == Xrefs ===================================================================
        processXrefs(publicationToEnrich, fetched);

        // == Annotations ===================================================================
        processAnnotations(publicationToEnrich, fetched);
    }

    protected void processCurationDepth(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        if (publicationToEnrich.getCurationDepth().equals(CurationDepth.undefined)
                && !fetched.getCurationDepth().equals(CurationDepth.undefined)){
            publicationToEnrich.setCurationDepth(fetched.getCurationDepth());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onCurationDepthUpdate(publicationToEnrich, CurationDepth.undefined);
            }
        }
    }

    protected void processReleasedDate(Publication publicationToEnrich, Publication fetched) throws EnricherException {
        if (publicationToEnrich.getReleasedDate() == null && fetched.getReleasedDate() != null){
            publicationToEnrich.setReleasedDate(fetched.getReleasedDate());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onReleaseDateUpdated(publicationToEnrich, null);
            }
        }
    }

    protected void processXrefs(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        EnricherUtils.mergeXrefs(publicationToEnrich, publicationToEnrich.getXrefs(), fetched.getXrefs(), false, false,
                getPublicationEnricherListener(), getPublicationEnricherListener());
    }

    protected void processAnnotations(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        EnricherUtils.mergeAnnotations(publicationToEnrich, publicationToEnrich.getAnnotations(), fetched.getAnnotations(), false,
                getPublicationEnricherListener());
    }

    protected void processJournal(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        if(publicationToEnrich.getJournal() == null
                && fetched.getJournal() != null) {
            publicationToEnrich.setJournal(fetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onJournalUpdated(publicationToEnrich, null);
        }
    }

    protected void processPublicationTitle(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        if(publicationToEnrich.getTitle() == null
                && fetched.getTitle() != null) {
            publicationToEnrich.setTitle(fetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , null);
        }
    }

}
