package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactModelledParticipantComparator;

/**
 * Unambiguous exact Allosteric interaction comparator.
 *
 * It will first compare the allosteric mechanisms using UnambiguousCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
 * using UnambiguousCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using UnambiguousExactModelledParticipantComparator.
 * If the allosteric molecules are the same, it will compare the allosteric effectors using the UnambiguousExactModelledParticipantComparator.
 * If the allosteric effectors are the same, it will compare the allosteric PTMs using UnambiguousBiologicalFeatureComparator. If the allosteric PTMs are the same,
 * it will compare the basic properties of a cooperative interaction using UnambiguousExactCooperativeInteractionComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousExactAllostericInteractionComparator extends AllostericInteractionComparator{

    private static UnambiguousExactAllostericInteractionComparator unambiguousExactAllostericInteractionComparator;

    /**
     * Creates a new UnambiguousExactAllostericInteractionComparator. It will use a UnambiguousExactCooperativeInteractionComparator to
     * compare basic cooperative interaction properties, UnambiguousCvTermComparator to compare responses, outcome effects and mechanisms,
     * UnambiguousExactModelledParticipantComparator to compare allosteric molecules and effectors and UnambiguousBiologicalFeatureComparator to compare allosteric PTMs
     */
    public UnambiguousExactAllostericInteractionComparator() {
        super(new UnambiguousExactCooperativeInteractionComparator(), new UnambiguousCvTermComparator(), new UnambiguousExactModelledParticipantComparator(), new UnambiguousModelledFeaturecomparator());
    }

    @Override
    public UnambiguousExactCooperativeInteractionComparator getInteractionComparator() {
        return (UnambiguousExactCooperativeInteractionComparator) this.interactionComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    public UnambiguousExactModelledParticipantComparator getComponentComparator() {
        return (UnambiguousExactModelledParticipantComparator) this.componentComparator;
    }

    @Override
    public UnambiguousModelledFeaturecomparator getPtmComparator() {
        return (UnambiguousModelledFeaturecomparator) ptmComparator;
    }

    @Override
    /**
     * It will first compare the allosteric mechanisms using UnambiguousCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
     * using UnambiguousCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using UnambiguousExactModelledParticipantComparator.
     * If the allosteric molecules are the same, it will compare the allosteric effectors using the UnambiguousExactModelledParticipantComparator.
     * If the allosteric effectors are the same, it will compare the allosteric PTMs using UnambiguousBiologicalFeatureComparator. If the allosteric PTMs are the same,
     * it will compare the basic properties of a cooperative interaction using UnambiguousExactCooperativeInteractionComparator.
     *
     **/
    public int compare(AllostericInteraction interaction1, AllostericInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactAllostericInteractionComparator to know if two allosteric interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two allosteric interactions are equal
     */
    public static boolean areEquals(AllostericInteraction interaction1, AllostericInteraction interaction2){
        if (unambiguousExactAllostericInteractionComparator == null){
            unambiguousExactAllostericInteractionComparator = new UnambiguousExactAllostericInteractionComparator();
        }

        return unambiguousExactAllostericInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
