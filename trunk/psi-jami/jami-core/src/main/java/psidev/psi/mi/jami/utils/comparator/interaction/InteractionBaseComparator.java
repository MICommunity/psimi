package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Comparator;

/**
 * Basic Interaction comparator.
 *
 * It will first compare the participants using ParticipantBaseComparator. If the participants are the same, it will compare
 * the interaction types using AbstractCvTermComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class InteractionBaseComparator implements Comparator<Interaction> {

    protected AbstractCvTermComparator cvTermComparator;

    /**
     * Creates a new InteractionBaseComparator.
     * @param cvTermComparator : required to compare interaction type
     */
    public InteractionBaseComparator(AbstractCvTermComparator cvTermComparator){

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare interaction types. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    /**
     * It will first compare the participants using ParticipantBaseComparator. If the participants are the same, it will compare
     * the interaction types using AbstractCvTermComparator. If the interaction types are the same, it will compare the negative properties.
     * A negative interaction will come after a positive interaction.
     *
     * @param interaction1
     * @param interaction2
     * @return
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

            // then compares interaction type
            CvTerm type1 = interaction1.getInteractionType();
            CvTerm type2 = interaction2.getInteractionType();

            return cvTermComparator.compare(type1, type2);
        }
    }
}
