package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Listener for changes in an entity
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface EntityPoolChangeListener<E extends EntityPool> extends EntityChangeListener<E> {

    public void onTypeUpdate(E participant, CvTerm oldType);

    public void onAddedEntity(E participant, Entity added);

    public void onRemovedEntity(E participant, Entity removed);
}
