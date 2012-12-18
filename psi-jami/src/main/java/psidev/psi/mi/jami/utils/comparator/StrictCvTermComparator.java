package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;

/**
 * Strict comparator for CvTerms :
 * - Two CvTerms which are null are equals
 * - The CvTerm which is not null is before null.
 * - If the two external identifiers are set, use StrictExternalIdentifier comparator
 * - The CvTerm without an external identifier is after the CvTerm with an identifier (do not compare short names if we have one external identifier)
 * - When both CvTerms do not have an external identifier, it compares the short names (case insensitive) which cannot be null
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class StrictCvTermComparator extends CvTermComparator<StrictExternalIdentifierComparator>{
    @Override
    protected void instantiateOntologyIdentifierComparator() {
        this.identifierComparator = new StrictExternalIdentifierComparator();
    }

    /**
     * - Two CvTerms which are null are equals
     * - The CvTerm which is not null is before null.
     * - If the two external identifiers are set, use StrictExternalIdentifier comparator
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
            ExternalIdentifier externalIdentifier1 = cvTerm1.getOntologyIdentifier();
            ExternalIdentifier externalIdentifier2 = cvTerm1.getOntologyIdentifier();
            // no identifiers so relies on short name
            if (externalIdentifier1 == null && externalIdentifier2 == null){
                // check names which cannot be null
                String label1 = cvTerm1.getShortName();
                String label2 = cvTerm1.getShortName();

                return label1.toLowerCase().compareTo(label2.toLowerCase());
            }
            // identifier 1 is null so it comes after
            else if (externalIdentifier1 == null){
                return AFTER;
            }
            // identifiers 2 is null so it comes after
            else if (externalIdentifier2 == null){
                return BEFORE;
            }
            else {
                return identifierComparator.compare(externalIdentifier1, externalIdentifier2);
            }
        }
    }
}
