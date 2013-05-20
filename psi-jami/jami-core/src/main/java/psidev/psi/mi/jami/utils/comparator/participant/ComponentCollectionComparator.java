package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for collection of components
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ComponentCollectionComparator extends CollectionComparator<Component> {

    /**
     * Creates a new component CollectionComparator. It requires a Comparator for the components in the Collection
     *
     * @param componentComparator
     */
    public ComponentCollectionComparator(ComponentComparator componentComparator) {
        super(componentComparator);
    }

    @Override
    public ComponentComparator getObjectComparator() {
        return (ComponentComparator) objectComparator;
    }
}
