package psidev.psi.mi.jami.enricher.listener.publication;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
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
}
