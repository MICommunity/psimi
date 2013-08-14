package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for a collection of variableParameters
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class VariableParameterCollectionComparator extends CollectionComparator<VariableParameter> {

    /**
     * Creates a new CollectionComparator.
     *
     */
    public VariableParameterCollectionComparator(VariableParameterComparator comparator) {
        super(comparator);
    }


    @Override
    public VariableParameterComparator getObjectComparator() {
        return (VariableParameterComparator) objectComparator;
    }
}
