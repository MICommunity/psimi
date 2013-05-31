package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactComplexComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

/**
 * Unambiguous exact biological participant comparator based on the interactor only.
 * It will compare the basic properties of an interactor using UnambiguousExactInteractorComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactModelledParticipantInteractorComparator extends ParticipantInteractorComparator<ModelledParticipant> implements CustomizableModelledParticipantComparator{

    private static UnambiguousExactModelledParticipantInteractorComparator unambiguousExactParticipantInteractorComparator;

    private boolean checkComplexesAsInteractor = true;
    /**
     * Creates a new UnambiguousExactModelledParticipantInteractorComparator. It will use a UnambiguousExactInteractorComparator to compare
     * the basic properties of an interactor.
     */
    public UnambiguousExactModelledParticipantInteractorComparator() {
        super(null);
        setInteractorComparator(new UnambiguousExactInteractorComparator(new UnambiguousExactComplexComparator(this)));
    }

    @Override
    public UnambiguousExactInteractorComparator getInteractorComparator() {
        return (UnambiguousExactInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the basic properties of an interactor using UnambiguousInteractorComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(ModelledParticipant component1, ModelledParticipant component2) {
        return checkComplexesAsInteractor ? super.compare(component1, component2) : 0;
    }

    /**
     * Use UnambiguousExactModelledParticipantInteractorComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledParticipant component1, ModelledParticipant component2){
        if (unambiguousExactParticipantInteractorComparator == null){
            unambiguousExactParticipantInteractorComparator = new UnambiguousExactModelledParticipantInteractorComparator();
        }

        return unambiguousExactParticipantInteractorComparator.compare(component1, component2) == 0;
    }

    public boolean isCheckComplexesAsInteractors() {
        return checkComplexesAsInteractor;
    }

    public void setCheckComplexesAsInteractors(boolean checkComplexesAsInteractors) {
        this.checkComplexesAsInteractor = checkComplexesAsInteractors;
    }
}
