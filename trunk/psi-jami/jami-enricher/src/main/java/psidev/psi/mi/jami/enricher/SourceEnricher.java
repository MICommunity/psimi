package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.enricher.listener.SourceEnricherListener;
import psidev.psi.mi.jami.model.Source;

/**
 * The source enricher is an enricher which can enrich either single source or a collection.
 * A source enricher must be initiated with a fetcher.
 * Sub enrichers: none.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public interface SourceEnricher extends MIEnricher<Source>{

    /**
     * The sourceEnricherListener to be used.
     * It will be fired at all points where a change is made to the source
     * @param listener  The listener to use. Can be null.
     */
    public void setSourceEnricherListener(SourceEnricherListener listener);

    /**
     * The current sourceTermEnricherListener.
     * @return  the current listener. May be null.
     */
    public SourceEnricherListener getSourceEnricherListener();

    /**
     * The fetcher to be used for used to collect data.
     * @return  The fetcher which is currently being used for fetching.
     */
    public SourceFetcher getSourceFetcher();

    /**
     * The publication enricher to be used.
     * @param enricher  The enricher to use. Can be null.
     */
    public void setPublicationEnricher(PublicationEnricher enricher);

    /**
     * The current publication enricher.
     * @return  the current listener. May be null.
     */
    public PublicationEnricher getPublicationEnricher();
}
