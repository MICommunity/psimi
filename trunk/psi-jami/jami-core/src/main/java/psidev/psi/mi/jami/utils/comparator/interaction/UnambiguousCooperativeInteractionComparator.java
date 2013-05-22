package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous CooperativeInteractionComparator.
 *
 * It will first compare the cooperative mechanism using UnambiguousCvTermComparator. If the cooperative mechanisms are the same,
 * it will compare the effect outcome using UnambiguousCvTermComparator. If the effect outcomes are the same, it will compare the responses using
 * UnambiguousCvTermComparator. If the responses are the same, it will compare the affected interactions using a UnambiguousModelledInteractionComparator that does not compare
 * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
 * UnambiguousModelledInteractionComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousCooperativeInteractionComparator extends CooperativeInteractionComparator{

    private static UnambiguousCooperativeInteractionComparator unambiguousCooperativeInteractionComparator;

    /**
     * Creates a new UnambiguousCooperativeInteractionComparator. It will use a UnambiguousModelledInteractionComparator to
     * compare basic interaction properties and affected interactions, UnambiguousCvTermComparator to compare responses, outcome effects and mechanisms
     */
    public UnambiguousCooperativeInteractionComparator() {
        super(new UnambiguousModelledInteractionComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousModelledInteractionComparator getInteractionComparator() {
        return (UnambiguousModelledInteractionComparator) this.interactionComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the cooperative mechanism using UnambiguousCvTermComparator. If the cooperative mechanisms are the same,
     * it will compare the effect outcome using UnambiguousCvTermComparator. If the effect outcomes are the same, it will compare the responses using
     * UnambiguousCvTermComparator. If the responses are the same, it will compare the affected interactions using a UnambiguousModelledInteractionComparator that does not compare
     * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
     * UnambiguousModelledInteractionComparator.
     *
     **/
    public int compare(CooperativeInteraction interaction1, CooperativeInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousCooperativeInteractionComparator to know if two cooperative interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two cooperative interactions are equal
     */
    public static boolean areEquals(CooperativeInteraction interaction1, CooperativeInteraction interaction2){
        if (unambiguousCooperativeInteractionComparator == null){
            unambiguousCooperativeInteractionComparator = new UnambiguousCooperativeInteractionComparator();
        }

        return unambiguousCooperativeInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
