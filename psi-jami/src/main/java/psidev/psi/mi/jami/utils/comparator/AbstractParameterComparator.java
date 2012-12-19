package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Abstract class for parameter comparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public abstract class AbstractParameterComparator<T extends AbstractCvTermComparator> implements Comparator<Parameter> {

    protected T cvTermComparator;

    public AbstractParameterComparator(){
        instantiateCvTermComparator();
    }

    protected abstract void instantiateCvTermComparator();

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

            // check unit
            CvTerm unit1 = parameter1.getUnit();
            CvTerm unit2 = parameter2.getUnit();

            int comp2 = cvTermComparator.compare(unit1, unit2);
            if (comp2 != 0){
                return comp2;
            }

            // check value
            ParameterValue value1 = parameter1.getValue();
            ParameterValue value2 = parameter2.getValue();

            int comp3 = (value1.getFactor().multiply(BigDecimal.valueOf(value1.getBase()^value1.getExponent())))
                    .compareTo(value2.getFactor().multiply(BigDecimal.valueOf(value2.getBase()^value2.getExponent())));
            if (comp3 != 0){
                return comp3;
            }

            // check uncertainty
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
