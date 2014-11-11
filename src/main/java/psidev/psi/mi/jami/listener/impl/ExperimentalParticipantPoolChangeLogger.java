package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ParticipantPoolChangeListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalParticipantPool;
import psidev.psi.mi.jami.model.ParticipantCandidate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just interactor change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ExperimentalParticipantPoolChangeLogger extends ParticipantEvidenceChangeLogger<ExperimentalParticipantPool>
        implements ParticipantPoolChangeListener<ExperimentalParticipantPool> {

    private static final Logger entityChangeLogger = Logger.getLogger("ParticipantPoolChangeLogger");

    public void onTypeUpdate(ExperimentalParticipantPool entity, CvTerm oldTerm) {
        entityChangeLogger.log(Level.INFO, "The pool type " + oldTerm + " has been updated with " + entity.getBiologicalRole() + " in the participant pool " + entity.toString());
    }

    public void onAddedCandidate(ExperimentalParticipantPool protein, ParticipantCandidate added) {
        entityChangeLogger.log(Level.INFO, "The candidate " + added.toString() + " has been added to the participant pool " + protein.toString());
    }

    public void onRemovedCandidate(ExperimentalParticipantPool protein, ParticipantCandidate removed) {
        entityChangeLogger.log(Level.INFO, "The candidate " + removed.toString() + " has been removed from the participant pool " + protein.toString());
    }
}
