package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.ExperimentalEntityPool;

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

public class DefaultExperimentalEntityPoolComparator {

    /**
     * Use DefaultEntityPoolComparator to know if two entityCandidates are equals.
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return true if the two entitySets are equal
     */
    public static boolean areEquals(ExperimentalEntityPool interactorCandidates1, ExperimentalEntityPool interactorCandidates2){
        if (interactorCandidates1 == interactorCandidates2){
            return true;
        }
        else if (interactorCandidates1 == null || interactorCandidates2 == null){
            return false;
        }
        else {

            if (!DefaultParticipantEvidenceComparator.areEquals(interactorCandidates1, interactorCandidates2, true)){
                return false;
            }
            // compare collections
            Iterator<ExperimentalEntity> f1Iterator = new ArrayList<ExperimentalEntity>(interactorCandidates1).iterator();
            Collection<ExperimentalEntity> f2List = new ArrayList<ExperimentalEntity>(interactorCandidates2);

            while (f1Iterator.hasNext()){
                ExperimentalEntity f1 = f1Iterator.next();
                ExperimentalEntity f2ToRemove = null;
                for (ExperimentalEntity f2 : f2List){
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
