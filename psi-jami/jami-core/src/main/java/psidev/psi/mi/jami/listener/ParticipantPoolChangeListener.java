package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Listener for changes in a participant pool
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface ParticipantPoolChangeListener<F extends ParticipantPool> extends ParticipantChangeListener<F> {

    public void onTypeUpdate(F participant, CvTerm oldType);

    public void onAddedCandidate(F participant, ParticipantCandidate added);

    public void onRemovedCandidate(F participant, ParticipantCandidate removed);
}
