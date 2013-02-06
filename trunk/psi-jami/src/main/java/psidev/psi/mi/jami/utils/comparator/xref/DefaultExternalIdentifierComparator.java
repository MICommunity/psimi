package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.Comparator;

/**
 * Default comparator for external identifiers (Xref database and id).
 * It compares first the databases and then the ids (case sensitive) but ignores the version.
 * To compare the databases, it looks first at the PSI-MI id if they exist, otherwise it looks at the database shortlabel only.
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

public class DefaultExternalIdentifierComparator implements Comparator<Xref> {

    private static DefaultExternalIdentifierComparator defaultIdentifierComparator;

    public DefaultExternalIdentifierComparator() {
    }

    /**
     * It compares first the databases and then the ids (case sensitive) but ignores the version.
     * To compare the databases, it looks first at the PSI-MI id if they exist, otherwise it looks at the database shortlabel only.
     *
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - Compare the databases. If both databases are equal, compare the ids (is case sensitive)
     * @param externalIdentifier1
     * @param externalIdentifier2
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
            // compares databases first (cannot use CvTermComparator because have to break the loop)
            CvTerm database1 = externalIdentifier1.getDatabase();
            CvTerm database2 = externalIdentifier2.getDatabase();
            String mi1 = database1.getMIIdentifier();
            String mi2 = database2.getMIIdentifier();

            // if MI of database is set, look at database MI only otherwise look at shortname
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
    public static boolean areEquals(Xref externalIdentifier1, Xref externalIdentifier2){
        if (defaultIdentifierComparator == null){
            defaultIdentifierComparator = new DefaultExternalIdentifierComparator();
        }

        return defaultIdentifierComparator.compare(externalIdentifier1, externalIdentifier2) == 0;
    }
}
