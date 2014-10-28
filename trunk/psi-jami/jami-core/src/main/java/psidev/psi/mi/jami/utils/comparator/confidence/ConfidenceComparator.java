package psidev.psi.mi.jami.utils.comparator.confidence;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Simple Comparator for Confidences.
 * It will compares the confidence types first, then the units and finally the value.
 *
 * - Two confidences which are null are equals
 * - The confidence which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class ConfidenceComparator implements Comparator<Confidence>{

    private Comparator<CvTerm> cvTermComparator;

    /**
     * Creates a new ConfidenceComparator.
     * @param termComparator: CvTerm comparator for the types and units. It is required
     */
    public ConfidenceComparator(Comparator<CvTerm> termComparator){

        if (termComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required for comparing confidence types and units. It cannot be null");
        }

        this.cvTermComparator = termComparator;
    }

    public Comparator<CvTerm> getCvTermComparator() {
        return cvTermComparator;
    }

    /**
     * It will compares the confidence types first, then the units and finally the value.
     *
     * - Two confidences which are null are equals
     * - The confidence which is not null is before null.
     * @param confidence1
     * @param confidence2
     * @return
     */
    public int compare(Confidence confidence1, Confidence confidence2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (confidence1 == confidence2){
            return 0;
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

            String value1 = confidence1.getValue();
            String value2 = confidence2.getValue();

            return value1.compareTo(value2);
        }
    }
}
