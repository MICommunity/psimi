package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * DefaultVariableParameter comparator
 * It will first compare the description (case insensitive). Then it will compare the unit using DefaultCvTermComparator.
 * Then it will compare the collection of VariableParameterValue using the VariableParameterValueComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultVariableParameterComparator {

    /**
     * Use DefaultVariableParameterComparator to know if two variableParameter are equals.
     * @param variableParameter1
     * @param variableParameter2
     * @return true if the two variableParameters are equal
     */
    public static boolean areEquals(VariableParameter variableParameter1, VariableParameter variableParameter2){
        if (variableParameter1 == null && variableParameter2 == null){
            return true;
        }
        else if (variableParameter1 == null || variableParameter2 == null){
            return false;
        }
        else {
            // first compares values
            String description1 = variableParameter1.getDescription();
            String description2 = variableParameter2.getDescription();

            if (!description1.equalsIgnoreCase(description2)){
                return false;
            }

            CvTerm unit1 = variableParameter1.getUnit();
            CvTerm unit2 = variableParameter2.getUnit();

            if (!DefaultCvTermComparator.areEquals(unit1, unit2)){
                return false;
            }

            Collection<VariableParameterValue> parameterValues1 = variableParameter1.getVariableValues();
            Collection<VariableParameterValue> parameterValues2 = variableParameter2.getVariableValues();
            // compare collections
            Iterator<VariableParameterValue> f1Iterator = new ArrayList<VariableParameterValue>(parameterValues1).iterator();
            Collection<VariableParameterValue> f2List = new ArrayList<VariableParameterValue>(parameterValues2);

            while (f1Iterator.hasNext()){
                VariableParameterValue f1 = f1Iterator.next();
                VariableParameterValue f2ToRemove = null;
                for (VariableParameterValue f2 : f2List){
                    if (VariableParameterValueComparator.areEquals(f1, f2)){
                        f2ToRemove = f2;
                        break;
                    }
                }
                if (f2ToRemove != null){
                    f2List.remove(f2ToRemove);
                    f1Iterator.remove();
                }
                else {
                    return false;
                }
            }

            if (f1Iterator.hasNext() || !f2List.isEmpty()){
                return false;
            }
            else{
                return true;
            }
        }
    }
}
