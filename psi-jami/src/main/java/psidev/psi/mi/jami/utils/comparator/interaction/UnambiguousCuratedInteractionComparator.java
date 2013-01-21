package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousParticipantComparator;

/**
 * Unambiguous comparator for Curated interactions.
 *
 * It will first compare the sources of the interactions using UnambiguousCvTermComparator. If the sources are the same, t will compare the participants using UnambiguousParticipantComparator. If the participants are the same, it will compare
 * the interaction types using UnambiguousCvTermComparator. If the interaction types are the same, it will compare the negative properties.
 * A negative interaction will come after a positive interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousCuratedInteractionComparator extends CuratedInteractionComparator {

    private static UnambiguousCuratedInteractionComparator unambiguousCuratedInteractionComparator;

    /**
     * Creates a new UnambiguousCuratedInteractionComparator. It will use a UnambiguousParticipantComparator to
     * compare participants and UnambiguousCvTermComparator to compare interaction types
     */
    public UnambiguousCuratedInteractionComparator() {
        super(new UnambiguousParticipantComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) cvTermComparator;
    }

    @Override
    /**
     * It will first compare the sources of the interactions using UnambiguousCvTermComparator. If the sources are the same, t will compare the participants using UnambiguousParticipantComparator. If the participants are the same, it will compare
     * the interaction types using UnambiguousCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousCuratedInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousCuratedInteractionComparator == null){
            unambiguousCuratedInteractionComparator = new UnambiguousCuratedInteractionComparator();
        }

        return unambiguousCuratedInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
