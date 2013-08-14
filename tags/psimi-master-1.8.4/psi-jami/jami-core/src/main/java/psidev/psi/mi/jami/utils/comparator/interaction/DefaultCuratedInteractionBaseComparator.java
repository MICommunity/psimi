package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

import java.util.Date;

/**
 * Default comparator for curated interactions.
 *
 * It will first compare the basic properties of an interaction using DefaultInteractionBaseComparator.
 * Then it will compare the created dates (null created dates always come after)
 * Finally it will compare the updated date (null updated date always come after)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultCuratedInteractionBaseComparator {

    /**
     * Use DefaultCuratedInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (interaction1 == null && interaction2 == null){
            return true;
        }
        else if (interaction1 == null || interaction2 == null){
            return false;
        }
        else {
            // first compare basic properties
            if (!DefaultInteractionBaseComparator.areEquals(interaction1, interaction2)){
                return false;
            }

            // then compares created date of interaction
            Date createdDate1 = interaction1.getCreatedDate();
            Date createdDate2 = interaction2.getCreatedDate();

            if (createdDate1 == null && createdDate2 == null){
                return true;
            }
            else if (createdDate1 == null || createdDate2 == null){
                return false;
            }
            else if (!createdDate1.equals(createdDate2)){
                return false;
            }

            // then compares updated date of interaction
            Date updatedDate1 = interaction1.getUpdatedDate();
            Date updatedDate2 = interaction2.getUpdatedDate();

            if (updatedDate1 == null && updatedDate2 == null){
                return true;
            }
            else if (updatedDate1 == null || updatedDate2 == null){
                return false;
            }
            else{
                return updatedDate1.equals(updatedDate2);
            }
        }
    }
}
