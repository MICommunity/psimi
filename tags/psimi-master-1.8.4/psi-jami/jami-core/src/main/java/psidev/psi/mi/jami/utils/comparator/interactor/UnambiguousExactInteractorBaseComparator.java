package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Unambiguous Interactor base comparator.
 * It will first compare the interactor types using UnambiguousCvTermComparator. If both types are equal,
 * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal,
 * it will use a UnambiguousInteractorBaseComparator to compare basic Interactor properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorBaseComparator implements Comparator<Interactor> {

    private static UnambiguousExactInteractorBaseComparator unambiguousExactInteractorComparator;
    private OrganismTaxIdComparator organismComparator;
    private UnambiguousCvTermComparator typeComparator;
    private UnambiguousInteractorBaseComparator interactorBaseComparator;

    /**
     * Creates a new UnambiguousExactInteractorBaseComparator.
     * It will use a UnambiguousInteractorBaseComparator to compare basic interactor properties, a OrganismTaxIdComparator to compare
     * organisms and a UnambiguousCvTermComparator to compare checksum types and interactor types
     */
    public UnambiguousExactInteractorBaseComparator() {
        this.interactorBaseComparator = new UnambiguousInteractorBaseComparator();
        this.organismComparator = new OrganismTaxIdComparator();
        this.typeComparator = new UnambiguousCvTermComparator();
    }

    public UnambiguousInteractorBaseComparator getInteractorBaseComparator() {
        return this.interactorBaseComparator;
    }

    public UnambiguousCvTermComparator getTypeComparator() {
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

        if (interactor1 == null && interactor2 == null){
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

    /**
     * Use UnambiguousExacttInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (unambiguousExactInteractorComparator == null){
            unambiguousExactInteractorComparator = new UnambiguousExactInteractorBaseComparator();
        }

        return unambiguousExactInteractorComparator.compare(interactor1, interactor2) == 0;
    }

    /**
     *
     * @param interactor
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Interactor interactor){
        if (unambiguousExactInteractorComparator == null){
            unambiguousExactInteractorComparator = new UnambiguousExactInteractorBaseComparator();
        }

        if (interactor == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(interactor.getInteractorType());
        hashcode = 31*hashcode + OrganismTaxIdComparator.hashCode(interactor.getOrganism());
        hashcode = 31*hashcode + UnambiguousInteractorBaseComparator.hashCode(interactor);

        return hashcode;
    }
}
