package psidev.psi.mi.jami.utils.comparator.cv;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.util.Comparator;

/**
 * Unambiguous comparator for CvTerms :
 * If one CvTerm does not have any identifiers, it will only compare the short names (case insensitive).
 * If both CvTerm objects have identifiers, it will look for exact same collection of identifiers using UnambiguousExternalIdentifierComparator and ignores all the other properties.
 *
 * - Two CvTerms which are null are equals
 * - The CvTerm which is not null is before null.
 * - If the two external identifiers are set, use UnambiguousExternalIdentifier comparator
 * - The CvTerm without an external identifier is after the CvTerm with an identifier (do not compare short names if we have one external identifier)
 * - When both CvTerms do not have an external identifier, it compares the short names (case insensitive) which cannot be null
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousCvTermComparator implements Comparator<CvTerm> {

    private static UnambiguousCvTermComparator unambiguousCvTermComparator;
    protected UnambiguousExternalIdentifierComparator identifierComparator;

    /**
     * Creates a new CvTermComparator with UnambiguousExternalIdentifierComparator
     *
     */
    public UnambiguousCvTermComparator() {
        this.identifierComparator = new UnambiguousExternalIdentifierComparator();
    }

    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return identifierComparator;
    }

    /**
     * If one CvTerm does not have any identifiers, it will only compare the short names (case insensitive).
     * If both CvTerm objects have identifiers, it will look for exact same collection of identifiers using UnambiguousExternalIdentifierComparator and ignores all the other properties.
     *
     * - Two CvTerms which are null are equals
     * - The CvTerm which is not null is before null.
     * - If the two external identifiers are set, use UnambiguousExternalIdentifier comparator
     * - The CvTerm without an external identifier is after the CvTerm with an identifier (do not compare short names if we have one external identifier)
     * - When both CvTerms do not have an external identifier, it compares the short names (case insensitive) which cannot be null
     * @param cvTerm1
     * @param cvTerm2
     * @return
     */
    public int compare(CvTerm cvTerm1, CvTerm cvTerm2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (cvTerm1 == null && cvTerm2 == null){
            return EQUAL;
        }
        else if (cvTerm1 == null){
            return AFTER;
        }
        else if (cvTerm2 == null){
            return BEFORE;
        }
        else {
            // first compares mi identifiers.
            String mi1 = cvTerm1.getMIIdentifier();
            String mi2 = cvTerm2.getMIIdentifier();
            String mod1 = cvTerm1.getMODIdentifier();
            String mod2 = cvTerm2.getMODIdentifier();
            String par1 = cvTerm1.getPARIdentifier();
            String par2 = cvTerm2.getPARIdentifier();

            if (mi1 != null && mi2 != null){
                return mi1.compareTo(mi2);
            }
            else if (mi1 != null){
                return BEFORE;
            }
            else if (mi2 != null){
                return AFTER;
            }
            else if (mod1 != null && mod2 != null){
                return mod1.compareTo(mod2);
            }
            else if (mod1 != null){
                return BEFORE;
            }
            else if (mod2 != null){
                return AFTER;
            }
            else if (par1 != null && par2 != null){
                return par1.compareTo(par2);
            }
            else if (par1 != null){
                return BEFORE;
            }
            else if (par2 != null){
                return AFTER;
            }
            else {
                // check names which cannot be null because we could not compare the identifiers
                String label1 = cvTerm1.getShortName();
                String label2 = cvTerm2.getShortName();

                return label1.toLowerCase().trim().compareTo(label2.toLowerCase().trim());
            }
        }
    }

    /**
     * Use UnambiguousCvTermComparator to know if two CvTerms are equals.
     * @param cv1
     * @param cv2
     * @return true if the two CvTerms are equal
     */
    public static boolean areEquals(CvTerm cv1, CvTerm cv2){
        if (unambiguousCvTermComparator == null){
            unambiguousCvTermComparator = new UnambiguousCvTermComparator();
        }

        return unambiguousCvTermComparator.compare(cv1, cv2) == 0;
    }

    /**
     *
     * @param cv1
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(CvTerm cv1){
        if (unambiguousCvTermComparator == null){
            unambiguousCvTermComparator = new UnambiguousCvTermComparator();
        }

        if (cv1 == null){
            return 0;
        }

        int hashcode = 31;

        if (cv1.getMIIdentifier() != null){
            hashcode = 31*hashcode + cv1.getMIIdentifier().hashCode();
        }
        else if (cv1.getMODIdentifier() != null){
            hashcode = 31*hashcode + cv1.getMODIdentifier().hashCode();
        }
        else if (cv1.getPARIdentifier() != null){
            hashcode = 31*hashcode + cv1.getPARIdentifier().hashCode();
        }
        else {
            hashcode = 31*hashcode + cv1.getShortName().toLowerCase().trim().hashCode();
        }

        return hashcode;
    }
}
