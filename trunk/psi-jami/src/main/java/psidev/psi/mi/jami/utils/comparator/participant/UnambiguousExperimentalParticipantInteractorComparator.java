package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.UnambiguousOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;

/**
 * Unambiguous experimental participant comparator based on the interactor only.
 * It will compare the basic properties of an experimental participant using UnambiguousParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of an experimental participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExperimentalParticipantInteractorComparator extends ExperimentalParticipantComparator{
    private static UnambiguousExperimentalParticipantInteractorComparator unambiguousExperimentalParticipantInteractorComparator;

    /**
     * Creates a new UnambiguousExperimentalParticipantInteractorComparator. It will use a UnambiguousParticipantInteractorComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousExperimentalParticipantInteractorComparator() {
        super(new UnambiguousParticipantComparator(), new UnambiguousCvTermComparator(), new UnambiguousOrganismComparator(), new UnambiguousParameterComparator());
    }

    @Override
    public UnambiguousParticipantComparator getParticipantComparator() {
        return (UnambiguousParticipantComparator) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of an experimental participant using UnambiguousParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of an experimental participant.
     */
    public int compare(ExperimentalParticipant experimentalParticipant1, ExperimentalParticipant experimentalParticipant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experimentalParticipant1 == null && experimentalParticipant1 == null){
            return EQUAL;
        }
        else if (experimentalParticipant1 == null){
            return AFTER;
        }
        else if (experimentalParticipant2 == null){
            return BEFORE;
        }
        else {

            // compares interactor participant properties
            return participantComparator.compare(experimentalParticipant1, experimentalParticipant2);
        }
    }

    /**
     * Use UnambiguousExperimentalParticipantInteractorComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalParticipant experimentalParticipant1, ExperimentalParticipant component2){
        if (unambiguousExperimentalParticipantInteractorComparator == null){
            unambiguousExperimentalParticipantInteractorComparator = new UnambiguousExperimentalParticipantInteractorComparator();
        }

        return unambiguousExperimentalParticipantInteractorComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
