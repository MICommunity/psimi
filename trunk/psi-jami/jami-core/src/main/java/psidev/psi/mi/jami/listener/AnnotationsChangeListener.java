package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Annotation;

import java.util.EventListener;


/**
 * A listener for changes to a bioactiveEntity.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface AnnotationsChangeListener extends EventListener {

    /**
     * Listen to the event where an annotation has been added to the object annotations.
     * @param o
     * @param added
     */
    public void onAddedAnnotation(Object o, Annotation added);

    /**
     * Listen to the event where an annotation has been removed from the object annotations.
     * @param o
     * @param removed
     */
    public void onRemovedAnnotation(Object o, Annotation removed);
}
