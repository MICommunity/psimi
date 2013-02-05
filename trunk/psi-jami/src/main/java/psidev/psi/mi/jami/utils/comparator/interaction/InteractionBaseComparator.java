package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ParticipantCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic Interaction comparator.
 *
 * It will first compare the participants using ParticipantBaseComparator. If the participants are the same, it will compare
 * the interaction types using AbstractCvTermComparator. If the interaction types are the same, it will compare the negative properties.
 * A negative interaction will come after a positive interaction.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class InteractionBaseComparator<T extends Participant> implements Comparator<Interaction> {

    protected ParticipantCollectionComparator participantCollectionComparator;
    protected AbstractCvTermComparator cvTermComparator;

    /**
     * Creates a new InteractionBaseComparator.
     * @param participantComparator : required to compare participants
     * @param cvTermComparator : required to compare interaction type
     */
    public InteractionBaseComparator(Comparator<T> participantComparator, AbstractCvTermComparator cvTermComparator){
        if (participantComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare participants of an interaction. It cannot be null");
        }
        this.participantCollectionComparator = new ParticipantCollectionComparator<T>(participantComparator);

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare interaction types. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
    }

    public ParticipantCollectionComparator getParticipantCollectionComparator() {
        return participantCollectionComparator;
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    /**
     * It will first compare the participants using ParticipantBaseComparator. If the participants are the same, it will compare
     * the interaction types using AbstractCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction.
     *
     * @param interaction1
     * @param interaction2
     * @return
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interaction1 == null && interaction2 == null){
            return EQUAL;
        }
        else if (interaction1 == null){
            return AFTER;
        }
        else if (interaction2 == null){
            return BEFORE;
        }
        else {
            // first compares participants of an interaction
            Collection<T> participants1 = (Collection<T>) interaction1.getParticipants();
            Collection<T> participants2 = (Collection<T>) interaction2.getParticipants();

            int comp = participantCollectionComparator.compare(participants1, participants2);
            if (comp != 0){
                return comp;
            }

            // then compares interaction type
            CvTerm type1 = interaction1.getType();
            CvTerm type2 = interaction2.getType();

            comp = cvTermComparator.compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // then compares negative
            boolean isNegative1 = interaction1.isNegative();
            boolean  isNegative2 = interaction2.isNegative();

            if (isNegative1 == isNegative2){
                return EQUAL;
            }
            else if (isNegative1){
                return AFTER;
            }
            else if (isNegative2){
                return BEFORE;
            }
            else {
                return EQUAL;
            }
        }
    }
}
