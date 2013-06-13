package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
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
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich)
            throws BridgeFailedException, MissingServiceException,
            BadToEnrichFormException, BadSearchTermException {

        CvTerm cvTermEnriched = getFullyEnrichedForm(cvTermToEnrich);
        runCvTermAdditionEnrichment(cvTermToEnrich, cvTermEnriched);
        runCvTermOverwriteUpdate(cvTermToEnrich, cvTermEnriched);
        //fireEnricherEvent(enricherEvent);
    }


    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
