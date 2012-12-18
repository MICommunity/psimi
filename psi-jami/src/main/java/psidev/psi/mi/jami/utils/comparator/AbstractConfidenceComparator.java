package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Abstract class for Confidence comparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public abstract class AbstractConfidenceComparator<T extends AbstractCvTermComparator> implements Comparator<Confidence>{

    protected T cvTermComparator;

    public AbstractConfidenceComparator(){
       instantiateCvTermComparator();
    }

    protected abstract void instantiateCvTermComparator();

    public int compare(Confidence confidence1, Confidence confidence2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (confidence1 == null && confidence2 == null){
            return EQUAL;
        }
        else if (confidence1 == null){
            return AFTER;
        }
        else if (confidence2 == null){
            return BEFORE;
        }
        else {
            CvTerm type1 = confidence1.getType();
            CvTerm type2 = confidence2.getType();

            int comp = cvTermComparator.compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // check unit
            CvTerm unit1 = confidence1.getUnit();
            CvTerm unit2 = confidence2.getUnit();

            // no units, check the value
            if (unit1 == null && unit2 == null){
                String value1 = confidence1.getValue();
                String value2 = confidence2.getValue();

                return value1.compareTo(value2);
            }
            else if (unit1 == null){
                return AFTER;
            }
            else if (unit2 == null){
                return BEFORE;
            }
            else {

                int comp2 = cvTermComparator.compare(unit1, unit2);
                if (comp2 != 0){
                    return comp2;
                }

                String value1 = confidence1.getValue();
                String value2 = confidence2.getValue();

                return value1.compareTo(value2);
            }
        }
    }
}
