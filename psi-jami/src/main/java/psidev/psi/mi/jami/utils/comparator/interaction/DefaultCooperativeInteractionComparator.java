package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CooperativeInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default CooperativeInteractionComparator.
 *
 * It will first compare the cooperative mechanism using DefaultCvTermComparator. If the cooperative mechanisms are the same,
 * it will compare the effect outcome using DefaultCvTermComparator. If the effect outcomes are the same, it will compare the responses using
 * DefaultCvTermComparator. If the responses are the same, it will compare the affected interactions using a DefaultModelledInteractionComparator that does not compare
 * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
 * DefaultModelledInteractionComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCooperativeInteractionComparator extends CooperativeInteractionComparator {

    private static DefaultCooperativeInteractionComparator defaultCooperativeInteractionComparator;

    /**
     * Creates a new DefaultCooperativeInteractionComparator. It will use a DefaultModelledInteractionComparator to
     * compare basic interaction properties and affected interactions, DefaultCvTermComparator to compare responses, outcome effects and mechanisms
     */
    public DefaultCooperativeInteractionComparator() {
        super(new DefaultModelledInteractionComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultModelledInteractionComparator getInteractionComparator() {
        return (DefaultModelledInteractionComparator) this.interactionComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the cooperative mechanism using DefaultCvTermComparator. If the cooperative mechanisms are the same,
     * it will compare the effect outcome using DefaultCvTermComparator. If the effect outcomes are the same, it will compare the responses using
     * DefaultCvTermComparator. If the responses are the same, it will compare the affected interactions using a DefaultModelledInteractionComparator that does not compare
     * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
     * DefaultModelledInteractionComparator.
     *
     **/
    public int compare(CooperativeInteraction interaction1, CooperativeInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultCooperativeInteractionComparator to know if two cooperative interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two cooperative interactions are equal
     */
    public static boolean areEquals(CooperativeInteraction interaction1, CooperativeInteraction interaction2){
        if (defaultCooperativeInteractionComparator == null){
            defaultCooperativeInteractionComparator = new DefaultCooperativeInteractionComparator();
        }

        return defaultCooperativeInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
