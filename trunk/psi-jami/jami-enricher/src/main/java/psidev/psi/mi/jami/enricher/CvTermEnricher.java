package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * All levels of enrichment for CvTerms must use this interface.
 * This interface can accept EnricherListeners
 * and will report an EnricherEvent after each enrichment.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:13
 */
public interface CvTermEnricher{

    /**
     * Enrichment of a single CvTerm.
     * If enrichment takes place, the ToEnrich will be edited.
     * @param cvTermToEnrich  a CvTerm to enrich
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich)
            throws BridgeFailedException, MissingServiceException,
            BadToEnrichFormException, BadSearchTermException;

    //public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich);



    /**
     *
     * @param fetcher
     */
    public void setCvTermFetcher(CvTermFetcher fetcher);
    public CvTermFetcher getCvTermFetcher();


    public void setCvTermEnricherListener(CvTermEnricherListener cvTermEnricherListener);
    public CvTermEnricherListener getCvTermEnricherListener();
}
