package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * Exact comparator for Xrefs.
 * It compares first the databases using UnambiguousCvTermComparator, then the ids (case sensitive) and then the version.
 * To compare the databases, it looks first at the identifiers id if they both exist, otherwise it looks at the database shortlabel only.
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *     - if both ids are the same, compares the versions.
 * - If both xref databases and ids are the same, use UnambiguousCvTermComparator to compare the qualifiers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class ExactXrefComparator extends UnambiguousXrefComparator {

    private static ExactXrefComparator exactXrefComparator;

    public ExactXrefComparator() {
        super();
    }

    @Override
    /**
     * It compares first the databases using UnambiguousCvTermComparator, then the ids (case sensitive) and then the version.
     * To compare the databases, it looks first at the identifiers id if they both exist, otherwise it looks at the database shortlabel only.
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)
     *     - if both ids are the same, compares the versions.
     * - If both xref databases and ids are the same, use UnambiguousCvTermComparator to compare the qualifiers.
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
            // use super comparator for database, qualifier and id
            int comp = super.compare(xref1, xref2);
            if (comp != 0){
                return comp;
            }

            // compare version
            Integer version1 = xref1.getVersion();
            Integer version2 = xref2.getVersion();

            if (version1 == null && version2 == null){
                return EQUAL;
            }
            else if (version1 == null){
                return AFTER;
            }
            else if (version2 == null){
                return BEFORE;
            }
            else {
                return version1.compareTo(version2);
            }
        }
    }

    /**
     * Use ExactXrefComparator to know if two xrefs are equals.
     * @param xref1
     * @param xref2
     * @return true if the two xrefs are equal
     */
    public static boolean areEquals(Xref xref1, Xref xref2){
        if (exactXrefComparator == null){
            exactXrefComparator = new ExactXrefComparator();
        }

        return exactXrefComparator.compare(xref1, xref2) == 0;
    }

    /**
     *
     * @param xref
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Xref xref){
        if (exactXrefComparator == null){
            exactXrefComparator = new ExactXrefComparator();
        }
        if (xref == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm database1 = xref.getDatabase();
        String mi1 = database1.getMIIdentifier();

        if (mi1 != null){
            hashcode = 31*hashcode + mi1.hashCode();
        }
        else {
            hashcode = 31*hashcode + database1.getShortName().toLowerCase().trim().hashCode();
        }

        hashcode = 31 * hashcode + xref.getId().hashCode();
        hashcode = 31 * hashcode + (xref.getVersion() != null ? xref.getVersion() : 0);

        CvTerm qualifier = xref.getQualifier();
        if (qualifier != null){
            String qualifierMi = qualifier.getMIIdentifier();

            if (qualifierMi != null){
                hashcode = 31*hashcode + qualifierMi.hashCode();
            }
            else {
                hashcode = 31*hashcode + qualifier.getShortName().toLowerCase().trim().hashCode();
            }
        }
        else {
            hashcode = 31 * hashcode;
        }

        return hashcode;
    }
}
