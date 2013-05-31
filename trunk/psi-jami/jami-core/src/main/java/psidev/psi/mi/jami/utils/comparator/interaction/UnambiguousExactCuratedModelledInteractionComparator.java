package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactModelledParticipantComparator;

/**
 * Unambiguous exact curated ModelledInteraction comparator.
 *
 * It will use a UnambiguousCuratedInteractionBase to compare basic interaction properties.
 * Then it will compare the modelledParticipants using UnambiguousExactModelledParticipantComparator.
 * Finally, it will compare the source of the modelledInteraction using UnambiguousCvTermComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousExactCuratedModelledInteractionComparator extends CuratedModelledInteractionComparator{

    private static UnambiguousExactCuratedModelledInteractionComparator unambiguousExactCuratedModelledInteractionComparator;

    /**
     * Creates a new UnambiguousExactCuratedModelledInteractionComparator. It will use a UnambiguousExactCuratedInteractionBaseComparator to
     * compare basic interaction properties
     */
    public UnambiguousExactCuratedModelledInteractionComparator() {
        super(new UnambiguousExactModelledParticipantComparator(), new UnambiguousCuratedInteractionBaseComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousCuratedInteractionBaseComparator) this.interactionBaseComparator;
    }
    public UnambiguousCvTermComparator getSourceComparator() {
        return (UnambiguousCvTermComparator) sourceComparator;
    }


    @Override
    /**
     * It will use a UnambiguousCuratedInteractionBase to compare basic interaction properties.
     * Then it will compare the modelledParticipants using UnambiguousExactModelledParticipantComparator.
     * Finally, it will compare the source of the modelledInteraction using UnambiguousCvTermComparator
     * */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactCuratedModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (unambiguousExactCuratedModelledInteractionComparator == null){
            unambiguousExactCuratedModelledInteractionComparator = new UnambiguousExactCuratedModelledInteractionComparator();
        }

        return unambiguousExactCuratedModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
