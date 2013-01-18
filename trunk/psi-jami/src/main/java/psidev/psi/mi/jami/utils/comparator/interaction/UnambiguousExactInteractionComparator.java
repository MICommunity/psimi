package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantComparator;

/**
 * Unambiguous exact Interaction comparator.
 *
 * It will first compare the participants using UnambiguousExactParticipantComparator. If the participants are the same, it will compare
 * the interaction types using UnambiguousCvTermComparator. If the interaction types are the same, it will compare the negative properties.
 * A negative interaction will come after a positive interaction.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactInteractionComparator extends InteractionComparator{

    private static UnambiguousExactInteractionComparator unambiguousExactInteractionComparator;

    /**
     * Creates a new UnambiguousExactInteractionComparator. It will use a UnambiguousExactParticipantComparator to
     * compare participants and  UnambiguousCvTermcomparator to compare interaction types
     */
    public UnambiguousExactInteractionComparator() {
        super(new UnambiguousExactParticipantComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) cvTermComparator;
    }

    @Override
    /**
     * It will first compare the participants using UnambiguousExactParticipantComparator. If the participants are the same, it will compare
     * the interaction types using UnambiguousCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction.
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousExactInteractionComparator == null){
            unambiguousExactInteractionComparator = new UnambiguousExactInteractionComparator();
        }

        return unambiguousExactInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
