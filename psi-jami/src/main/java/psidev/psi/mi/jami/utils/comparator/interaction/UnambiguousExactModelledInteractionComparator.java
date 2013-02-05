package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactComponentComparator;

/**
 * Unambiguous exact ModelledInteraction comparator.
 *
 * It will use a UnambiguousExactInteractionBaseComparator to compare basic interaction properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousExactModelledInteractionComparator extends ModelledInteractionComparator{

    private static UnambiguousExactModelledInteractionComparator unambiguousExactModelledInteractionComparator;

    /**
     * Creates a new UnambiguousExactModelledInteractionComparator. It will use a UnambiguousExactInteractionBaseComparator to
     * compare basic interaction properties
     */
    public UnambiguousExactModelledInteractionComparator() {
        super(new InteractionBaseComparator<Component>(new UnambiguousExactComponentComparator(), new UnambiguousCvTermComparator()));
    }

    @Override
    /**
     * It will use a UnambiguousExactCuratedInteractionBaseComparator to compare basic interaction properties.
     */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (unambiguousExactModelledInteractionComparator == null){
            unambiguousExactModelledInteractionComparator = new UnambiguousExactModelledInteractionComparator();
        }

        return unambiguousExactModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
