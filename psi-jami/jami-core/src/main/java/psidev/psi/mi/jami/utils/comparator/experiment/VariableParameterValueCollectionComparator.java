package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for a collection of variableParameterValues
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class VariableParameterValueCollectionComparator extends CollectionComparator<VariableParameterValue> {
    /**
     * Creates a new CollectionComparator.
     *
     */
    public VariableParameterValueCollectionComparator() {
        super(new VariableParameterValueComparator());
    }


    @Override
    public VariableParameterValueComparator getObjectComparator() {
        return (VariableParameterValueComparator) objectComparator;
    }
}
