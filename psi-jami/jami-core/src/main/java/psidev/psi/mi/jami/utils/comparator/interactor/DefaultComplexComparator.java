package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantInteractorComparator;

/**
 * Default Complex comparator
 *
 * It will first look at the default properties of an interactor using DefaultInteractorBaseComparator.
 * It will then compare interaction types using DefaultCvTermComparator.
 * If the basic interactor properties are the same, It will first compare the collection of components using DefaultModelledParticipantComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultComplexComparator extends ComplexComparator{

    private static DefaultComplexComparator defaultComplexComparator;

    /**
     * Creates a new DefaultComplexComparator. It will use a DefaultInteractorBaseComparator, DefaultComponentComparator to
     * compares components..
     */
    public DefaultComplexComparator() {
        super(new DefaultInteractorBaseComparator(), new DefaultModelledParticipantComparator(), new DefaultCvTermComparator());
    }

    public DefaultComplexComparator(DefaultModelledParticipantComparator modelledParticipantComparator) {
        super(new DefaultInteractorBaseComparator(), modelledParticipantComparator != null ? modelledParticipantComparator : new DefaultModelledParticipantComparator(), new DefaultCvTermComparator());
    }

    public DefaultComplexComparator(DefaultModelledParticipantInteractorComparator modelledParticipantComparator) {
        super(new DefaultInteractorBaseComparator(), modelledParticipantComparator != null ? modelledParticipantComparator : new DefaultModelledParticipantInteractorComparator(), new DefaultCvTermComparator());
    }

    @Override
    /**
     * It will first look at the default properties of an interactor using DefaultInteractorBaseComparator.
     * It will then compare interaction types using DefaultCvTermComparator.
     * If the basic interactor properties are the same, It will first compare the collection of components using DefaultModelledParticipantComparator.
     *
     */
    public int compare(Complex complex1, Complex complex2) {
        return super.compare(complex1, complex2);
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorBaseComparator() {
        return (DefaultInteractorBaseComparator) this.interactorBaseComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) super.getCvTermComparator();
    }

    /**
     * Use DefaultComplexComparator to know if two complexes are equals.
     * @param complex1
     * @param complex2
     * @return true if the two complexes are equal
     */
    public static boolean areEquals(Complex complex1, Complex complex2){
        if (defaultComplexComparator == null){
            defaultComplexComparator = new DefaultComplexComparator();
        }

        return defaultComplexComparator.compare(complex1, complex2) == 0;
    }
}
