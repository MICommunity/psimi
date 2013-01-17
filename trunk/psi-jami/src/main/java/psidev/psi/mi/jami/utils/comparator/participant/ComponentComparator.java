package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;

import java.util.Comparator;

/**
 * Basic component comparator.
 * It will compare the basic properties of a component using ParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a component.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ComponentComparator implements Comparator<Component> {

    protected ParticipantInteractorComparator participantComparator;

    /**
     * Creates a new ComponentComparator
     * @param participantComparator : the participant comparator required to compare basic participant properties
     */
    public ComponentComparator(ParticipantInteractorComparator participantComparator){
        if (participantComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantComparator = participantComparator;
    }

    public ParticipantInteractorComparator getParticipantComparator() {
        return participantComparator;
    }

    /**
     * It will compare the basic properties of a component using ParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a component.
     * @param component1
     * @param component2
     * @return
     */
    public int compare(Component component1, Component component2) {
        return participantComparator.compare(component1, component2);
    }
}
