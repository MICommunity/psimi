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

public class DefaultExactPolymerComparator extends AbstractPolymerComparator {

    private static DefaultExactPolymerComparator defaultExactPolymerComparator;

    /**
     * Creates a new DefaultExactPolymerComparator. It will uses a DefaultExactInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultExactPolymerComparator(){
        super(new DefaultExactInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will first use DefaultExactInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, it will look at sequence/organism.
     */
    public int compare(Polymer polymer1, Polymer polymer2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (polymer1 == null && polymer2 == null){
            return EQUAL;
        }
        else if (polymer1 == null){
            return AFTER;
        }
        else if (polymer2 == null){
            return BEFORE;
        }
        else {

            // First compares the basic interactor properties
            int comp = interactorComparator.compare(polymer1, polymer2);
            if (comp != 0){
                return comp;
            }

            // compares sequences
            String seq1 = polymer1.getSequence();
            String seq2 = polymer2.getSequence();

            if (seq1 != null && seq2 != null){
                comp = seq1.toLowerCase().trim().compareTo(seq2.toLowerCase().trim());
            }
            return comp;
        }
    }

    @Override
    public DefaultExactInteractorBaseComparator getInteractorComparator() {
        return (DefaultExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultExactPolymerComparator to know if two polymers are equals.
     * @param polymer1
     * @param polymer2
     * @return true if the two polymers are equal
     */
    public static boolean areEquals(Polymer polymer1, Polymer polymer2){
        if (defaultExactPolymerComparator == null){
            defaultExactPolymerComparator = new DefaultExactPolymerComparator();
        }

        return defaultExactPolymerComparator.compare(polymer1, polymer2) == 0;
    }
}
