package psidev.psi.mi.jami.utils.comparator;

/**
 * Strict Xref comparator
 *
 * - Two xrefs which are null are equals
 * - The xref which is not null is before null.
 * - Use StrictExternalIdentifierComparator to compare first the database and id.
 * - If both xref databases and ids are the same, use StrictCvTermComparator to compare the qualifiers. *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class StrictXrefComparator extends AbstractXrefComparator<StrictExternalIdentifierComparator> {
    @Override
    protected void instantiateIdentifierComparator() {
        this.identifierComparator = new StrictExternalIdentifierComparator();
    }
}
