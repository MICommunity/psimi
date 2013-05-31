package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * Basic comparator for Allostery
 *
 * It will first compare basic cooperative effect properties using CooperativeEffectBaseComparator.
 * Then, it will compare the allosteric effector types :
 * - molecule effector types always come first
 * - if both allosteric effector are molecule effectors, it will use MoleculeEffectorComparator to compare them
 * - if both allosteric effector are feature modification effectors, it will use FeatureModificationEffectorComparator to compare them
 * Then, it will compare the allosteric mechanisms using AbstractCvTermComparator
 * Then, it will compare the allostery type using AbstractCvTermComparator
 * Finally, it will compare the allosteric molecule using ModelledParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class AllosteryComparator implements Comparator<Allostery>{

    private CooperativeEffectBaseComparator cooperativeEffectComparator;
    private MoleculeEffectorComparator moleculeEffectorComparator;
    private FeatureModificationEffectorComparator featureModificationEffectorComparator;

    public AllosteryComparator(CooperativeEffectBaseComparator cooperativeEffectComparator, MoleculeEffectorComparator moleculeEffectorComparator,
                               FeatureModificationEffectorComparator featureModificationEffectorComparator){

        if (cooperativeEffectComparator == null){
             throw new IllegalArgumentException("The cooperative effect comparator is required to compare basic cooperative effect properties.");
        }
        this.cooperativeEffectComparator = cooperativeEffectComparator;

        if (moleculeEffectorComparator == null){
            throw new IllegalArgumentException("The molecule effector comparator is required to compare molecule effectors.");
        }
        this.moleculeEffectorComparator = moleculeEffectorComparator;

        if (featureModificationEffectorComparator == null){
            throw new IllegalArgumentException("The feature modification effector comparator is required to compare feature modification effectors.");
        }
        this.featureModificationEffectorComparator = featureModificationEffectorComparator;
    }

    public CooperativeEffectBaseComparator getCooperativeEffectComparator() {
        return cooperativeEffectComparator;
    }

    public MoleculeEffectorComparator getMoleculeEffectorComparator() {
        return moleculeEffectorComparator;
    }

    public FeatureModificationEffectorComparator getFeatureModificationEffectorComparator() {
        return featureModificationEffectorComparator;
    }

    /**
     * It will first compare basic cooperative effect properties using CooperativeEffectBaseComparator.
     * Then, it will compare the allosteric effector types :
     * - molecule effector types always come first
     * - if both allosteric effector are molecule effectors, it will use MoleculeEffectorComparator to compare them
     * - if both allosteric effector are feature modification effectors, it will use FeatureModificationEffectorComparator to compare them
     * Then, it will compare the allosteric mechanisms using AbstractCvTermComparator
     * Then, it will compare the allostery type using AbstractCvTermComparator
     * Finally, it will compare the allosteric molecule using ModelledParticipantComparator
     * @param allostery1
     * @param allostery2
     * @return
     */
    public int compare(Allostery allostery1, Allostery allostery2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (allostery1 == null && allostery2 == null){
            return EQUAL;
        }
        else if (allostery1 == null){
            return AFTER;
        }
        else if (allostery2 == null){
            return BEFORE;
        }
        else {
            int comp = cooperativeEffectComparator.compare(allostery1, allostery2);
            if (comp != 0){
               return comp;
            }

            // first compare allosteric effector
            AllostericEffector effector1 = allostery1.getAllostericEffector();
            AllostericEffector effector2 = allostery2.getAllostericEffector();

            // both effectors are molecules
            if (effector1.getEffectorType().equals(AllostericEffectorType.molecule) && effector2.getEffectorType().equals(AllostericEffectorType.molecule)){
                comp = moleculeEffectorComparator.compare((MoleculeEffector) effector1, (MoleculeEffector) effector2);
                if (comp != 0){
                    return comp;
                }
            }
            // effector 1 is molecule, comes first
            else if (effector1.getEffectorType().equals(AllostericEffectorType.molecule)){
                return BEFORE;
            }
            // effector 2 is molecule, comes first
            else if (effector2.getEffectorType().equals(AllostericEffectorType.molecule)){
                 return AFTER;
            }
            else {
                comp = featureModificationEffectorComparator.compare((FeatureModificationEffector) effector1, (FeatureModificationEffector) effector2);
                if (comp != 0){
                    return comp;
                }
            }

            // same allosteric effector, compare mechanism
            CvTerm mechanism1 = allostery1.getAllostericMechanism();
            CvTerm mechanism2 = allostery2.getAllostericMechanism();
            comp = cooperativeEffectComparator.getCvTermComparator().compare(mechanism1, mechanism2);
            if (comp != 0){
                return comp;
            }

            // compare allostery type
            CvTerm type1 = allostery1.getAllosteryType();
            CvTerm type2 = allostery2.getAllosteryType();
            comp = cooperativeEffectComparator.getCvTermComparator().compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // compare allosteric molecule
            ModelledParticipant mol1 = allostery1.getAllostericMolecule();
            ModelledParticipant mol2 = allostery2.getAllostericMolecule();
            comp = moleculeEffectorComparator.getParticipantComparator().compare(mol1, mol2);
            if (comp != 0){
                return comp;
            }

            return comp;
        }
    }
}
