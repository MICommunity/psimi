package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousComponentComparator;

/**
 * Unambiguous Complex comparator
 *
 * It will first look at the default properties of an interactor using UnambiguousInteractorBaseComparator.
 * If the basic interactor properties are the same, It will first compare the collection of components using ComponentComparator.
 * If the collection of components is the same, it will look at the parameters using ParameterComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousComplexComparator extends ComplexComparator{

    private static UnambiguousComplexComparator unambiguousComplexComparator;

    /**
     * Creates a new UnambiguousComplexComparator. It will use a UnambiguousInteractorBaseComparator, UnambiguousComponentComparator to
     * compares components and a UnambiguousParameterComparator to compare parameters..
     */
    public UnambiguousComplexComparator() {
        super(new UnambiguousInteractorBaseComparator(), new UnambiguousComponentComparator(), new UnambiguousParameterComparator());
    }

    @Override
    /**
     * It will first look at the default properties of an interactor using UnambiguousInteractorBaseComparator.
     * If the basic interactor properties are the same, It will first compare the collection of components using ComponentComparator.
     * If the collection of components is the same, it will look at the parameters using ParameterComparator.
     */
    public int compare(Complex complex1, Complex complex2) {
        return super.compare(complex1, complex2);
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousComplexComparator to know if two complexes are equals.
     * @param complex1
     * @param complex2
     * @return true if the two complexes are equal
     */
    public static boolean areEquals(Complex complex1, Complex complex2){
        if (unambiguousComplexComparator == null){
            unambiguousComplexComparator = new UnambiguousComplexComparator();
        }

        return unambiguousComplexComparator.compare(complex1, complex2) == 0;
    }
}
