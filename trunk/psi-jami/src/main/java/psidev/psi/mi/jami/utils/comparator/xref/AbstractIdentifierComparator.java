package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.util.Comparator;

/**
 * Comparator for Xref identifiers.
 *
 * Identifiers (Xref with qualifier identity or secondary ac) will always come first.
 * It allows some extension so we can sort identifiers as well depending on database or id.
 * If the Xref is not an identifier, it will compare the Xref properties based on the same logic as DefaultXrefComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/02/13</pre>
 */

public abstract class AbstractIdentifierComparator implements Comparator<Xref> {

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

            CvTerm qualifier1 = xref1.getQualifier();
            CvTerm qualifier2 = xref2.getQualifier();

            // it is not an id, compare databases and then id
            if (qualifier1 == null && qualifier2 == null){
                // compares databases and identifiers
                return compareDatabaseAndId(xref1, xref2);
            }
            else if (qualifier1 == null){
                return AFTER;
            }
            else if (qualifier2 == null){
                return BEFORE;
            }
            else {
                boolean isIdenticalObject1 = XrefUtils.doesXrefHaveQualifier(xref1, Xref.IDENTITY_MI, Xref.IDENTITY);
                boolean isIdenticalObject2 = XrefUtils.doesXrefHaveQualifier(xref2, Xref.IDENTITY_MI, Xref.IDENTITY);
                boolean isSecondary1 = XrefUtils.doesXrefHaveQualifier(xref1, Xref.SECONDARY_MI, Xref.SECONDARY);
                boolean isSecondary2 = XrefUtils.doesXrefHaveQualifier(xref2, Xref.SECONDARY_MI, Xref.SECONDARY);
                // identifier is first (identity qualifier or secondary-ac)
                if ( (isIdenticalObject1 || isSecondary1) && (isIdenticalObject2 || isSecondary2)){
                    return compareIdentifiers(xref1, xref2, isIdenticalObject1, isIdenticalObject2);
                }
                else if (isIdenticalObject1 || isSecondary1){
                    return BEFORE;
                }
                else if (isIdenticalObject2 || isSecondary2){
                    return AFTER;
                }
                // it is not an identifier, compare qualifier, databases and then id
                else {
                    // compares databases first (cannot use CvTermComparator because have to break the loop)
                    int comp = compareDatabaseAndId(xref1, xref2);
                    if (comp != 0){
                        return comp;
                    }

                    return compareQualifiers(qualifier1, qualifier2);
                }
            }
        }
    }

    protected abstract int compareIdentifiers(Xref xref1, Xref xref2, boolean isIdenticalObject1, boolean isIdenticalObject2);

    private int compareDatabaseAndId(Xref xref1, Xref xref2){
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

        return id1.compareTo(id2);
    }

    private int compareQualifiers(CvTerm qualifier1, CvTerm qualifier2){
        String qualifierMi1 = qualifier1.getMIIdentifier();
        String qualifierMi2 = qualifier2.getMIIdentifier();

        // if external id of qualifier is set, look at qualifier id only otherwise look at shortname
        if (qualifierMi1 != null && qualifierMi2 != null){
            return qualifierMi1.compareTo(qualifierMi2);
        }
        else {
            return qualifier1.getShortName().toLowerCase().trim().compareTo(qualifier2.getShortName().toLowerCase().trim());
        }
    }

    protected int compareDefaultXref(Xref xref1, Xref xref2){
        // compares databases first (cannot use CvTermComparator because have to break the loop)
        int comp = compareDatabaseAndId(xref1, xref2);
        if (comp != 0){
            return comp;
        }

        return compareQualifiers(xref1.getQualifier(), xref2.getQualifier());
    }
}
