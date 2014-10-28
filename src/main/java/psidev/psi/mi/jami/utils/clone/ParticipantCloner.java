package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;

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
    public static void copyAndOverrideParticipantEvidenceProperties(ParticipantEvidence source, ParticipantEvidence target, boolean createNewFeature){
        if (source != null && target != null){
            target.setExperimentalRole(source.getExperimentalRole());
            target.setExpressedInOrganism(source.getExpressedInOrganism());
            target.setBiologicalRole(source.getBiologicalRole());
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

            // special case for participant candidates
            if (source instanceof ExperimentalParticipantPool && target instanceof ExperimentalParticipantPool){
                ExperimentalParticipantPool poolTarget = (ExperimentalParticipantPool)target;
                ExperimentalParticipantPool poolSource = (ExperimentalParticipantPool)source;

                poolTarget.clear();
                for (ExperimentalParticipantCandidate candidate : poolSource){
                    ExperimentalParticipantCandidate candidateClone = new DefaultExperimentalParticipantCandidate(candidate.getInteractor());
                    ParticipantCandidateCloner.copyAndOverrideExperimentalCandidateProperties(candidate, candidateClone, createNewFeature);
                    poolTarget.add(candidateClone);
                }
            }
            else{
                target.setInteractor(source.getInteractor());
            }

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
    public static void copyAndOverrideModelledParticipantProperties(ModelledParticipant source, ModelledParticipant target, boolean createNewFeature){
        if (source != null && target != null){
            target.setBiologicalRole(source.getBiologicalRole());
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

            // special case for participant candidates
            if (source instanceof ModelledParticipantPool && target instanceof ModelledParticipantPool){
                ModelledParticipantPool poolTarget = (ModelledParticipantPool)target;
                ModelledParticipantPool poolSource = (ModelledParticipantPool)source;

                poolTarget.clear();
                for (ModelledParticipantCandidate candidate : poolSource){
                    ModelledParticipantCandidate candidateClone = new DefaultModelledParticipantCandidate(candidate.getInteractor());
                    ParticipantCandidateCloner.copyAndOverrideModelledPCandidateProperties(candidate, candidateClone, createNewFeature);
                    poolTarget.add(candidateClone);
                }
            }
            else{
                target.setInteractor(source.getInteractor());
            }

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
    public static void copyAndOverrideBasicParticipantProperties(Participant source, Participant target, boolean createNewFeature){
        if (source != null && target != null){
            target.setBiologicalRole(source.getBiologicalRole());
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

            // special case for participant candidates
            if (source instanceof ParticipantPool && target instanceof ParticipantPool){
                ParticipantPool poolTarget = (ParticipantPool)target;
                ParticipantPool poolSource = (ParticipantPool)source;

                poolTarget.clear();
                for (Object candidate : poolSource){
                    ParticipantCandidate candidateClone = new DefaultParticipantCandidate(((ParticipantCandidate)candidate).getInteractor());
                    ParticipantCandidateCloner.copyAndOverrideBasicCandidateProperties((ParticipantCandidate)candidate, candidateClone, createNewFeature);
                    poolTarget.add(candidateClone);
                }
            }
            else{
                target.setInteractor(source.getInteractor());
            }

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

    public static void copyAndOverrideBasicEntityProperties(Entity source, Entity target, boolean createNewFeature){
        if (source != null && target != null){
            target.setStoichiometry(source.getStoichiometry());

            // copy collections
            target.getCausalRelationships().clear();
            target.getCausalRelationships().addAll(source.getCausalRelationships());

            // special case for participant candidates
            if (source instanceof ParticipantPool && target instanceof ParticipantPool){
                ParticipantPool poolTarget = (ParticipantPool)target;
                ParticipantPool poolSource = (ParticipantPool)source;

                poolTarget.clear();
                for (Object candidate : poolSource){
                    ParticipantCandidate candidateClone = new DefaultParticipantCandidate(((ParticipantCandidate)candidate).getInteractor());
                    ParticipantCandidateCloner.copyAndOverrideBasicCandidateProperties((ParticipantCandidate)candidate, candidateClone, createNewFeature);
                    poolTarget.add(candidateClone);
                }
            }
            else{
                target.setInteractor(source.getInteractor());
            }

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
