package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.Comparator;

/**
 * Default Xref comparator
 * It compares first the databases and then the ids (case sensitive) but ignores the version.
 * To compare the databases, it looks first at the identifiers id if they exist, otherwise it looks at the database shortlabel only. Then it will
 * compare the qualifier.
 * To compare the qualifiers, it looks first at the identifiers id if they exist, otherwise it looks at the qualifier shortlabel only.
 * It will ignore the version
 *
 * - Two xrefs which are null are equals
 * - The xref which is not null is before null.
 * - Use DefaultExternalIdentifierComparator to compare first the database and id.
 * - If both xref databases and ids are the same, compare the qualifiers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class DefaultXrefComparator implements Comparator<Xref> {

    private static DefaultXrefComparator defaultXrefComparator;

    public DefaultXrefComparator() {
        super();
    }

    /**
     * It compares first the databases and then the ids (case sensitive) but ignores the version.
     * To compare the databases, it looks first at the identifiers id if they exist, otherwise it looks at the database shortlabel only. Then it will
     * compare the qualifier.
     * To compare the qualifiers, it looks first at the identifiers id if they exist, otherwise it looks at the qualifier shortlabel only.
     * It will ignore the version
     *
     * - Two xrefs which are null are equals
     * - The xref which is not null is before null.
     * - Use DefaultExternalIdentifierComparator to compare first the database and id.
     * - If both xref databases and ids are the same, compare the qualifiers.
     * @param xref1
     * @param xref2
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
            // compares databases first (cannot use CvTermComparator because have to break the loop)
            CvTerm database1 = xref1.getDatabase();
            CvTerm database2 = xref2.getDatabase();
            String mi1 = database1.getMIIdentifier();
            String mi2 = database2.getMIIdentifier();

            // if external id of database is set, look at database id only otherwise look at shortname
            int comp;
            if (mi1 != null && mi2 != null){
                comp = mi1.compareTo(mi2);
            }
            else {
                comp = database1.getShortName().toLowerCase().trim().compareTo(database2.getShortName().toLowerCase().trim());
            }

            if (comp != 0){
                return comp;
            }
            // check identifiers which cannot be null
            String id1 = xref1.getId();
            String id2 = xref2.getId();

            comp = id1.compareTo(id2);
            if (comp != 0){
                return comp;
            }

            CvTerm qualifier1 = xref1.getQualifier();
            CvTerm qualifier2 = xref2.getQualifier();

            if (qualifier1 == null && qualifier2 == null){
                return EQUAL;
            }
            else if (qualifier1 == null){
                return AFTER;
            }
            else if (qualifier2 == null){
                return BEFORE;
            }
            else {
                String qualifierMi1 = qualifier1.getMIIdentifier();
                String qualifierMi2 = qualifier2.getMIIdentifier();

                // if external id of qualifier is set, look at qualifier id only otherwise look at shortname
                int comp2;
                if (qualifierMi1 != null && qualifierMi2 != null){
                    return qualifierMi1.compareTo(qualifierMi2);
                }
                else {
                    return qualifier1.getShortName().toLowerCase().trim().compareTo(qualifier2.getShortName().toLowerCase().trim());
                }
            }
        }
    }

    /**
     * Use DefaultXrefComparator to know if two xrefs are equals.
     * @param xref1
     * @param xref2
     * @return true if the two xrefs are equal
     */
    public static boolean areEquals(Xref xref1, Xref xref2){
        if (defaultXrefComparator == null){
            defaultXrefComparator = new DefaultXrefComparator();
        }

        return defaultXrefComparator.compare(xref1, xref2) == 0;
    }
}
