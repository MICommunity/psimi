package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default exact biological participant comparator.
 * It will compare the basic properties of a biological participant using DefaultExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultExactModelledParticipantComparator {

    /**
     * Use DefaultExactModelledParticipantComparator to know if two biological participants are equals.
     * @param bioParticipant1
     * @param bioParticipant2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledParticipant bioParticipant1, ModelledParticipant bioParticipant2, boolean checkComplexesAsInteractors){

        if (bioParticipant1 == null && bioParticipant2 == null){
            return true;
        }
        else if (bioParticipant1 == null || bioParticipant2 == null){
            return false;
        }
        else {
            boolean ignoreInteractors = false;
            if (!checkComplexesAsInteractors){
                // the bioparticipant 1 contains a complex that self interacts
                if (bioParticipant1.getInteractor() == bioParticipant1.getModelledInteraction()){
                    // the bioparticipant 2 contains a complex that self interacts
                    if (bioParticipant2.getInteractor() == bioParticipant2.getModelledInteraction()){
                        ignoreInteractors = true;
                    }
                    // the bioparticipant 2 is not self, it comes after the self participant
                    else {
                        return false;
                    }
                }
                // the bioparticipant 2 contains a complex that self interacts, comes before
                else if (bioParticipant2.getInteractor() == bioParticipant2.getModelledInteraction()){
                    return false;
                }
            }

            if (!DefaultExactParticipantBaseComparator.areEquals(bioParticipant1, bioParticipant2, ignoreInteractors)){
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
                else{
                    return true;
                }
            }
        }
    }
}
