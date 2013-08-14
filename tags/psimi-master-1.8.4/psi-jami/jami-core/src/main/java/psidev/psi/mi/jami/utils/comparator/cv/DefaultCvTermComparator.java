package psidev.psi.mi.jami.utils.comparator.cv;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

import java.util.*;

/**
 * Default comparator for CvTerms.
 * If one CvTerm does not have any identifiers (MOD or MI, then the all identifiers), it will only compare the short names (case insensitive).
 * If both CvTerm objects have identifiers, it will look for at least one identical identifier using DefaultExternalIdentifierComparator and ignores all the other properties.
 *
 * - Two CvTerms which are null are equals
 * - If the two external identifiers are set, use DefaultExternalIdentifierComparator
 * - When one of the CvTerms (or both CvTerms) do not have an external identifier, it compares the short names (case insensitive) which cannot be null
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultCvTermComparator {

    /**
     * Use DefaultCvTermComparator to know if two CvTerms are equals.
     * @param cvTerm1
     * @param cvTerm2
     * @return true if the two CvTerms are equal
     */
    public static boolean areEquals(CvTerm cvTerm1, CvTerm cvTerm2){
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (cvTerm1 == null && cvTerm2 == null){
            return true;
        }
        else if (cvTerm1 == null || cvTerm2 == null){
            return false;
        }
        else {
            // first compares mi identifiers.
            String mi1 = cvTerm1.getMIIdentifier();
            String mi2 = cvTerm2.getMIIdentifier();
            String mod1 = cvTerm1.getMODIdentifier();
            String mod2 = cvTerm2.getMODIdentifier();
            String par1 = cvTerm1.getPARIdentifier();
            String par2 = cvTerm2.getPARIdentifier();

            // first compares identifiers if both CvTerms have identifiers.
            if (mi1 != null && mi2 != null){
                return mi1.equals(mi2);
            }
            else if (mod2 != null && mod1 != null){
                return mod1.equals(mod2);
            }
            else if (par2 != null && par1 != null){
                return par1.equals(par2);
            }
            else if (!cvTerm1.getIdentifiers().isEmpty() && !cvTerm2.getIdentifiers().isEmpty()){
                // get an iterator
                Iterator<Xref> iterator1 = cvTerm1.getIdentifiers().iterator();

                // at least one external identifier must match
                boolean comp = false;
                while (!comp && iterator1.hasNext()){
                    Xref altid1 = iterator1.next();

                    for (Xref altid2 : cvTerm2.getIdentifiers()){
                        if (DefaultExternalIdentifierComparator.areEquals(altid1, altid2)){
                            return true;
                        }
                    }
                }

                return false;
            }
            else {
                // check names which cannot be null because we could not compare the identifiers
                String label1 = cvTerm1.getShortName();
                String label2 = cvTerm2.getShortName();

                return label1.equalsIgnoreCase(label2);
            }
        }
    }
}
