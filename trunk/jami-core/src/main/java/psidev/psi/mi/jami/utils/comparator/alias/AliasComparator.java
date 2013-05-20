package psidev.psi.mi.jami.utils.comparator.alias;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Comparator;

/**
 * Simple comparator for aliases.
 * It will first compare alias types and then alias names (case sensitive)
 *
 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * - If the alias types are not set, compares the names (case sensitive)
 * - If both alias types are set, use CvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class AliasComparator implements Comparator<Alias>{

    protected AbstractCvTermComparator typeComparator;

    /**
     * Creates a new AliasComparator.
     * @param typeComparator : CvTerm comparator for alias types. It is required
     */
    public AliasComparator(AbstractCvTermComparator typeComparator){
       if (typeComparator == null){
           throw new IllegalArgumentException("The CvTerm comparator is required for comparing alias types. It cannot be null");
       }
        this.typeComparator = typeComparator;
    }

    public AbstractCvTermComparator getTypeComparator() {
        return typeComparator;
    }

    /**
     * It will first compare alias types and then alias names (case sensitive)
     *
     * - Two aliases which are null are equals
     * - The alias which is not null is before null.
     * - If the alias types are not set, compares the names (case sensitive)
     * - If both alias types are set, use CvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
     *
     * @param alias1
     * @param alias2
     * @return
     */
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
