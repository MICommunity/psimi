package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.Participant;

import java.util.Comparator;

/**
 * Generic participant comparator.
 * Modelled participants come first and then experimental participants.
 * - It uses ParticipantEvidenceComparator to compare experimental participants
 * - It uses ModelledParticipantComparator to compare biological participants
 * - It uses ParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class ParticipantComparator implements Comparator<Participant> {

    protected ParticipantBaseComparator participantBaseComparator;
    protected ParticipantEvidenceComparator experimentalParticipantComparator;
    protected ModelledParticipantComparator biologicalParticipantComparator;

    public ParticipantComparator(ParticipantBaseComparator participantBaseComparator, ParticipantEvidenceComparator experimentalParticipantComparator, ModelledParticipantComparator modelledParticipantComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The participantBaseComparator is required to create more specific participant comparators and compares basic participant properties. It cannot be null");
        }
        this.participantBaseComparator = participantBaseComparator;
        if (experimentalParticipantComparator == null){
            throw new IllegalArgumentException("The experimentalParticipantComparator is required to compare experimental participant properties. It cannot be null");
        }
        this.experimentalParticipantComparator = experimentalParticipantComparator;
        if (modelledParticipantComparator == null){
            throw new IllegalArgumentException("The modelledParticipantComparator is required to compare modelled participant properties. It cannot be null");
        }
        this.biologicalParticipantComparator = modelledParticipantComparator;
    }

    /**
     * Modelled participants come first and then experimental participants.
     * - It uses ParticipantEvidenceComparator to compare experimental participants
     * - It uses ModelledParticipantComparator to compare biological participants
     * - It uses ParticipantBaseComparator to compare basic participant properties
     * @param participant1
     * @param participant2
     * @return
     */
    public int compare(Participant participant1, Participant participant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (participant1 == null && participant2 == null){
            return EQUAL;
        }
        else if (participant1 == null){
            return AFTER;
        }
        else if (participant2 == null){
            return BEFORE;
        }
        else {
            // first check if both participants are from the same interface

            // both are biological participants
            boolean isBiologicalParticipant1 = participant1 instanceof ModelledParticipant;
            boolean isBiologicalParticipant2 = participant2 instanceof ModelledParticipant;
            if (isBiologicalParticipant1 && isBiologicalParticipant2){
                return biologicalParticipantComparator.compare((ModelledParticipant) participant1, (ModelledParticipant) participant2);
            }
            // the biological participant is before
            else if (isBiologicalParticipant1){
                return BEFORE;
            }
            else if (isBiologicalParticipant2){
                return AFTER;
            }
            else {
                // both are experimental participants
                boolean isExperimentalParticipant1 = participant1 instanceof ParticipantEvidence;
                boolean isExperimentalParticipant2 = participant2 instanceof ParticipantEvidence;
                if (isExperimentalParticipant1 && isExperimentalParticipant2){
                    return experimentalParticipantComparator.compare((ParticipantEvidence) participant1, (ParticipantEvidence) participant2);
                }
                // the experimental participant is before
                else if (isExperimentalParticipant1){
                    return BEFORE;
                }
                else if (isExperimentalParticipant2){
                    return AFTER;
                }
                else {
                    return participantBaseComparator.compare(participant1, participant2);
                }
            }
        }
    }

    public ParticipantBaseComparator getParticipantBaseComparator() {
        return participantBaseComparator;
    }

    public ParticipantEvidenceComparator getExperimentalParticipantComparator() {
        return experimentalParticipantComparator;
    }

    public ModelledParticipantComparator getBiologicalParticipantComparator() {
        return biologicalParticipantComparator;
    }
}
