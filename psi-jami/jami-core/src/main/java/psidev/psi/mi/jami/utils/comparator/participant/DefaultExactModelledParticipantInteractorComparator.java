package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * Default biological participant comparator based on the interactor only.
 * It will compare the interactor using DefaultExactInteractorComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultExactModelledParticipantInteractorComparator extends ParticipantInteractorComparator<ModelledParticipant> {

    private static DefaultExactModelledParticipantInteractorComparator defaultExactBiologicalParticipantInteractorComparator;

    /**
     * Creates a new DefaultExactModelledParticipantInteractorComparator. It will use a DefaultExactInteractorComparator to compare
     * the basic properties of a interactor in the participant.
     */
    public DefaultExactModelledParticipantInteractorComparator() {
        super(new DefaultExactInteractorComparator());
    }

    @Override
    public DefaultExactInteractorComparator getInteractorComparator() {
        return (DefaultExactInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the basic properties of interactor using DefaultInteractorComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(ModelledParticipant component1, ModelledParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultExactModelledParticipantInteractorComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledParticipant component1, ModelledParticipant component2){
        if (defaultExactBiologicalParticipantInteractorComparator == null){
            defaultExactBiologicalParticipantInteractorComparator = new DefaultExactModelledParticipantInteractorComparator();
        }

        return defaultExactBiologicalParticipantInteractorComparator.compare(component1, component2) == 0;
    }
}
