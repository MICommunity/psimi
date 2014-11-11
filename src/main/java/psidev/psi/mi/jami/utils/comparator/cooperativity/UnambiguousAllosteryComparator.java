package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.Allostery;

/**
 * Unambiguous comparator for Allostery
 *
 * It will first compare basic cooperative effect properties using UnambiguousCooperativeEffectBaseComparator.
 * Then, it will compare the allosteric effector types :
 * - molecule effector types always come first
 * - if both allosteric effector are molecule effectors, it will use UnambiguousMoleculeEffectorComparator to compare them
 * - if both allosteric effector are feature modification effectors, it will use UnambiguousFeatureModificationEffectorComparator to compare them
 * Then, it will compare the allosteric mechanisms using UnambiguousCvTermComparator
 * Then, it will compare the allostery type using UnambiguousCvTermComparator
 * Finally, it will compare the allosteric molecule using UnambiguousModelledParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousAllosteryComparator extends AllosteryComparator{

    private static UnambiguousAllosteryComparator unambiguousAllosteryComparator;

    public UnambiguousAllosteryComparator() {
        super(new UnambiguousCooperativeEffectBaseComparator(), new UnambiguousMoleculeEffectorComparator(), new UnambiguousFeatureModificationEffectorComparator());
    }

    @Override
    public UnambiguousCooperativeEffectBaseComparator getCooperativeEffectComparator() {
        return (UnambiguousCooperativeEffectBaseComparator) super.getCooperativeEffectComparator();
    }

    @Override
    public UnambiguousMoleculeEffectorComparator getMoleculeEffectorComparator() {
        return (UnambiguousMoleculeEffectorComparator) super.getMoleculeEffectorComparator();
    }

    @Override
    public UnambiguousFeatureModificationEffectorComparator getFeatureModificationEffectorComparator() {
        return (UnambiguousFeatureModificationEffectorComparator) super.getFeatureModificationEffectorComparator();
    }

    /**
     *
     * It will first compare basic cooperative effect properties using UnambiguousCooperativeEffectBaseComparator.
     * Then, it will compare the allosteric effector types :
     * - molecule effector types always come first
     * - if both allosteric effector are molecule effectors, it will use UnambiguousMoleculeEffectorComparator to compare them
     * - if both allosteric effector are feature modification effectors, it will use UnambiguousFeatureModificationEffectorComparator to compare them
     * Then, it will compare the allosteric mechanisms using UnambiguousCvTermComparator
     * Then, it will compare the allostery type using UnambiguousCvTermComparator
     * Finally, it will compare the allosteric molecule using UnambiguousModelledParticipantComparator
     */
    public int compare(Allostery effect1, Allostery effect2) {
        return super.compare(effect1, effect2);
    }

    /**
     * Use UnambiguousAllosteryComparator to know if two allostery are equals.
     * @param effect1
     * @param effect2
     * @return true if the two Allostery are equal
     */
    public static boolean areEquals(Allostery effect1, Allostery effect2){
        if (unambiguousAllosteryComparator == null){
            unambiguousAllosteryComparator = new UnambiguousAllosteryComparator();
        }

        return unambiguousAllosteryComparator.compare(effect1, effect2) == 0;
    }
}
