package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;

import java.util.Comparator;

/**
 * Unambiguous comparator for external identifiers.
 * It compares first the databases and then the ids (case sensitive) but ignores the version.
 * To compare the databases, it looks first at the identifiers id if they both exist, otherwise it looks at the database shortlabel only.
 * If one database identifier is null, it will always comes after an ExternalIdentifier having a non null database id.
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousExternalIdentifierComparator implements Comparator<ExternalIdentifier> {

    public UnambiguousExternalIdentifierComparator() {
    }

    /**
     * It compares first the databases and then the ids (case sensitive) but ignores the version.
     * To compare the databases, it looks first at the identifiers id if they both exist, otherwise it looks at the database shortlabel only.
     * If one database identifier is null, it will always comes after an ExternalIdentifier having a non null database id.
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - compare the databases. If both databases are equal, compare the ids (is case sensitive)mparator to compare the databases. If both databases are equal, compare the ids (is case sensitive)
     * @param externalIdentifier1
     * @param externalIdentifier2
     * */
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
            // compares databases first (cannot use CvTermComparator because have to break the loop)
            CvTerm database1 = externalIdentifier1.getDatabase();
            CvTerm database2 = externalIdentifier2.getDatabase();
            ExternalIdentifier databaseId1 = database1.getOntologyIdentifier();
            ExternalIdentifier databaseId2 = database2.getOntologyIdentifier();

            // if external id of database is set, look at database id only otherwise look at shortname
            int comp;
            if (databaseId1 != null && databaseId2 != null){
                comp = databaseId1.getId().compareTo(databaseId2.getId());
            }
            else if (databaseId1 == null && databaseId2 != null){
                return AFTER;
            }
            else if (databaseId2 == null && databaseId1 != null){
                return BEFORE;
            }
            else {
                comp = database1.getShortName().toLowerCase().trim().compareTo(database2.getShortName().toLowerCase().trim());
            }

            if (comp != 0){
                return comp;
            }
            // check identifiers which cannot be null
            String id1 = externalIdentifier1.getId();
            String id2 = externalIdentifier2.getId();

            return id1.compareTo(id2);
        }
    }
}
