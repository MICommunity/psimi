package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;

/**
 * Generic default exact participant comparator.
 * Modelled participants come first and then experimental participants
 * - It uses DefaultExactParticipantPoolComparator to compare participant sets
 * - It uses DefaultExactComponentComparator to compare components
 * - It uses DefaultExactParticipantEvidenceComparator to compare experimental participants
 * - It uses DefaultExactParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultExactParticipantComparator {

    /**
     * Use DefaultExactParticipantComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){

        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            // first check if both participants are from the same interface
            // both are entity sets
            boolean isEntitySet1 = participant1 instanceof ParticipantPool;
            boolean isEntitySet2 = participant2 instanceof ParticipantPool;
            if (isEntitySet1 && isEntitySet2){
                boolean isBiologicalSet1 = participant1 instanceof ModelledParticipantPool;
                boolean isBiologicalSet2 = participant2 instanceof ModelledParticipantPool;
                if (isBiologicalSet1 && isBiologicalSet2){
                    return DefaultExactModelledParticipantPoolComparator.areEquals((ModelledParticipantPool) participant1, (ModelledParticipantPool) participant2);
                }
                // the first entity set participant is before
                else if (isBiologicalSet1 || isBiologicalSet2){
                    return false;
                }
                else {
                    boolean isExperimentalSet1 = participant1 instanceof ModelledParticipantPool;
                    boolean isExperimentalSet2 = participant2 instanceof ModelledParticipantPool;
                    if (isExperimentalSet1 && isExperimentalSet2){
                        return DefaultExactParticipantEvidencePoolComparator.areEquals((ParticipantEvidencePool) participant1, (ParticipantEvidencePool) participant2);
                    }
                    // the first entity set participant is before
                    else if (isExperimentalSet1 || isExperimentalSet2){
                        return false;
                    }
                    else {
                        return DefaultExactParticipantPoolComparator.areEquals((ParticipantPool) participant1, (ParticipantPool) participant2);
                    }
                }
            }
            // the first entity set participant is before
            else if (isEntitySet1 || isEntitySet2){
                return false;
            }
            else {
                // both are biological participants
                boolean isBiologicalParticipant1 = participant1 instanceof ModelledParticipant;
                boolean isBiologicalParticipant2 = participant2 instanceof ModelledParticipant;
                if (isBiologicalParticipant1 && isBiologicalParticipant2){
                    return DefaultExactModelledParticipantComparator.areEquals((ModelledParticipant) participant1, (ModelledParticipant) participant2, false);
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
                        return DefaultExactParticipantEvidenceComparator.areEquals((ParticipantEvidence) participant1, (ParticipantEvidence) participant2, false);
                    }
                    // the experimental participant is before
                    else if (isExperimentalParticipant1 || isExperimentalParticipant2){
                        return false;
                    }
                    else {
                        return DefaultExactParticipantBaseComparator.areEquals(participant1, participant2, false);
                    }
                }
            }
        }
    }
}
