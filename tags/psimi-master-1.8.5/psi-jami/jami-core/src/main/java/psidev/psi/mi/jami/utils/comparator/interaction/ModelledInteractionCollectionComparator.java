package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for collection of modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class ModelledInteractionCollectionComparator extends CollectionComparator<ModelledInteraction> {
    /**
     * Creates a new modelled interaction CollectionComparator. It requires a Comparator for the parameters in the Collection
     *
     * @param modelledInteractionComparator
     */
    public ModelledInteractionCollectionComparator(ModelledInteractionComparator modelledInteractionComparator) {
        super(modelledInteractionComparator);
    }

    @Override
    public ModelledInteractionComparator getObjectComparator() {
        return (ModelledInteractionComparator) objectComparator;
    }
}