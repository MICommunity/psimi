package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Parameter;

import java.util.EventListener;


/**
 * A listener for changes to an object parameters list.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface ParametersChangeListener<T extends Object> extends EventListener {

    /**
     * Listen to the event where a parameter has been added to the object parameters.
     * @param o        The object which has changed.
     * @param added             The added parameter.
     */
    public void onAddedParameter(T o, Parameter added);

    /**
     * Listen to the event where a parameter has been removed from the object parameters.
     * @param o        The object which has changed.
     * @param removed           The removed parameter.
     */
    public void onRemovedParameter(T o, Parameter removed);
}
