package psidev.psi.mi.jami.enricher.enricherimplementation.cvterm;

import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
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
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich)
            throws BridgeFailedException, MissingServiceException,
            BadToEnrichFormException, BadSearchTermException {

        CvTerm cvTermEnriched = getFullyEnrichedForm(cvTermToEnrich);
        runCvTermAdditionEnrichment(cvTermToEnrich, cvTermEnriched);
        runCvTermMismatchComparison(cvTermToEnrich, cvTermEnriched);
        //fireEnricherEvent(enricherEvent);
    }




    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
