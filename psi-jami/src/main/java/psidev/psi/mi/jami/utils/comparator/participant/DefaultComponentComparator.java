package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;

/**
 * Default component comparator.
 * It will compare the basic properties of a component using DefaultParticipantComparator.
 *
 * This comparator will ignore all the other properties of a component.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultComponentComparator extends ComponentComparator {

    private static DefaultComponentComparator defaultParticipantComparator;

    /**
     * Creates a new DefaultComponentComparator. It will use a DefaultParticipantComparator to compare
     * the basic properties of a participant.
     */
    public DefaultComponentComparator() {
        super(new DefaultParticipantComparator());
    }

    @Override
    public DefaultParticipantComparator getParticipantComparator() {
        return (DefaultParticipantComparator) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using DefaultParticipantComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(Component component1, Component component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultComponentComparator to know if two components are equals.
     * @param component1
     * @param component2
     * @return true if the two components are equal
     */
    public static boolean areEquals(Component component1, Component component2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new DefaultComponentComparator();
        }

        return defaultParticipantComparator.compare(component1, component2) == 0;
    }
}
