package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;

/**
 * Default curated ModelledInteraction comparator.
 *
 * It will use a DefaultCuratedInteractionBaseComparator to compare basic interaction properties.
 * Then it will compare the modelledParticipants using DefaultModelledParticipantComparator.
 * Finally, it will compare the source of the modelledInteraction using DefaultCvTermComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCuratedModelledInteractionComparator extends CuratedModelledInteractionComparator {

    private static DefaultCuratedModelledInteractionComparator defaultCuratedModelledInteractionComparator;

    /**
     * Creates a new DefaultCuratedModelledInteractionComparator. It will use a DefaultCuratedInteractionBaseComparator to
     * compare basic interaction properties
     */
    public DefaultCuratedModelledInteractionComparator() {
        super(new DefaultModelledParticipantComparator(), new DefaultCuratedInteractionBaseComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultCuratedInteractionBaseComparator) this.interactionBaseComparator;
    }

    public DefaultCvTermComparator getSourceComparator() {
        return (DefaultCvTermComparator) sourceComparator;
    }

    @Override
    /**
     * It will use a DefaultCuratedInteractionBaseComparator to compare basic interaction properties.
     * Then it will compare the modelledParticipants using DefaultModelledParticipantComparator.
     * Finally, it will compare the source of the modelledInteraction using DefaultCvTermComparator
     * */
    public int compare(ModelledInteraction interaction1, ModelledInteraction interaction2) {
        return super.compare(interaction1, interaction2);
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
