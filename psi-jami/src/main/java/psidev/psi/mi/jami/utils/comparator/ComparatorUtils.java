package psidev.psi.mi.jami.utils.comparator;

/**
 * Utility class for Comparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class ComparatorUtils {

    /**
     * If identifier1 and identifier2 are equals, it returns 0.
     * If identifier1 and identifier2 are not equals:
     *  - the external identifier that is equal to firstIdentifier will always come first
     *  - if both external identifiers are different from the the first identifier, it will return the results of
     *   identifier1.compareTo(identifier2)
     *
     * @param identifier1 : first identifier to compare
     * @param identifier2 : second identifier to compare
     * @param firstIdentifier : the default identifier that we want to have first
     * @return
     */
    public static int compareIdentifiersWithDefaultIdentifier(String identifier1, String identifier2, String firstIdentifier) {
        int comp;
        comp = identifier1.compareTo(identifier2);
        if (comp == 0){
            return 0;
        }

        // the unique gene property is first
        if (firstIdentifier != null && firstIdentifier.equals(identifier1)){
            return -1;
        }
        else if (firstIdentifier != null && firstIdentifier.equals(identifier2)){
            return 1;
        }
        else {
            return comp;
        }
    }
}
