package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.ModelledFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ModelledParticipantComparator;

import java.util.Comparator;

/**
 * Basic Allosteric interaction comparator.
 *
 * It will first compare the basic properties of a cooperative interaction using CooperativeInteractionComparator. It will then compare the allosteric mechanisms using AbstractCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
 * using AbsctractCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using ModelledParticipantComparator.
 * If the allosteric molecules are the same, it will compare the allosteric effectors using the ModelledParticipantComparator.
 * If the allosteric effectors are the same, it will compare the allosteric PTMs using ModelledFeatureComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class AllostericInteractionComparator implements Comparator<AllostericInteraction> {

    protected AbstractCvTermComparator cvTermComparator;
    protected CooperativeInteractionComparator interactionComparator;
    protected ModelledParticipantComparator componentComparator;
    protected ModelledFeatureComparator ptmComparator;

    /**
     * Creates a new AllostericInteractionComparator
     * @param interactionComparator : required to compare basic properties of a cooperative interaction
     * @param cvTermComparator : required to compare allosteric mechanism and types
     * @param componentComparator : required to compare allosteric molecules and allosteric effectors
     * @param ptmComparator : required to compare allosteric PTMs
     */
    public AllostericInteractionComparator(CooperativeInteractionComparator interactionComparator,
                                           AbstractCvTermComparator cvTermComparator, ModelledParticipantComparator componentComparator,
                                           ModelledFeatureComparator ptmComparator){
        if (interactionComparator == null){
            throw new IllegalArgumentException("The Interaction comparator is required to compare basic interaction properties. It cannot be null");
        }
        this.interactionComparator = interactionComparator;

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare allosteric mechanism and type. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;

        if (componentComparator == null){
            throw new IllegalArgumentException("The component comparator is required to compare allosteric effector and allosteric molecule. It cannot be null");
        }
        this.componentComparator = componentComparator;

        if (ptmComparator == null){
            throw new IllegalArgumentException("The Biological Feature comparator is required to compare allosteric ptm. It cannot be null");
        }
        this.ptmComparator = ptmComparator;
    }

    public CooperativeInteractionComparator getInteractionComparator() {
        return interactionComparator;
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public ModelledParticipantComparator getComponentComparator() {
        return componentComparator;
    }

    public ModelledFeatureComparator getPtmComparator() {
        return ptmComparator;
    }

    /**
     * It will first compare the basic properties of a cooperative interaction using CooperativeInteractionComparator. It will then compare the allosteric mechanisms using AbstractCvTermComparator. If the mechanisms are the same, it will compare the allosteric types
     * using AbsctractCvTermComparator. If the allosteric types are the same, it will compare the allosteric molecule using ModelledParticipantComparator.
     * If the allosteric molecules are the same, it will compare the allosteric effectors using the ModelledParticipantComparator.
     * If the allosteric effectors are the same, it will compare the allosteric PTMs using ModelledFeatureComparator.
     *
     * @param allostericInteraction1
     * @param allostericInteraction2
     * @return
     */
    public int compare(AllostericInteraction allostericInteraction1, AllostericInteraction allostericInteraction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (allostericInteraction1 == null && allostericInteraction2 == null){
            return EQUAL;
        }
        else if (allostericInteraction1 == null){
            return AFTER;
        }
        else if (allostericInteraction2 == null){
            return BEFORE;
        }
        else {
            // first compares basic properties
            int comp = interactionComparator.compare(allostericInteraction1, allostericInteraction2);
            if (comp != 0){
                return comp;
            }

            // first check allosteric mechanism
            CvTerm mechanism1 = allostericInteraction1.getAllostericMechanism();
            CvTerm mechanism2 = allostericInteraction2.getAllostericMechanism();

            comp = cvTermComparator.compare(mechanism1, mechanism2);
            if (comp != 0){
                return comp;
            }

            // then compare allostery type
            CvTerm type1 = allostericInteraction1.getType();
            CvTerm type2 = allostericInteraction2.getType();

            comp = cvTermComparator.compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // then compare allosteric molecule
            ModelledParticipant molecule1 = allostericInteraction1.getAllostericMolecule();
            ModelledParticipant molecule2 = allostericInteraction2.getAllostericMolecule();

            comp = componentComparator.compare(molecule1, molecule2);
            if (comp != 0){
                return comp;
            }

            // then compare allosteric effector
            ModelledParticipant effector1 = allostericInteraction1.getAllostericEffector();
            ModelledParticipant effector2 = allostericInteraction2.getAllostericEffector();

            comp = componentComparator.compare(effector1, effector2);
            if (comp != 0){
                return comp;
            }

            // then compare post translational modification
            ModelledFeature feature1 = allostericInteraction1.getAllostericPtm();
            ModelledFeature feature2 = allostericInteraction2.getAllostericPtm();

            return ptmComparator.compare(feature1, feature2);
        }
    }
}
