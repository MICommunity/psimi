package psidev.psi.mi.jami.utils.comparator.alias;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default alias comparator.
 * It will first compare alias types using DefaultCvTermComparator and then alias names (case sensitive)

 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * - If the alias types are not set, compares the names (case sensitive)
 * - If both alias types are set, use DefaultCvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultAliasComparator {

    /**
     * Use DefaultAliasComparator to know if two aliases are equals.
     * @param alias1
     * @param alias2
     * @return true if the two aliases are equal
     */
    public static boolean areEquals(Alias alias1, Alias alias2){
        if (alias1 == null && alias2 == null){
            return true;
        }
        else if (alias1 == null || alias2 == null){
            return false;
        }
        else {
            CvTerm type1 = alias1.getType();
            CvTerm type2 = alias2.getType();

            if (!DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }
            // check identifiers which cannot be null
            String name1 = alias1.getName();
            String name2 = alias2.getName();

            return name1.equals(name2);
        }
    }
}
