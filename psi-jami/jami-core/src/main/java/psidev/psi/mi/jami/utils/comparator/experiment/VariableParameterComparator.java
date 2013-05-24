package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic comparator for VariableParameterComparator
 *
 * It will first compare the description (case insensitive). Then it will compare the unit using AbstractCvTermComparator.
 * Then it will compare the collection of VariableParameterValue using the VariableParameterValueComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class VariableParameterComparator implements Comparator<VariableParameter> {

    private AbstractCvTermComparator cvTermComparator;
    private VariableParameterValueCollectionComparator variableParameterValueCollectionComparator;

    public VariableParameterComparator(AbstractCvTermComparator cvTermComparator){
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The cvTermComparator should not be null and is needed to compare units.");
        }
        this.cvTermComparator = cvTermComparator;
        this.variableParameterValueCollectionComparator = new VariableParameterValueCollectionComparator();
    }


    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public VariableParameterValueCollectionComparator getVariableParameterValueCollectionComparator() {
        return variableParameterValueCollectionComparator;
    }

    /**
     * It will first compare the description (case insensitive). Then it will compare the unit using AbstractCvTermComparator.
     * Then it will compare the collection of VariableParameterValue using the VariableParameterValueComparator.
     * @param variableParameter1
     * @param variableParameter2
     * @return
     */
    public int compare(VariableParameter variableParameter1, VariableParameter variableParameter2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (variableParameter1 == null && variableParameter2 == null){
            return EQUAL;
        }
        else if (variableParameter1 == null){
            return AFTER;
        }
        else if (variableParameter2 == null){
            return BEFORE;
        }
        else {
            // first compares values
            String description1 = variableParameter1.getDescription();
            String description2 = variableParameter2.getDescription();

            int comp = description1.toLowerCase().trim().compareTo(description2.toLowerCase().trim());

            if (comp != 0){
                return comp;
            }

            CvTerm unit1 = variableParameter1.getUnit();
            CvTerm unit2 = variableParameter2.getUnit();

            comp = cvTermComparator.compare(unit1, unit2);
            if (comp != 0){
                return comp;
            }

            Collection<VariableParameterValue> parameterValues1 = variableParameter1.getVariableValues();
            Collection<VariableParameterValue> parameterValues2 = variableParameter2.getVariableValues();
            return variableParameterValueCollectionComparator.compare(parameterValues1, parameterValues2);
        }
    }
}
