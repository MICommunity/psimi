package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousComponentComparator;

/**
 * Unambiguous curated ModelledInteraction comparator.
 *
 * It will use a UnambiguousCuratedInteractionComparator to compare basic interaction properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousCuratedModelledInteractionComparator extends ModelledInteractionComparator{

    private static UnambiguousCuratedModelledInteractionComparator unambiguousCuratedModelledInteractionComparator;

    /**
     * Creates a new UnambiguousCuratedModelledInteractionComparator. It will use a UnambiguousCuratedInteractionComparator to
     * compare basic interaction properties
     */
    public UnambiguousCuratedModelledInteractionComparator() {
        super(new InteractionComparator<Component>(new UnambiguousComponentComparator(), new UnambiguousCvTermComparator()));
    }

    @Override
    public InteractionComparator<Component> getInteractionComparator() {
        return (InteractionComparator<Component>) this.interactionComparator;
    }

    @Override
    /**
     * It will use a UnambiguousCuratedInteractionComparator to compare basic interaction properties.
     */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousCuratedModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (unambiguousCuratedModelledInteractionComparator == null){
            unambiguousCuratedModelledInteractionComparator = new UnambiguousCuratedModelledInteractionComparator();
        }

        return unambiguousCuratedModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
