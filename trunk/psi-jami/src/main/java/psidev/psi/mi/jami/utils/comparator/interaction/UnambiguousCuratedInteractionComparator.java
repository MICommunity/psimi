package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Unambiguous curated Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses UnambiguousInteractionEvidenceComparator to compare experimental interactions
 * - It uses UnambiguousCuratedModelledInteractionComparator to compare modelled interactions
 * - It uses UnambiguousCuratedCooperativeInteractionComparator to compare cooperative interactions
 * - It uses UnambiguousCuratedAllostericInteractionComparator to compare allosteric interactions
 * - It uses UnambiguousInteractionBaseComparator to compare basic interaction properties
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousCuratedInteractionComparator extends InteractionComparator {
    private static UnambiguousCuratedInteractionComparator unambiguousCuratedInteractionComparator;

    /**
     * Creates a new UnambiguousCuratedInteractionComparator.
     */
    public UnambiguousCuratedInteractionComparator() {
        super(new UnambiguousInteractionBaseComparator(), new UnambiguousCuratedInteractionEvidenceComparator(), new UnambiguousCuratedCooperativeInteractionComparator(), new UnambiguousCuratedAllostericInteractionComparator());
    }

    @Override
    public UnambiguousInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public UnambiguousCuratedInteractionEvidenceComparator getExperimentalInteractionComparator() {
        return (UnambiguousCuratedInteractionEvidenceComparator) this.experimentalInteractionComparator;
    }

    @Override
    public UnambiguousCuratedCooperativeInteractionComparator getCooperativeInteractionComparator() {
        return (UnambiguousCuratedCooperativeInteractionComparator) this.cooperativeInteractionComparator;
    }

    @Override
    public UnambiguousCuratedAllostericInteractionComparator getAllostericInteractionComparator() {
        return (UnambiguousCuratedAllostericInteractionComparator) allostericInteractionComparator;
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses UnambiguousInteractionEvidenceComparator to compare experimental interactions
     * - It uses UnambiguousCuratedModelledInteractionComparator to compare modelled interactions
     * - It uses UnambiguousCuratedCooperativeInteractionComparator to compare cooperative interactions
     * - It uses UnambiguousCuratedAllostericInteractionComparator to compare allosteric interactions
     * - It uses UnambiguousInteractionBaseComparator to compare basic interaction properties
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousCuratedInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousCuratedInteractionComparator == null){
            unambiguousCuratedInteractionComparator = new UnambiguousCuratedInteractionComparator();
        }

        return unambiguousCuratedInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
