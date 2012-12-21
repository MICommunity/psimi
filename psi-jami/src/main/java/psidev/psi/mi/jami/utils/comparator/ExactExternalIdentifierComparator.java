package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.ExternalIdentifier;

/**
 * Exact comparator for external identifiers.
 * It compares first the databases using UnambiguousCvTermComparator, then the ids (case sensitive) and then the version.
 * To compare the databases, it looks first at the identifiers id if they both exist, otherwise it looks at the database shortlabel only.
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *     - if both ids are the same, compares the versions.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class ExactExternalIdentifierComparator extends UnambiguousExternalIdentifierComparator{

    public ExactExternalIdentifierComparator() {
        super();
    }

    /**
     * It compares first the databases using ExactCvTermComparator, then the ids (case sensitive) and then the version.
     * To compare the databases, it looks first at the identifiers id if they both exist, otherwise it looks at the database shortlabel only.
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)
     *     - if both ids are the same, compares the versions.
     * @param externalIdentifier1
     * @param externalIdentifier2
     * @return
     */
    public int compare(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2) {
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
}
