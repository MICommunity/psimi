package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultExactModelledParticipantComparator;

/**
 * Default exact ModelledInteraction comparator.
 *
 * It will use a DefaultInteractionBaseComparator to compare basic interaction properties.
 * Then it will compare the modelledParticipants using DefaultExactModelledParticipantComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultExactModelledInteractionComparator extends ModelledInteractionComparator{

    private static DefaultExactModelledInteractionComparator defaultExactModelledInteractionComparator;

    /**
     * Creates a new DefaultExactModelledInteractionComparator. It will use a DefaultExactInteractionBaseComparator to
     * compare basic interaction properties
     */
    public DefaultExactModelledInteractionComparator() {
        super(new DefaultExactModelledParticipantComparator(), new DefaultInteractionBaseComparator());
    }

    @Override
    /**
     * It will use a DefaultExactInteractionBaseComparator to compare basic interaction properties.
     */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * It will use a DefaultInteractionBaseComparator to compare basic interaction properties.
     * Then it will compare the modelledParticipants using DefaultExactModelledParticipantComparator.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (defaultExactModelledInteractionComparator == null){
            defaultExactModelledInteractionComparator = new DefaultExactModelledInteractionComparator();
        }

        return defaultExactModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
