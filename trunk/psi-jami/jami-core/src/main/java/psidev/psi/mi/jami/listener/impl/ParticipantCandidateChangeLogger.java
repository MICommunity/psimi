package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ParticipantCandidateChangeListener;
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

public class ParticipantCandidateChangeLogger<T extends ParticipantCandidate> implements ParticipantCandidateChangeListener<T> {

    private static final Logger entityChangeLogger = Logger.getLogger("ParticipantCandidateChangeLogger");

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

    public void onInteractorUpdate(Entity entity, Interactor oldInteractor) {
        entityChangeLogger.log(Level.INFO, "The interactor " + oldInteractor + " has been updated with " + entity.getInteractor() + " in the entity " + entity.toString());
    }
}
