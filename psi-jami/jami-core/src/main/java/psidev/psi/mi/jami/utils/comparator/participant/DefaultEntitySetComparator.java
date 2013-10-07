package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.EntitySet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default EntitySetComparator.
 *
 * It will first compare the basic entity properties using DefaultParticipantBaseComparator
 * Then it will compare the collection of entities using DefaultParticipantBaseComparator
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class DefaultEntitySetComparator {

    /**
     * Use DefaultEntitySetComparator to know if two entityCandidates are equals.
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return true if the two entitySets are equal
     */
    public static boolean areEquals(EntitySet interactorCandidates1, EntitySet interactorCandidates2){
        if (interactorCandidates1 == null && interactorCandidates2 == null){
            return true;
        }
        else if (interactorCandidates1 == null || interactorCandidates2 == null){
            return false;
        }
        else {

            if (!DefaultParticipantBaseComparator.areEquals(interactorCandidates1, interactorCandidates2, true)){
                return false;
            }
            // compare collections
            Iterator<Entity> f1Iterator = new ArrayList<Entity>(interactorCandidates1).iterator();
            Collection<Entity> f2List = new ArrayList<Entity>(interactorCandidates2);

            while (f1Iterator.hasNext()){
                Entity f1 = f1Iterator.next();
                Entity f2ToRemove = null;
                for (Entity f2 : f2List){
                    if (DefaultParticipantComparator.areEquals(f1, f2)){
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
}
