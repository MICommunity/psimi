package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.CooperativeEffect;

/**
 * Unambiguous cooperative effect comparator
 *
 * Allostery effects will always come before basic cooperative effects (preassembly)
 *
 * - It will use UnambiguousAllosteryComparator to compare allostery
 * - It will use UnambiguousCooperativeEffectBaseComparator to compare basic cooperative effects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousCooperativeEffectComparator extends CooperativeEffectComparator {

    private static UnambiguousCooperativeEffectComparator unambiguousCooperativeEffectComparator;

    public UnambiguousCooperativeEffectComparator() {
        super(new UnambiguousAllosteryComparator());
    }

    @Override
    public UnambiguousAllosteryComparator getAllosteryComparator() {
        return (UnambiguousAllosteryComparator) super.getAllosteryComparator();
    }

    /**
     * Allostery effects will always come before basic cooperative effects (preassembly)
     *
     * - It will use DefaultExactAllosteryComparator to compare allostery
     * - It will use DefaultExactCooperativeEffectBaseComparator to compare basic cooperative effects
     */
    public int compare(CooperativeEffect effect1, CooperativeEffect effect2) {
        return super.compare(effect1, effect2);
    }

    /**
     * Use UnambiguousCooperativeEffectComparator to know if two CooperativeEffects are equals.
     * @param effect1
     * @param effect2
     * @return true if the two CooperativeEffects are equal
     */
    public static boolean areEquals(CooperativeEffect effect1, CooperativeEffect effect2){
        if (unambiguousCooperativeEffectComparator == null){
            unambiguousCooperativeEffectComparator = new UnambiguousCooperativeEffectComparator();
        }

        return unambiguousCooperativeEffectComparator.compare(effect1, effect2) == 0;
    }
}
