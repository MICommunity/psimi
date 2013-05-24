package psidev.psi.mi.jami.enricher.enricherimplementation.cvterm;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 14:19
 */
public class MinimumCvTermEnricher
        extends AbstractCvTermEnricher{

    public MinimumCvTermEnricher() {
        super();
    }

    public MinimumCvTermEnricher(CvTermFetcher fetcher) {
        super(fetcher);
    }

    /**
     * Enrichment of a single CvTerm.
     * If enrichment takes place, the ToEnrich will be edited.
     *
     * @param cvTermToEnrich  a CvTerm to enrich
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich)
            throws EnrichmentException{

        CvTerm cvTermEnriched = getFullyEnrichedForm(cvTermToEnrich);
        runCvTermAdditionEnrichment(cvTermToEnrich, cvTermEnriched);
        runCvTermMismatchComparison(cvTermToEnrich, cvTermEnriched);
        fireEnricherEvent(enricherEvent);
    }




    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
