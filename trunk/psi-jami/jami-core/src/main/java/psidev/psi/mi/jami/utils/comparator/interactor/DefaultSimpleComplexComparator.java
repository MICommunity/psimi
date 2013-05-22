package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantInteractorComparator;

/**
 * Default Complex comparator which will only compare interactors in the components
 *
 * It will first compare the collection of components using DefaultComponentInteractorComparator.
 * If the components are the same, it will look at the default properties of an interactor
 * using DefaultInteractorBaseComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultSimpleComplexComparator extends ComplexComparator{

    private static DefaultSimpleComplexComparator defaultSimpleComplexComparator;

    /**
     * Creates a new DefaultSimpleComplexComparator. It will use a DefaultInteractorBaseComparator, DefaultModelledParticipantInteractorComparator to
     * compares components
     */
    public DefaultSimpleComplexComparator() {
        super(new DefaultInteractorBaseComparator(), new DefaultModelledParticipantInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of components using DefaultModelledParticipantInteractorComparator.
     * If the components are the same, it will look at the default properties of an interactor
     * using DefaultInteractorBaseComparator.
     */
    public int compare(Complex complex1, Complex complex2) {
        return super.compare(complex1, complex2);
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorComparator() {
        return (DefaultInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultSimpleComplexComparator to know if two complexes are equals.
     * @param complex1
     * @param complex2
     * @return true if the two complexes are equal
     */
    public static boolean areEquals(Complex complex1, Complex complex2){
        if (defaultSimpleComplexComparator == null){
            defaultSimpleComplexComparator = new DefaultSimpleComplexComparator();
        }

        return defaultSimpleComplexComparator.compare(complex1, complex2) == 0;
    }
}
