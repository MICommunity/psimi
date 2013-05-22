package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactModelledParticipantComparator;

/**
 * Unambiguous exact Complex comparator
 *
 * It will first look at the default properties of an interactor using UnambiguousExactInteractorBaseComparator.
 * It will then compare interaction types using UnambiguousExactCvTermComparator.
 * If the basic interactor properties are the same, It will first compare the collection of components using UnambiguousExactModelledParticipantComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactComplexComparator extends ComplexComparator {

    private static UnambiguousExactComplexComparator unambiguousExactComplexComparator;

    /**
     * Creates a new UnambiguousExactComplexComparator. It will use a UnambiguousExactInteractorBaseComparator, UnambiguousExactModelledParticipantComparator to
     * compares components.
     */
    public UnambiguousExactComplexComparator() {
        super(new UnambiguousExactInteractorBaseComparator(), new UnambiguousExactModelledParticipantComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    /**
     *
     * It will first look at the default properties of an interactor using UnambiguousExactInteractorBaseComparator.
     * It will then compare interaction types using UnambiguousExactCvTermComparator.
     * If the basic interactor properties are the same, It will first compare the collection of components using UnambiguousExactModelledParticipantComparator.
     */
    public int compare(Complex complex1, Complex complex2) {
        return super.compare(complex1, complex2);
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorComparator;
    }


    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
    }

    /**
     * Use UnambiguousExactComplexComparator to know if two complexes are equals.
     * @param complex1
     * @param complex2
     * @return true if the two complexes are equal
     */
    public static boolean areEquals(Complex complex1, Complex complex2){
        if (unambiguousExactComplexComparator == null){
            unambiguousExactComplexComparator = new UnambiguousExactComplexComparator();
        }

        return unambiguousExactComplexComparator.compare(complex1, complex2) == 0;
    }
}
