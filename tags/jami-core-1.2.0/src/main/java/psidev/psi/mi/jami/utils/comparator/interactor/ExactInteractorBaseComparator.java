package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Interactor base comparator.
 * It will first compare the interactor types using UnambiguousCvTermComparator. If both types are equal,
 * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal,
 * it will use a UnambiguousInteractorBaseComparator to compare basic Interactor properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ExactInteractorBaseComparator implements Comparator<Interactor> {

    private OrganismTaxIdComparator organismComparator;
    private Comparator<CvTerm> typeComparator;
    private InteractorBaseComparator interactorBaseComparator;

    /**
     * Creates a new UnambiguousExactInteractorBaseComparator.
     * It will use a UnambiguousInteractorBaseComparator to compare basic interactor properties, a OrganismTaxIdComparator to compare
     * organisms and a UnambiguousCvTermComparator to compare checksum types and interactor types
     */
    public ExactInteractorBaseComparator(OrganismTaxIdComparator organismComparator, Comparator<CvTerm> typeComparator,
                                         InteractorBaseComparator interactorBaseComparator) {
        if (interactorBaseComparator == null){
            throw new IllegalArgumentException("The interactor base comparator cannot be null");
        }
        this.interactorBaseComparator = interactorBaseComparator;
        if (organismComparator == null){
            throw new IllegalArgumentException("The organism comparator cannot be null");
        }
        this.organismComparator = organismComparator;
        if (typeComparator == null){
            throw new IllegalArgumentException("The interactor type comparator cannot be null");
        }
        this.typeComparator = typeComparator;
    }

    public InteractorBaseComparator getInteractorBaseComparator() {
        return this.interactorBaseComparator;
    }

    public Comparator<CvTerm> getTypeComparator() {
        return this.typeComparator;
    }

    public OrganismTaxIdComparator getOrganismComparator() {
        return organismComparator;
    }

    /**
     * It will first compare the interactor types using UnambiguousCvTermComparator. If both types are equal,
     * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal,
     * it will use a UnambiguousInteractorBaseComparator to compare basic Interactor properties.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactor1 == interactor2){
            return EQUAL;
        }
        else if (interactor1 == null){
            return AFTER;
        }
        else if (interactor2 == null){
            return BEFORE;
        }
        else{
            // compares first interactor types
            CvTerm type1 = interactor1.getInteractorType();
            CvTerm type2 = interactor2.getInteractorType();

            int comp = typeComparator.compare(type1, type2);

            if (comp != 0){
                return comp;
            }

            // then compares organism
            Organism organism1 = interactor1.getOrganism();
            Organism organism2 = interactor2.getOrganism();

            comp = organismComparator.compare(organism1, organism2);

            if (comp != 0){
                return comp;
            }

            // then compares basic properties

            return interactorBaseComparator.compare(interactor1, interactor2);
        }
    }
}
