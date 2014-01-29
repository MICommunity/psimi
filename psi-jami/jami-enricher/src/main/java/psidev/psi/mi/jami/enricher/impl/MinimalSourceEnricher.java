package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.SourceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.SourceEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;

/**
 * Provides minimum enrichment of the Source.
 * Will enrich the full name if it is null and the identifiers.
 * As an enricher, no values from the provided OntologyTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class MinimalSourceEnricher extends AbstractMIEnricher<Source> implements SourceEnricher{

    private CvTermEnricher cvEnricher = null;
    private PublicationEnricher publicationEnricher=null;

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimalSourceEnricher(SourceFetcher cvTermFetcher) {
        this.cvEnricher = new MinimalCvTermEnricher(cvTermFetcher);
    }

    protected MinimalSourceEnricher(CvTermEnricher cvEnricher) {
        if (cvEnricher == null){
           throw new IllegalArgumentException("The cv term enricher cannot be null in source enricher");
        }
        this.cvEnricher = cvEnricher;
    }

    /**
     * The fetcher to be used for used for fetcher.
     * @return  The fetcher which is being used for fetching.
     */
    public SourceFetcher getSourceFetcher() {
        return (SourceFetcher)this.cvEnricher.getCvTermFetcher();
    }

    /**
     * The sourceEnricherListener to be used.
     * It will be fired at all points where a change is made to the cvTerm
     * @param listener  The listener to use. Can be null.
     */
    public void setSourceEnricherListener(SourceEnricherListener listener) {
        this.cvEnricher.setCvTermEnricherListener(listener);
    }
    /**
     * The current sourceEnricherListener.
     * @return  the current listener. May be null.
     */
    public SourceEnricherListener getSourceEnricherListener() {
        return (SourceEnricherListener)this.cvEnricher.getCvTermEnricherListener();
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

    protected void processPublication(Source cvTermToEnrich, Source cvTermFetched) {
        if (cvTermToEnrich.getPublication() == null && cvTermFetched.getPublication() != null){
             cvTermToEnrich.setPublication(cvTermFetched.getPublication());
            if (getSourceEnricherListener() != null){
                getSourceEnricherListener().onPublicationUpdate(cvTermToEnrich, null);
            }
        }
    }

    @Override
    public void enrich(Source cvTermToEnrich, Source cvTermFetched) throws EnricherException {
        this.cvEnricher.enrich(cvTermToEnrich, cvTermFetched);
        processSource(cvTermToEnrich, cvTermFetched);
        if(getSourceEnricherListener() != null) getSourceEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.SUCCESS, "Ontology term enriched successfully.");
    }

    @Override
    public Source find(Source objectToEnrich) throws EnricherException {
        return (Source)((AbstractMIEnricher<CvTerm>)this.cvEnricher).find(objectToEnrich);
    }

    @Override
    protected void onEnrichedVersionNotFound(Source objectToEnrich) throws EnricherException {
        if(getSourceEnricherListener() != null)
            getSourceEnricherListener().onEnrichmentComplete(objectToEnrich, EnrichmentStatus.FAILED, "The source does not exist.");
    }

    public PublicationEnricher getPublicationEnricher() {
        return publicationEnricher;
    }

    public void setPublicationEnricher(PublicationEnricher publicationEnricher) {
        this.publicationEnricher = publicationEnricher;
    }
}
