package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;

/**
 * Default exact curated ModelledInteraction comparator.
 *
 * It will use a DefaultExactCuratedInteractionComparator to compare basic interaction properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultExactCuratedModelledInteractionComparator extends ModelledInteractionComparator{

    private static DefaultExactCuratedModelledInteractionComparator defaultExactCuratedModelledInteractionComparator;

    /**
     * Creates a new DefaultExactCuratedModelledInteractionComparator. It will use a DefaultExactCuratedInteractionComparator to
     * compare basic interaction properties
     */
    public DefaultExactCuratedModelledInteractionComparator() {
        super(new DefaultExactCuratedInteractionComparator());
    }

    @Override
    public DefaultExactCuratedInteractionComparator getInteractionComparator() {
        return (DefaultExactCuratedInteractionComparator) this.interactionComparator;
    }

    @Override
    /**
     * It will use a DefaultExactCuratedInteractionComparator to compare basic interaction properties.
     */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExactCuratedModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (defaultExactCuratedModelledInteractionComparator == null){
            defaultExactCuratedModelledInteractionComparator = new DefaultExactCuratedModelledInteractionComparator();
        }

        return defaultExactCuratedModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
