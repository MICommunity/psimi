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
 * Default experimental participant pool comparator.
 * It will compare the basic properties of a experimental participant using DefaultParticipantBaseComparator.
 * It will compare the participant candidates using DefaultExperimentalEntityComparator
 *
 * This comparator will ignore all the other properties of a experimental participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultExperimentalParticipantPoolComparator {

    /**
     * Use DefaultExperimentalParticipantPoolComparator to know if two experimental participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalParticipantPool participant1, ExperimentalParticipantPool participant2, boolean checkComplexesAsInteractors){
        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            if (participant1 == participant2){
                return true;
            }
            else if (participant1 == null || participant2 == null){
                return false;
            }
            else {
                if (!DefaultParticipantBaseComparator.areEquals(participant1, participant2, true)){
                    return false;
                }

                // first compares the experimental roles
                CvTerm expRoles1 = participant1.getExperimentalRole();
                CvTerm expRoles2 = participant2.getExperimentalRole();

                if (!DefaultCvTermComparator.areEquals(expRoles1, expRoles2)){
                    return false;
                }

                // then compares the participant identification method
                Collection<CvTerm> method1 = participant1.getIdentificationMethods();
                Collection<CvTerm> method2 = participant2.getIdentificationMethods();

                if (!ComparatorUtils.areCvTermsEqual(method1, method2)){
                    return false;
                }

                // then compares the participant experimental preparations
                Collection<CvTerm> prep1 = participant1.getExperimentalPreparations();
                Collection<CvTerm> prep2 = participant2.getExperimentalPreparations();

                if (!ComparatorUtils.areCvTermsEqual(prep1, prep2)){
                    return false;
                }

                // then compares the expressed in organisms
                Organism organism1 = participant1.getExpressedInOrganism();
                Organism organism2 = participant2.getExpressedInOrganism();

                if (!DefaultOrganismComparator.areEquals(organism1, organism2)){
                    return false;
                }

                // then compares the parameters
                Collection<Parameter> param1 = participant1.getParameters();
                Collection<Parameter> param2 = participant2.getParameters();

                if (!ComparatorUtils.areParametersEqual(param1, param2)){
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
                }

                // compare types
                CvTerm type1 = participant1.getType();
                CvTerm type2 = participant2.getType();
                if (!DefaultCvTermComparator.areEquals(type1, type2)){
                    return false;
                }

                // compare collections
                Iterator<ExperimentalParticipantCandidate> f1Iterator = new ArrayList<ExperimentalParticipantCandidate>(participant1).iterator();
                Collection<ExperimentalParticipantCandidate> f2List = new ArrayList<ExperimentalParticipantCandidate>(participant2);

                while (f1Iterator.hasNext()){
                    ExperimentalParticipantCandidate f1 = f1Iterator.next();
                    ExperimentalParticipantCandidate f2ToRemove = null;
                    for (ExperimentalParticipantCandidate f2 : f2List){
                        if (DefaultExperimentalEntityComparator.areEquals(f1, f2, checkComplexesAsInteractors)){
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
