package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Unambiguous exact curated Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses UnambiguousExactCuratedInteractionEvidenceComparator to compare experimental interactions
 * - It uses UnambiguousExactCuratedModelledInteractionComparator to compare modelled interactions
 * - It uses UnambiguousExactCuratedCooperativeInteractionComparator to compare cooperative interactions
 * - It uses UnambiguousExactCuratedAllostericInteractionComparator to compare allosteric interactions
 * - It uses UnambiguousExactCuratedInteractionBaseComparator to compare basic interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousExactCuratedInteractionComparator extends InteractionComparator {

    private static UnambiguousExactCuratedInteractionComparator unambiguousExactCuratedInteractionComparator;

    /**
     * Creates a new UnambiguousExactCuratedInteractionComparator.
     */
    public UnambiguousExactCuratedInteractionComparator() {
        super(new UnambiguousExactCuratedInteractionBaseComparator(), new UnambiguousExactCuratedInteractionEvidenceComparator(), new UnambiguousExactCuratedCooperativeInteractionComparator(), new UnambiguousExactCuratedAllostericInteractionComparator());
    }

    @Override
    public UnambiguousExactCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousExactCuratedInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public UnambiguousExactCuratedInteractionEvidenceComparator getExperimentalInteractionComparator() {
        return (UnambiguousExactCuratedInteractionEvidenceComparator) this.experimentalInteractionComparator;
    }

    @Override
    public UnambiguousExactCuratedCooperativeInteractionComparator getCooperativeInteractionComparator() {
        return (UnambiguousExactCuratedCooperativeInteractionComparator) this.cooperativeInteractionComparator;
    }

    @Override
    public UnambiguousExactCuratedAllostericInteractionComparator getAllostericInteractionComparator() {
        return (UnambiguousExactCuratedAllostericInteractionComparator) allostericInteractionComparator;
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses UnambiguousExactCuratedInteractionEvidenceComparator to compare experimental interactions
     * - It uses UnambiguousExactCuratedModelledInteractionComparator to compare modelled interactions
     * - It uses UnambiguousExactCuratedCooperativeInteractionComparator to compare cooperative interactions
     * - It uses UnambiguousExactCuratedAllostericInteractionComparator to compare allosteric interactions
     * - It uses UnambiguousExactCuratedInteractionBaseComparator to compare basic interaction properties
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactCuratedInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousExactCuratedInteractionComparator == null){
            unambiguousExactCuratedInteractionComparator = new UnambiguousExactCuratedInteractionComparator();
        }

        return unambiguousExactCuratedInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
