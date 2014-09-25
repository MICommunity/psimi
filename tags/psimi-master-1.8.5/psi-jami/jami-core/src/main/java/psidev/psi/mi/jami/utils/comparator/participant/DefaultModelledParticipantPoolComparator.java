package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipantCandidate;
import psidev.psi.mi.jami.model.ModelledParticipantPool;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default biological participant pool comparator.
 * It will compare the basic properties of a biological participant using DefaultParticipantBaseComparator.
 * It will compare the participant candidates using DefaultModelledEntityComparator
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultModelledParticipantPoolComparator {

    /**
     * Use DefaultModelledParticipantPoolComparator to know if two biological participant pools are equals.
     * @param bioParticipant1
     * @param bioParticipant2
     * @return true if the two biological participant pools are equal
     */
    public static boolean areEquals(ModelledParticipantPool bioParticipant1, ModelledParticipantPool bioParticipant2, boolean checkComplexesAsInteractors){
        if (bioParticipant1 == bioParticipant2){
            return true;
        }
        else if (bioParticipant1 == null || bioParticipant2 == null){
            return false;
        }
        else {
            if (bioParticipant1 == bioParticipant2){
                return true;
            }
            else if (bioParticipant1 == null || bioParticipant2 == null){
                return false;
            }
            else {
                if (!DefaultParticipantBaseComparator.areEquals(bioParticipant1, bioParticipant2, true)){
                    return false;
                }

                // then compares the features
                Collection<ModelledFeature> features1 = bioParticipant1.getFeatures();
                Collection<ModelledFeature> features2 = bioParticipant2.getFeatures();

                if (features1.size() != features2.size()){
                    return false;
                }
                else {
                    Iterator<ModelledFeature> f1Iterator = new ArrayList<ModelledFeature>(features1).iterator();
                    Collection<ModelledFeature> f2List = new ArrayList<ModelledFeature>(features2);

                    while (f1Iterator.hasNext()){
                        ModelledFeature f1 = f1Iterator.next();
                        ModelledFeature f2ToRemove = null;
                        for (ModelledFeature f2 : f2List){
                            if (DefaultModelledFeatureComparator.areEquals(f1, f2)){
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
                }

                // compare types
                CvTerm type1 = bioParticipant1.getType();
                CvTerm type2 = bioParticipant2.getType();
                if (!DefaultCvTermComparator.areEquals(type1, type2)){
                    return false;
                }

                // compare collections
                Iterator<ModelledParticipantCandidate> f1Iterator = new ArrayList<ModelledParticipantCandidate>(bioParticipant1).iterator();
                Collection<ModelledParticipantCandidate> f2List = new ArrayList<ModelledParticipantCandidate>(bioParticipant2);

                while (f1Iterator.hasNext()){
                    ModelledParticipantCandidate f1 = f1Iterator.next();
                    ModelledParticipantCandidate f2ToRemove = null;
                    for (ModelledParticipantCandidate f2 : f2List){
                        if (DefaultModelledEntityComparator.areEquals(f1, f2, checkComplexesAsInteractors)){
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
