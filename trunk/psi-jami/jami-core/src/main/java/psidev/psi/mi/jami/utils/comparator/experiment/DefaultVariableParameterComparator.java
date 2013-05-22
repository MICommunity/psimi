package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * DefaultVariableParameter comparator
 * It will first compare the description (case insensitive). Then it will compare the unit using DefaultCvTermComparator.
 * Then it will compare the collection of VariableParameterValue using the VariableParameterValueComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultVariableParameterComparator extends VariableParameterComparator {

    private static DefaultVariableParameterComparator defaultVariableParameterComparator;

    public DefaultVariableParameterComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    /**
     * It will first compare the description (case insensitive). Then it will compare the unit using DefaultCvTermComparator.
     * Then it will compare the collection of VariableParameterValue using the VariableParameterValueComparator.
     */
    public int compare(VariableParameter param1, VariableParameter param2) {
        return super.compare(param1, param2);
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) super.getCvTermComparator();
    }

    /**
     * Use DefaultVariableParameterComparator to know if two variableParameter are equals.
     * @param param1
     * @param param2
     * @return true if the two variableParameters are equal
     */
    public static boolean areEquals(VariableParameter param1, VariableParameter param2){
        if (defaultVariableParameterComparator == null){
            defaultVariableParameterComparator = new DefaultVariableParameterComparator();
        }

        return defaultVariableParameterComparator.compare(param1, param2) == 0;
    }
}
