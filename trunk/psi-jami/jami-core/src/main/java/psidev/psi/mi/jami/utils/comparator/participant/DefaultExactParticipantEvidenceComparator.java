package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultFeatureEvidenceComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default exact Experimental participant comparator.
 *
 * It will first compares experimental roles using DefaultCvTermComparator. If both experimental roles are equals, it
 * will look at the identification methods using DefaultCvTermComparator. If both identification methods are equals, it will
 * look at the experimental preparations using DefaultCvTermComparator. If both experimental preparations are equals, it will
 * look at the expressed in organisms using DefaultOrganismComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactParticipantEvidenceComparator {

    /**
     * Use DefaultExactParticipantEvidenceComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param experimentalParticipant2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalEntity experimentalParticipant1, ExperimentalEntity experimentalParticipant2, boolean ignoreInteractors){
        if (experimentalParticipant1 == experimentalParticipant2){
            return true;
        }
        else if (experimentalParticipant1 == null || experimentalParticipant2 == null){
            return false;
        }
        else {

            // first compares basic participant properties
            if (!DefaultExactParticipantBaseComparator.areEquals(experimentalParticipant1, experimentalParticipant2, ignoreInteractors)){
                return false;
            }

            // first compares the experimental roles
            CvTerm expRoles1 = experimentalParticipant1.getExperimentalRole();
            CvTerm expRoles2 = experimentalParticipant2.getExperimentalRole();

            if (DefaultCvTermComparator.areEquals(expRoles1, expRoles2)){
                return false;
            }

            // then compares the participant identification method
            Collection<CvTerm> method1 = experimentalParticipant1.getIdentificationMethods();
            Collection<CvTerm> method2 = experimentalParticipant2.getIdentificationMethods();

            if (!ComparatorUtils.areCvTermsEqual(method1, method2)){
                return false;
            }

            // then compares the participant experimental preparations
            Collection<CvTerm> prep1 = experimentalParticipant1.getExperimentalPreparations();
            Collection<CvTerm> prep2 = experimentalParticipant2.getExperimentalPreparations();

            if (!ComparatorUtils.areCvTermsEqual(prep1, prep2)){
                return false;
            }

            // then compares the expressed in organisms
            Organism organism1 = experimentalParticipant1.getExpressedInOrganism();
            Organism organism2 = experimentalParticipant2.getExpressedInOrganism();

            if (!DefaultOrganismComparator.areEquals(organism1, organism2)){
                return false;
            }

            // then compares the parameters
            Collection<Parameter> param1 = experimentalParticipant1.getParameters();
            Collection<Parameter> param2 = experimentalParticipant2.getParameters();

            if (!ComparatorUtils.areParametersEqual(param1, param2)){
                return false;
            }

            // then compares the features
            Collection<FeatureEvidence> features1 = experimentalParticipant1.getFeatures();
            Collection<FeatureEvidence> features2 = experimentalParticipant2.getFeatures();

            return compareCollectionOfFeatures(features1, features2);
        }
    }

    private static boolean compareCollectionOfFeatures(Collection<FeatureEvidence> features1, Collection<FeatureEvidence> features2) {
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
