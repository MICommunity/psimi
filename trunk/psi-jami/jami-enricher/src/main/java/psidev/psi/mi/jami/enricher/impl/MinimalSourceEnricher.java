package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.SourceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.SourceEnricherListener;
import psidev.psi.mi.jami.model.Source;

/**
 * Provides minimum enrichment of a Source.
 *
 * - enrich minimal properties of CvTerm (see MinimalCvTermEnricher for more details)
 * - enrich publication using publication enricher. If the publication is not null in the source to enrich,
 * it will ignore the publication loaded from the fetched source
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class MinimalSourceEnricher extends AbstractMIEnricher<Source> implements SourceEnricher{

    private MinimalCvTermEnricher<Source> delegate = null;
    private PublicationEnricher publicationEnricher=null;

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimalSourceEnricher(SourceFetcher cvTermFetcher) {
        this.delegate = new MinimalCvTermEnricher<Source>(cvTermFetcher);
    }

    protected MinimalSourceEnricher(MinimalCvTermEnricher<Source> cvEnricher) {
        if (cvEnricher == null){
           throw new IllegalArgumentException("The cv term enricher delegate cannot be null in source enricher");
        }
        this.delegate = cvEnricher;
    }

    /**
     * A method that can be overridden to add to or change the behaviour of enrichment without effecting fetching.
     * @param cvTermToEnrich the CvTerm to enrich
     */
    protected void processSource(Source cvTermToEnrich, Source cvTermFetched) throws EnricherException {
        // process publication if not done
        processPublication(cvTermToEnrich, cvTermFetched);

        if (cvTermToEnrich.getPublication() != null && this.publicationEnricher != null){
            this.publicationEnricher.enrich(cvTermToEnrich.getPublication());
        }
    }

    protected void processPublication(Source cvTermToEnrich, Source cvTermFetched) throws EnricherException {
        if (cvTermToEnrich.getPublication() == null && cvTermFetched.getPublication() != null){
             cvTermToEnrich.setPublication(cvTermFetched.getPublication());
            if (getCvTermEnricherListener() instanceof SourceEnricherListener){
                ((SourceEnricherListener)getCvTermEnricherListener()).onPublicationUpdate(cvTermToEnrich, null);
            }
        }
    }

    @Override
    public void enrich(Source cvTermToEnrich, Source cvTermFetched) throws EnricherException {
        this.delegate.enrich(cvTermToEnrich, cvTermFetched);
        processSource(cvTermToEnrich, cvTermFetched);
        if(getCvTermEnricherListener() != null) getCvTermEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.SUCCESS, "Ontology term enriched successfully.");
    }

    @Override
    public Source find(Source objectToEnrich) throws EnricherException {
        return ((AbstractMIEnricher<Source>)this.delegate).find(objectToEnrich);
    }

    @Override
    protected void onEnrichedVersionNotFound(Source objectToEnrich) throws EnricherException {
        if(getCvTermEnricherListener() != null)
            getCvTermEnricherListener().onEnrichmentComplete(objectToEnrich, EnrichmentStatus.FAILED, "The source does not exist.");
    }

    public PublicationEnricher getPublicationEnricher() {
        return publicationEnricher;
    }

    public void setPublicationEnricher(PublicationEnricher publicationEnricher) {
        this.publicationEnricher = publicationEnricher;
    }

    public CvTermFetcher<Source> getCvTermFetcher() {
        return this.delegate.getCvTermFetcher();
    }

    public void setCvTermEnricherListener(CvTermEnricherListener<Source> listener) {
        this.delegate.setCvTermEnricherListener(listener);
    }

    public CvTermEnricherListener<Source> getCvTermEnricherListener() {
        return this.delegate.getCvTermEnricherListener();
    }
}
