package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ParticipantEvidenceChangeListener;
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

public class ParticipantEvidenceChangeLogger<T extends ParticipantEvidence> extends ParticipantChangeLogger<T> implements ParticipantEvidenceChangeListener<T> {

    private static final Logger experimentalEntityChangeLogger = Logger.getLogger("ParticipantEvidenceChangeLogger");

    public void onExperimentalRoleUpdate(T entity, CvTerm oldTerm) {
        experimentalEntityChangeLogger.log(Level.INFO, "The experimental role " + oldTerm + " has been updated with " + entity.getBiologicalRole() + " in the experimental entity " + entity.toString());
    }

    public void onExpressedInUpdate(T entity, Organism oldOrganism) {
        if (oldOrganism == null){
            experimentalEntityChangeLogger.log(Level.INFO, "The expressed in organism has been initialised for the experimental entity " + entity.toString());
        }
        else if (entity.getExpressedInOrganism() == null){
            experimentalEntityChangeLogger.log(Level.INFO, "The expressed in organism has been reset for the experimental entity " + entity.toString());
        }
        else {
            experimentalEntityChangeLogger.log(Level.INFO, "The expressed in organism " + oldOrganism + " has been updated with " + entity.getExpressedInOrganism() + " in the experimental entity " + entity.toString());
        }
    }

    public void onAddedIdentificationMethod(T entity, CvTerm added) {
        experimentalEntityChangeLogger.log(Level.INFO, "The identification method " + added.toString() + " has been added to the experimental entity " + entity.toString());
    }

    public void onRemovedIdentificationMethod(T entity, CvTerm removed) {
        experimentalEntityChangeLogger.log(Level.INFO, "The identification method " + removed.toString() + " has been removed from the experimental entity " + entity.toString());
    }

    public void onAddedExperimentalPreparation(T entity, CvTerm added) {
        experimentalEntityChangeLogger.log(Level.INFO, "The experimental preparation " + added.toString() + " has been added to the experimental entity " + entity.toString());
    }

    public void onRemovedExperimentalPreparation(T entity, CvTerm removed) {
        experimentalEntityChangeLogger.log(Level.INFO, "The experimental preparation " + removed.toString() + " has been removed from the experimental entity " + entity.toString());
    }

    public void onAddedConfidence(T o, Confidence added) {
        experimentalEntityChangeLogger.log(Level.INFO, "The confidence " + added.toString() + " has been added to the experimental entity " + o.toString());
    }

    public void onRemovedConfidence(T o, Confidence removed) {
        experimentalEntityChangeLogger.log(Level.INFO, "The confidence " + removed.toString() + " has been removed from the experimental entity " + o.toString());
    }

    public void onAddedParameter(T o, Parameter added) {
        experimentalEntityChangeLogger.log(Level.INFO, "The parameter " + added.toString() + " has been added to the experimental entity " + o.toString());
    }

    public void onRemovedParameter(T o, Parameter removed) {
        experimentalEntityChangeLogger.log(Level.INFO, "The parameter " + removed.toString() + " has been removed from the experimental entity " + o.toString());

    }
}
