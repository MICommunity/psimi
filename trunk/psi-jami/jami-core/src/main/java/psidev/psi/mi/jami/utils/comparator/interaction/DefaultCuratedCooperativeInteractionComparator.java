package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default curated CooperativeInteractionComparator.
 *
 * It will first compare the cooperative mechanism using DefaultCvTermComparator. If the cooperative mechanisms are the same,
 * it will compare the effect outcome using DefaultCvTermComparator. If the effect outcomes are the same, it will compare the responses using
 * DefaultCvTermComparator. If the responses are the same, it will compare the affected interactions using a DefaultCuratedModelledInteractionComparator that does not compare
 * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
 * DefaultCuratedModelledInteractionComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCuratedCooperativeInteractionComparator extends CooperativeInteractionComparator{

    private static DefaultCuratedCooperativeInteractionComparator defaultCuratedCooperativeInteractionComparator;

    /**
     * Creates a new DefaultCuratedCooperativeInteractionComparator. It will use a DefaultCuratedModelledInteractionComparator to
     * compare basic interaction properties and affected interactions, DefaultCvTermComparator to compare responses, outcome effects and mechanisms
     */
    public DefaultCuratedCooperativeInteractionComparator() {
        super(new DefaultCuratedModelledInteractionComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultCuratedModelledInteractionComparator getInteractionComparator() {
        return (DefaultCuratedModelledInteractionComparator) this.interactionComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the cooperative mechanism using DefaultCvTermComparator. If the cooperative mechanisms are the same,
     * it will compare the effect outcome using DefaultCvTermComparator. If the effect outcomes are the same, it will compare the responses using
     * DefaultCvTermComparator. If the responses are the same, it will compare the affected interactions using a DefaultCuratedModelledInteractionComparator that does not compare
     * cooperative interaction properties (avoiding internal loop). If the affected interactions are the same, it will compare the basic interaction properties using
     * DefaultCuratedModelledInteractionComparator.
     *
     **/
    public int compare(CooperativeInteraction interaction1, CooperativeInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultCuratedCooperativeInteractionComparator to know if two cooperative interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two cooperative interactions are equal
     */
    public static boolean areEquals(CooperativeInteraction interaction1, CooperativeInteraction interaction2){
        if (defaultCuratedCooperativeInteractionComparator == null){
            defaultCuratedCooperativeInteractionComparator = new DefaultCuratedCooperativeInteractionComparator();
        }

        return defaultCuratedCooperativeInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
