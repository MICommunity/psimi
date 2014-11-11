package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;
import psidev.psi.mi.jami.utils.comparator.range.DefaultRangeAndResultingSequenceComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class for Comparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class ComparatorUtils {

    /**
     * If identifier1 and identifier2 are equals, it returns 0.
     * If identifier1 and identifier2 are not equals:
     *  - the external identifier that is equal to firstIdentifier will always come first
     *  - if both external identifiers are different from the the first identifier, it will return the results of
     *   identifier1.compareTo(identifier2)
     *
     * @param identifier1 : first identifier to compare
     * @param identifier2 : second identifier to compare
     * @param firstIdentifier : the default identifier that we want to have first
     * @return
     */
    public static int compareIdentifiersWithDefaultIdentifier(String identifier1, String identifier2, String firstIdentifier) {
        int comp;
        comp = identifier1.compareTo(identifier2);
        if (comp == 0){
            return 0;
        }

        // the unique gene property is first
        if (firstIdentifier != null && firstIdentifier.equals(identifier1)){
            return -1;
        }
        else if (firstIdentifier != null && firstIdentifier.equals(identifier2)){
            return 1;
        }
        else {
            return comp;
        }
    }

    /**
     * True if there is at least one identifier in identifiers1 that is identical to at least one identifier in identfiers2 (
     * equality based on DefaultExternalIdentifierComparator.areEquals)
     * @param identifiers1
     * @param identifiers2
     * @return
     */
    public static boolean findAtLeastOneMatchingIdentifier(Collection<? extends Xref> identifiers1, Collection<? extends Xref> identifiers2){
        // get an iterator
        Iterator<? extends Xref> iterator1 = identifiers1.iterator();

        // at least one external identifier must match
        boolean comp = false;
        while (!comp && iterator1.hasNext()){
            Xref altid1 = iterator1.next();

            for (Xref altid2 : identifiers2){
                if (DefaultExternalIdentifierComparator.areEquals(altid1, altid2)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean findAtLeastOneMatchingAlias(Collection<? extends Alias> aliases1, Collection<? extends Alias> aliases2){
        // get an iterator
        Iterator<? extends Alias> iterator1 = aliases1.iterator();

        // at least one external identifier must match
        boolean comp = false;
        while (!comp && iterator1.hasNext()){
            Alias altid1 = iterator1.next();

            for (Alias altid2 : aliases2){
                if (DefaultAliasComparator.areEquals(altid1, altid2)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean areCvTermsEqual(Collection<? extends CvTerm> method1, Collection<? extends CvTerm> method2) {
        if (method1.size() != method2.size()){
            return false;
        }
        else {
            Iterator<? extends CvTerm> f1Iterator = new ArrayList<CvTerm>(method1).iterator();
            Collection<? extends CvTerm> f2List = new ArrayList<CvTerm>(method2);

            while (f1Iterator.hasNext()){
                CvTerm f1 = f1Iterator.next();
                CvTerm f2ToRemove = null;
                for (CvTerm f2 : f2List){
                    if (DefaultCvTermComparator.areEquals(f1, f2)){
                        f2ToRemove = f2;
                        break;
                    }
                }
                if (f2ToRemove != null){
                    f2List.remove(f2ToRemove);
                    f1Iterator.remove();
                }
                else {
                    return false;
                }
            }

            if (f1Iterator.hasNext() || !f2List.isEmpty()){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean areParametersEqual(Collection<? extends Parameter> method1, Collection<? extends Parameter> method2) {
        if (method1.size() != method2.size()){
            return false;
        }
        else {
            Iterator<? extends Parameter> f1Iterator = new ArrayList<Parameter>(method1).iterator();
            Collection<? extends Parameter> f2List = new ArrayList<Parameter>(method2);

            while (f1Iterator.hasNext()){
                Parameter f1 = f1Iterator.next();
                Parameter f2ToRemove = null;
                for (Parameter f2 : f2List){
                    if (DefaultParameterComparator.areEquals(f1, f2)){
                        f2ToRemove = f2;
                        break;
                    }
                }
                if (f2ToRemove != null){
                    f2List.remove(f2ToRemove);
                    f1Iterator.remove();
                }
                else {
                    return false;
                }
            }

            if (f1Iterator.hasNext() || !f2List.isEmpty()){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean areRangesEqual(Collection<? extends Range> method1, Collection<? extends Range> method2) {
        if (method1.size() != method2.size()){
            return false;
        }
        else {
            Iterator<? extends Range> f1Iterator = new ArrayList<Range>(method1).iterator();
            Collection<? extends Range> f2List = new ArrayList<Range>(method2);

            while (f1Iterator.hasNext()){
                Range f1 = f1Iterator.next();
                Range f2ToRemove = null;
                for (Range f2 : f2List){
                    if (DefaultRangeAndResultingSequenceComparator.areEquals(f1, f2)){
                        f2ToRemove = f2;
                        break;
                    }
                }
                if (f2ToRemove != null){
                    f2List.remove(f2ToRemove);
                    f1Iterator.remove();
                }
                else {
                    return false;
                }
            }

            if (f1Iterator.hasNext() || !f2List.isEmpty()){
                return false;
            }
            else{
                return true;
            }
        }
    }
}
