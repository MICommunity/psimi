package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;

/**
 * Default experimental participant comparator based on the interactor only.
 * It will compare the basic properties of an experimental participant using DefaultParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of an experimental participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultParticipantEvidenceInteractorComparator extends ParticipantEvidenceComparator {

    private static DefaultParticipantEvidenceInteractorComparator defaultExperimentalParticipantInteractorComparator;

    /**
     * Creates a new DefaultParticipantEvidenceInteractorComparator. It will use a DefaultParticipantInteractorComparator to compare
     * the basic properties of a participant.
     */
    public DefaultParticipantEvidenceInteractorComparator() {
        super(new DefaultParticipantInteractorComparator(), new DefaultCvTermComparator(), new DefaultOrganismComparator(), new DefaultParameterComparator());
    }

    @Override
    public DefaultParticipantBaseComparator getParticipantComparator() {
        return (DefaultParticipantBaseComparator) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of an experimental participant using DefaultParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of an experimental participant.
     */
    public int compare(ParticipantEvidence experimentalParticipant1, ParticipantEvidence experimentalParticipant2) {
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
     * Use DefaultParticipantEvidenceInteractorComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ParticipantEvidence experimentalParticipant1, ParticipantEvidence component2){
        if (defaultExperimentalParticipantInteractorComparator == null){
            defaultExperimentalParticipantInteractorComparator = new DefaultParticipantEvidenceInteractorComparator();
        }

        return defaultExperimentalParticipantInteractorComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
