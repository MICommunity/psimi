package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.Xref;

/**
 * Exact comparator for Xrefs.
 * It will first compare the external identifier composed of database, id and version using ExactExternalIdentifierComparator and then it will
 * compare the qualifier with the same CvTermComparator used by ExactExternalIdentifierComparator.
 *
 * - Two xrefs which are null are equals
 * - The xref which is not null is before null.
 * - Use ExactExternalIdentifierComparator to compare first the database, id and version.
 * - If both xref databases and ids are the same, use UnambiguousCvTermComparator to compare the qualifiers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class ExactXrefComparator extends UnambiguousXrefComparator {

    private static ExactXrefComparator exactXrefComparator;

    public ExactXrefComparator() {
        super(new ExactExternalIdentifierComparator());
    }

    @Override
    public ExactExternalIdentifierComparator getIdentifierComparator() {
        return (ExactExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    /**
     * It will first compare the external identifier composed of database, id and version using ExactExternalIdentifierComparator and then it will
     * compare the qualifier with the same CvTermComparator used by ExactExternalIdentifierComparator.
     *
     * - Two xrefs which are null are equals
     * - The xref which is not null is before null.
     * - Use ExactExternalIdentifierComparator to compare first the database, id and version.
     * - If both xref databases and ids are the same, use UnambiguousCvTermComparator to compare the qualifiers.
     * @param xref1
     * @param xref2
     */
    public int compare(Xref xref1, Xref xref2) {
        return super.compare(xref1, xref2);
    }

    /**
     * Use ExactXrefComparator to know if two xrefs are equals.
     * @param xref1
     * @param xref2
     * @return true if the two xrefs are equal
     */
    public static boolean areEquals(Xref xref1, Xref xref2){
        if (exactXrefComparator == null){
            exactXrefComparator = new ExactXrefComparator();
        }

        return exactXrefComparator.compare(xref1, xref2) == 0;
    }
}
