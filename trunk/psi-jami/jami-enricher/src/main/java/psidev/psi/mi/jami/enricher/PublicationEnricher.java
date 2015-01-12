package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.model.Publication;

/**
 * An enricher for publications which can enrich either a single publication or a collection.
 * It must be initiated with a fetcher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public interface PublicationEnricher extends MIEnricher<Publication>{

    /**
     * Gets the publication fetcher which is currently being used to retrieve entries
     * @return  the current publication fetcher.
     */
    public PublicationFetcher getPublicationFetcher();

    /**
     * Gets the current publication listener
     * @return  the current publication listener
     */
    public PublicationEnricherListener getPublicationEnricherListener();

    public void setPublicationEnricherListener(PublicationEnricherListener listener);
}
