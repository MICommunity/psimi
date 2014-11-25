package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.InteractorPoolChangeListener;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just interactor pool change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class InteractorPoolChangeLogger extends InteractorChangeLogger<InteractorPool> implements InteractorPoolChangeListener {

    private static final Logger interactorPoolChangeLogger = Logger.getLogger("InteractorPoolChangeLogger");

    public void onAddedInteractor(InteractorPool protein, Interactor added) {
        interactorPoolChangeLogger.log(Level.INFO, "The interactor " + added.toString() + " has been added to the interactor pool" + protein.toString());
    }

    public void onRemovedInteractor(InteractorPool protein, Interactor removed) {
        interactorPoolChangeLogger.log(Level.INFO, "The interactor " + removed.toString() + " has been removed from the interactor pool" + protein.toString());
    }
}
