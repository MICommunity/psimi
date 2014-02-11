package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.EntityChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just interactor change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class EntityChangeLogger<T extends Entity> implements EntityChangeListener<T> {

    private static final Logger entityChangeLogger = Logger.getLogger("EntityChangeLogger");

    public void onBiologicalRoleUpdate(T entity, CvTerm oldTerm) {
        entityChangeLogger.log(Level.INFO, "The biological role " + oldTerm + " has been updated with " + entity.getBiologicalRole() + " in the entity " + entity.toString());
    }

    public void onStoichiometryUpdate(T entity, Stoichiometry oldStoichiometry) {
        if (oldStoichiometry == null){
            entityChangeLogger.log(Level.INFO, "The stoichiometry has been initialised for the entity " + entity.toString());
        }
        else if (entity.getStoichiometry() == null){
            entityChangeLogger.log(Level.INFO, "The stoichiometry has been reset for the entity " + entity.toString());
        }
        else {
            entityChangeLogger.log(Level.INFO, "The stoichiometry " + oldStoichiometry + " has been updated with " + entity.getStoichiometry() + " in the entity " + entity.toString());
        }
    }

    public void onAddedCausalRelationship(T entity, CausalRelationship added) {
        entityChangeLogger.log(Level.INFO, "The causal relationship " + added.toString() + " has been added to the entity " + entity.toString());
    }

    public void onRemovedCausalRelationship(T entity, CausalRelationship removed) {
        entityChangeLogger.log(Level.INFO, "The causal relationship " + removed.toString() + " has been removed from the entity " + entity.toString());
    }

    public void onAddedFeature(T entity, Feature added) {
        entityChangeLogger.log(Level.INFO, "The feature " + added.toString() + " has been added to the entity " + entity.toString());
    }

    public void onRemovedFeature(T entity, Feature removed) {
        entityChangeLogger.log(Level.INFO, "The feature " + removed.toString() + " has been removed from the entity " + entity.toString());
    }

    public void onAddedXref(T protein, Xref added) {
        entityChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the entity " + protein.toString());
    }

    public void onRemovedXref(T protein, Xref removed) {
        entityChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the entity " + protein.toString());
    }

    public void onAddedAlias(T protein, Alias added) {
        entityChangeLogger.log(Level.INFO, "The alias " + added.toString() + " has been added to the entity " + protein.toString());
    }

    public void onRemovedAlias(T protein, Alias removed) {
        entityChangeLogger.log(Level.INFO, "The alias " + removed.toString() + " has been removed from the entity " + protein.toString());
    }

    public void onAddedAnnotation(T protein, Annotation added) {
        entityChangeLogger.log(Level.INFO, "The annotation " + added.toString() + " has been added to the entity " + protein.toString());
    }

    public void onRemovedAnnotation(T protein, Annotation removed) {
        entityChangeLogger.log(Level.INFO, "The annotation " + removed.toString() + " has been removed from the entity " + protein.toString());
    }

    public void onInteractorUpdate(Entity entity, Interactor oldInteractor) {
        entityChangeLogger.log(Level.INFO, "The interactor " + oldInteractor + " has been updated with " + entity.getInteractor() + " in the entity " + entity.toString());
    }
}
