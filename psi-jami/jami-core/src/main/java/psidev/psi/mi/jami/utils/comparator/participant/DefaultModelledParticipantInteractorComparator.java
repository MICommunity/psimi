package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultComplexComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default biological participant comparator based on the interactor only.
 * It will compare the basic properties of a biological participant using DefaultParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultModelledParticipantInteractorComparator extends ParticipantInteractorComparator<ModelledParticipant> {

    private static DefaultModelledParticipantInteractorComparator defaultBiologicalParticipantInteractorComparator;

    /**
     * Creates a new DefaultComponentInteractorComparator. It will use a DefaultParticipantInteractorComparator to compare
     * the basic properties of a participant.
     */
    public DefaultModelledParticipantInteractorComparator() {
        super(null);
        setInteractorComparator(new DefaultInteractorComparator(new DefaultComplexComparator(this)));
    }

    @Override
    public DefaultInteractorComparator getInteractorComparator() {
        return (DefaultInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using DefaultParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(ModelledParticipant component1, ModelledParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultModelledParticipantInteractorComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledParticipant component1, ModelledParticipant component2){
        if (defaultBiologicalParticipantInteractorComparator == null){
            defaultBiologicalParticipantInteractorComparator = new DefaultModelledParticipantInteractorComparator();
        }

        return defaultBiologicalParticipantInteractorComparator.compare(component1, component2) == 0;
    }
}
