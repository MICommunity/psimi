package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Xref;

import java.util.EventListener;


/**
 * A listener for changes to a bioactiveEntity.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface IdentifiersChangeListener<T extends Object> extends EventListener {

    /**
     * Listen to the event where an identifier has been added to the protein identifiers.
     * @param o        The object which has changed.
     * @param added             The added identifier
     */
    public void onAddedIdentifier(T o , Xref added);

    /**
     * Listen to the event where an identifier has been removed from the protein identifiers.
     * @param o        The object which has changed.
     * @param removed           The removed identifier.
     */
    public void onRemovedIdentifier(T o , Xref removed);


}
