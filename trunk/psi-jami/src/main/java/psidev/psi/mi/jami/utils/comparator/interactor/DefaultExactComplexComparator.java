package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultComponentComparator;

/**
 * Default exact Complex comparator
 *
 * It will first look at the default properties of an interactor using DefaultExactInteractorBaseComparator.
 * If the basic interactor properties are the same, It will first compare the collection of components using ComponentComparator.
 * If the collection of components is the same, it will look at the parameters using ParameterComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactComplexComparator extends ComplexComparator{

    private static DefaultExactComplexComparator defaultExactComplexComparator;

    /**
     * Creates a new DefaultExactComplexComparator. It will use a DefaultExactInteractorBaseComparator, DefaultComponentComparator to
     * compares components and a DefaultParameterComparator to compare parameters..
     */
    public DefaultExactComplexComparator() {
        super(new DefaultExactInteractorBaseComparator(), new DefaultComponentComparator(), new DefaultParameterComparator());
    }

    @Override
    /**
     *
     * It will first look at the default properties of an interactor using DefaultExactInteractorBaseComparator.
     * If the basic interactor properties are the same, It will first compare the collection of components using ComponentComparator.
     * If the collection of components is the same, it will look at the parameters using ParameterComparator.
     *
     */
    public int compare(Complex complex1, Complex complex2) {
        return super.compare(complex1, complex2);
    }

    @Override
    public DefaultExactInteractorBaseComparator getInteractorComparator() {
        return (DefaultExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultExactComplexComparator to know if two complexes are equals.
     * @param complex1
     * @param complex2
     * @return true if the two complexes are equal
     */
    public static boolean areEquals(Complex complex1, Complex complex2){
        if (defaultExactComplexComparator == null){
            defaultExactComplexComparator = new DefaultExactComplexComparator();
        }

        return defaultExactComplexComparator.compare(complex1, complex2) == 0;
    }
}
