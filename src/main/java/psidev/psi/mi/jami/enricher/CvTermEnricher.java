package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * The CvTerm enricher is an enricher which can enrich either single cvTerm or a collection.
 * A cvTerm enricher must be initiated with a fetcher.
 * Sub enrichers: none.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   13/05/13
 */
public interface CvTermEnricher<C extends CvTerm> extends MIEnricher<C>{

    /**
     * The fetcher to be used for used to collect data.
     * @return  The fetcher which is currently being used for fetching.
     */
    public CvTermFetcher<C> getCvTermFetcher();

    /**
     * The current CvTermEnricherListener.
     * @return  the current listener. May be null.
     */
    public CvTermEnricherListener<C> getCvTermEnricherListener();

    public void setCvTermEnricherListener(CvTermEnricherListener<C> listener);
}
