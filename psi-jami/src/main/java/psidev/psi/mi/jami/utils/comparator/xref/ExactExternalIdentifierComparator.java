package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * Exact comparator for external identifiers (Xref database and id).
 * It compares first the databases using UnambiguousCvTermComparator, then the ids (case sensitive) and then the version.
 * To compare the databases, it looks first at the PSI-MI ids if at least one of the database has a PSI-MI id, otherwise it looks at the database shortlabel only.
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *     - if both ids are the same, compares the versions.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class ExactExternalIdentifierComparator extends UnambiguousExternalIdentifierComparator {

    private static ExactExternalIdentifierComparator exactIdentifierComparator;

    public ExactExternalIdentifierComparator() {
        super();
    }

    /**
     * It compares first the databases using UnambiguousCvTermComparator, then the ids (case sensitive) and then the version.
     * To compare the databases, it looks first at the PSI-MI ids if at least one of the database has a PSI-MI id, otherwise it looks at the database shortlabel only.
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)
     *     - if both ids are the same, compares the versions.
     * @param externalIdentifier1
     * @param externalIdentifier2
     * @return
     */
    public int compare(Xref externalIdentifier1, Xref externalIdentifier2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (externalIdentifier1 == null && externalIdentifier2 == null){
            return EQUAL;
        }
        else if (externalIdentifier1 == null){
            return AFTER;
        }
        else if (externalIdentifier2 == null){
            return BEFORE;
        }
        else {
            // use super comparator for database and id
            int comp = super.compare(externalIdentifier1, externalIdentifier2);
            if (comp != 0){
                return comp;
            }

            // compare version
            Integer version1 = externalIdentifier1.getVersion();
            Integer version2 = externalIdentifier2.getVersion();

            if (version1 == version2){
                return EQUAL;
            }
            else if (version1 == null){
                return AFTER;
            }
            else if (version2 == null){
                return BEFORE;
            }
            else if (version1 < version2) {
                return BEFORE;
            }
            else {
                return AFTER;
            }
        }
    }

    /**
     * Use ExactExternalIdentifierComparator to know if two external identifiers are equals.
     * @param externalIdentifier1
     * @param externalIdentifier2
     * @return true if the two external identifiers are equal
     */
    public static boolean areEquals(Xref externalIdentifier1, Xref externalIdentifier2){
        if (exactIdentifierComparator == null){
            exactIdentifierComparator = new ExactExternalIdentifierComparator();
        }

        return exactIdentifierComparator.compare(externalIdentifier1, externalIdentifier2) == 0;
    }

    /**
     *
     * @param externalIdentifier1
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Xref externalIdentifier1){
        if (exactIdentifierComparator == null){
            exactIdentifierComparator = new ExactExternalIdentifierComparator();
        }
        if (externalIdentifier1 == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm database1 = externalIdentifier1.getDatabase();
        String mi = database1.getMIIdentifier();

        if (mi != null){
            hashcode = 31*hashcode + mi.hashCode();
        }
        else {
            hashcode = 31*hashcode + database1.getShortName().toLowerCase().trim().hashCode();
        }

        hashcode = 31 * hashcode + externalIdentifier1.getId().hashCode();
        hashcode = 31 * hashcode + (externalIdentifier1.getVersion() != null ? externalIdentifier1.getVersion().hashCode() : 0);

        return hashcode;
    }
}
