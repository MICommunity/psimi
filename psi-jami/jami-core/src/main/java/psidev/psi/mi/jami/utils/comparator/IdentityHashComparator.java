package psidev.psi.mi.jami.utils.comparator;

import java.util.Comparator;

/**
 * The IdentityHashComparator will use the hashcode of each objects to compare the objects.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class IdentityHashComparator<T> implements Comparator<T>{

    /**
     * Compares two objects based on their hashcode methods
     * @param t
     * @param t2
     * @return
     */
    public int compare(T t, T t2) {
        if (t == t2){
            return 0;
        }
        else if (t != null && t2 != null){
            int hashcode1 = t.hashCode();
            int hashcode2 = t2.hashCode();
            if (hashcode1 == hashcode2){
                return 0;
            }
            else if (hashcode1 < hashcode2){
                return -1;
            }
            else{
                return 1;
            }
        }
        else if (t2 == null){
            return -1;
        }
        else {
            return 1;
        }
    }
}
