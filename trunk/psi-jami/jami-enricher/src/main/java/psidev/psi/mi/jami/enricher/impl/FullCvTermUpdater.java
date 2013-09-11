package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;



/**
 * Provides maximum updating of the CvTerm.
 * Will update all aspects covered by the minimum updater as well as updating the Aliases.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/05/13
 */
public class FullCvTermUpdater extends FullCvTermEnricher{

    private MinimalCvTermUpdater minimalCvTermUpdater;
    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public FullCvTermUpdater(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
        this.minimalCvTermUpdater = new MinimalCvTermUpdater(cvTermFetcher);
    }

    @Override
    protected void processMinimalUpdates(CvTerm cvTermToEnrich, CvTerm termFetched) {
        this.minimalCvTermUpdater.processCvTerm(cvTermToEnrich, termFetched);
    }

    @Override
    protected void processAnnotations(CvTerm cvTermToEnrich, CvTerm termFetched) {
        mergeAnnotations(cvTermToEnrich, termFetched.getAnnotations(), true);
    }

    @Override
    protected void processSynonyms(CvTerm cvTermToEnrich, CvTerm termFetched) {
        mergeSynonyms(cvTermToEnrich, termFetched.getSynonyms(), true);
    }

    @Override
    protected void processXrefs(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        mergeXrefs(cvTermToEnrich, cvTermFetched.getXrefs(), true, false);
    }
}
