package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;

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
            // both are entity sets
            boolean isEntitySet1 = participant1 instanceof EntityPool;
            boolean isEntitySet2 = participant2 instanceof EntityPool;
            if (isEntitySet1 && isEntitySet2){
                boolean isBiologicalSet1 = participant1 instanceof ModelledEntityPool;
                boolean isBiologicalSet2 = participant2 instanceof ModelledEntityPool;
                if (isBiologicalSet1 && isBiologicalSet2){
                    return DefaultExactModelledEntityPoolComparator.areEquals((ModelledEntityPool) participant1, (ModelledEntityPool) participant2);
                }
                // the first entity set participant is before
                else if (isBiologicalSet1 || isBiologicalSet2){
                    return false;
                }
                else {
                    boolean isExperimentalSet1 = participant1 instanceof ModelledEntityPool;
                    boolean isExperimentalSet2 = participant2 instanceof ModelledEntityPool;
                    if (isExperimentalSet1 && isExperimentalSet2){
                        return DefaultExactExperimentalEntityPoolComparator.areEquals((ExperimentalEntityPool) participant1, (ExperimentalEntityPool) participant2);
                    }
                    // the first entity set participant is before
                    else if (isExperimentalSet1 || isExperimentalSet2){
                        return false;
                    }
                    else {
                        return DefaultExactEntityPoolComparator.areEquals((EntityPool) participant1, (EntityPool) participant2);
                    }
                }
            }
            // the first entity set participant is before
            else if (isEntitySet1 || isEntitySet2){
                return false;
            }
            else {
                // both are biological participants
                boolean isBiologicalParticipant1 = participant1 instanceof ModelledEntity;
                boolean isBiologicalParticipant2 = participant2 instanceof ModelledEntity;
                if (isBiologicalParticipant1 && isBiologicalParticipant2){
                    return DefaultExactModelledParticipantComparator.areEquals((ModelledEntity) participant1, (ModelledParticipant) participant2, false);
                }
                // the biological participant is before
                else if (isBiologicalParticipant1 || isBiologicalParticipant2){
                    return false;
                }
                else {
                    // both are experimental participants
                    boolean isExperimentalParticipant1 = participant1 instanceof ExperimentalEntity;
                    boolean isExperimentalParticipant2 = participant2 instanceof ExperimentalEntity;
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
