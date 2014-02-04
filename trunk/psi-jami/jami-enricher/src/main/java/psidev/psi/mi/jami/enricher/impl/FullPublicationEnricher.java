package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Publication;

/**
 * An enricher for publications which can enrich either a single publication or a collection.
 * The publicationEnricher has no subEnrichers. The publicationEnricher must be initiated with a fetcher.
 *
 * At the maximum level, the publication enricher enriches the minimum level fields pubmedId and authors.
 * It also enriches the fields for DOI, Title, Journal, Publication Date, Xrefs and release date.
 *
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
    protected void processPublication(Publication publicationToEnrich, Publication fetched) throws EnricherException {
        processMinimalEnrichment(publicationToEnrich, fetched);

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

    protected void processCurationDepth(Publication publicationToEnrich, Publication fetched) {
        if (publicationToEnrich.getCurationDepth() == null && fetched.getCurationDepth() != null){
            publicationToEnrich.setCurationDepth(fetched.getCurationDepth());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onCurationDepthUpdate(publicationToEnrich, null);
            }
        }
    }

    protected void processReleasedDate(Publication publicationToEnrich, Publication fetched) {
        if (publicationToEnrich.getReleasedDate() == null && fetched.getReleasedDate() != null){
            publicationToEnrich.setReleasedDate(fetched.getReleasedDate());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onReleaseDateUpdated(publicationToEnrich, null);
            }
        }
    }

    protected void processXrefs(Publication publicationToEnrich, Publication fetched) {
        EnricherUtils.mergeXrefs(publicationToEnrich, publicationToEnrich.getXrefs(), fetched.getXrefs(), false, false,
                getPublicationEnricherListener(), getPublicationEnricherListener());
    }

    protected void processAnnotations(Publication publicationToEnrich, Publication fetched) {
        EnricherUtils.mergeAnnotations(publicationToEnrich, publicationToEnrich.getAnnotations(), fetched.getAnnotations(), false,
                getPublicationEnricherListener());
    }

    protected void processMinimalEnrichment(Publication publicationToEnrich, Publication fetched) throws EnricherException {
        super.processPublication(publicationToEnrich, fetched);
    }

    protected void processJournal(Publication publicationToEnrich, Publication fetched) {
        if(publicationToEnrich.getJournal() == null
                && fetched.getJournal() != null) {
            publicationToEnrich.setJournal(fetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onJournalUpdated(publicationToEnrich, null);
        }
    }

    protected void processPublicationTitle(Publication publicationToEnrich, Publication fetched) {
        if(publicationToEnrich.getTitle() == null
                && fetched.getTitle() != null) {
            publicationToEnrich.setTitle(fetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , null);
        }
    }

}
