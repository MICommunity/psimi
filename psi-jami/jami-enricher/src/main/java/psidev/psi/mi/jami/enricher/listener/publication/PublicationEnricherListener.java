package psidev.psi.mi.jami.enricher.listener.publication;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.PublicationChangeListener;
import psidev.psi.mi.jami.model.Publication;

/**
 * An extension of the PublicationChangeListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the publication.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public interface PublicationEnricherListener
        extends EnricherListener<Publication>, PublicationChangeListener{

    /**
     *
     * @param publication   The publication which was being enriched
     * @param status        The status of the enrichment upon completion.
     * @param message       Additional information about the enrichment. Can be null.
     */
    public void onEnrichmentComplete(
            Publication publication ,EnrichmentStatus status , String message);
}
