package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.participant.ParticipantComparator;

/**
 * Basic comparator for Curated interactions.
 *
 * It will first compare the sources of the interactions using AbstractCvTermComparator. If the sources are the same, t will compare the participants using ParticipantComparator. If the participants are the same, it will compare
 * the interaction types using AbstractCvTermComparator. If the interaction types are the same, it will compare the negative properties.
 * A negative interaction will come after a positive interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class CuratedInteractionComparator extends InteractionComparator {

    /**
     * Creates a new CuratedInteractionComparator.
     * @param participantComparator : required for comparing the participants
     */
    public CuratedInteractionComparator(ParticipantComparator<Feature> participantComparator) {
        super(participantComparator, participantComparator.getCvTermComparator());
    }

    @Override
    /**
     *  * It will first compare the sources of the interactions using AbstractCvTermComparator. If the sources are the same, t will compare the participants using ParticipantComparator. If the participants are the same, it will compare
     * the interaction types using AbstractCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction
     */
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
