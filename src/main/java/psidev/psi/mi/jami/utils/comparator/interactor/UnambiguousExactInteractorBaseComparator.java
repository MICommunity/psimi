package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

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

public class UnambiguousExactInteractorBaseComparator extends ExactInteractorBaseComparator {

    private static UnambiguousExactInteractorBaseComparator unambiguousExactInteractorComparator;

    /**
     * Creates a new UnambiguousExactInteractorBaseComparator.
     * It will use a UnambiguousInteractorBaseComparator to compare basic interactor properties, a OrganismTaxIdComparator to compare
     * organisms and a UnambiguousCvTermComparator to compare checksum types and interactor types
     */
    public UnambiguousExactInteractorBaseComparator() {
        super(new OrganismTaxIdComparator(), new UnambiguousCvTermComparator(), new UnambiguousInteractorBaseComparator());
    }

    public UnambiguousInteractorBaseComparator getInteractorBaseComparator() {
        return (UnambiguousInteractorBaseComparator)super.getInteractorBaseComparator();
    }

    public UnambiguousCvTermComparator getTypeComparator() {
        return (UnambiguousCvTermComparator)super.getTypeComparator();
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
