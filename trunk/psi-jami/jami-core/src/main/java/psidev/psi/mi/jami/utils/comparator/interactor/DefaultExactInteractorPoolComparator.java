package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default exact InteractorPoolComparator.
 * It will first compare the basic interactor properties using DefaultExactInteractorBaseComparator
 * Then it will compare the collection of Interactors using DefaultExactInteractorBaseComparator
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorPoolComparator {

    /**
     * Use DefaultExactInteractorPoolComparator to know if two interactorCandidates are equals.
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorPool interactorCandidates1, InteractorPool interactorCandidates2){
        if (interactorCandidates1 == null && interactorCandidates2 == null){
            return true;
        }
        else if (interactorCandidates1 == null || interactorCandidates2 == null){
            return false;
        }
        else {

            if (!DefaultExactInteractorBaseComparator.areEquals(interactorCandidates1, interactorCandidates2)){
                return false;
            }
            // compare collections
            Iterator<Interactor> f1Iterator = new ArrayList<Interactor>(interactorCandidates1).iterator();
            Collection<Interactor> f2List = new ArrayList<Interactor>(interactorCandidates2);

            while (f1Iterator.hasNext()){
                Interactor f1 = f1Iterator.next();
                Interactor f2ToRemove = null;
                for (Interactor f2 : f2List){
                    if (DefaultExactInteractorComparator.areEquals(f1, f2)){
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
