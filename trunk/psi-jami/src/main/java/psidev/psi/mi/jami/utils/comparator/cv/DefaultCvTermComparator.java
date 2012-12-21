package psidev.psi.mi.jami.utils.comparator.cv;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

/**
 * Default comparator for CvTerms.
 * If one of the identifiers is null (or both), it will only compare the short names (case insensitive).
 * If both identifiers are set, it will compare the ontology identifiers using DefaultExternalIdentifierComparator and ignores all the other properties.
 *
 * - Two CvTerms which are null are equals
 * - The CvTerm which is not null is before null.
 * - If the two external identifiers are set, use DefaultExternalIdentifierComparator
 * - When one of the CvTerms (or both CvTerms) do not have an external identifier, it compares the short names (case insensitive) which cannot be null
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultCvTermComparator extends AbstractCvTermComparator {

    private static DefaultCvTermComparator defaultCvTermComparator;

    public DefaultCvTermComparator() {
        super(new DefaultExternalIdentifierComparator());
    }

    @Override
    public DefaultExternalIdentifierComparator getIdentifierComparator(){
        return (DefaultExternalIdentifierComparator) identifierComparator;
    }

    /**
     * If one of the identifiers is null (or both), it will only compare the short names (case insensitive).
     * If both identifiers are set, it will compare the ontology identifiers using DefaultExternalIdentifierComparator and ignores all the other properties.
     *
     * - Two CvTerms which are null are equals
     * - The CvTerm which is not null is before null.
     * - If the two external identifiers are set, use DefaultExternalIdentifierComparator
     * - When one of the CvTerms (or both CvTerms) do not have an external identifier, it compares the short names (case insensitive) which cannot be null
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
            ExternalIdentifier externalIdentifier1 = cvTerm1.getOntologyIdentifier();
            ExternalIdentifier externalIdentifier2 = cvTerm1.getOntologyIdentifier();

            // no identifiers or one identifier is null so relies on short name
            if ((externalIdentifier1 == null && externalIdentifier2 == null)
                    || (externalIdentifier1 != null && externalIdentifier2 == null)
                    || (externalIdentifier1 == null && externalIdentifier2 != null)){
                // check names which cannot be null
                String label1 = cvTerm1.getShortName();
                String label2 = cvTerm1.getShortName();

                return label1.toLowerCase().trim().compareTo(label2.toLowerCase().trim());
            }
            // both identifiers are set so compares identifiers
            else {
                return identifierComparator.compare(externalIdentifier1, externalIdentifier2);
            }
        }
    }

    /**
     * Use DefaultCvTermComparator to know if two CvTerms are equals.
     * @param cv1
     * @param cv2
     * @return true if the two CvTerms are equal
     */
    public static boolean areEquals(CvTerm cv1, CvTerm cv2){
        if (defaultCvTermComparator == null){
            defaultCvTermComparator = new DefaultCvTermComparator();
        }

        return defaultCvTermComparator.compare(cv1, cv2) == 0;
    }
}
