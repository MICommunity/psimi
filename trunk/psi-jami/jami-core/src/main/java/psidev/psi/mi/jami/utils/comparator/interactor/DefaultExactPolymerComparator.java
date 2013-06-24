package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Default exact polymer comparator.
 * It will first use DefaultExactInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will look at sequence/organism.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultExactPolymerComparator {

    /**
     * Use DefaultExactPolymerComparator to know if two polymers are equals.
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
            if (!DefaultExactInteractorBaseComparator.areEquals(polymer1, polymer2)){
                return false;
            }

            // compares sequences
            String seq1 = polymer1.getSequence();
            String seq2 = polymer2.getSequence();

            if (seq1 != null && seq2 != null){
                if (seq1.toLowerCase().trim().equals(seq2.toLowerCase().trim())){
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
