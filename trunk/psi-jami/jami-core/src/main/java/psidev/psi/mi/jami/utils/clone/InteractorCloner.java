package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;

/**
 * Cloner for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class InteractorCloner {

    /***
     * This method will copy properties of interactor source in interactor target and will override all the other properties of Target interactor.
     * @param source
     * @param target
     */
    public static void copyAndOverrideBasicInteractorProperties(Interactor source, Interactor target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());
            target.setInteractorType(source.getInteractorType());
            target.setOrganism(source.getOrganism());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
        }
    }

    /***
     * This method will copy properties of complex source in complex target and will override all the other properties of Target complex.
     * This method does not copy the
     * @param source
     * @param target
     * @param createNewParticipant If true, this method will clone each participant from source instead of reusing the participant instances from source.
     *                         It will then set the interactionEvidence of the cloned participants to target
     * @param ignoreParticipants If true, this method will clone the interaction properties and ignore the participants of the source
     */
    public static void copyAndOverrideComplexProperties(Complex source, Complex target, boolean createNewParticipant, boolean ignoreParticipants){
        if (source != null && target != null){
            copyAndOverrideBasicInteractorProperties(source, target);
            target.setSource(source.getSource());
            target.setCreatedDate(source.getCreatedDate());
            target.setUpdatedDate(source.getUpdatedDate());
            target.setInteractionType(source.getInteractionType());

            // copy collections
            target.getInteractionEvidences().clear();
            target.getInteractionEvidences().addAll(source.getInteractionEvidences());
            target.getModelledParameters().clear();
            target.getModelledParameters().addAll(source.getModelledParameters());
            target.getModelledConfidences().clear();
            target.getModelledConfidences().addAll(source.getModelledConfidences());
            target.getCooperativeEffects().clear();
            target.getCooperativeEffects().addAll(source.getCooperativeEffects());

            // copy or create participants if not ignored
            if (!ignoreParticipants){
                target.getParticipants().clear();

                if (!createNewParticipant){
                    target.getParticipants().addAll(source.getParticipants());
                }
                else{
                    for (ModelledParticipant p : source.getParticipants()){
                        ModelledParticipant clone = new DefaultModelledParticipant(p.getInteractor());
                        ParticipantCloner.copyAndOverrideModelledParticipantProperties(p, clone, true);
                        target.addModelledParticipant(clone);
                    }
                }
            }
        }
    }

    /**
     * This method will copy basic properties from the Interaction source in the complex target.
     * It will erase existing annotations, xrefs, checksums and identifiers
     * This method does not copy the participants of the source
     * @param source
     * @param target
     */
    public static void copyAndOverrideBasicComplexPropertiesWithInteractionProperties(Interaction source, Complex target){
        if (source != null && target != null){
            target.setInteractionType(source.getInteractionType());
            target.setCreatedDate(source.getCreatedDate());
            target.setUpdatedDate(source.getUpdatedDate());
            if (source.getShortName() != null){
                target.setShortName(source.getShortName());
            }

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
        }
    }

    /**
     * This method will copy basic properties from the Interaction source in the complex target.
     * This method does not copy the participants of the source
     * @param source
     * @param target
     */
    public static void copyAndOverrideBasicComplexPropertiesWithModelledInteractionProperties(ModelledInteraction source, Complex target){
        if (source != null && target != null){
            target.setInteractionType(source.getInteractionType());
            target.setCreatedDate(source.getCreatedDate());
            target.setUpdatedDate(source.getUpdatedDate());
            if (source.getShortName() != null){
                target.setShortName(source.getShortName());
            }

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getInteractionEvidences().clear();
            target.getInteractionEvidences().addAll(source.getInteractionEvidences());
            target.getModelledParameters().clear();
            target.getModelledParameters().addAll(source.getModelledParameters());
            target.getModelledConfidences().clear();
            target.getModelledConfidences().addAll(source.getModelledConfidences());
            target.getCooperativeEffects().clear();
            target.getCooperativeEffects().addAll(source.getCooperativeEffects());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
        }
    }
}
