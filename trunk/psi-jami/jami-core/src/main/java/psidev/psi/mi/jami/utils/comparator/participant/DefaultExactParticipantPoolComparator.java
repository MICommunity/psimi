package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default exact EntityPoolComparator.
 * It will first compare the basic entity properties using DefaultExactEntityBaseComparator
 * Then it will compare the collection of entities using DefaultExactEntityBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class DefaultExactParticipantPoolComparator {

    /**
     * Use DefaultExactParticipantPoolComparator to know if two entityCandidates are equals.
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ParticipantPool interactorCandidates1, ParticipantPool interactorCandidates2){
        if (interactorCandidates1 == interactorCandidates2){
            return true;
        }
        else if (interactorCandidates1 == null || interactorCandidates2 == null){
            return false;
        }
        else {

            if (!DefaultExactParticipantBaseComparator.areEquals(interactorCandidates1, interactorCandidates2, true)){
                return false;
            }
            // compare collections
            Iterator<Participant> f1Iterator = new ArrayList<Participant>(interactorCandidates1).iterator();
            Collection<Participant> f2List = new ArrayList<Participant>(interactorCandidates2);

            while (f1Iterator.hasNext()){
                Participant f1 = f1Iterator.next();
                Participant f2ToRemove = null;
                for (Participant f2 : f2List){
                    if (DefaultExactParticipantComparator.areEquals(f1, f2)){
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
