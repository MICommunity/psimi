package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ExperimentalEntityPoolChangeListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.ExperimentalEntityPool;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just entity pool change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ExperimentalEntityPoolChangeLogger extends ExperimentalEntityChangeLogger<ExperimentalEntityPool> implements ExperimentalEntityPoolChangeListener {

    private static final Logger entityPoolChangeLogger = Logger.getLogger("EntityPoolChangeLogger");

    public void onTypeUpdate(ExperimentalEntityPool entity, CvTerm oldTerm) {
        entityPoolChangeLogger.log(Level.INFO, "The pool type " + oldTerm + " has been updated with " + entity.getType() + " in the experimental entity pool " + entity.toString());
    }

    public void onAddedEntity(ExperimentalEntityPool entity, Entity added) {
        entityPoolChangeLogger.log(Level.INFO, "The entity " + added.toString() + " has been added to the experimental entity pool " + entity.toString());
    }

    public void onRemovedEntity(ExperimentalEntityPool entity, Entity removed) {
        entityPoolChangeLogger.log(Level.INFO, "The entity " + removed.toString() + " has been removed from the experimental entity pool " + entity.toString());
    }
}
