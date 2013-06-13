package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

import java.util.Comparator;
import java.util.Date;

/**
 * Basic comparator for curated interactions.
 *
 * It will first compare the basic properties of an interaction using AbstractInteractionBaseComparator.
 * Then it will compare the created dates (null created dates always come after)
 * Finally it will compare the updated date (null updated date always come after)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class CuratedInteractionBaseComparator implements Comparator<Interaction>{

    protected Comparator<Interaction> interactionBaseComparator;

    /**
     * @param interactionBaseComparator : required to compare basic properties of an interaction
     */
    public CuratedInteractionBaseComparator(Comparator<Interaction> interactionBaseComparator){
        if (interactionBaseComparator == null){
            throw new IllegalArgumentException("The interactionBaseComparator comparator is required to compares basic properties of an interaction. It cannot be null");
        }
        this.interactionBaseComparator = interactionBaseComparator;
    }

    public Comparator<Interaction> getInteractionBaseComparator() {
        return interactionBaseComparator;
    }

    /**
     * It will first compare the basic properties of an interaction using AbstractInteractionBaseComparator.
     * Then it will compare the created dates (null created dates always come after)
     * Finally it will compare the updated date (null updated date always come after)
     *
     * @param interaction1
     * @param interaction2
     * @return
     */
    public int compare(Interaction interaction1, Interaction interaction2){
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
            // first compare basic properties
            int comp = interactionBaseComparator.compare(interaction1, interaction2);
            if (comp != 0){
                return comp;
            }

            // then compares created date of interaction
            Date createdDate1 = interaction1.getCreatedDate();
            Date createdDate2 = interaction2.getCreatedDate();

            if (createdDate1 == null && createdDate2 == null){
                comp = EQUAL;
            }
            else if (createdDate1 == null){
                return AFTER;
            }
            else if (createdDate2 == null){
                return BEFORE;
            }
            else if (createdDate1.before(createdDate2)){
                return BEFORE;
            }
            else if (createdDate2.before(createdDate1)){
                return AFTER;
            }

            // then compares updated date of interaction
            Date updatedDate1 = interaction1.getUpdatedDate();
            Date updatedDate2 = interaction2.getUpdatedDate();

            if (updatedDate1 == null && updatedDate2 == null){
                return EQUAL;
            }
            else if (updatedDate1 == null){
                return AFTER;
            }
            else if (updatedDate2 == null){
                return BEFORE;
            }
            else if (updatedDate1.before(updatedDate2)){
                return BEFORE;
            }
            else if (updatedDate2.before(updatedDate1)){
                return AFTER;
            }
            else{
                return EQUAL;
            }
        }
    }
}
