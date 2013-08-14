package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Generic default participant comparator.
 * Modelled participants come first and then experimental participants.
 * - It uses DefaultComponentComparator to compare components
 * - It uses DefaultParticipantEvidenceComparator to compare experimental participants
 * - It uses DefaultParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultParticipantComparator {

    /**
     * Use DefaultParticipantComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){

        if (participant1 == null && participant2 == null){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            // first check if both participants are from the same interface

            // both are biological participants
            boolean isBiologicalParticipant1 = participant1 instanceof ModelledParticipant;
            boolean isBiologicalParticipant2 = participant2 instanceof ModelledParticipant;
            if (isBiologicalParticipant1 && isBiologicalParticipant2){
                return DefaultModelledParticipantComparator.areEquals((ModelledParticipant) participant1, (ModelledParticipant) participant2, true);
            }
            // the biological participant is before
            else if (isBiologicalParticipant1 || isBiologicalParticipant2){
                return false;
            }
            else {
                // both are experimental participants
                boolean isExperimentalParticipant1 = participant1 instanceof ParticipantEvidence;
                boolean isExperimentalParticipant2 = participant2 instanceof ParticipantEvidence;
                if (isExperimentalParticipant1 && isExperimentalParticipant2){
                    return DefaultParticipantEvidenceComparator.areEquals((ParticipantEvidence) participant1, (ParticipantEvidence) participant2);
                }
                // the experimental participant is before
                else if (isExperimentalParticipant1 || isExperimentalParticipant2){
                    return false;
                }
                else {
                    return DefaultParticipantBaseComparator.areEquals(participant1, participant2, false);
                }
            }
        }
    }
}
