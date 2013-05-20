package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CooperativeInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous exact CooperativeInteractionComparator.
 *
 * It will first compare the cooperative mechanism using UnambiguousCvTermComparator. If the cooperative mechanisms are the same,
 * it will compare the effect outcome using UnambiguousCvTermComparator. If the effect outcomes are the same, it will compare the responses using
 * UnambiguousCvTermComparator. If the responses are the same, it will compare the affected interactions using a UnambiguousExactModelledInteractionComparator that does not compare
 * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
 * UnambiguousExactModelledInteractionComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousExactCooperativeInteractionComparator extends CooperativeInteractionComparator{

    private static UnambiguousExactCooperativeInteractionComparator unambiguousExactCooperativeInteractionComparator;

    /**
     * Creates a new UnambiguousExactCooperativeInteractionComparator. It will use a UnambiguousExactModelledInteractionComparator to
     * compare basic interaction properties and affected interactions, UnambiguousCvTermComparator to compare responses, outcome effects and mechanisms
     */
    public UnambiguousExactCooperativeInteractionComparator() {
        super(new UnambiguousExactModelledInteractionComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousExactModelledInteractionComparator getInteractionComparator() {
        return (UnambiguousExactModelledInteractionComparator) this.interactionComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the cooperative mechanism using UnambiguousCvTermComparator. If the cooperative mechanisms are the same,
     * it will compare the effect outcome using UnambiguousCvTermComparator. If the effect outcomes are the same, it will compare the responses using
     * UnambiguousCvTermComparator. If the responses are the same, it will compare the affected interactions using a UnambiguousExactModelledInteractionComparator that does not compare
     * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
     * UnambiguousExactModelledInteractionComparator.
     *
     **/
    public int compare(CooperativeInteraction interaction1, CooperativeInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactCooperativeInteractionComparator to know if two cooperative interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two cooperative interactions are equal
     */
    public static boolean areEquals(CooperativeInteraction interaction1, CooperativeInteraction interaction2){
        if (unambiguousExactCooperativeInteractionComparator == null){
            unambiguousExactCooperativeInteractionComparator = new UnambiguousExactCooperativeInteractionComparator();
        }

        return unambiguousExactCooperativeInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
