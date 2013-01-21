package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousComponentComparator;

/**
 * Unambiguous ModelledInteraction comparator.
 *
 * It will use a UnambiguousInteractionComparator to compare basic interaction properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousModelledInteractionComparator extends ModelledInteractionComparator{

    private static UnambiguousModelledInteractionComparator unambiguousModelledInteractionComparator;

    /**
     * Creates a new UnambiguousModelledInteractionComparator. It will use a UnambiguousInteractionComparator to
     * compare basic interaction properties
     */
    public UnambiguousModelledInteractionComparator() {
        super(new InteractionComparator<Component>(new UnambiguousComponentComparator(), new UnambiguousCvTermComparator()));
    }

    @Override
    /**
     * It will use a UnambiguousExactCuratedInteractionComparator to compare basic interaction properties.
     */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (unambiguousModelledInteractionComparator == null){
            unambiguousModelledInteractionComparator = new UnambiguousModelledInteractionComparator();
        }

        return unambiguousModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
