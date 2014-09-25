package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for a collection of variableParameterValueSet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class VariableParameterValueSetCollectionComparator extends CollectionComparator<VariableParameterValueSet> {

    /**
     * Creates a new CollectionComparator.
     *
     */
    public VariableParameterValueSetCollectionComparator() {
        super(new VariableParameterValueSetComparator());
    }


    @Override
    public VariableParameterValueSetComparator getObjectComparator() {
        return (VariableParameterValueSetComparator) objectComparator;
    }
}
