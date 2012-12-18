package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Compare two aliases :
 *
 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * - If the alias types are not set,
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultAliasComparator implements Comparator<Alias>{

    private DefaultCvTermComparator typeComparator;

    public DefaultAliasComparator(){
        typeComparator = new DefaultCvTermComparator();
    }

    public int compare(Alias alias1, Alias alias2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (alias1 == null && alias2 == null){
            return EQUAL;
        }
        else if (alias1 == null){
            return AFTER;
        }
        else if (alias2 == null){
            return BEFORE;
        }
        else {
            CvTerm type1 = alias1.getType();
            CvTerm type2 = alias2.getType();
            // no types so relies on the name only
            if (type1 == null && type2 == null){
                // check names which cannot be null
                String name1 = alias1.getName();
                String name2 = alias2.getName();

                return name1.compareTo(name2);
            }
            // type 1 is null so it comes after
            else if (type1 == null){
                return AFTER;
            }
            // type 2 is null so it comes after
            else if (type2 == null){
                return BEFORE;
            }
            else {
                int comp = typeComparator.compare(type1, type2);
                if (comp != 0){
                    return comp;
                }
                // check identifiers which cannot be null
                String name1 = alias1.getName();
                String name2 = alias2.getName();

                return name1.compareTo(name2);
            }
        }
    }
}
