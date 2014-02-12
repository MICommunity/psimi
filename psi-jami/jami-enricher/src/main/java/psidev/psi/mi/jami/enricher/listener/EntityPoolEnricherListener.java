package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.EntityPoolChangeListener;
import psidev.psi.mi.jami.model.EntityPool;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface EntityPoolEnricherListener<P extends EntityPool> extends ParticipantEnricherListener<P>, EntityPoolChangeListener<P> {

}
