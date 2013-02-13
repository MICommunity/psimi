package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Default curated Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses DefaultCuratedInteractionEvidenceComparator to compare experimental interactions
 * - It uses DefaultCuratedModelledInteractionComparator to compare modelled interactions
 * - It uses DefaultCuratedCooperativeInteractionComparator to compare cooperative interactions
 * - It uses DefaultCuratedAllostericInteractionComparator to compare allosteric interactions
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
        super(new DefaultCuratedInteractionBaseComparator(), new DefaultCuratedInteractionEvidenceComparator(), new DefaultCuratedCooperativeInteractionComparator(), new DefaultCuratedAllostericInteractionComparator());
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
    public DefaultCuratedCooperativeInteractionComparator getCooperativeInteractionComparator() {
        return (DefaultCuratedCooperativeInteractionComparator) this.cooperativeInteractionComparator;
    }

    @Override
    public DefaultCuratedAllostericInteractionComparator getAllostericInteractionComparator() {
        return (DefaultCuratedAllostericInteractionComparator) allostericInteractionComparator;
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses DefaultCuratedInteractionEvidenceComparator to compare experimental interactions
     * - It uses DefaultCuratedModelledInteractionComparator to compare modelled interactions
     * - It uses DefaultCuratedCooperativeInteractionComparator to compare cooperative interactions
     * - It uses DefaultCuratedAllostericInteractionComparator to compare allosteric interactions
     * - It uses DefaultCuratedInteractionBaseComparator to compare basic interaction properties
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
