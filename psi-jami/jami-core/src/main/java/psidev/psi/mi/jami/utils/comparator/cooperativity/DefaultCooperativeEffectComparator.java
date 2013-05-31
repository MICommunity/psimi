package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.DefaultModelledInteractionComparator;

/**
 * Default cooperative effect comparator
 *
 * Allostery effects will always come before basic cooperative effects (preassembly)
 *
 * - It will use DefaultAllosteryComparator to compare allostery
 * - It will use DefaultCooperativeEffectBaseComparator to compare basic cooperative effects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultCooperativeEffectComparator extends CooperativeEffectComparator{

    private static DefaultCooperativeEffectComparator defaultCooperativeEffectComparator;

    public DefaultCooperativeEffectComparator() {
        super(new DefaultAllosteryComparator());
    }

    @Override
    public DefaultAllosteryComparator getAllosteryComparator() {
        return (DefaultAllosteryComparator) super.getAllosteryComparator();
    }

    /**
     * Allostery effects will always come before basic cooperative effects (preassembly)
     *
     * - It will use DefaultAllosteryComparator to compare allostery
     * - It will use DefaultCooperativeEffectBaseComparator to compare basic cooperative effects
     */
    public int compare(CooperativeEffect effect1, CooperativeEffect effect2) {
        return super.compare(effect1, effect2);
    }

    /**
     * Use DefaultCooperativeEffectComparator to know if two CooperativeEffects are equals.
     * @param effect1
     * @param effect2
     * @return true if the two CooperativeEffects are equal
     */
    public static boolean areEquals(CooperativeEffect effect1, CooperativeEffect effect2){
        if (defaultCooperativeEffectComparator == null){
            defaultCooperativeEffectComparator = new DefaultCooperativeEffectComparator();
        }

        return defaultCooperativeEffectComparator.compare(effect1, effect2) == 0;
    }
}
