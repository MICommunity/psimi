package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;



/**
 * Provides maximum updating of the CvTerm.
 * Will update all aspects covered by the minimum updater as well as updating the Aliases.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/05/13
 */
public class FullCvTermUpdater<C extends CvTerm> extends FullCvTermEnricher<C>{

    private MinimalCvTermUpdater<C> minimalCvTermUpdater;
    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public FullCvTermUpdater(CvTermFetcher<C> cvTermFetcher) {
        super(cvTermFetcher);
        this.minimalCvTermUpdater = new MinimalCvTermUpdater<C>(cvTermFetcher);
    }

    @Override
    protected void processMinimalUpdates(C cvTermToEnrich, C termFetched) throws EnricherException {
        this.minimalCvTermUpdater.processCvTerm(cvTermToEnrich, termFetched);
    }

    @Override
    protected void processAnnotations(C cvTermToEnrich, C termFetched) {
        mergeAnnotations(cvTermToEnrich, termFetched.getAnnotations(), true);
    }

    @Override
    protected void processSynonyms(C cvTermToEnrich, C termFetched) {
        EnricherUtils.mergeAliases(cvTermToEnrich, cvTermToEnrich.getSynonyms(), termFetched.getSynonyms(), true, getCvTermEnricherListener());
    }

    @Override
    protected void processXrefs(C cvTermToEnrich, C cvTermFetched) {
        EnricherUtils.mergeXrefs(cvTermToEnrich, cvTermToEnrich.getXrefs(), cvTermFetched.getXrefs(), true, false,
                getCvTermEnricherListener(), getCvTermEnricherListener());
    }

    @Override
    public void setCvTermEnricherListener(CvTermEnricherListener<C> listener) {
        super.setCvTermEnricherListener(listener);
        this.minimalCvTermUpdater.setCvTermEnricherListener(listener);
    }
}
