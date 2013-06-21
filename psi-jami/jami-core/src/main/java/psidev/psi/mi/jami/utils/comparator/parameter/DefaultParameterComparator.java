package psidev.psi.mi.jami.utils.comparator.parameter;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.math.BigDecimal;

/**
 * Default parameter comparator.
 *
 *
 * - Two parameters which are null are equals
 * - The parameter which is not null is before null.
 * - Use DefaultCvTermComparator to compare first the parameter types.
 * - If parameter types are equals, use DefaultCvTermComparator to compare the units.
 * - If the units are not set, compares the values
 * - If both units are set and If they are equals, compares the values (case sensitive)
 * - If both values are equals, compares the uncertainty.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class DefaultParameterComparator {

    /**
     * Use DefaultComparator to know if two parameters are equals.
     * @param parameter1
     * @param parameter2
     * @return true if the two parameters are equal
     */
    public static boolean areEquals(Parameter parameter1, Parameter parameter2){

        if (parameter1 == null && parameter2 == null){
            return true;
        }
        else if (parameter1 == null || parameter2 == null){
            return false;
        }
        else {
            CvTerm type1 = parameter1.getType();
            CvTerm type2 = parameter2.getType();

            if (DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }

            // compare units
            CvTerm unit1 = parameter1.getUnit();
            CvTerm unit2 = parameter2.getUnit();

            if (DefaultCvTermComparator.areEquals(unit1, unit2)){
                return false;
            }

            // compare values
            ParameterValue value1 = parameter1.getValue();
            ParameterValue value2 = parameter2.getValue();

            if (ParameterValueComparator.areEquals(value1, value2)){
                return false;
            }

            // compare uncertainty
            BigDecimal uncertainty1 = parameter1.getUncertainty();
            BigDecimal uncertainty2 = parameter2.getUncertainty();
            if (uncertainty1 == null && uncertainty2 == null){
                return true;
            }
            else if (uncertainty1 == null || uncertainty2 == null){
                return false;
            }
            else {
                return uncertainty1.equals(uncertainty2);
            }
        }
    }
}
