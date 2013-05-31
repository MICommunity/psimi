package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * Basic cooperative effect comparator
 *
 * Allostery effects will always come before basic cooperative effects (preassembly)
 *
 * - It will use AllosteryComparator to compare allostery
 * - It will use CooperativeEffectBaseComparator to compare basic cooperative effects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class CooperativeEffectComparator implements Comparator<CooperativeEffect>{

    private AllosteryComparator allosteryComparator;

    public CooperativeEffectComparator(AllosteryComparator allosteryComparator){

        if (allosteryComparator == null){
            throw new IllegalArgumentException("The AllosteryComparator is required to compare basic properties of allostery");
        }
        this.allosteryComparator = allosteryComparator;
    }

    public AllosteryComparator getAllosteryComparator() {
        return allosteryComparator;
    }

    /**
     * Allostery effects will always come before basic cooperative effects (preassembly)
     *
     * - It will use AllosteryComparator to compare allostery
     * - It will use CooperativeEffectBaseComparator to compare basic cooperative effects
     * @param cooperativeEffect1
     * @param cooperativeEffect2
     * @return
     */
    public int compare(CooperativeEffect cooperativeEffect1, CooperativeEffect cooperativeEffect2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (cooperativeEffect1 == null && cooperativeEffect2 == null){
            return EQUAL;
        }
        else if (cooperativeEffect1 == null){
            return AFTER;
        }
        else if (cooperativeEffect2 == null){
            return BEFORE;
        }
        else {
            // first check if both cooperative effects are from the same interface

            // both are allostery
            boolean isAllostery1 = cooperativeEffect1 instanceof Allostery;
            boolean isAllostery2 = cooperativeEffect2 instanceof Allostery;
            if (isAllostery1 && isAllostery2){
                return allosteryComparator.compare((Allostery) cooperativeEffect1, (Allostery) cooperativeEffect2);
            }
            // the allostery is before
            else if (isAllostery1){
                return BEFORE;
            }
            else if (isAllostery2){
                return AFTER;
            }
            else {
                // both are simple preassembly effects
                return allosteryComparator.getCooperativeEffectComparator().compare(cooperativeEffect1, cooperativeEffect2);
            }
        }
    }
}
