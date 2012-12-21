package psidev.psi.mi.jami.utils.comparator.alias;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.alias.AliasComparator;

/**
 * Comparator for collection of aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class AliasesCollectionComparator extends CollectionComparator<Alias> {
    /**
     * Creates a new alias CollectionComparator. It requires a Comparator for the aliases in the Collection
     *
     * @param aliasComparator
     */
    public AliasesCollectionComparator(AliasComparator aliasComparator) {
        super(aliasComparator);
    }

    @Override
    public AliasComparator getObjectComparator() {
        return (AliasComparator) objectComparator;
    }
}
