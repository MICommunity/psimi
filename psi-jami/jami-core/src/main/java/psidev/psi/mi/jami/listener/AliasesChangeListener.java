package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Alias;

import java.util.EventListener;


/**
 * A listener for changes to a bioactiveEntity.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface AliasesChangeListener extends EventListener {

    /**
     * Listen to the event where an alias has been added to the object aliases.
     * @param o        The object which has changed.
     * @param added             The added alias.
     */
    public void onAddedAlias(Object o , Alias added);

    /**
     * Listen to the event where an alias has been removed from the object aliases.
     * @param o        The object which has changed.
     * @param removed           The removed alias.
     */
    public void onRemovedAlias(Object o , Alias removed);
}
