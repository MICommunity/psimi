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
 * - The CvTerm which is not null is before null.
 * - If the two external identifiers are set, use DefaultExternalIdentifierComparator
 * - When one of the CvTerms (or both CvTerms) do not have an external identifier, it compares the short names (case insensitive) which cannot be null
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultCvTermComparator implements Comparator<CvTerm> {

    private static DefaultCvTermComparator defaultCvTermComparator;
    private DefaultExternalIdentifierComparator identifierComparator;

    public DefaultCvTermComparator() {
        this.identifierComparator = new DefaultExternalIdentifierComparator();
    }

    public DefaultExternalIdentifierComparator getIdentifierComparator(){
        return identifierComparator;
    }

    /**
     * If one CvTerm does not have any identifiers, it will only compare the short names (case insensitive).
     * If both CvTerm objects have identifiers, it will look for at least one identical identifier using DefaultExternalIdentifierComparator and ignores all the other properties.
     *
     * - Two CvTerms which are null are equals
     * - The CvTerm which is not null is before null.
     * - If the two external identifiers are set, use DefaultExternalIdentifierComparator
     * - When one of the CvTerms (or both CvTerms) do not have an external identifier, it compares the short names (case insensitive) which cannot be null
     *
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

            // first compares identifiers if both CvTerms have identifiers.
            if (mi1 != null && mi2 != null){
                return mi1.compareTo(mi2);
            }
            else if (mod2 != null && mod1 != null){
                return mod1.compareTo(mod2);
            }
            else if (par2 != null && par1 != null){
                return par1.compareTo(par2);
            }
            else if (!cvTerm1.getIdentifiers().isEmpty() && !cvTerm2.getIdentifiers().isEmpty()){
                List<Xref> ids1 = new ArrayList<Xref>(cvTerm1.getIdentifiers());
                List<Xref> ids2 = new ArrayList<Xref>(cvTerm2.getIdentifiers());
                // sort the collections first
                Collections.sort(ids1, identifierComparator);
                Collections.sort(ids2, identifierComparator);
                // get an iterator
                Iterator<Xref> iterator1 = ids1.iterator();
                Iterator<Xref> iterator2 = ids2.iterator();

                // at least one external identifier must match
                Xref altid1 = iterator1.next();
                Xref altid2 = iterator2.next();
                int comp = identifierComparator.compare(altid1, altid2);
                while (comp != 0 && altid1 != null && altid2 != null){
                    // altid1 is before altid2
                    if (comp < 0){
                        // we need to get the next element from ids1
                        if (iterator1.hasNext()){
                            altid1 = iterator1.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 1 is empty, we can stop here
                        else {
                            altid1 = null;
                        }
                    }
                    // altid2 is before altid1
                    else {
                        // we need to get the next element from ids2
                        if (iterator2.hasNext()){
                            altid2 = iterator2.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 2 is empty, we can stop here
                        else {
                            altid2 = null;
                        }
                    }
                }

                return comp;
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
