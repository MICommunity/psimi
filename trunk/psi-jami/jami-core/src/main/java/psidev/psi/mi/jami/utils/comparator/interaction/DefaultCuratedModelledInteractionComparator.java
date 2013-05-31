package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;

/**
 * Default curated ModelledInteraction comparator.
 *
 * It will use a DefaultInteractionBaseComparator<Component> to compare basic interaction properties
 * and DefaultCvTermComparator to compare the Source.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCuratedModelledInteractionComparator extends ModelledInteractionComparator {

    private static DefaultCuratedModelledInteractionComparator defaultCuratedModelledInteractionComparator;

    protected DefaultCvTermComparator sourceComparator;

    /**
     * Creates a new DefaultCuratedModelledInteractionComparator. It will use a DefaultCuratedInteractionBaseComparator to
     * compare basic interaction properties
     */
    public DefaultCuratedModelledInteractionComparator() {
        super(new DefaultModelledParticipantComparator(), new DefaultCuratedInteractionBaseComparator());
        this.sourceComparator = new DefaultCvTermComparator();
    }

    @Override
    public DefaultCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultCuratedInteractionBaseComparator) this.interactionBaseComparator;
    }

    public DefaultCvTermComparator getSourceComparator() {
        return sourceComparator;
    }

    @Override
    /**
     * It will use a DefaultInteractionBaseComparator<Component> to compare basic interaction properties.
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
     * Use DefaultCuratedModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (defaultCuratedModelledInteractionComparator == null){
            defaultCuratedModelledInteractionComparator = new DefaultCuratedModelledInteractionComparator();
        }

        return defaultCuratedModelledInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
