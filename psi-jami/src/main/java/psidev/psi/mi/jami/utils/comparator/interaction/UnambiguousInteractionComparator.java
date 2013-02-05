package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Unambiguous Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses UnambiguousExperimentalInteractionComparator to compare experimental interactions
 * - It uses UnambiguousModelledInteractionComparator to compare modelled interactions
 * - It uses UnambiguousCooperativeInteractionComparator to compare cooperative interactions
 * - It uses UnambiguousAllostericInteractionComparator to compare allosteric interactions
 * - It uses UnambiguousInteractionBaseComparator to compare basic interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousInteractionComparator extends InteractionComparator {

    private static UnambiguousInteractionComparator unambiguousInteractionComparator;

    /**
     * Creates a new UnambiguousInteractionComparator.
     */
    public UnambiguousInteractionComparator() {
        super(new UnambiguousInteractionBaseComparator(), new UnambiguousExperimentalInteractionComparator(), new UnambiguousCooperativeInteractionComparator(), new UnambiguousAllostericInteractionComparator());
    }

    @Override
    public UnambiguousInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public UnambiguousExperimentalInteractionComparator getExperimentalInteractionComparator() {
        return (UnambiguousExperimentalInteractionComparator) this.experimentalInteractionComparator;
    }

    @Override
    public UnambiguousCooperativeInteractionComparator getCooperativeInteractionComparator() {
        return (UnambiguousCooperativeInteractionComparator) this.cooperativeInteractionComparator;
    }

    @Override
    public UnambiguousAllostericInteractionComparator getAllostericInteractionComparator() {
        return (UnambiguousAllostericInteractionComparator) allostericInteractionComparator;
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses UnambiguousExperimentalInteractionComparator to compare experimental interactions
     * - It uses UnambiguousModelledInteractionComparator to compare modelled interactions
     * - It uses UnambiguousCooperativeInteractionComparator to compare cooperative interactions
     * - It uses UnambiguousAllostericInteractionComparator to compare allosteric interactions
     * - It uses UnambiguousInteractionBaseComparator to compare basic interaction properties
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousInteractionComparator == null){
            unambiguousInteractionComparator = new UnambiguousInteractionComparator();
        }

        return unambiguousInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
