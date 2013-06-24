package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.CooperativeEffect;

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

public class DefaultCooperativeEffectComparator {

    /**
     * Use DefaultCooperativeEffectComparator to know if two CooperativeEffects are equals.
     * @param cooperativeEffect1
     * @param cooperativeEffect2
     * @return true if the two CooperativeEffects are equal
     */
    public static boolean areEquals(CooperativeEffect cooperativeEffect1, CooperativeEffect cooperativeEffect2){
        if (cooperativeEffect1 == null && cooperativeEffect2 == null){
            return true;
        }
        else if (cooperativeEffect1 == null || cooperativeEffect2 == null){
            return false;
        }
        else {
            // first check if both cooperative effects are from the same interface

            // both are allostery
            boolean isAllostery1 = cooperativeEffect1 instanceof Allostery;
            boolean isAllostery2 = cooperativeEffect2 instanceof Allostery;
            if (isAllostery1 && isAllostery2){
                return DefaultAllosteryComparator.areEquals((Allostery) cooperativeEffect1, (Allostery) cooperativeEffect2);
            }
            // the allostery is before
            else if (isAllostery1 || isAllostery2){
                return false;
            }
            else {
                // both are simple preassembly effects
                return DefaultCooperativeEffectBaseComparator.areEquals(cooperativeEffect1, cooperativeEffect2);
            }
        }
    }
}
