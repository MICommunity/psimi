package psidev.psi.mi.jami.enricher.enricher;


import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessor;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
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
public interface CvTermEnricher extends EnricherEventProcessor {

    /**
     * Enrichment of a single CvTerm.
     * If enrichment takes place, the master will be edited.
     * @param cvTermMaster  a CvTerm to enrich
     * @throws EnrichmentException  Thrown if the bridge fails or other problems halt the enrichment.
     */
    public void enrichCvTerm(CvTerm cvTermMaster)  throws EnrichmentException;


    public void enrichCvTerms(Collection<CvTerm> cvTermMasters);

    /**
     *
     * @param fetcher
     */
    public void setFetcher(CvTermFetcher fetcher);

    public CvTermFetcher getFetcher();
}
