package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.Allostery;

/**
 * Default comparator for Allostery
 *
 * It will first compare basic cooperative effect properties using DefaultCooperativeEffectBaseComparator.
 * Then, it will compare the allosteric effector types :
 * - molecule effector types always come first
 * - if both allosteric effector are molecule effectors, it will use DefaultMoleculeEffectorComparator to compare them
 * - if both allosteric effector are feature modification effectors, it will use DefaultFeatureModificationEffectorComparator to compare them
 * Then, it will compare the allosteric mechanisms using DefaultCvTermComparator
 * Then, it will compare the allostery type using DefaultCvTermComparator
 * Finally, it will compare the allosteric molecule using DefaultModelledParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultAllosteryComparator extends AllosteryComparator{

    private static DefaultAllosteryComparator defaultAllosteryComparator;

    public DefaultAllosteryComparator() {
        super(new DefaultCooperativeEffectBaseComparator(), new DefaultMoleculeEffectorComparator(), new DefaultFeatureModificationEffectorComparator());
    }

    @Override
    public DefaultCooperativeEffectBaseComparator getCooperativeEffectComparator() {
        return (DefaultCooperativeEffectBaseComparator) super.getCooperativeEffectComparator();
    }

    @Override
    public DefaultMoleculeEffectorComparator getMoleculeEffectorComparator() {
        return (DefaultMoleculeEffectorComparator) super.getMoleculeEffectorComparator();
    }

    @Override
    public DefaultFeatureModificationEffectorComparator getFeatureModificationEffectorComparator() {
        return (DefaultFeatureModificationEffectorComparator) super.getFeatureModificationEffectorComparator();
    }

    /**
     * It will first compare basic cooperative effect properties using DefaultCooperativeEffectBaseComparator.
     * Then, it will compare the allosteric effector types :
     * - molecule effector types always come first
     * - if both allosteric effector are molecule effectors, it will use DefaultMoleculeEffectorComparator to compare them
     * - if both allosteric effector are feature modification effectors, it will use DefaultFeatureModificationEffectorComparator to compare them
     * Then, it will compare the allosteric mechanisms using DefaultCvTermComparator
     * Then, it will compare the allostery type using DefaultCvTermComparator
     * Finally, it will compare the allosteric molecule using DefaultModelledParticipantComparator
     */
    public int compare(Allostery effect1, Allostery effect2) {
        return super.compare(effect1, effect2);
    }

    /**
     * Use DefaultAllosteryComparator to know if two allostery are equals.
     * @param effect1
     * @param effect2
     * @return true if the two Allostery are equal
     */
    public static boolean areEquals(Allostery effect1, Allostery effect2){
        if (defaultAllosteryComparator == null){
            defaultAllosteryComparator = new DefaultAllosteryComparator();
        }

        return defaultAllosteryComparator.compare(effect1, effect2) == 0;
    }
}
