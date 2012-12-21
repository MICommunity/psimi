package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Xref;

/**
 * Strict Xref comparator
 * It will first compare the external identifier composed of database and id using UnambiguousExternalIdentifierComparator and then it will
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

public class UnambiguousXrefComparator extends AbstractXrefComparator {
    public UnambiguousXrefComparator() {
        super(new UnambiguousExternalIdentifierComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    /**
     * It will first compare the external identifier composed of database and id using UnambiguousExternalIdentifierComparator and then it will
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
            int comp = identifierComparator.compare(xref1, xref2);
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
}
