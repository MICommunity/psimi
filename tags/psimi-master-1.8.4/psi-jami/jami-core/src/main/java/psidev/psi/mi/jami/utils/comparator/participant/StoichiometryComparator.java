package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Stoichiometry;

import java.util.Comparator;

/**
 * Simple stoichiometry comparator.
 *
 * It will first compare the minValue. If both minValues are the same, it will compare maxValues.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class StoichiometryComparator implements Comparator<Stoichiometry> {

    private static StoichiometryComparator stoichiometryComparator;
    /**
     * It will first compare the minValue. If both minValues are the same, it will compare maxValues.
     * @param stoichiometry1
     * @param stoichiometry2
     * @return
     */
    public int compare(Stoichiometry stoichiometry1, Stoichiometry stoichiometry2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (stoichiometry1 == null && stoichiometry2 == null){
            return EQUAL;
        }
        else if (stoichiometry1 == null){
            return AFTER;
        }
        else if (stoichiometry2 == null){
            return BEFORE;
        }
        else {

            // compare start and then end

            long min1 = stoichiometry1.getMinValue();
            long min2 = stoichiometry2.getMinValue();

            if (min1 < min2){
                return BEFORE;
            }
            else if (min1 > min2){
                return AFTER;
            }
            else {
                long max1 = stoichiometry1.getMaxValue();
                long max2 = stoichiometry2.getMaxValue();

                if (max1 < max2){
                    return BEFORE;
                }
                else if (max1 > max2){
                    return AFTER;
                }
                else {
                    return EQUAL;
                }
            }
        }
    }

    /**
     * Use StoichiometryComparator to know if two stoichiometry are equals.
     * @param stc1
     * @param stc2
     * @return true if the two stoichiometry are equal
     */
    public static boolean areEquals(Stoichiometry stc1, Stoichiometry stc2){
        if (stoichiometryComparator == null){
            stoichiometryComparator = new StoichiometryComparator();
        }

        return stoichiometryComparator.compare(stc1, stc2) == 0;
    }

    /**
     *
     * @param stc
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Stoichiometry stc){
        if (stoichiometryComparator == null){
            stoichiometryComparator = new StoichiometryComparator();
        }

        if (stc == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31 * hashcode + (int)(stc.getMinValue() ^ (stc.getMinValue() >>> 32));
        hashcode = 31*hashcode + (int)(stc.getMaxValue() ^ (stc.getMaxValue() >>> 32));

        return hashcode;
    }
}
