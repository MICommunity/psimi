package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.Comparator;

/**
 * Simple Xref comparator.
 * It will first compare the external identifier composed of database and id using ExternalIdentifierComparator and then it will
 * compare the qualifier with the same CvTermComparator used by ExternalIdentifierComparator.
 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class XrefComparator implements Comparator<Xref> {

    protected ExternalIdentifierComparator identifierComparator;

    /**
     * Creates a new XrefComparator
     * @param identifierComparator : the ExternalIdentifierComparator is required to compare database, id and qualifier
     */
    public XrefComparator(ExternalIdentifierComparator identifierComparator){
        if(identifierComparator == null){
            throw new IllegalArgumentException("The ExternalIdentifierComparator is required to compare the database, id and qualifier. It cannot be null");
        }
        this.identifierComparator = identifierComparator;
    }

    public ExternalIdentifierComparator getIdentifierComparator() {
        return identifierComparator;
    }

    /**
     * It will first compare the external identifier composed of database and id using ExternalIdentifierComparator and then it will
     * compare the qualifier with the same CvTermComparator used by ExternalIdentifierComparator.
     * - Two aliases which are null are equals
     * - The alias which is not null is before null.
     * @param xref1
     * @param xref2
     * @return
     */
    public int compare(Xref xref1, Xref xref2) {

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (xref1 == null && xref2 == null){
            return EQUAL;
        }
        else if (xref1 == null){
            return AFTER;
        }
        else if (xref2 == null){
            return BEFORE;
        }
        else {
            int comp = identifierComparator.compare(xref1, xref2);
            if (comp != 0){
                return comp;
            }

            CvTerm qualifier1 = xref1.getQualifier();
            CvTerm qualifier2 = xref2.getQualifier();

            return identifierComparator.getDatabaseComparator().compare(qualifier1, qualifier2);
        }
    }
}
