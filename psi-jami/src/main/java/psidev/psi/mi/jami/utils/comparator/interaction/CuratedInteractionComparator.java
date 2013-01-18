package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ParticipantComparator;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class CuratedInteractionComparator extends InteractionComparator {

    public CuratedInteractionComparator(ParticipantComparator<Feature> participantComparator, AbstractCvTermComparator cvTermComparator) {
        super(participantComparator, cvTermComparator);
    }

    @Override
    public int compare(Interaction interaction1, Interaction interaction2) {
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
            // first compares source of an interaction
            Source source1 = interaction1.getSource();
            Source source2 = interaction2.getSource();

            int comp = cvTermComparator.compare(source1, source2);
            if (comp != 0){
                return comp;
            }

            // then compares basic interaction properties
            return super.compare(interaction1, interaction2);
        }
    }
}
