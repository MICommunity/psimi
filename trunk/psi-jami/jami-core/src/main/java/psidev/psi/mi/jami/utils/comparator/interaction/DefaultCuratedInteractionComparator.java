package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Default curated Generic interaction comparator.
 * Modelled interaction come first and then experimental interactions
 * - It uses DefaultCuratedInteractionEvidenceComparator to compare experimental interactions
 * - It uses DefaultCuratedModelledInteractionComparator to compare modelled interactions
 * - It uses DefaultCuratedInteractionBaseComparator to compare basic interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultCuratedInteractionComparator extends InteractionComparator {
    private static DefaultCuratedInteractionComparator defaultCuratedInteractionComparator;

    /**
     * Creates a new DefaultCuratedInteractionComparator.
     */
    public DefaultCuratedInteractionComparator() {
        super(new DefaultCuratedInteractionBaseComparator(), new DefaultCuratedModelledInteractionComparator(), new DefaultCuratedInteractionEvidenceComparator());
    }

    @Override
    public DefaultCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultCuratedInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public DefaultCuratedInteractionEvidenceComparator getExperimentalInteractionComparator() {
        return (DefaultCuratedInteractionEvidenceComparator) this.experimentalInteractionComparator;
    }

    @Override
    public DefaultCuratedModelledInteractionComparator getModelledInteractionComparator() {
        return (DefaultCuratedModelledInteractionComparator) super.getModelledInteractionComparator();
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * Modelled interaction come first and then experimental interactions
     * - It uses DefaultInteractionEvidenceComparator to compare experimental interactions
     * - It uses DefaultCuratedModelledInteractionComparator to compare modelled interactions
     * - It uses DefaultInteractionBaseComparator to compare basic interaction properties
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultCuratedInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultCuratedInteractionComparator == null){
            defaultCuratedInteractionComparator = new DefaultCuratedInteractionComparator();
        }

        return defaultCuratedInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
