package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.feature.BiologicalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.feature.ExperimentalFeatureComparator;

import java.util.Comparator;

/**
 * Generic participant comparator.
 * Components come first and then experimental participants.
 * - It uses ComponentComparator to compare components
 * - It uses ExperimentalParticipantComparator to compare experimental participants
 * - It uses ParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class ParticipantComparator implements Comparator<Participant> {

    protected ParticipantBaseComparator participantBaseComparator;
    protected ComponentComparator componentComparator;
    protected ExperimentalParticipantComparator experimentalParticipantComparator;

    public ParticipantComparator(ParticipantBaseComparator participantBaseComparator, ExperimentalParticipantComparator experimentalParticipantComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The participantBaseComparator is required to create more specific participant comparators and compares basic participant properties. It cannot be null");
        }
        this.participantBaseComparator = participantBaseComparator;
        this.componentComparator = new ComponentComparator(this.participantBaseComparator);
        if (experimentalParticipantComparator == null){
            throw new IllegalArgumentException("The experimentalParticipantComparator is required to compare experimental participant properties. It cannot be null");
        }
        this.experimentalParticipantComparator = experimentalParticipantComparator;
    }

    /**
     *  * Components come first and then experimental participants.
     * - It uses ComponentComparator to compare components
     * - It uses ExperimentalParticipantComparator to compare experimental participants
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

            // both are components
            boolean isComponent1 = participant1 instanceof Component;
            boolean isComponent2 = participant2 instanceof Component;
            if (isComponent1 && isComponent2){
                return componentComparator.compare((Component) participant1, (Component) participant2);
            }
            // the component is before
            else if (isComponent1){
                return BEFORE;
            }
            else if (isComponent2){
                return AFTER;
            }
            else {
                // both are experimental participants
                boolean isExperimentalParticipant1 = participant1 instanceof ExperimentalParticipant;
                boolean isExperimentalParticipant2 = participant2 instanceof ExperimentalParticipant;
                if (isExperimentalParticipant1 && isExperimentalParticipant2){
                    return experimentalParticipantComparator.compare((ExperimentalParticipant) participant1, (ExperimentalParticipant) participant2);
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

    public ComponentComparator getComponentComparator() {
        return componentComparator;
    }

    public ExperimentalParticipantComparator getExperimentalParticipantComparator() {
        return experimentalParticipantComparator;
    }
}
