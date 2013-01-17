package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;

/**
 * Unambiguous component comparator based on the interactor only.
 * It will compare the basic properties of a component using UnambiguousParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a component.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousComponentInteractorComparator extends ComponentComparator{

    private static UnambiguousComponentInteractorComparator unambiguousParticipantInteractorComparator;

    /**
     * Creates a new UnambiguousComponentInteractorComparator. It will use a UnambiguousParticipantInteractorComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousComponentInteractorComparator() {
        super(new UnambiguousParticipantInteractorComparator());
    }

    @Override
    public UnambiguousParticipantInteractorComparator getParticipantComparator() {
        return (UnambiguousParticipantInteractorComparator) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using UnambiguousParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(Component component1, Component component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousComponentInteractorComparator to know if two components are equals.
     * @param component1
     * @param component2
     * @return true if the two components are equal
     */
    public static boolean areEquals(Component component1, Component component2){
        if (unambiguousParticipantInteractorComparator == null){
            unambiguousParticipantInteractorComparator = new UnambiguousComponentInteractorComparator();
        }

        return unambiguousParticipantInteractorComparator.compare(component1, component2) == 0;
    }
}
