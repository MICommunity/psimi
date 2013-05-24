package psidev.psi.mi.jami.enricher.enricherimplementation.cvterm;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 *
 * Date: 13/05/13
 * Time: 14:16
 */
public class MaximumCvTermUpdater
        extends AbstractCvTermEnricher
        implements CvTermEnricher {


    public MaximumCvTermUpdater()  {
        super();
    }

    public MaximumCvTermUpdater(CvTermFetcher fetcher){
        super(fetcher);
    }

    /**
     * Enrichment and update of a single CvTerm.
     * If update takes place, the ToEnrich will be edited.
     *
     * @param cvTermToEnrich  a CvTerm to update
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich)
            throws EnrichmentException {

        CvTerm cvTermEnriched = getFullyEnrichedForm(cvTermToEnrich);
        runCvTermAdditionEnrichment(cvTermToEnrich, cvTermEnriched);
        runCvTermOverwriteUpdate(cvTermToEnrich, cvTermEnriched);
        fireEnricherEvent(enricherEvent);
    }


    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
