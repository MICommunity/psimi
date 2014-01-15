package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.EntityPool;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Generic default exact participant comparator.
 * Modelled participants come first and then experimental participants
 * - It uses DefaultExactEntityPoolComparator to compare participant sets
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
    public static boolean areEquals(Entity participant1, Entity participant2){

        if (participant1 == null && participant2 == null){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            // first check if both participants are from the same interface
            // both are experimental participants
            boolean isParticipantSet1 = participant1 instanceof EntityPool;
            boolean isParticipantSet2 = participant2 instanceof EntityPool;
            if (isParticipantSet1 && isParticipantSet2){
                return DefaultExactEntityPoolComparator.areEquals((EntityPool) participant1, (EntityPool) participant2);
            }
            else if (isParticipantSet1 || isParticipantSet2){
                return false;
            }
            else {
                // both are biological participants
                boolean isBiologicalParticipant1 = participant1 instanceof ModelledParticipant;
                boolean isBiologicalParticipant2 = participant2 instanceof ModelledParticipant;
                if (isBiologicalParticipant1 && isBiologicalParticipant2){
                    return DefaultExactModelledParticipantComparator.areEquals((ModelledParticipant) participant1, (ModelledParticipant) participant2, true);
                }
                else if (isBiologicalParticipant1 || isBiologicalParticipant2){
                    return false;
                }
                else {
                    // both are experimental participants
                    boolean isExperimentalParticipant1 = participant1 instanceof ParticipantEvidence;
                    boolean isExperimentalParticipant2 = participant2 instanceof ParticipantEvidence;
                    if (isExperimentalParticipant1 && isExperimentalParticipant2){
                        return DefaultExactParticipantEvidenceComparator.areEquals((ParticipantEvidence) participant1, (ParticipantEvidence) participant2);
                    }
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
