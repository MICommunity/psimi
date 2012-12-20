package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Simple comparator for Parameter
 *
 * It first compares parameter types, then parameter units and then it uses ParameterValueComparator for comparing parameter values
 * It will also compare the uncertainty.
 * - Two parameters which are null are equals
 * - The parameter which is not null is before null.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public abstract class ParameterComparator implements Comparator<Parameter> {

    protected AbstractCvTermComparator cvTermComparator;
    protected ParameterValueComparator valueComparator;

    /**
     * Creates a new ParameterComparator
     * @param cvTermComparator : cv term comparator to compare parameter types and units. It is required
     */
    public ParameterComparator(AbstractCvTermComparator cvTermComparator){
        if(cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required for comparing parameter types and units. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
        valueComparator = new ParameterValueComparator();
    }

    /**
     * Creates a new ParameterComparator
     * @param cvTermComparator : cv term comparator to compare parameter types and units. It is required
     * @param valueComparator : parameter value comparator. If null, it will create a new ParameterValueComparator
     */
    public ParameterComparator(AbstractCvTermComparator cvTermComparator, ParameterValueComparator valueComparator){
        if(cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required for comparing parameter types and units. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
        this.valueComparator = valueComparator != null ? valueComparator : new ParameterValueComparator();
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public ParameterValueComparator getValueComparator() {
        return valueComparator;
    }

    /**
     * It first compares parameter types, then parameter units and then it uses ParameterValueComparator for comparing parameter values
     * It will also compare the uncertainty.
     * - Two parameters which are null are equals
     * - The parameter which is not null is before null.
     * @param parameter1
     * @param parameter2
     * @return
     */
    public int compare(Parameter parameter1, Parameter parameter2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (parameter1 == null && parameter2 == null){
            return EQUAL;
        }
        else if (parameter1 == null){
            return AFTER;
        }
        else if (parameter2 == null){
            return BEFORE;
        }
        else {
            CvTerm type1 = parameter1.getType();
            CvTerm type2 = parameter2.getType();

            int comp = cvTermComparator.compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // compare units
            CvTerm unit1 = parameter1.getUnit();
            CvTerm unit2 = parameter2.getUnit();

            int comp2 = cvTermComparator.compare(unit1, unit2);
            if (comp2 != 0){
                return comp2;
            }

            // compare values
            ParameterValue value1 = parameter1.getValue();
            ParameterValue value2 = parameter2.getValue();

            int comp3 = valueComparator.compare(value1, value2);
            if (comp3 != 0){
                return comp3;
            }

            // compare uncertainty
            BigDecimal uncertainty1 = parameter1.getUncertainty();
            BigDecimal uncertainty2 = parameter2.getUncertainty();
            if (uncertainty1 == null && uncertainty2 == null){
                return EQUAL;
            }
            else if (uncertainty1 == null){
                return AFTER;
            }
            else if (uncertainty2 == null){
                return BEFORE;
            }
            else {
                return uncertainty1.compareTo(uncertainty2);
            }
        }
    }
}
