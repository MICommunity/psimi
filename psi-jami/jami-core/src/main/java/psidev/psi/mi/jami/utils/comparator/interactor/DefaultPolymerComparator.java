package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Default polymer comparator.
 * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will only look at sequence/organism(case insensitive) if both interactors have sequence.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultPolymerComparator {
    /**
     * Use DefaultPolymerComparator to know if two proteins are equals.
     * @param polymer1
     * @param polymer2
     * @return true if the two polymers are equal
     */
    public static boolean areEquals(Polymer polymer1, Polymer polymer2){
        if (polymer1 == null && polymer2 == null){
            return true;
        }
        else if (polymer1 == null || polymer2 == null){
            return false;
        }
        else {

            // First compares the basic interactor properties
            if (!DefaultInteractorBaseComparator.areEquals(polymer1, polymer2)){
                return false;
            }

            // compares sequences
            String seq1 = polymer1.getSequence();
            String seq2 = polymer2.getSequence();

            if (seq1 != null && seq2 != null){
                if (seq1.equals(seq2)){
                    return OrganismTaxIdComparator.areEquals(polymer1.getOrganism(), polymer2.getOrganism());
                }
                else {
                    return false;
                }
            }
            return true;
        }
    }
}
