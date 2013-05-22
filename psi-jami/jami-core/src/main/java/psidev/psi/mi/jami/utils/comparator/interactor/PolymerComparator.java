package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Basic polymer comparator.
 * It will first use InteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will look at sequence/organism.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class PolymerComparator implements Comparator<Polymer> {

    protected InteractorBaseComparator interactorComparator;
    protected OrganismTaxIdComparator organismComparator;

    /**
     * Creates a new PolymerComparator. It needs a InteractorBaseComparator to compares interactor properties and it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public PolymerComparator(InteractorBaseComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare polymers. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        this.organismComparator = new OrganismTaxIdComparator();

    }

    /**
     * Creates a new PolymerComparator. It needs a InteractorBaseComparator to compares interactor properties and a OrganismComparator
     * to compare the sequence and organism. If the organism comparator is null,it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     * @param organismComparator : comparator for organism
     */
    public PolymerComparator(InteractorBaseComparator interactorComparator, OrganismTaxIdComparator organismComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare polymers. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        if (organismComparator == null){
            this.organismComparator = new OrganismTaxIdComparator();
        }
        else {
            this.organismComparator = organismComparator;
        }
    }

    /**
     * It will first use InteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, it will look at sequence/organism.
     *
     * @param polymer1
     * @param polymer2
     * @return
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
                comp = seq1.compareTo(seq2);
                // if sequences are equal, look at the organism before saying that the proteins are equals.
                if (comp == 0){
                    comp = organismComparator.compare(polymer1.getOrganism(), polymer2.getOrganism());
                }
            }
            return comp;
        }
    }

    public InteractorBaseComparator getInteractorComparator() {
        return interactorComparator;
    }
}
