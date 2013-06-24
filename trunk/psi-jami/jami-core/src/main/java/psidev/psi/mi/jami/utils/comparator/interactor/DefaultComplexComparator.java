package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default Complex comparator
 *
 * It will first look at the default properties of an interactor using DefaultInteractorBaseComparator.
 * It will then compare interaction types using DefaultCvTermComparator.
 * If the basic interactor properties are the same, It will first compare the collection of components using DefaultModelledParticipantComparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultComplexComparator {

    /**
     * Use DefaultComplexComparator to know if two complexes are equals.
     * @param complex1
     * @param complex2
     * @return true if the two complexes are equal
     */
    public static boolean areEquals(Complex complex1, Complex complex2){

        if (complex1 == null && complex2 == null){
            return true;
        }
        else if (complex1 == null || complex2 == null){
            return false;
        }
        else {

            // compares the basic interactor properties first
            if (!DefaultInteractorBaseComparator.areEquals(complex1, complex2)){
                return false;
            }

            // compares the interaction type
            CvTerm type1 = complex1.getInteractionType();
            CvTerm type2 = complex2.getInteractionType();

            if (!DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }

            // then compares collection of components
            Collection<ModelledParticipant> components1 = complex1.getParticipants();
            Collection<ModelledParticipant> components2 = complex2.getParticipants();

            if (components1.size() != components2.size()){
                return false;
            }
            else {
                Iterator<ModelledParticipant> f1Iterator = new ArrayList<ModelledParticipant>(components1).iterator();
                Collection<ModelledParticipant> f2List = new ArrayList<ModelledParticipant>(components2);

                while (f1Iterator.hasNext()){
                    ModelledParticipant f1 = f1Iterator.next();
                    ModelledParticipant f2ToRemove = null;
                    for (ModelledParticipant f2 : f2List){
                        if (DefaultModelledParticipantComparator.areEquals(f1, f2, false)){
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
}
