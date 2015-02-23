package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParticipantCandidate;
import psidev.psi.mi.jami.model.ParticipantPool;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default exact participant pool comparator
 * It will first compare the interactors, biological role and stoichiometry using DefaultExactParticipantBaseComparator
 * it will then compare the participant candidates using DefaultExactEntityBaseComparator.
 *
 * This comparator will ignore all the other properties of a participantPool.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultExactParticipantPoolComparator {

    /**
     * Use DefaultExactParticipantPoolComparator to know if two participant pools are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participant pools are equal
     */
    public static boolean areEquals(ParticipantPool participant1, ParticipantPool participant2, boolean ignoreInteractors){

        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            int comp;
            // first compares interactors
            if (!DefaultExactParticipantBaseComparator.areEquals(participant1, participant2, true)){
                return false;
            }

            // compare types
            CvTerm type1 = participant1.getType();
            CvTerm type2 = participant2.getType();
            if (!DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }

            // compare collections
            Iterator<ParticipantCandidate> f1Iterator = new ArrayList<ParticipantCandidate>(participant1).iterator();
            Collection<ParticipantCandidate> f2List = new ArrayList<ParticipantCandidate>(participant2);

            while (f1Iterator.hasNext()){
                ParticipantCandidate f1 = f1Iterator.next();
                ParticipantCandidate f2ToRemove = null;
                for (ParticipantCandidate f2 : f2List){
                    if (DefaultExactEntityBaseComparator.areEquals(f1, f2, ignoreInteractors)){
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
