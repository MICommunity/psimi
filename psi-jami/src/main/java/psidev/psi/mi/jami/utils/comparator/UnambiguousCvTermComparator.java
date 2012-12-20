package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;

/**
 * Unambiguous comparator for CvTerms :
 * It will only compare the short names (case insensitive) when both identifiers are null.
 * The CvTerm with a non null identifier will always comes before the one with a null identifier.
 * If both identifiers are set, it will compare the ontology identifiers using UnambiguousExternalIdentifierComparator and ignores all the other properties.
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

public class UnambiguousCvTermComparator extends AbstractCvTermComparator {

    /**
     * Creates a new CvTermComparator with UnambiguousExternalIdentifierComparator
     *
     */
    public UnambiguousCvTermComparator() {
        super(new UnambiguousExternalIdentifierComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) identifierComparator;
    }

    /**
     * It will only compare the short names (case insensitive) when both identifiers are null.
     * The CvTerm with a non null identifier will always comes before the one with a null identifier.
     * If both identifiers are set, it will compare the ontology identifiers using UnambiguousExternalIdentifierComparator and ignores all the other properties.
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
            ExternalIdentifier externalIdentifier1 = cvTerm1.getOntologyIdentifier();
            ExternalIdentifier externalIdentifier2 = cvTerm1.getOntologyIdentifier();
            int comp2 = identifierComparator.compare(externalIdentifier1, externalIdentifier2);
            if (comp2 != 0){
               return comp2;
            }

            // check names which cannot be null
            String label1 = cvTerm1.getShortName();
            String label2 = cvTerm1.getShortName();

            return label1.toLowerCase().trim().compareTo(label2.toLowerCase().trim());
        }
    }
}
