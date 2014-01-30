package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * Listener of changes in interactor pool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public interface InteractorPoolChangeListener extends InteractorChangeListener<InteractorPool> {

    /**
     * Listen to the event where an interactor has been added to the pool
     * @param interactor        The interactor which has changed.
     * @param added
     */
    public void onAddedInteractor(InteractorPool interactor, Interactor added);

    /**
     * Listen to the event where an interactor has been added to the pool
     * @param interactor        The interactor which has changed.
     * @param removed
     */
    public void onRemovedInteractor(InteractorPool interactor, Interactor removed);
}
