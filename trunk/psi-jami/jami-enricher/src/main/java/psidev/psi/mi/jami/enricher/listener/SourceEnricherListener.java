package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.SourceChangeListener;

/**
 * An extension of the SourceChangeListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the source
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface SourceEnricherListener
        extends CvTermEnricherListener, SourceChangeListener{

}
