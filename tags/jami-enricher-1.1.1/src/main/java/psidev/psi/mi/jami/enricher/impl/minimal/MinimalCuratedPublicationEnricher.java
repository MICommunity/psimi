package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.CuratedPublicationEnricher;
import psidev.psi.mi.jami.enricher.SourceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.AbstractMIEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.model.Publication;

/**
 * Provides minimal enrichment of curatedPublication.
 *
 * - enrich minimal properties of publication. See description in MinimalPublicationEnricher
 * - enrich source of a publication if the sourceEnricher is not null. If the source is not null in the publication to enrich,
 * it will ignore the source loaded from the fetched publication
 *
 * It will ignore all other properties of a publication
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class MinimalCuratedPublicationEnricher extends AbstractMIEnricher<Publication> implements CuratedPublicationEnricher {
    private SourceEnricher sourceEnricher =null;
    private MinimalPublicationEnricher delegate;

    /**
     * The only constructor. It requires a publication fetcher.
     * If the publication fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param fetcher  The PublicationFetcher to use.
     */
    public MinimalCuratedPublicationEnricher(PublicationFetcher fetcher){
        super();
        this.delegate = new MinimalPublicationEnricher(fetcher);
    }

    protected MinimalCuratedPublicationEnricher(MinimalPublicationEnricher delegate){
        super();
        if (delegate == null){
            throw new IllegalArgumentException("The curated publication enricher needs a non null delegate enricher");
        }
        this.delegate = delegate;
    }

    /**
     * The strategy for the enrichment of the publication.
     * This methods can be overwritten to change the behaviour of the enrichment.
     * @param publicationToEnrich   The publication which is being enriched.
     */
    public void processPublication(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException{

        this.delegate.processPublication(publicationToEnrich, fetchedPublication);

        // == SOURCE ==========================================================
        processSource(publicationToEnrich, fetchedPublication);

        // other properties
        processOtherProperties(publicationToEnrich, fetchedPublication);
    }

    public void setSourceEnricher(SourceEnricher cvTermEnricher){
        this.sourceEnricher = cvTermEnricher;
    }

    public SourceEnricher getSourceEnricher(){
        return sourceEnricher;
    }

    protected void processOtherProperties(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException{
         // nothing to do
    }

    protected void processSource(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException {
        if (fetchedPublication.getSource() != null && publicationToEnrich.getSource() == null){
            publicationToEnrich.setSource(fetchedPublication.getSource());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onSourceUpdated(publicationToEnrich, null);
            }
        }
        if (this.sourceEnricher != null && publicationToEnrich.getSource() != null){
            this.sourceEnricher.enrich(publicationToEnrich.getSource());
        }
    }

    public PublicationFetcher getPublicationFetcher() {
        return this.delegate.getPublicationFetcher();
    }

    public PublicationEnricherListener getPublicationEnricherListener() {
        return this.delegate.getPublicationEnricherListener();
    }

    public void setPublicationEnricherListener(PublicationEnricherListener listener){
         this.delegate.setPublicationEnricherListener(listener);
    }

    @Override
    public void enrich(Publication objectToEnrich, Publication fetchedObject) throws EnricherException {
        processPublication(objectToEnrich, fetchedObject);

        if( getPublicationEnricherListener() != null)
            getPublicationEnricherListener().onEnrichmentComplete(objectToEnrich , EnrichmentStatus.SUCCESS , "The publication has been successfully enriched");
    }

    @Override
    public Publication find(Publication objectToEnrich) throws EnricherException {
        return this.delegate.find(objectToEnrich);
    }

    @Override
    protected void onEnrichedVersionNotFound(Publication objectToEnrich) throws EnricherException {
         this.delegate.onEnrichedVersionNotFound(objectToEnrich);
    }

    protected MinimalPublicationEnricher getDelegate() {
        return delegate;
    }
}
