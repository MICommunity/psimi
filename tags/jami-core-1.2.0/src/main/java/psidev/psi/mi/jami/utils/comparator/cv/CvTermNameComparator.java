package psidev.psi.mi.jami.utils.comparator.cv;

import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Comparator for CvTerms that only takes into consideration the shortName of ths cv term
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class CvTermNameComparator implements Comparator<CvTerm> {

    private static CvTermNameComparator cvTermNameComparator;

    /**
     * Creates a new CvTermComparator with UnambiguousExternalIdentifierComparator
     *
     */
    public CvTermNameComparator() {
    }


    /**
     * Only compares the shortname of two cv terms. The comparison is case insensitive
     * @param cvTerm1
     * @param cvTerm2
     * @return
     */
    public int compare(CvTerm cvTerm1, CvTerm cvTerm2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (cvTerm1 == cvTerm2){
            return 0;
        }
        else if (cvTerm1 == null){
            return AFTER;
        }
        else if (cvTerm2 == null){
            return BEFORE;
        }
        else {
            // check names which cannot be null because we could not compare the identifiers
            String label1 = cvTerm1.getShortName();
            String label2 = cvTerm2.getShortName();

            return label1.toLowerCase().trim().compareTo(label2.toLowerCase().trim());
        }
    }

    /**
     * Use UnambiguousCvTermComparator to know if two CvTerms are equals.
     * @param cv1
     * @param cv2
     * @return true if the two CvTerms are equal
     */
    public static boolean areEquals(CvTerm cv1, CvTerm cv2){
        if (cvTermNameComparator == null){
            cvTermNameComparator = new CvTermNameComparator();
        }

        return cvTermNameComparator.compare(cv1, cv2) == 0;
    }

    /**
     *
     * @param cv1
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(CvTerm cv1){
        if (cvTermNameComparator == null){
            cvTermNameComparator = new CvTermNameComparator();
        }

        if (cv1 == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + cv1.getShortName().toLowerCase().trim().hashCode();

        return hashcode;
    }
}
