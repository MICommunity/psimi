package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Xref;

import java.util.Comparator;

/**
 * Strict Xref comparator
 *
 * It compares first the databases and then the ids (case sensitive) but ignores the version.
 * To compare the databases, it looks first at the identifiers id (the database with identifier will always come first), otherwise it looks at the database shortlabel only.
 * If one database identifier is null, it will always comes after an ExternalIdentifier having a non null database id. Then it will
 * compare the qualifier.
 * To compare the qualifiers, it looks first at the identifiers id if they both exist, otherwise it looks at the qualifier shortnames only.
 * If one qualifier identifier is null, it will always comes after a Xref having a non null qualifiers id.
 *
 * - Two xrefs which are null are equals
 * - The xref which is not null is before null.
 * - Use UnambiguousExternalIdentifierComparator to compare first the database and id.
 * - If both xref databases and ids are the same, compare the qualifiers.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class UnambiguousXrefComparator implements Comparator<Xref> {
    private static UnambiguousXrefComparator unambiguousXrefComparator;

    public UnambiguousXrefComparator() {
        super();
    }

    /**
     * It compares first the databases and then the ids (case sensitive) but ignores the version.
     * To compare the databases, it looks first at the identifiers id (the database with identifier will always come first), otherwise it looks at the database shortlabel only.
     * If one database identifier is null, it will always comes after an ExternalIdentifier having a non null database id. Then it will
     * compare the qualifier.
     * To compare the qualifiers, it looks first at the identifiers id if they both exist, otherwise it looks at the qualifier shortnames only.
     * If one qualifier identifier is null, it will always comes after a Xref having a non null qualifiers id.
     *
     * - Two xrefs which are null are equals
     * - The xref which is not null is before null.
     * - Use UnambiguousExternalIdentifierComparator to compare first the database and id.
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
            String id1 = xref1.getId();
            String id2 = xref2.getId();

            comp = id1.compareTo(id2);
            if (comp != 0){
                return comp;
            }

            CvTerm qualifier1 = xref1.getQualifier();
            CvTerm qualifier2 = xref2.getQualifier();
            ExternalIdentifier qualifierId1 = qualifier1.getOntologyIdentifier();
            ExternalIdentifier qualifierId2 = qualifier2.getOntologyIdentifier();

            // if external id of qualifier is set, look at qualifier id only otherwise look at shortname
            if (qualifierId1 != null && qualifierId2 != null){
                return qualifierId1.getId().compareTo(qualifierId2.getId());
            }
            else if(qualifierId1 == null && qualifierId2 != null){
                return AFTER;
            }
            else if(qualifierId1 != null && qualifierId2 == null){
                return BEFORE;
            }
            else {
                return qualifier1.getShortName().toLowerCase().trim().compareTo(qualifier2.getShortName().toLowerCase().trim());
            }
        }
    }

    /**
     * Use UnambiguousXrefComparator to know if two xrefs are equals.
     * @param xref1
     * @param xref2
     * @return true if the two xrefs are equal
     */
    public static boolean areEquals(Xref xref1, Xref xref2){
        if (unambiguousXrefComparator == null){
            unambiguousXrefComparator = new UnambiguousXrefComparator();
        }

        return unambiguousXrefComparator.compare(xref1, xref2) == 0;
    }

    /**
     *
     * @param xref
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Xref xref){
        if (unambiguousXrefComparator == null){
            unambiguousXrefComparator = new UnambiguousXrefComparator();
        }
        if (xref == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm database1 = xref.getDatabase();
        ExternalIdentifier databaseId1 = database1.getOntologyIdentifier();

        if (databaseId1 != null){
            hashcode = 31*hashcode + databaseId1.getId().hashCode();
        }
        else {
            hashcode = 31*hashcode + database1.getShortName().toLowerCase().hashCode();
        }

        hashcode = 31 * hashcode + xref.getId().hashCode();

        CvTerm qualifier = xref.getQualifier();
        if (qualifier != null){
            ExternalIdentifier qualifierId = qualifier.getOntologyIdentifier();

            if (qualifierId != null){
                hashcode = 31*hashcode + qualifierId.getId().hashCode();
            }
            else {
                hashcode = 31*hashcode + qualifier.getShortName().toLowerCase().hashCode();
            }
        }

        return hashcode;
    }
}
