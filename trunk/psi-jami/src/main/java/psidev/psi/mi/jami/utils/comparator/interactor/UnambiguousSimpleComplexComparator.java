package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousComponentInteractorComparator;

/**
 * Unambiguous Complex comparator which will only compare interactors in the components
 *
 * It will first compare the collection of components using UnambiguousComponentInteractorComparator.
 * If the collection of components is the same, it will look at the parameters using UnambiguousParameterComparator.
 * If the parameters are the same and the collection of components was empty in both complexes, it will look at the default properties of an interactor
 * using UnambiguousInteractorBaseComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousSimpleComplexComparator extends ComplexComparator {

    private static UnambiguousSimpleComplexComparator unambiguousSimpleComplexComparator;

    /**
     * Creates a new UnambiguousSimpleComplexComparator. It will use a UnambiguousInteractorBaseComparator, UnambiguousComponentInteractorComparator to
     * compares components and aUnambiguousParameterComparator to compare parameters..
     */
    public UnambiguousSimpleComplexComparator() {
        super(new UnambiguousInteractorBaseComparator(), new UnambiguousComponentInteractorComparator(), new UnambiguousParameterComparator());
    }

    @Override
    /**
     * It will first compare the collection of components using UnambiguousComponentInteractorComparator.
     * If the collection of components is the same, it will look at the parameters using UnambiguousParameterComparator.
     * If the parameters are the same and the collection of components was empty in both complexes, it will look at the default properties of an interactor
     * using UnambiguousInteractorBaseComparator.
     */
    public int compare(Complex complex1, Complex complex2) {
        return super.compare(complex1, complex2);
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousSimpleComplexComparator to know if two complexes are equals.
     * @param complex1
     * @param complex2
     * @return true if the two complexes are equal
     */
    public static boolean areEquals(Complex complex1, Complex complex2){
        if (unambiguousSimpleComplexComparator == null){
            unambiguousSimpleComplexComparator = new UnambiguousSimpleComplexComparator();
        }

        return unambiguousSimpleComplexComparator.compare(complex1, complex2) == 0;
    }
}
