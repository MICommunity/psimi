package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;

/**
 * Default component comparator based on the interactor only.
 * It will compare the basic properties of a component using DefaultParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a component.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultComponentInteractorComparator extends ComponentComparator{
    private static DefaultComponentInteractorComparator defaultParticipantInteractorComparator;

    /**
     * Creates a new DefaultComponentInteractorComparator. It will use a DefaultParticipantInteractorComparator to compare
     * the basic properties of a participant.
     */
    public DefaultComponentInteractorComparator() {
        super(new DefaultParticipantInteractorComparator());
    }

    @Override
    public DefaultParticipantInteractorComparator getParticipantComparator() {
        return (DefaultParticipantInteractorComparator) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using DefaultParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(Component component1, Component component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultComponentInteractorComparator to know if two components are equals.
     * @param component1
     * @param component2
     * @return true if the two components are equal
     */
    public static boolean areEquals(Component component1, Component component2){
        if (defaultParticipantInteractorComparator == null){
            defaultParticipantInteractorComparator = new DefaultComponentInteractorComparator();
        }

        return defaultParticipantInteractorComparator.compare(component1, component2) == 0;
    }
}
