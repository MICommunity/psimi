package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.EntityChangeListener;
import psidev.psi.mi.jami.model.Entity;

/**
 * Entity enricher listener
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface EntityEnricherListener<P extends Entity> extends EnricherListener<P>, EntityChangeListener<P> {

}
