package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalCvTermEnricher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Provides full enrichment of cv term.
 *
 * - enrich minimal properties of CvTerm. See MinimalCvTermEnricher for more details
 * - enrich xrefs of CvTerm. It will only add missing xrefs and not remove any existing xrefs using DefaultXrefComparator
 * - enrich synonyms of CvTerm. It will only add missing synonyms and not remove any existing synonyms using DefaultAliasComparator
 * - enrich annotations of CvTerm. It will only add missing annotations and not remove any existing annotations using DefaultAnnotationComparator
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class FullCvTermEnricher<C extends CvTerm>
        extends MinimalCvTermEnricher<C> {

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
    public void processCvTerm(C cvTermToEnrich, C termFetched) throws EnricherException {
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

    protected void processAnnotations(C cvTermToEnrich, C termFetched) throws EnricherException{
        EnricherUtils.mergeAnnotations(cvTermToEnrich, cvTermToEnrich.getAnnotations(), termFetched.getAnnotations(), false, getCvTermEnricherListener());
    }

    protected void processSynonyms(C cvTermToEnrich, C termFetched) throws EnricherException{
        EnricherUtils.mergeAliases(cvTermToEnrich, cvTermToEnrich.getSynonyms(), termFetched.getSynonyms(), false, getCvTermEnricherListener());
    }

    protected void processXrefs(C cvTermToEnrich, C cvTermFetched) throws EnricherException{
        EnricherUtils.mergeXrefs(cvTermToEnrich, cvTermToEnrich.getXrefs(), cvTermFetched.getXrefs(), false, false,
                getCvTermEnricherListener(), getCvTermEnricherListener());
    }
}
