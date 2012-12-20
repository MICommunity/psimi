package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Xref;

/**
 * Default Xref comparator
 * It will first compare the external identifier composed of database and id using DefaultExternalIdentifierComparator and then it will
 * compare the qualifier with the same CvTermComparator used by DefaultExternalIdentifierComparator.
 * It will ignore the version
 *
 * - Two xrefs which are null are equals
 * - The xref which is not null is before null.
 * - Use DefaultExternalIdentifierComparator to compare first the database and id.
 * - If both xref databases and ids are the same, use DefaultCvTermComparator to compare the qualifiers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class DefaultXrefComparator extends XrefComparator {

    public DefaultXrefComparator() {
        super(new DefaultExternalIdentifierComparator());
    }

    @Override
    public DefaultExternalIdentifierComparator getIdentifierComparator() {
        return (DefaultExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    /**
     * It will first compare the external identifier composed of database and id using DefaultExternalIdentifierComparator and then it will
     * compare the qualifier with the same CvTermComparator used by DefaultExternalIdentifierComparator.
     * It will ignore the version
     *
     * - Two xrefs which are null are equals
     * - The xref which is not null is before null.
     * - Use DefaultExternalIdentifierComparator to compare first the database and id.
     * - If both xref databases and ids are the same, use DefaultCvTermComparator to compare the qualifiers.
     * @param xref1
     * @param xref2
     */
    public int compare(Xref xref1, Xref xref2) {
        return super.compare(xref1, xref2);
    }
}
