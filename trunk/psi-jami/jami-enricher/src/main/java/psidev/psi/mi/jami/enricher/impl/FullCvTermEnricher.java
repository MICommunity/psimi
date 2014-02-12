package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Provides maximum enrichment of the CvTerm.
 * Will enrich all aspects covered by the minimum enricher as well as enriching the Aliases.
 * As an enricher, no values from the provided CvTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class FullCvTermEnricher<C extends CvTerm>
        extends MinimalCvTermEnricher<C>{

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public FullCvTermEnricher(CvTermFetcher<C> cvTermFetcher) {
        super(cvTermFetcher);
    }

    /**
     * Strategy for the cvTerm enrichment.
     * This method can be overwritten to change how the cvTerm is enriched.
     * @param cvTermToEnrich   The protein to be enriched.
     */
    @Override
    protected void processCvTerm(C cvTermToEnrich, C termFetched) throws EnricherException {
        processMinimalUpdates(cvTermToEnrich, termFetched);

        // == Xrefs =======================================================
        processXrefs(cvTermToEnrich, termFetched);

        // == Synonyms =======================================================
        processSynonyms(cvTermToEnrich, termFetched);

        // == Annotations =======================================================
        processAnnotations(cvTermToEnrich, termFetched);
    }

    protected void processMinimalUpdates(C cvTermToEnrich, C termFetched) throws EnricherException {
        super.processCvTerm(cvTermToEnrich, termFetched);
    }

    protected void processAnnotations(C cvTermToEnrich, C termFetched) {
        EnricherUtils.mergeAnnotations(cvTermToEnrich, cvTermToEnrich.getAnnotations(), termFetched.getAnnotations(), false, getCvTermEnricherListener());
    }

    protected void processSynonyms(C cvTermToEnrich, C termFetched) {
        EnricherUtils.mergeAliases(cvTermToEnrich, cvTermToEnrich.getSynonyms(), termFetched.getSynonyms(), false, getCvTermEnricherListener());
    }

    protected void processXrefs(C cvTermToEnrich, C cvTermFetched) {
        EnricherUtils.mergeXrefs(cvTermToEnrich, cvTermToEnrich.getXrefs(), cvTermFetched.getXrefs(), false, false,
                getCvTermEnricherListener(), getCvTermEnricherListener());
    }
}
