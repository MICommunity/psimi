package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * polymer comparator.
 * It will first use UnambiguousInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will look at sequence/organism.
 * *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class PolymerComparator implements Comparator<Polymer> {

    private Comparator<Interactor> interactorComparator;
    private OrganismTaxIdComparator organismComparator;

    /**
     * Creates a new UnambiguousPolymerComparator. It will uses a UnambiguousInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public PolymerComparator(Comparator<Interactor> interactorBaseComparator, OrganismTaxIdComparator organismComparator){
        if (organismComparator == null){
            throw new IllegalArgumentException("The organism comparator cannot be null");
        }
        if (interactorBaseComparator == null){
            throw new IllegalArgumentException("The interactor base comparator cannot be null");
        }
        this.interactorComparator = interactorBaseComparator;
        this.organismComparator = organismComparator;
    }

    public Comparator<Interactor> getInteractorComparator() {
        return interactorComparator;
    }

    public OrganismTaxIdComparator getOrganismComparator() {
        return organismComparator;
    }

    /**
     * It will first use DefaultExactInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, it will look at sequence/organism.
     */
    public int compare(Polymer polymer1, Polymer polymer2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (polymer1 == polymer2){
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
                comp = seq1.trim().toLowerCase().compareTo(seq2.trim().toLowerCase());
                // if sequences are equal, look at the organism before saying that the polymers are equals.
                if (comp == 0){
                    comp = organismComparator.compare(polymer1.getOrganism(), polymer2.getOrganism());
                }
            }
            else if (seq1 != null) {
                return BEFORE;
            }
            else if (seq2 != null) {
                return AFTER;
            }
            return comp;
        }
    }
}
