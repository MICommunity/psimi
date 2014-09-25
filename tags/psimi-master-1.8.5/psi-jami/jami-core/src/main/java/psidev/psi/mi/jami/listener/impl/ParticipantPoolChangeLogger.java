package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ParticipantPoolChangeListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParticipantCandidate;
import psidev.psi.mi.jami.model.ParticipantPool;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just interactor change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ParticipantPoolChangeLogger<T extends ParticipantPool> extends ParticipantChangeLogger<T>
        implements ParticipantPoolChangeListener<T> {

    private static final Logger entityChangeLogger = Logger.getLogger("ParticipantPoolChangeLogger");

    public void onTypeUpdate(T entity, CvTerm oldTerm) {
        entityChangeLogger.log(Level.INFO, "The pool type " + oldTerm + " has been updated with " + entity.getType() + " in the participant pool " + entity.toString());
    }

    public void onAddedCandidate(T protein, ParticipantCandidate added) {
        entityChangeLogger.log(Level.INFO, "The candidate " + added.toString() + " has been added to the participant pool " + protein.toString());
    }

    public void onRemovedCandidate(T protein, ParticipantCandidate removed) {
        entityChangeLogger.log(Level.INFO, "The candidate " + removed.toString() + " has been removed from the participant pool " + protein.toString());
    }
}
