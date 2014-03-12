package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Listener for changes in an entity
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface ParticipantPoolChangeListener<E extends ParticipantPool> extends ParticipantChangeListener<E> {

    public void onTypeUpdate(E participant, CvTerm oldType);

    public void onAddedEntity(E participant, Participant added);

    public void onRemovedEntity(E participant, Participant removed);
}
