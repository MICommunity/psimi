package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultExactParticipantComparator;

/**
 * Default exact comparator for Curated interactions.
 *
 * It will first compare the sources of the interactions using DefaultCvTermComparator. If the sources are the same, t will compare the participants using DefaultExactParticipantComparator. If the participants are the same, it will compare
 * the interaction types using DefaultCvTermComparator. If the interaction types are the same, it will compare the negative properties.
 * A negative interaction will come after a positive interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactCuratedInteractionComparator extends CuratedInteractionComparator{

    private static DefaultExactCuratedInteractionComparator defaultExactCuratedInteractionComparator;

    /**
     * Creates a new DefaultExactCuratedInteractionComparator. It will use a DefaultParticipantComparator to
     * compare participants and DefaultCvTermcomparator to compare interaction types
     */
    public DefaultExactCuratedInteractionComparator() {
        super(new DefaultExactParticipantComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) cvTermComparator;
    }

    @Override
    /**
     * It will first compare the sources of the interactions using DefaultCvTermComparator. If the sources are the same, t will compare the participants using DefaultExactParticipantComparator. If the participants are the same, it will compare
     * the interaction types using DefaultCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction
     *
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExactCuratedInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultExactCuratedInteractionComparator == null){
            defaultExactCuratedInteractionComparator = new DefaultExactCuratedInteractionComparator();
        }

        return defaultExactCuratedInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
