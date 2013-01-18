package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorComparator;

/**
 * Unambiguous exact interaction comparator only based on the interactors of an interaction.
 *
 * It will use a UnambiguousExactInteractorComparator to compare the interactors involved in the interaction.
 *
 * It will ignore all other properties of an Interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactInteractionInteractorComparator extends InteractionInteractorComparator{

    private static UnambiguousExactInteractionInteractorComparator unambiguousInteractionInteractorComparator;

    /**
     * Creates a new UnambiguousExactInteractionInteractorComparator. It will use a UnambiguousInteractorComparator to
     * compare interactors involved in the interaction
     */
    public UnambiguousExactInteractionInteractorComparator() {
        super(new UnambiguousInteractorComparator());
    }

    @Override
    /**
     * It will use a UnambiguousInteractorComparator to compare the interactors involved in the interaction.
     *
     * It will ignore all other properties of an Interaction
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactInteractionInteractorComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousInteractionInteractorComparator == null){
            unambiguousInteractionInteractorComparator = new UnambiguousExactInteractionInteractorComparator();
        }

        return unambiguousInteractionInteractorComparator.compare(interaction1, interaction2) == 0;
    }
}
