package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorBaseComparator;

/**
 * Default exact interaction comparator only based on the interactors of an interaction.
 *
 * It will use a DefaultExactInteractorComparator to compare the interactors involved in the interaction.
 *
 * It will ignore all other properties of an Interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactInteractionInteractorComparator extends InteractionInteractorComparator{

    private static DefaultExactInteractionInteractorComparator defaultExactInteractionInteractorComparator;

    /**
     * Creates a new DefaultExactInteractionInteractorComparator. It will use a DefaultExactInteractorComparator to
     * compare interactors involved in the interaction
     */
    public DefaultExactInteractionInteractorComparator() {
        super(new DefaultExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will use a DefaultExactInteractorComparator to compare the interactors involved in the interaction.
     *
     * It will ignore all other properties of an Interaction
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExactInteractionInteractorComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultExactInteractionInteractorComparator == null){
            defaultExactInteractionInteractorComparator = new DefaultExactInteractionInteractorComparator();
        }

        return defaultExactInteractionInteractorComparator.compare(interaction1, interaction2) == 0;
    }
}
