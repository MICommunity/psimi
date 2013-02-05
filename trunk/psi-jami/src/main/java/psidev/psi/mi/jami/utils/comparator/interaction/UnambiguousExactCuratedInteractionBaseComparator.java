package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantBaseComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous exact comparator for Curated interactions.
 *
 * It will first compare the sources of the interactions using UnambiguousCvTermComparator. If the sources are the same, t will compare the participants using UnambiguousExactParticipantBaseComparator. If the participants are the same, it will compare
 * the interaction types using UnambiguousCvTermComparator. If the interaction types are the same, it will compare the negative properties.
 * A negative interaction will come after a positive interaction
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactCuratedInteractionBaseComparator extends CuratedInteractionBaseComparator {

    private static UnambiguousExactCuratedInteractionBaseComparator unambiguousExactCuratedInteractionComparator;

    /**
     * Creates a new UnambiguousExactCuratedInteractionBaseComparator. It will use a UnambiguousExactParticipantBaseComparator to
     * compare participants and UnambiguousCvTermcomparator to compare interaction types
     */
    public UnambiguousExactCuratedInteractionBaseComparator() {
        super(new UnambiguousExactParticipantBaseComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) cvTermComparator;
    }

    @Override
    /**
     * It will first compare the sources of the interactions using UnambiguousCvTermComparator. If the sources are the same, t will compare the participants using UnambiguousExactParticipantBaseComparator. If the participants are the same, it will compare
     * the interaction types using UnambiguousCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction
     *
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactCuratedInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousExactCuratedInteractionComparator == null){
            unambiguousExactCuratedInteractionComparator = new UnambiguousExactCuratedInteractionBaseComparator();
        }

        return unambiguousExactCuratedInteractionComparator.compare(interaction1, interaction2) == 0;
    }

    /**
     *
     * @param interaction
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Interaction interaction){
        if (unambiguousExactCuratedInteractionComparator == null){
            unambiguousExactCuratedInteractionComparator = new UnambiguousExactCuratedInteractionBaseComparator();
        }

        if (interaction == null){
            return 0;
        }

        int hashcode = 31;
        List<Participant> list1 = new ArrayList<Participant>(interaction.getParticipants());

        Collections.sort(list1, unambiguousExactCuratedInteractionComparator.getParticipantCollectionComparator().getObjectComparator());
        for (Participant participant : list1){
            hashcode = 31*hashcode + UnambiguousExactParticipantBaseComparator.hashCode(participant);
        }
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(interaction.getType());
        hashcode = 31 * hashcode + (interaction.isNegative() ? 0 : 1);
        hashcode = 31 * hashcode + UnambiguousCvTermComparator.hashCode(interaction.getSource());

        return hashcode;
    }
}
