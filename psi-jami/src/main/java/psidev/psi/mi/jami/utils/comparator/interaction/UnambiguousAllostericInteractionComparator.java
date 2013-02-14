package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.AllostericInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousModelledParticipantComparator;

/**
 * Unambiguous Allosteric interaction comparator.
 *
 * It will first compare the allosteric mechanisms using UnambiguousCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
 * using UnambiguousCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using UnambiguousModelledParticipantComparator.
 * If the allosteric molecules are the same, it will compare the allosteric effectors using the UnambiguousModelledParticipantComparator.
 * If the allosteric effectors are the same, it will compare the allosteric PTMs using UnambiguousBiologicalFeatureComparator. If the allosteric PTMs are the same,
 * it will compare the basic properties of a cooperative interaction using UnambiguousCooperativeInteractionComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousAllostericInteractionComparator extends AllostericInteractionComparator {

    private static UnambiguousAllostericInteractionComparator unambiguousAllostericInteractionComparator;

    /**
     * Creates a new UnambiguousAllostericInteractionComparator. It will use a UnambiguousCooperativeInteractionComparator to
     * compare basic cooperative interaction properties, UnambiguousCvTermComparator to compare responses, outcome effects and mechanisms,
     * UnambiguousModelledParticipantComparator to compare allosteric molecules and effectors and UnambiguousBiologicalFeatureComparator to compare allosteric PTMs
     */
    public UnambiguousAllostericInteractionComparator() {
        super(new UnambiguousCooperativeInteractionComparator(), new UnambiguousCvTermComparator(), new UnambiguousModelledParticipantComparator(), new UnambiguousModelledFeaturecomparator());
    }

    @Override
    public UnambiguousCooperativeInteractionComparator getInteractionComparator() {
        return (UnambiguousCooperativeInteractionComparator) this.interactionComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    public UnambiguousModelledParticipantComparator getComponentComparator() {
        return (UnambiguousModelledParticipantComparator) this.componentComparator;
    }

    @Override
    public UnambiguousModelledFeaturecomparator getPtmComparator() {
        return (UnambiguousModelledFeaturecomparator) ptmComparator;
    }

    @Override
    /**
     * It will first compare the allosteric mechanisms using UnambiguousCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
     * using UnambiguousCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using UnambiguousModelledParticipantComparator.
     * If the allosteric molecules are the same, it will compare the allosteric effectors using the UnambiguousModelledParticipantComparator.
     * If the allosteric effectors are the same, it will compare the allosteric PTMs using UnambiguousBiologicalFeatureComparator. If the allosteric PTMs are the same,
     * it will compare the basic properties of a cooperative interaction using UnambiguousCooperativeInteractionComparator.
     *
     **/
    public int compare(AllostericInteraction interaction1, AllostericInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousAllostericInteractionComparator to know if two allosteric interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two allosteric interactions are equal
     */
    public static boolean areEquals(AllostericInteraction interaction1, AllostericInteraction interaction2){
        if (unambiguousAllostericInteractionComparator == null){
            unambiguousAllostericInteractionComparator = new UnambiguousAllostericInteractionComparator();
        }

        return unambiguousAllostericInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
