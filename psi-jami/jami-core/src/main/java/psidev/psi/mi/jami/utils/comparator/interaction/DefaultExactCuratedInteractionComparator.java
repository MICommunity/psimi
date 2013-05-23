package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Default exact curated Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses DefaultExactInteractionEvidenceComparator to compare experimental interactions
 * - It uses DefaultExactCuratedModelledInteractionComparator to compare modelled interactions
 * - It uses DefaultExactCuratedCooperativeInteractionComparator to compare cooperative interactions
 * - It uses DefaultExactCuratedAllostericInteractionComparator to compare allosteric interactions
 * - It uses DefaultExactInteractionBaseComparator to compare basic interaction properties
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
        super(new DefaultExactInteractionBaseComparator(), new DefaultExactCuratedModelledInteractionComparator(), new DefaultExactInteractionEvidenceComparator());
    }

    @Override
    public DefaultExactInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultExactInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public DefaultExactInteractionEvidenceComparator getExperimentalInteractionComparator() {
        return (DefaultExactInteractionEvidenceComparator) this.experimentalInteractionComparator;
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses DefaultExactInteractionEvidenceComparator to compare experimental interactions
     * - It uses DefaultExactCuratedModelledInteractionComparator to compare modelled interactions
     * - It uses DefaultExactCuratedCooperativeInteractionComparator to compare cooperative interactions
     * - It uses DefaultExactCuratedAllostericInteractionComparator to compare allosteric interactions
     * - It uses DefaultExactInteractionBaseComparator to compare basic interaction properties
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
