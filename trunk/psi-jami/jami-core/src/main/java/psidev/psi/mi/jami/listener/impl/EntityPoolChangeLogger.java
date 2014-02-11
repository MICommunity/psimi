package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.EntityPoolChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just entity pool change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class EntityPoolChangeLogger<T extends EntityPool> extends EntityChangeLogger<T> implements EntityPoolChangeListener<T> {

    private static final Logger entityPoolChangeLogger = Logger.getLogger("EntityPoolChangeLogger");

    public void onTypeUpdate(T entity, CvTerm oldTerm) {
        entityPoolChangeLogger.log(Level.INFO, "The pool type " + oldTerm + " has been updated with " + entity.getType() + " in the entity pool " + entity.toString());
    }

    public void onAddedEntity(T entity, Entity added) {
        entityPoolChangeLogger.log(Level.INFO, "The entity " + added.toString() + " has been added to the entity pool " + entity.toString());
    }

    public void onRemovedEntity(T entity, Entity removed) {
        entityPoolChangeLogger.log(Level.INFO, "The entity " + removed.toString() + " has been removed from the entity pool " + entity.toString());
    }
}
