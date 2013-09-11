package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * The CvTerm enricher is an enricher which can enrich either single cvTerm or a collection.
 * A cvTerm enricher must be initiated with a fetcher.
 * Sub enrichers: none.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   13/05/13
 */
public interface CvTermEnricher{

    /**
     * Enrichment of a single CvTerm.
     * At the end of the enrichment, the listener will be fired
     * @param cvTermToEnrich        A CvTerm to enrich
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich) throws EnricherException;

    /**
     * Enriches a collection of CvTerms.
     * @param cvTermsToEnrich       The cvTerms to be enriched
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) throws EnricherException;


    /**
     * Sets the cvTerm fetcher to be used for enrichment.
     * If the fetcher is null, an illegal state exception will be thrown at the the next enrichment.
     * @param fetcher   The fetcher to be used to gather data for enrichment
     */
    public void setCvTermFetcher(CvTermFetcher fetcher);

    /**
     * The fetcher to be used for used to collect data.
     * @return  The fetcher which is currently being used for fetching.
     */
    public CvTermFetcher getCvTermFetcher();

    /**
     * The cvTermEnricherListener to be used.
     * It will be fired at all points where a change is made to the cvTerm
     * @param listener  The listener to use. Can be null.
     */
    public void setCvTermEnricherListener(CvTermEnricherListener listener);

    /**
     * The current CvTermEnricherListener.
     * @return  the current listener. May be null.
     */
    public CvTermEnricherListener getCvTermEnricherListener();



}
