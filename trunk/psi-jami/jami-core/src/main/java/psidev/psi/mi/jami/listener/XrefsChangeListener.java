package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Xref;

import java.util.EventListener;


/**
 * A listener for changes to a bioactiveEntity.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface XrefsChangeListener extends EventListener{

    /**
     * Listen to the event where a xref has been added to the protein xrefs.
     * @param o        The object which has changed.
     * @param added             The added Xref.
     */
    public void onAddedXref(Object o , Xref added);

    /**
     * Listen to the event where a xref has been removed from the interactor xrefs.
     * @param o        The object which has changed.
     * @param removed           The removed Xref.
     */
    public void onRemovedXref(Object o , Xref removed);
}
