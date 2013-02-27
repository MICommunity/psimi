package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactModelledParticipantComparator;

/**
 * Unambiguous exact curated ModelledInteraction comparator.
 *
 * It will use a UnambiguousExactInteractionBaseComparator to compare basic interaction properties and then UnambiguousCvtermComparator to compare sources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousExactCuratedModelledInteractionComparator extends ModelledInteractionComparator{

    private static UnambiguousExactCuratedModelledInteractionComparator unambiguousExactCuratedModelledInteractionComparator;

    protected UnambiguousCvTermComparator sourceComparator;
    /**
     * Creates a new UnambiguousExactCuratedModelledInteractionComparator. It will use a UnambiguousExactCuratedInteractionBaseComparator to
     * compare basic interaction properties
     */
    public UnambiguousExactCuratedModelledInteractionComparator() {
        super(new UnambiguousExactModelledParticipantComparator(), new InteractionBaseComparator(new UnambiguousCvTermComparator()));
        this.sourceComparator = new UnambiguousCvTermComparator();
    }

    @Override
    public InteractionBaseComparator getInteractionComparator() {
        return this.interactionComparator;
    }
    public UnambiguousCvTermComparator getSourceComparator() {
        return sourceComparator;
    }


    @Override
    /**
     * It will use a UnambiguousExactInteractionBaseComparator to compare basic interaction properties and then UnambiguousCvtermComparator to compare sources
     */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interaction1 == null && interaction2 == null){
            return EQUAL;
        }
        else if (interaction1 == null){
            return AFTER;
        }
        else if (interaction2 == null){
            return BEFORE;
        }
        else {
            int comp = super.compare(interaction1, interaction2);
            if (comp != 0){
                return comp;
            }

            // first compares source of an interaction
            Source source1 = interaction1.getSource();
            Source source2 = interaction2.getSource();

            return sourceComparator.compare(source1, source2);
        }
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
