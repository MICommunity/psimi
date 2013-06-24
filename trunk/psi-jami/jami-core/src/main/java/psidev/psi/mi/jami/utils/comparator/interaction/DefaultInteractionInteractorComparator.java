package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultParticipantInteractorComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default interaction comparator only based on the interactors of an interaction.
 *
 * It will use a DefaultInteractorComparator to compare the interactors involved in the interaction.
 *
 * It will ignore all other properties of an Interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultInteractionInteractorComparator {

    /**
     * Use DefaultInteractionInteractorComparator to know if two interactions are equals.
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

            // first compares participants of an interaction
            Collection<? extends Participant> participants1 = interaction1.getParticipants();
            Collection<? extends Participant> participants2 = interaction2.getParticipants();

            return compareParticipants(participants1, participants2);
        }
    }

    private static boolean compareParticipants(Collection<? extends Participant> participants1, Collection<? extends Participant> participants2) {
        // compare collections
        Iterator<Participant> f1Iterator = new ArrayList<Participant>(participants1).iterator();
        Collection<Participant> f2List = new ArrayList<Participant>(participants2);

        while (f1Iterator.hasNext()){
            Participant f1 = f1Iterator.next();
            Participant f2ToRemove = null;
            for (Participant f2 : f2List){
                if (DefaultParticipantInteractorComparator.areEquals(f1, f2)){
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
