package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultFeatureEvidenceComparator;

import java.util.*;

/**
 * Default experimental entity comparator.
 * It will compare the basic properties of a experimental entity using DefaultEntityBaseComparator.
 *
 * This comparator will ignore all the other properties of an experimental entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultExperimentalEntityComparator {

    /**
     * Use DefaultExperimentalEntityComparator to know if two experimental entities are equals.
     * @param participant1
     * @param participant2
     * @return true if the two experimental entities are equal
     */
    public static boolean areEquals(ExperimentalEntity participant1, ExperimentalEntity participant2, boolean checkComplexesAsInteractors){
        Map<Complex, Set<Interactor>> processedComplexes = new IdentityHashMap<Complex, Set<Interactor>>();

        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            boolean ignoreInteractors = false;
            if (!checkComplexesAsInteractors){
                if (checkIfComplexAlreadyProcessed(participant1, participant2, processedComplexes)
                        || checkIfComplexAlreadyProcessed(participant2, participant1, processedComplexes)){
                    ignoreInteractors = true;
                }
            }

            if (!DefaultEntityBaseComparator.areEquals(participant1, participant2, ignoreInteractors)){
                return false;
            }

            // then compares the features
            Collection<FeatureEvidence> features1 = participant1.getFeatures();
            Collection<FeatureEvidence> features2 = participant2.getFeatures();

            if (features1.size() != features2.size()){
                 return false;
            }
            else {
                Iterator<FeatureEvidence> f1Iterator = new ArrayList<FeatureEvidence>(features1).iterator();
                Collection<FeatureEvidence> f2List = new ArrayList<FeatureEvidence>(features2);

                while (f1Iterator.hasNext()){
                    FeatureEvidence f1 = f1Iterator.next();
                    FeatureEvidence f2ToRemove = null;
                    for (FeatureEvidence f2 : f2List){
                        if (DefaultFeatureEvidenceComparator.areEquals(f1, f2)){
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

    private static boolean checkIfComplexAlreadyProcessed(ExperimentalEntity bioParticipant1, ExperimentalEntity bioParticipant2, Map<Complex, Set<Interactor>> processedComplexes) {
        Complex complex = null;
        if (bioParticipant1.getInteractor() instanceof Complex){
            complex = (Complex) bioParticipant1.getInteractor();
        }

        // we already processed complex1 as first interactor
        if (complex != null && processedComplexes.containsKey(complex)){
            Set<Interactor> interactorSet = processedComplexes.get(complex);
            // already processed this pair
            if (interactorSet.contains(bioParticipant2.getInteractor())){
                return true;
            }
            else{
                interactorSet.add(bioParticipant2.getInteractor());
            }
        }

        return false;
    }
}
