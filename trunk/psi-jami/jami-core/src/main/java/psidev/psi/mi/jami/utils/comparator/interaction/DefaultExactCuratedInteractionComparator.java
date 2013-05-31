package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Default exact curated Generic interaction comparator.
 * Modelled interactions come first and then experimental interactions
 * - It uses DefaultExactCuratedInteractionEvidenceComparator to compare experimental interactions
 * - It uses DefaultExactCuratedModelledInteractionComparator to compare modelled interactions
 * - It uses DefaultCuratedInteractionBaseComparator to compare basic interaction properties
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultExactCuratedInteractionComparator extends InteractionComparator {
    private static DefaultExactCuratedInteractionComparator defaultExactCuratedInteractionComparator;

    /**
     * Creates a new DefaultExactCuratedInteractionComparator.
     */
    public DefaultExactCuratedInteractionComparator() {
        super(new DefaultCuratedInteractionBaseComparator(), new DefaultExactCuratedModelledInteractionComparator(), new DefaultExactCuratedInteractionEvidenceComparator());
    }

    @Override
    public DefaultCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultCuratedInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public DefaultExactCuratedInteractionEvidenceComparator getExperimentalInteractionComparator() {
        return (DefaultExactCuratedInteractionEvidenceComparator) this.experimentalInteractionComparator;
    }

    @Override
    public DefaultExactCuratedModelledInteractionComparator getModelledInteractionComparator() {
        return (DefaultExactCuratedModelledInteractionComparator) super.getModelledInteractionComparator();
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * Modelled interactions come first and then experimental interactions
     * - It uses DefaultExactCuratedInteractionEvidenceComparator to compare experimental interactions
     * - It uses DefaultExactCuratedModelledInteractionComparator to compare modelled interactions
     * - It uses DefaultExactCuratedInteractionBaseComparator to compare basic interaction properties
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExactCuratedInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultExactCuratedInteractionComparator == null){
            defaultExactCuratedInteractionComparator = new DefaultExactCuratedInteractionComparator();
        }

        return defaultExactCuratedInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
