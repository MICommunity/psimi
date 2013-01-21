package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;

/**
 * Default curated ModelledInteraction comparator.
 *
 * It will use a DefaultCuratedInteractionComparator to compare basic interaction properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCuratedModelledInteractionComparator extends ModelledInteractionComparator {

    private static DefaultCuratedModelledInteractionComparator defaultCuratedModelledInteractionComparator;

    /**
     * Creates a new DefaultCuratedModelledInteractionComparator. It will use a DefaultCuratedInteractionComparator to
     * compare basic interaction properties
     */
    public DefaultCuratedModelledInteractionComparator() {
        super(new DefaultCuratedInteractionComparator());
    }

    @Override
    public DefaultCuratedInteractionComparator getInteractionComparator() {
        return (DefaultCuratedInteractionComparator) this.interactionComparator;
    }

    @Override
    /**
     * It will use a DefaultCuratedInteractionComparator to compare basic interaction properties.
     */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultCuratedModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (defaultCuratedModelledInteractionComparator == null){
            defaultCuratedModelledInteractionComparator = new DefaultCuratedModelledInteractionComparator();
        }

        return defaultCuratedModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
