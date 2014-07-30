package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;

/**
 * Generic default exact entity comparator.
 * Modelled participants come first and then experimental participants
 * - It uses DefaultExactModelledParticipantComparator to compare components
 * - It uses DefaultExactParticipantEvidenceComparator to compare experimental participants
 * - It uses DefaultExactParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultExactEntityComparator {

    /**
     * Use DefaultExactEntityComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2){

        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            // first check if both participants are from the same interface
            // both are participants
            boolean isParticipant1 = participant1 instanceof Participant;
            boolean isParticipant2 = participant2 instanceof Participant;
            if (isParticipant1 && isParticipant2){
                return DefaultExactParticipantComparator.areEquals((Participant) participant1,
                        (Participant) participant2);
            }
            // the participant is before
            else if (isParticipant1 || isParticipant2){
                return false;
            }
            else {
                // both are biological participants
                boolean isBiologicalParticipant1 = participant1 instanceof ModelledEntity;
                boolean isBiologicalParticipant2 = participant2 instanceof ModelledEntity;
                if (isBiologicalParticipant1 && isBiologicalParticipant2){
                    return DefaultExactModelledEntityComparator.areEquals((ModelledEntity) participant1,
                            (ModelledEntity) participant2, false);
                }
                // the biological participant is before
                else if (isBiologicalParticipant1 || isBiologicalParticipant2){
                    return false;
                }
                else {
                    // both are experimental entities
                    boolean isExperimentalParticipant1 = participant1 instanceof ExperimentalEntity;
                    boolean isExperimentalParticipant2 = participant2 instanceof ExperimentalEntity;
                    if (isExperimentalParticipant1 && isExperimentalParticipant2) {
                        return DefaultExactExperimentalEntityComparator.areEquals(
                                (ExperimentalEntity) participant1,
                                (ExperimentalEntity) participant2, false);
                    }
                    // the experimental participant is before
                    else if (isExperimentalParticipant1 || isExperimentalParticipant2) {
                        return false;
                    } else {
                        return DefaultExactEntityBaseComparator.areEquals(participant1,
                                participant2, false);
                    }
                }
            }
        }
    }
}
