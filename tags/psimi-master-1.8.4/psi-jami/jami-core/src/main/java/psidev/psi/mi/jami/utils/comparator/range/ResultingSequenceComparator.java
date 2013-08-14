package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.ResultingSequence;

import java.util.Comparator;

/**
 * Simple resultingSequence comparator
 *
 * It will first compare the original sequence and then if the original sequences are the same, it will compare the
 * new sequences (case insensitive).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class ResultingSequenceComparator implements Comparator<ResultingSequence>{

    private static ResultingSequenceComparator resultingSequenceComparator;

    /**
     * It will first compare the original sequence and then if the original sequences are the same, it will compare the
     * new sequences.
     * @param resultingSequence1
     * @param resultingSequence2
     * @return
     */
    public int compare(ResultingSequence resultingSequence1, ResultingSequence resultingSequence2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (resultingSequence1 == null && resultingSequence2 == null){
            return EQUAL;
        }
        else if (resultingSequence1 == null){
            return AFTER;
        }
        else if (resultingSequence2 == null){
            return BEFORE;
        }
        else {

            String originalSequence1 = resultingSequence1.getOriginalSequence();
            String originalSequence2 = resultingSequence2.getOriginalSequence();
            int comp;

            if (originalSequence1 == null && originalSequence2 == null){
                comp = EQUAL;
            }
            else if (originalSequence1 == null){
                comp = AFTER;
            }
            else if (originalSequence2 == null){
                comp = BEFORE;
            }
            else{
                comp = originalSequence1.toLowerCase().trim().compareTo(originalSequence2.toLowerCase().trim());
            }
            if (comp != 0){
               return comp;
            }

            String newSequence1 = resultingSequence1.getNewSequence();
            String newSequence2 = resultingSequence2.getNewSequence();

            if (newSequence1 == null && newSequence2 == null){
                return EQUAL;
            }
            else if (newSequence1 == null){
                return AFTER;
            }
            else if (newSequence2 == null){
                return BEFORE;
            }
            else{
                return newSequence1.toLowerCase().trim().compareTo(newSequence2.toLowerCase().trim());
            }
        }
    }


    /**
     * Use ResultingSequenceComparator to know if two resulting sequences are equals.
     * @param resultingSequence1
     * @param resultingSequence2
     * @return true if the two resulting sequences are equal
     */
    public static boolean areEquals(ResultingSequence resultingSequence1, ResultingSequence resultingSequence2){
        if (resultingSequenceComparator == null){
            resultingSequenceComparator = new ResultingSequenceComparator();
        }

        return resultingSequenceComparator.compare(resultingSequence1, resultingSequence2) == 0;
    }

    /**
     *
     * @param resultingSequence
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(ResultingSequence resultingSequence){
        if (resultingSequenceComparator == null){
            resultingSequenceComparator = new ResultingSequenceComparator();
        }

        if (resultingSequence == null){
            return 0;
        }

        int hashcode = 31;
        String originalSequence = resultingSequence.getOriginalSequence();
        hashcode = 31*hashcode + (originalSequence != null ? originalSequence.toLowerCase().trim().hashCode() : 0);
        String newSequence = resultingSequence.getNewSequence();
        hashcode = 31*hashcode + (newSequence != null ? newSequence.toLowerCase().trim().hashCode() : 0);

        return hashcode;
    }
}
