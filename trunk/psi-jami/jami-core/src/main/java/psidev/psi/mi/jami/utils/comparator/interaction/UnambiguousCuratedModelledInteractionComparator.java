package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousModelledParticipantComparator;

/**
 * Unambiguous curated ModelledInteraction comparator.
 *
 * It will use a UnambiguousInteractionBaseComparator<Component> to compare basic interaction properties
 * and then it will use UnambiguousCvTermComparator to compare sources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousCuratedModelledInteractionComparator extends ModelledInteractionComparator{

    private static UnambiguousCuratedModelledInteractionComparator unambiguousCuratedModelledInteractionComparator;

    protected UnambiguousCvTermComparator sourceComparator;
    /**
     * Creates a new UnambiguousCuratedModelledInteractionComparator. It will use a UnambiguousInteractionBaseComparator to
     * compare basic interaction properties
     */
    public UnambiguousCuratedModelledInteractionComparator() {
        super(new UnambiguousModelledParticipantComparator(), new UnambiguousCuratedInteractionBaseComparator());
        this.sourceComparator = new UnambiguousCvTermComparator();
    }

    @Override
    public UnambiguousCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousCuratedInteractionBaseComparator) this.interactionBaseComparator;
    }

    public UnambiguousCvTermComparator getSourceComparator() {
        return sourceComparator;
    }

    @Override
    /**
     * It will use a UnambiguousInteractionBaseComparator<Component> to compare basic interaction properties
     * and then it will use UnambiguousCvTermComparator to compare sources     */
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
     * Use UnambiguousCuratedModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (unambiguousCuratedModelledInteractionComparator == null){
            unambiguousCuratedModelledInteractionComparator = new UnambiguousCuratedModelledInteractionComparator();
        }

        return unambiguousCuratedModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
