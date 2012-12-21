package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.xref.AbstractXrefComparator;

/**
 * Comparator for collection of Xrefs
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class XrefsCollectionComparator extends CollectionComparator<Xref> {
    /**
     * Creates a new xref CollectionComparator. It requires a Comparator for the xrefs in the Collection
     *
     * @param xrefComparator
     */
    public XrefsCollectionComparator(AbstractXrefComparator xrefComparator) {
        super(xrefComparator);
    }

    @Override
    public AbstractXrefComparator getObjectComparator() {
        return (AbstractXrefComparator) objectComparator;
    }
}
