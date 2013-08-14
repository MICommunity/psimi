package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default ModelledInteraction comparator.
 *
 * It will use a DefaultInteractionBaseComparator to compare basic interaction properties.
 * Then it will compare the modelledParticipants using DefaultModelledParticipantComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultModelledInteractionComparator {

    /**
     * Use DefaultModelledInteractionComparator to know if two modelled interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction interaction1, ModelledInteraction interaction2){
        if (interaction1 == null && interaction2 == null){
            return true;
        }
        else if (interaction1 == null || interaction2 == null){
            return false;
        }
        else {
            if (!DefaultInteractionBaseComparator.areEquals(interaction1, interaction2)){
                return false;
            }

            // first compares participants of an interaction
            if (!compareParticipants(interaction1.getParticipants(), interaction2.getParticipants())){
                return false;
            }

            // then compares source
            Source s1 = interaction1.getSource();
            Source s2 = interaction2.getSource();

            return DefaultCvTermComparator.areEquals(s1, s2);
        }
    }

    private static boolean compareParticipants(Collection<ModelledParticipant> participants1, Collection<ModelledParticipant> participants2) {
        // compare collections
        Iterator<ModelledParticipant> f1Iterator = new ArrayList<ModelledParticipant>(participants1).iterator();
        Collection<ModelledParticipant> f2List = new ArrayList<ModelledParticipant>(participants2);

        while (f1Iterator.hasNext()){
            ModelledParticipant f1 = f1Iterator.next();
            ModelledParticipant f2ToRemove = null;
            for (ModelledParticipant f2 : f2List){
                if (DefaultModelledParticipantComparator.areEquals(f1, f2, true)){
                    f2ToRemove = f2;
                    break;
                }
            }
            if (f2ToRemove != null){
                f2List.remove(f2ToRemove);
                f1Iterator.remove();
            }
            else {
                return false;
            }
        }

        if (f1Iterator.hasNext() || !f2List.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}
