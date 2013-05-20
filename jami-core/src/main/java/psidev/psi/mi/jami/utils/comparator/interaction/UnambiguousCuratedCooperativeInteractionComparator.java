package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CooperativeInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous curated CooperativeInteractionComparator.
 *
 * It will first compare the cooperative mechanism using UnambiguousCvTermComparator. If the cooperative mechanisms are the same,
 * it will compare the effect outcome using UnambiguousCvTermComparator. If the effect outcomes are the same, it will compare the responses using
 * UnambiguousCvTermComparator. If the responses are the same, it will compare the affected interactions using a UnambiguousCuratedModelledInteractionComparator that does not compare
 * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
 * UnambiguousCuratedModelledInteractionComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousCuratedCooperativeInteractionComparator extends CooperativeInteractionComparator{

    private static UnambiguousCuratedCooperativeInteractionComparator unambiguousCuratedCooperativeInteractionComparator;

    /**
     * Creates a new UnambiguousCuratedCooperativeInteractionComparator. It will use a UnambiguousCuratedModelledInteractionComparator to
     * compare basic interaction properties and affected interactions, UnambiguousCvTermComparator to compare responses, outcome effects and mechanisms
     */
    public UnambiguousCuratedCooperativeInteractionComparator() {
        super(new UnambiguousCuratedModelledInteractionComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCuratedModelledInteractionComparator getInteractionComparator() {
        return (UnambiguousCuratedModelledInteractionComparator) this.interactionComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the cooperative mechanism using UnambiguousCvTermComparator. If the cooperative mechanisms are the same,
     * it will compare the effect outcome using UnambiguousCvTermComparator. If the effect outcomes are the same, it will compare the responses using
     * UnambiguousCvTermComparator. If the responses are the same, it will compare the affected interactions using a UnambiguousCuratedModelledInteractionComparator that does not compare
     * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
     * UnambiguousCuratedModelledInteractionComparator.
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
        if (unambiguousCuratedCooperativeInteractionComparator == null){
            unambiguousCuratedCooperativeInteractionComparator = new UnambiguousCuratedCooperativeInteractionComparator();
        }

        return unambiguousCuratedCooperativeInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
