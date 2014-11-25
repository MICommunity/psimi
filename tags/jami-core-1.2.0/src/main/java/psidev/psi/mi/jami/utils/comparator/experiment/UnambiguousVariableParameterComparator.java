package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous VariableParameter comparator
 * It will first compare the description (case insensitive). Then it will compare the unit using UnambiguousCvTermComparator.
 * Then it will compare the collection of VariableParameterValue using the VariableParameterValueComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class UnambiguousVariableParameterComparator extends VariableParameterComparator {

    private static UnambiguousVariableParameterComparator unambiguousVariableParameterComparator;

    public UnambiguousVariableParameterComparator() {
        super(new UnambiguousCvTermComparator());
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
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
    }

    /**
     * Use UnambiguousVariableParameterComparator to know if two variableParameter are equals.
     * @param param1
     * @param param2
     * @return true if the two variableParameters are equal
     */
    public static boolean areEquals(VariableParameter param1, VariableParameter param2){
        if (unambiguousVariableParameterComparator == null){
            unambiguousVariableParameterComparator = new UnambiguousVariableParameterComparator();
        }

        return unambiguousVariableParameterComparator.compare(param1, param2) == 0;
    }

    /**
     *
     * @param param
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(VariableParameter param){
        if (unambiguousVariableParameterComparator == null){
            unambiguousVariableParameterComparator = new UnambiguousVariableParameterComparator();
        }

        if (param == null){
            return 0;
        }

        int hashcode = 31;
        String description = param.getDescription();
        hashcode = 31*hashcode + (description != null ? description.toLowerCase().trim().hashCode() : 0);

        CvTerm unit = param.getUnit();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(unit);

        List<VariableParameterValue> list1 = new ArrayList<VariableParameterValue>(param.getVariableValues());
        Collections.sort(list1, unambiguousVariableParameterComparator.getVariableParameterValueCollectionComparator().getObjectComparator());
        for (VariableParameterValue value : list1){
            hashcode = 31*hashcode + VariableParameterValueComparator.hashCode(value);
        }

        return hashcode;
    }
}
