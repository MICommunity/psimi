package psidev.psi.mi.jami.utils.comparator.cv;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class UnambiguousCvTermComparator extends CvTermComparator {

    private static UnambiguousCvTermComparator unambiguousCvTermComparator;

    /**
     * Creates a new CvTermComparator with UnambiguousExternalIdentifierComparator
     *
     */
    public UnambiguousCvTermComparator() {
        super(new UnambiguousExternalIdentifierComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator)super.getIdentifierComparator();
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
        else if (!cv1.getIdentifiers().isEmpty()){
            List<Xref> list1 = new ArrayList<Xref>(cv1.getIdentifiers());
            Collections.sort(list1, unambiguousCvTermComparator.getIdentifierComparator());
            for (Xref ref : list1){
                hashcode = 31*hashcode + UnambiguousExternalIdentifierComparator.hashCode(ref);
            }
        }
        else {
            hashcode = 31*hashcode + cv1.getShortName().toLowerCase().trim().hashCode();
        }

        return hashcode;
    }
}
