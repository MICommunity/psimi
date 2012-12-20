package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Xref;

/**
 * Strict Xref comparator
 * It will first compare the external identifier composed of database, id and version using UnambiguousExternalIdentifierComparator and then it will
 * compare the qualifier with the same CvTermComparator used by UnambiguousExternalIdentifierComparator.
 *
 * - Two xrefs which are null are equals
 * - The xref which is not null is before null.
 * - Use UnambiguousExternalIdentifierComparator to compare first the database and id.
 * - If both xref databases and ids are the same, use UnambiguousCvTermComparator to compare the qualifiers.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class UnambiguousXrefComparator extends XrefComparator {
    public UnambiguousXrefComparator() {
        super(new UnambiguousExternalIdentifierComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    /**
     * It will first compare the external identifier composed of database, id and version using UnambiguousExternalIdentifierComparator and then it will
     * compare the qualifier with the same CvTermComparator used by UnambiguousExternalIdentifierComparator.
     *
     * - Two xrefs which are null are equals
     * - The xref which is not null is before null.
     * - Use UnambiguousExternalIdentifierComparator to compare first the database and id.
     * - If both xref databases and ids are the same, use UnambiguousCvTermComparator to compare the qualifiers.
     * @param xref1
     * @param xref2
     */
    public int compare(Xref xref1, Xref xref2) {
        return super.compare(xref1, xref2);
    }
}
