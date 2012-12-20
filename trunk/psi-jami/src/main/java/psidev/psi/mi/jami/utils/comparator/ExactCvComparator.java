package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;

/**
 * Exact comparator for CvTerms :
 * It will first compare the identifiers, then the short labels (case insensitive), then the fullnames (case insensitive)
 * and then the definitions (case insensitive). It will ignore annotations, xrefs, synonyms, parents and children
 *
 * - Two CvTerms which are null are equals
 * - The CvTerm which is not null is before null.
 * - If the two external identifiers are set, use ExactExternalIdentifier comparator
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class ExactCvComparator extends UnambiguousCvTermComparator {

    /**
     * Creates a new CvTermComparator with ExactExternalIdentifierComparator
     *
     */
    public ExactCvComparator() {
        super(new ExactExternalIdentifierComparator());
    }

    /**
     * Creates a new CvTermComparator
     * @param comparator : external identifier comparator which is required to compare ontology identifiers
     */
    public ExactCvComparator(ExactExternalIdentifierComparator comparator){
        super(comparator);
    }

    @Override
    public ExactExternalIdentifierComparator getIdentifierComparator() {
        return (ExactExternalIdentifierComparator) identifierComparator;
    }

    /**
     * It will first compare the identifiers, then the short labels (case insensitive), then the fullnames (case insensitive)
     * and then the definitions (case insensitive). It will ignore annotations, xrefs, synonyms, [arents and children
     *
     * - Two CvTerms which are null are equals
     * - The CvTerm which is not null is before null.
     * - If the two external identifiers are set, use ExactExternalIdentifier comparator
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
            // parent comparison first
            int comp = super.compare(cvTerm1, cvTerm2);
            if (comp != 0){
                return comp;
            }

            // compare full names
            int comp4;
            String fullName1 = cvTerm1.getFullName();
            String fullName2 = cvTerm2.getFullName();

            if (fullName1 == null && fullName2 == null){
                comp4 = EQUAL;
            }
            else if (fullName1 == null){
                return AFTER;
            }
            else if (fullName2 == null){
                return BEFORE;
            }
            else {
                comp4 = fullName1.toLowerCase().compareTo(fullName2.toLowerCase());
            }
            if (comp4 != 0){
                return comp4;
            }

            // compare definitions
            String def1 = cvTerm1.getDefinition();
            String def2 = cvTerm2.getDefinition();

            if (def1 == null && def2 == null){
                return EQUAL;
            }
            else if (def1 == null){
                return AFTER;
            }
            else if (def2 == null){
                return BEFORE;
            }
            else {
                return def1.toLowerCase().compareTo(def2.toLowerCase());
            }

        }
    }
}
