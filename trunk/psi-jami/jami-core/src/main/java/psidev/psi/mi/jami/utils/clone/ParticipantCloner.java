package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultFeature;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;

/**
 * Utility class for cloning a participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ParticipantCloner {

    /***
     * This method will copy properties of participant source in participant target and will override all the other properties of Target participant.
     * This method will ignore interaction
     * @param source
     * @param target
     * @param createNewFeature If true, this method will clone each feature from source instead of reusing the feature instances from source.
     *                         It will then set the participantEvidence of the cloned features to target
     */
    public static void copyAndOverrideParticipantEvidenceProperties(ExperimentalEntity source, ExperimentalEntity target, boolean createNewFeature){
        if (source != null && target != null){
            target.setExperimentalRole(source.getExperimentalRole());
            target.setExpressedInOrganism(source.getExpressedInOrganism());
            target.setBiologicalRole(source.getBiologicalRole());
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());

            // copy collections
            target.getCausalRelationships().clear();
            target.getCausalRelationships().addAll(source.getCausalRelationships());
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getConfidences().clear();
            target.getConfidences().addAll(source.getConfidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());
            target.getExperimentalPreparations().clear();
            target.getExperimentalPreparations().addAll(source.getExperimentalPreparations());
            target.getParameters().clear();
            target.getParameters().addAll(source.getParameters());
            target.getIdentificationMethods().clear();
            target.getIdentificationMethods().addAll(source.getIdentificationMethods());

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
     * This method will copy properties of modelled participant source in modelled participant target and will override all the other properties of Target modelled participant.
     * This method will ignore interaction
     * @param source
     * @param target
     * @param createNewFeature If true, this method will clone each feature from source instead of reusing the feature instances from source.
     *                         It will then set the modelledParticipant of the cloned features to target
     */
    public static void copyAndOverrideModelledParticipantProperties(ModelledEntity source, ModelledEntity target, boolean createNewFeature){
        if (source != null && target != null){
            target.setBiologicalRole(source.getBiologicalRole());
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());

            // copy collections
            target.getCausalRelationships().clear();
            target.getCausalRelationships().addAll(source.getCausalRelationships());
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());

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
     * This method will copy properties of participant source in participant target and will override all the other properties of Target participant.
     * @param source
     * @param target
     * @param createNewFeature If true, this method will clone each feature from source instead of reusing the feature instances from source.
     */
    public static void copyAndOverrideBasicParticipantProperties(Entity<Feature> source, Entity<Feature> target, boolean createNewFeature){
        if (source != null && target != null){
            target.setBiologicalRole(source.getBiologicalRole());
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());

            // copy collections
            target.getCausalRelationships().clear();
            target.getCausalRelationships().addAll(source.getCausalRelationships());
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());

            // copy features or create new ones
            if (!createNewFeature){
                target.getFeatures().clear();
                target.getFeatures().addAll(source.getFeatures());
            }
            else {
                target.getFeatures().clear();
                for (Feature f : source.getFeatures()){
                    Feature clone = new DefaultFeature();
                    FeatureCloner.copyAndOverrideBasicFeaturesProperties(f, clone);
                    target.getFeatures().add(clone);
                }
            }
        }
    }

    public static void copyAndOverrideParticipantPoolProperties(EntityPool source, EntityPool target, boolean newFeatures){
        if (source != null && target != null){
            copyAndOverrideBasicParticipantProperties(source, target, newFeatures);
            target.clear();
            target.addAll(source);
        }
    }

    public static void copyAndOverrideExperimentalEntityPoolProperties(ExperimentalEntityPool source, ExperimentalEntityPool target, boolean newFeatures){
        if (source != null && target != null){
            copyAndOverrideParticipantEvidenceProperties(source, target, newFeatures);
            target.clear();
            target.addAll(source);
        }
    }
}
