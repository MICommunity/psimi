package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * Generic entity comparator.
 * Modelled participants come first and then experimental participants.
 * - It uses ParticipantEvidenceComparator to compare experimental participants
 * - It uses ModelledParticipantComparator to compare biological participants
 * - It uses ParticipantBaseComparator to compare basic participant properties
 * - It uses ParticipantPoolComparator to compare basic participant pool properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class EntityComparator implements Comparator<Entity> {

    protected EntityBaseComparator entityBaseComparator;
    protected ExperimentalEntityComparator experimentalEntityComparator;
    protected ModelledEntityComparator biologicalEntityComparator;
    protected ParticipantComparator participantComparator;

    public EntityComparator(EntityBaseComparator participantBaseComparator,
                            ExperimentalEntityComparator experimentalParticipantComparator,
                            ModelledEntityComparator modelledParticipantComparator,
                            ParticipantComparator poolComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The entityBaseComparator is required to create more specific participant comparators and compares basic participant properties. It cannot be null");
        }
        this.entityBaseComparator = participantBaseComparator;
        if (experimentalParticipantComparator == null){
            throw new IllegalArgumentException("The experimentalEntityComparator is required to compare experimental participant properties. It cannot be null");
        }
        this.experimentalEntityComparator = experimentalParticipantComparator;
        if (modelledParticipantComparator == null){
            throw new IllegalArgumentException("The modelledParticipantComparator is required to compare modelled participant properties. It cannot be null");
        }
        this.biologicalEntityComparator = modelledParticipantComparator;
        if (poolComparator == null){
            throw new IllegalArgumentException("The ParticipantComparator is required to compare participant properties. It cannot be null");
        }
        this.participantComparator = poolComparator;
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
    public int compare(Entity participant1, Entity participant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (participant1 == participant2){
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
            // both are participants
            boolean isParticipant1 = participant1 instanceof Participant;
            boolean isParticipant2 = participant2 instanceof Participant;
            if (isParticipant1 && isParticipant2){
                return participantComparator.compare((Participant) participant1, (Participant) participant2);
            }
            // the participant is before
            else if (isParticipant1){
                return BEFORE;
            }
            else if (isParticipant2){
                return AFTER;
            }
            else {
                // both are biological participants
                boolean isBiologicalParticipant1 = participant1 instanceof ModelledEntity;
                boolean isBiologicalParticipant2 = participant2 instanceof ModelledEntity;
                if (isBiologicalParticipant1 && isBiologicalParticipant2){
                    return biologicalEntityComparator.compare((ModelledEntity) participant1, (ModelledEntity) participant2);
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
                    boolean isExperimentalParticipant1 = participant1 instanceof ExperimentalEntity;
                    boolean isExperimentalParticipant2 = participant2 instanceof ExperimentalEntity;
                    if (isExperimentalParticipant1 && isExperimentalParticipant2){
                        return experimentalEntityComparator.compare((ExperimentalEntity) participant1, (ExperimentalEntity) participant2);
                    }
                    // the experimental participant is before
                    else if (isExperimentalParticipant1){
                        return BEFORE;
                    }
                    else if (isExperimentalParticipant2){
                        return AFTER;
                    }
                    else {
                        return entityBaseComparator.compare(participant1, participant2);
                    }
                }
            }
        }
    }

    public EntityBaseComparator getEntityBaseComparator() {
        return entityBaseComparator;
    }

    public ExperimentalEntityComparator getExperimentalEntityComparator() {
        return experimentalEntityComparator;
    }

    public ModelledEntityComparator getBiologicalEntityComparator() {
        return biologicalEntityComparator;
    }

    public ParticipantComparator getParticipantComparator() {
        return participantComparator;
    }
}
