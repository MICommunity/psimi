package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;

import java.util.Comparator;

/**
 * Default comparator for external identifiers.
 * It compares first the databases and then the ids (case sensitive) but ignores the version.
 * To compare the databases, it looks first at the identifiers id if they exist, otherwise it looks at the database shortlabel only.
 *
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - Compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultExternalIdentifierComparator implements Comparator<ExternalIdentifier> {

    private static DefaultExternalIdentifierComparator defaultIdentifierComparator;

    public DefaultExternalIdentifierComparator() {
    }

    /**
     * Default comparator for external identifiers.
     * It compares first the databases and then the ids (case sensitive) but ignores the version.
     * To compare the databases, it looks first at the identifiers id if they exist, otherwise it looks at the database shortlabel only.
     *
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - Compare the databases. If both databases are equal, compare the ids (is case sensitive)
     * @param externalIdentifier1
     * @param externalIdentifier2
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

    /**
     * Use DefaultIdentifierComparator to know if two external identifiers are equals.
     * @param externalIdentifier1
     * @param externalIdentifier2
     * @return true if the two external identifiers are equal
     */
    public static boolean areEquals(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2){
        if (defaultIdentifierComparator == null){
            defaultIdentifierComparator = new DefaultExternalIdentifierComparator();
        }

        return defaultIdentifierComparator.compare(externalIdentifier1, externalIdentifier2) == 0;
    }
}
