package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultFeature;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;

/**
 * Utility class for cloning a participant candidate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ParticipantCandidateCloner {

    /***
     * This method will copy properties of participant candidate source to participant candidate target and will override all the other properties of Target participant candidate.
     * This method will ignore interaction
     * @param source
     * @param target
     * @param createNewFeature If true, this method will clone each feature from source instead of reusing the feature instances from source.
     *                         It will then set the experimental entity of the cloned features to target
     */
    public static void copyAndOverrideExperimentalCandidateProperties(ExperimentalParticipantCandidate source,
                                                                    ExperimentalParticipantCandidate target,
                                                                    boolean createNewFeature){
        if (source != null && target != null){
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());

            // copy collections
            target.getCausalRelationships().clear();
            target.getCausalRelationships().addAll(source.getCausalRelationships());

            // copy features or create new ones
            if (!createNewFeature){
                target.getFeatures().clear();
                target.getFeatures().addAll(source.getFeatures());
            }
            else {
                target.getFeatures().clear();
                for (FeatureEvidence f : source.getFeatures()){
                    FeatureEvidence clone = new DefaultFeatureEvidence();
                    FeatureCloner.copyAndOverrideFeatureEvidenceProperties(f, clone);
                    target.addFeature(clone);
                }
            }
        }
    }

    /***
     * This method will copy properties of modelled participant candidate source in modelled participant candidate target and will override all the other properties of Target modelled participant.
     * This method will ignore interaction
     * @param source
     * @param target
     * @param createNewFeature If true, this method will clone each feature from source instead of reusing the feature instances from source.
     *                         It will then set the modelledParticipant of the cloned features to target
     */
    public static void copyAndOverrideModelledPCandidateProperties(ModelledParticipantCandidate source,
                                                                   ModelledParticipantCandidate target,
                                                                   boolean createNewFeature){
        if (source != null && target != null){
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());

            // copy collections
            target.getCausalRelationships().clear();
            target.getCausalRelationships().addAll(source.getCausalRelationships());

            // copy features or create new ones
            if (!createNewFeature){
                target.getFeatures().clear();
                target.getFeatures().addAll(source.getFeatures());
            }
            else {
                target.getFeatures().clear();
                for (ModelledFeature f : source.getFeatures()){
                    ModelledFeature clone = new DefaultModelledFeature();
                    FeatureCloner.copyAndOverrideModelledFeaturesProperties(f, clone);
                    target.addFeature(clone);
                }
            }
        }
    }

    /**
     * This method will copy properties of participant candidate source in participant candidate
     * target and will override all the other properties of Target participant.
     * @param source
     * @param target
     * @param createNewFeature If true, this method will clone each feature from source instead of reusing the feature instances from source.
     */
    public static void copyAndOverrideBasicCandidateProperties(ParticipantCandidate source,
                                                               ParticipantCandidate target,
                                                               boolean createNewFeature){
        if (source != null && target != null){
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());

            // copy collections
            target.getCausalRelationships().clear();
            target.getCausalRelationships().addAll(source.getCausalRelationships());

            // copy features or create new ones
            if (!createNewFeature){
                target.getFeatures().clear();
                target.addAllFeatures(source.getFeatures());
            }
            else {
                target.getFeatures().clear();
                for (Object f : source.getFeatures()){
                    Feature clone = new DefaultFeature();
                    FeatureCloner.copyAndOverrideBasicFeaturesProperties((Feature)f, clone);
                    target.addFeature(clone);
                }
            }
        }
    }
}
