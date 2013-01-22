package psidev.psi.mi.jami.utils.comparator.parameter;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

import java.math.BigDecimal;

/**
 * Unambiguous parameter comparator.
 *
 * - Two parameters which are null are equals
 * - The parameter which is not null is before null.
 * - Use UnambiguousCvTermComparator to compare first the parameter types.
 * - If parameter types are equals, use UnambiguousCvTermComparator to compare the units.
 * - If the units are not set, compares the values
 * - If both units are set and If they are equals, compares the values (case sensitive)
 * - If both values are equals, compares the uncertainty.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class UnambiguousParameterComparator extends ParameterComparator {

    private static UnambiguousParameterComparator unambiguousParameterComparator;

    public UnambiguousParameterComparator() {
        super(new UnambiguousCvTermComparator());
    }

    public UnambiguousParameterComparator(ParameterValueComparator valueComparator) {
        super(new UnambiguousCvTermComparator(), valueComparator);
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) cvTermComparator;
    }

    /**
     * It first compares parameter types, then parameter units and then it uses ParameterValueComparator for comparing parameter values
     * It will also compare the uncertainty.
     * - Two parameters which are null are equals
     * - The parameter which is not null is before null.
     * - Use UnambiguousCvTermComparator to compare first the parameter types.
     * - If parameter types are equals, use UnambiguousCvTermComparator to compare the units.
     * - If the units are not set, compares the values
     * - If both units are set and If they are equals, compares the values (case sensitive)
     * - If both values are equals, compares the uncertainty.
     * @param parameter1
     * @param parameter2
     * @return
     */
    public int compare(Parameter parameter1, Parameter parameter2) {
        return super.compare(parameter1, parameter2);
    }

    /**
     * Use UnabmbiguousComparatorParameter to know if two parameters are equals.
     * @param param1
     * @param param2
     * @return true if the two parameters are equal
     */
    public static boolean areEquals(Parameter param1, Parameter param2){
        if (unambiguousParameterComparator == null){
            unambiguousParameterComparator = new UnambiguousParameterComparator();
        }

        return unambiguousParameterComparator.compare(param1, param2) == 0;
    }

    /**
     *
     * @param param
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Parameter param){
        if (unambiguousParameterComparator == null){
            unambiguousParameterComparator = new UnambiguousParameterComparator();
        }

        if (param == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm type = param.getType();
        hashcode = 31*hashcode + unambiguousParameterComparator.getCvTermComparator().hashCode(type);

        CvTerm unit = param.getUnit();
        hashcode = 31*hashcode + unambiguousParameterComparator.getCvTermComparator().hashCode(unit);

        ParameterValue value = param.getValue();
        hashcode = 31*hashcode + unambiguousParameterComparator.getValueComparator().hashCode(value);

        BigDecimal uncertainty = param.getUncertainty();
        hashcode = 31*hashcode + (uncertainty != null ? uncertainty.hashCode() : 0);

        return hashcode;
    }
}
