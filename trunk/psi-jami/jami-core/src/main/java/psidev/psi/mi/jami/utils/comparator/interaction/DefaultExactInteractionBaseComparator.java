package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default exact Interaction comparator.
 *
 * It will first compare the participants using DefaultExactParticipantBaseComparator. If the participants are the same, it will compare
 * the interaction types using DefaultCvTermComparator. If the interaction types are the same, it will compare the negative properties.
 * A negative interaction will come after a positive interaction.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactInteractionBaseComparator extends InteractionBaseComparator {

    private static DefaultExactInteractionBaseComparator defaultExactInteractionComparator;

    /**
     * Creates a new DefaultExactInteractionBaseComparator. It will use a DefaultExactParticipantBaseComparator to
     * compare participants and DefaultCvTermcomparator to compare interaction types
     */
    public DefaultExactInteractionBaseComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) cvTermComparator;
    }

    @Override
    /**
     * It will first compare the participants using DefaultExactParticipantBaseComparator. If the participants are the same, it will compare
     * the interaction types using DefaultCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction.
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExactInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultExactInteractionComparator == null){
            defaultExactInteractionComparator = new DefaultExactInteractionBaseComparator();
        }

        return defaultExactInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
