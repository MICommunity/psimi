package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ModelledParticipantPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default EntityPoolComparator.
 *
 * It will first compare the basic entity properties using DefaultParticipantEvidenceBaseComparator
 * Then it will compare the collection of entities using DefaultParticipantEvidenceBaseComparator
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class DefaultExactModelledParticipantPoolComparator {

    /**
     * Use DefaultParticipantPoolComparator to know if two entityCandidates are equals.
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return true if the two entitySets are equal
     */
    public static boolean areEquals(ModelledParticipantPool interactorCandidates1, ModelledParticipantPool interactorCandidates2){
        if (interactorCandidates1 == interactorCandidates2){
            return true;
        }
        else if (interactorCandidates1 == null || interactorCandidates2 == null){
            return false;
        }
        else {

            if (!DefaultExactModelledParticipantComparator.areEquals(interactorCandidates1, interactorCandidates2, true)){
                return false;
            }
            // compare collections
            Iterator<ModelledParticipant> f1Iterator = new ArrayList<ModelledParticipant>(interactorCandidates1).iterator();
            Collection<ModelledParticipant> f2List = new ArrayList<ModelledParticipant>(interactorCandidates2);

            while (f1Iterator.hasNext()){
                ModelledParticipant f1 = f1Iterator.next();
                ModelledParticipant f2ToRemove = null;
                for (ModelledParticipant f2 : f2List){
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
