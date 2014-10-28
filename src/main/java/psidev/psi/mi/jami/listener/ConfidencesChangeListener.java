package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Confidence;

import java.util.EventListener;


/**
 * A listener for changes to an object having confidences.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface ConfidencesChangeListener<T extends Object> extends EventListener {

    /**
     * Listen to the event where a confidence has been added to the object confidences.
     * @param o        The object which has changed.
     * @param added             The added confidence.
     */
    public void onAddedConfidence(T o, Confidence added);

    /**
     * Listen to the event where a confidence has been removed from the object confidences.
     * @param o        The object which has changed.
     * @param removed           The removed confidence.
     */
    public void onRemovedConfidence(T o, Confidence removed);
}
