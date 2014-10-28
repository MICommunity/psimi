package psidev.psi.mi.jami.utils.comparator;

import java.util.Comparator;

/**
 * Comparator for comparing integers.
 * By default, it will sort integers ascending but it can be changed by setting
 * property boolean ascending to false;
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class IntegerComparator implements Comparator<Integer>{
    private boolean ascending = true;

    public int compare(Integer integer, Integer integer2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (integer == null && integer2 == null){
            return EQUAL;
        }
        else if (integer == null){
            return AFTER;
        }
        else if (integer2 == null){
            return BEFORE;
        }
        else {
            int comp = integer.compareTo(integer2);
            return ascending ? comp : -comp;
        }
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
