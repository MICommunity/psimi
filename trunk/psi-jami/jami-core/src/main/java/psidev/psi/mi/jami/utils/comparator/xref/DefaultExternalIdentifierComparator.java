package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * Default comparator for external identifiers (Xref database and id).
 * It is not a java comparator because the order can change.
 * It compares first the databases and then the ids (case sensitive) but ignores the version.
 * To compare the databases, it looks first at the PSI-MI id if they exist, otherwise it looks at the database shortlabel only.
 *
 * - Two external identifiers which are null are equals
 * - If the two external identifiers are set :
 *     - Compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultExternalIdentifierComparator {

    /**
     * Use DefaultIdentifierComparator to know if two external identifiers are equals.
     * @param externalIdentifier1
     * @param externalIdentifier2
     * @return true if the two external identifiers are equal
     */
    public static boolean areEquals(Xref externalIdentifier1, Xref externalIdentifier2){

        if (externalIdentifier1 == null && externalIdentifier2 == null){
            return true;
        }
        else if (externalIdentifier1 == null || externalIdentifier2 == null){
            return false;
        }
        else {
            // compares databases first (cannot use CvTermComparator because have to break the loop)
            CvTerm database1 = externalIdentifier1.getDatabase();
            CvTerm database2 = externalIdentifier2.getDatabase();
            String mi1 = database1.getMIIdentifier();
            String mi2 = database2.getMIIdentifier();

            // if MI of database is set, look at database MI only otherwise look at shortname
            boolean comp;
            if (mi1 != null && mi2 != null){
                comp = mi1.equals(mi2);
            }
            else {
                comp = database1.getShortName().toLowerCase().trim().equals(database2.getShortName().toLowerCase().trim());
            }

            if (!comp){
                return comp;
            }
            // check identifiers which cannot be null
            String id1 = externalIdentifier1.getId();
            String id2 = externalIdentifier2.getId();

            return id1.equals(id2);
        }
    }
}
