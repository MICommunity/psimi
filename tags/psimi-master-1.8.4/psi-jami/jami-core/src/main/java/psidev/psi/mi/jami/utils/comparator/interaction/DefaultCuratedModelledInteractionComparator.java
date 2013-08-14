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

public class DefaultCuratedModelledInteractionComparator {

    /**
     * Use DefaultCuratedModelledInteractionComparator to know if two modelled interactions are equals.
     * @param modelledInteraction1
     * @param modelledInteraction2
     * @return true if the two modelled interactions are equal
     */
    public static boolean areEquals(ModelledInteraction modelledInteraction1, ModelledInteraction modelledInteraction2){
        if (modelledInteraction1 == null && modelledInteraction2 == null){
            return true;
        }
        else if (modelledInteraction1 == null || modelledInteraction2 == null){
            return false;
        }
        else {

            if (!DefaultCuratedInteractionBaseComparator.areEquals(modelledInteraction1, modelledInteraction2)){
                return false;
            }

            if (!compareParticipants(modelledInteraction1.getParticipants(), modelledInteraction2.getParticipants())){
                return false;
            }

            // then compares source
            Source s1 = modelledInteraction1.getSource();
            Source s2 = modelledInteraction2.getSource();

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
