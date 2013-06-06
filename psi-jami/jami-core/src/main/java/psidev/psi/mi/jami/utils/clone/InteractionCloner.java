package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class to clone and copy interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class InteractionCloner {

    /***
     * This method will copy properties of interaction source in interaction target and will override all the other properties of Target interaction.
     * This method will set the experiment of this interaction evidence but it will not add this interaction to the list of interactionEvidences
     * This method will add all the participant evidences of the source but will not set their interactionEvidence to the target
     * @param source
     * @param target
     * @param createNewParticipant If true, this method will clone each participant from source instead of reusing the participant instances from source.
     *                         It will then set the interactionEvidence of the cloned participants to target
     * @param ignoreParticipants If true, this method will clone the interaction properties and ignore the participants of the source
     */
    public static void copyAndOverrideInteractionEvidenceProperties(InteractionEvidence source, InteractionEvidence target, boolean createNewParticipant, boolean ignoreParticipants){
        if (source != null && target != null){
            target.setAvailability(source.getAvailability());
            target.setExperiment(source.getExperiment());
            target.setInferred(source.isInferred());
            target.setCreatedDate(source.getCreatedDate());
            target.setNegative(source.isNegative());
            target.setShortName(source.getShortName());
            target.setInteractionType(source.getInteractionType());
            target.setUpdatedDate(source.getUpdatedDate());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
            target.getConfidences().clear();
            target.getConfidences().addAll(source.getConfidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getParameters().clear();
            target.getParameters().addAll(source.getParameters());
            target.getVariableParameterValues().clear();
            target.getVariableParameterValues().addAll(source.getVariableParameterValues());

            // copy or create participants if not ignored
            if (!ignoreParticipants){
                target.getParticipants().clear();

                if (!createNewParticipant){
                    target.getParticipants().addAll(source.getParticipants());
                }
                else{
                    for (ParticipantEvidence p : source.getParticipants()){
                        ParticipantEvidence clone = new DefaultParticipantEvidence(p.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(p, clone, true);
                        target.addParticipantEvidence(clone);
                    }
                }
            }
        }
    }

    /***
     * This method will copy properties of interaction source in interaction target and will override all the other properties of Target interaction.
     * This method will add all the participant of the source but will not set their modelledInteraction to the target
     * @param source
     * @param target
     * @param createNewParticipant If true, this method will clone each participant from source instead of reusing the participant instances from source.
     *                         It will then set the modelledInteraction of the cloned participants to target
     * @param ignoreParticipants If true, this method will clone the interaction properties and ignore the participants of the source
     */
    public static void copyAndOverrideModelledInteractionProperties(ModelledInteraction source, ModelledInteraction target, boolean createNewParticipant, boolean ignoreParticipants){
        if (source != null && target != null){
            target.setSource(source.getSource());
            target.setCreatedDate(source.getCreatedDate());
            target.setShortName(source.getShortName());
            target.setInteractionType(source.getInteractionType());
            target.setUpdatedDate(source.getUpdatedDate());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
            target.getModelledConfidences().clear();
            target.getModelledConfidences().addAll(source.getModelledConfidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getModelledParameters().clear();
            target.getModelledParameters().addAll(source.getModelledParameters());
            target.getInteractionEvidences().clear();
            target.getInteractionEvidences().addAll(source.getInteractionEvidences());
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

    /***
     * This method will copy basic properties of interaction source in interaction target and will override all the other properties of Target interaction.
     * @param source
     * @param target
     * @param createNewParticipant If true, this method will clone each participant from source instead of reusing the participant instances from source.
     * @param ignoreParticipants If true, this method will clone the interaction properties and ignore the participants of the source
     */
    public static void copyAndOverrideBasicInteractionProperties(Interaction source, Interaction target, boolean createNewParticipant, boolean ignoreParticipants){
        if (source != null && target != null){
            target.setCreatedDate(source.getCreatedDate());
            target.setShortName(source.getShortName());
            target.setInteractionType(source.getInteractionType());
            target.setUpdatedDate(source.getUpdatedDate());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());

            // copy or create participants if not ignored
            if (!ignoreParticipants){
                target.getParticipants().clear();
                if (!createNewParticipant){
                    ((Collection<Participant>)target.getParticipants()).addAll(source.getParticipants());
                }
                else{
                    for (Participant p : source.getParticipants()){
                        Participant clone = new DefaultParticipant(p.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantProperties(p, clone, true);
                        ((Collection<Participant>)target.getParticipants()).add(clone);
                    }
                }
            }
        }
    }

    /***
     * This method will copy participants of interaction evidence source in binary target.
     * @param source
     * @param target
     * @param createNewParticipant If true, this method will clone each participant from source instead of reusing the participant instances from source.
     *                         It will then set the interactionEvidence of the cloned participants to target
     * @param self If true, it will only look at the first participant and duplicate this participant with stoichiometry 0
     * @throws IllegalArgumentException if the number of participants in source is superior to 2 or superior to 1 with self = true
     */
    public static void copyAndOverrideParticipantsEvidencesToBinary(InteractionEvidence source, BinaryInteractionEvidence target, boolean createNewParticipant, boolean self){
        if (source != null && target != null){
            if (source.getParticipants().size() > 2){
                 throw new IllegalArgumentException("We cannot copy the participants from the source because it contains more than 2 participants : " +source.getParticipants().size());
            }
            else if (source.getParticipants().size() != 1 && self){
                throw new IllegalArgumentException("We cannot copy the participants from the source because it is a self interaction containing more than one participant");
            }

            if (source.getParticipants().isEmpty()){
                target.setParticipantA(null);
                target.setParticipantB(null);
            }
            else {
                Iterator<ParticipantEvidence> iterator = source.getParticipants().iterator();

                if (!createNewParticipant){
                    ParticipantEvidence first = iterator.next();
                    target.setParticipantA(first);
                    if (self){
                        ParticipantEvidence clone = new DefaultParticipantEvidence(first.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(first, clone, true);
                        clone.setInteractionEvidence(target);
                        // second self interactor has stoichiometry 0 if necessary
                        if (first.getStoichiometry() != null){
                            clone.setStoichiometry(0);
                        }
                        target.setParticipantB(clone);
                    }
                    else if (iterator.hasNext()){
                        target.setParticipantB(iterator.next());
                    }
                    else {
                        target.setParticipantB(null);
                    }
                }
                else {
                    ParticipantEvidence first = iterator.next();
                    ParticipantEvidence clone = new DefaultParticipantEvidence(first.getInteractor());
                    ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(first, clone, true);
                    target.setParticipantA(clone);
                    clone.setInteractionEvidence(target);

                    if (self){
                        ParticipantEvidence clone2 = new DefaultParticipantEvidence(first.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(first, clone2, true);
                        clone2.setInteractionEvidence(target);
                        // second self interactor has stoichiometry 0 if necessary
                        if (first.getStoichiometry() != null){
                            clone2.setStoichiometry(0);
                        }
                        target.setParticipantB(clone2);
                    }
                    else if (iterator.hasNext()){
                        ParticipantEvidence second = iterator.next();
                        ParticipantEvidence clone2 = new DefaultParticipantEvidence(second.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(second, clone2, true);
                        target.setParticipantB(clone2);
                        clone2.setInteractionEvidence(target);
                    }
                    else {
                        target.setParticipantB(null);
                    }
                }
            }
        }
    }

    /***
     * This method will copy participants of modelled interaction source in binary target.
     * @param source
     * @param target
     * @param createNewParticipant If true, this method will clone each participant from source instead of reusing the participant instances from source.
     *                         It will then set the modelledInteraction of the cloned participants to target
     * @param self if true, we only take the first participant and duplicate it in the Binary interaction. We then set the stoichiometry to 0 for the second interactor
     * @throws IllegalArgumentException if the source has more than two participants or more than one participant when self is true
     */
    public static void copyAndOverrideModelledParticipantsToBinary(ModelledInteraction source, ModelledBinaryInteraction target, boolean createNewParticipant, boolean self){
        if (source != null && target != null){
            if (source.getParticipants().size() > 2){
                throw new IllegalArgumentException("We cannot copy the participants from the source because it contains more than 2 participants : " +source.getParticipants().size());
            }
            else if (source.getParticipants().size() != 1 && self){
                throw new IllegalArgumentException("We cannot copy the participants from the source because it is a self interaction containing more than one participant");
            }

            if (source.getParticipants().isEmpty()){
                target.setParticipantA(null);
                target.setParticipantB(null);
            }
            else {
                Iterator<ModelledParticipant> iterator = source.getParticipants().iterator();

                if (!createNewParticipant){
                    ModelledParticipant first = iterator.next();
                    target.setParticipantA(first);

                    if (self){
                        ModelledParticipant clone = new DefaultModelledParticipant(first.getInteractor());
                        ParticipantCloner.copyAndOverrideModelledParticipantProperties(first, clone, true);
                        clone.setModelledInteraction(target);
                        // second self interactor has stoichiometry 0 if necessary
                        if (first.getStoichiometry() != null){
                            clone.setStoichiometry(0);
                        }
                        target.setParticipantB(clone);
                    }
                    else if (iterator.hasNext()){
                        target.setParticipantB(iterator.next());
                    }
                    else {
                        target.setParticipantB(null);
                    }
                }
                else {
                    ModelledParticipant first = iterator.next();
                    ModelledParticipant clone = new DefaultModelledParticipant(first.getInteractor());
                    ParticipantCloner.copyAndOverrideModelledParticipantProperties(first, clone, true);
                    target.setParticipantA(clone);
                    clone.setModelledInteraction(target);

                    if (self){
                        ModelledParticipant clone2 = new DefaultModelledParticipant(first.getInteractor());
                        ParticipantCloner.copyAndOverrideModelledParticipantProperties(first, clone2, true);
                        clone2.setModelledInteraction(target);
                        // second self interactor has stoichiometry 0 if necessary
                        if (first.getStoichiometry() != null){
                            clone2.setStoichiometry(0);
                        }
                        target.setParticipantB(clone2);
                    }
                    else if (iterator.hasNext()){
                        ModelledParticipant second = iterator.next();
                        ModelledParticipant clone2 = new DefaultModelledParticipant(second.getInteractor());
                        ParticipantCloner.copyAndOverrideModelledParticipantProperties(second, clone2, true);
                        target.setParticipantB(clone2);
                        clone2.setModelledInteraction(target);
                    }
                    else {
                        target.setParticipantB(null);
                    }
                }
            }
        }
    }

    /***
     * This method will copy participants of interaction source in binary target.
     * @param source
     * @param target
     * @param createNewParticipant If true, this method will clone each participant from source instead of reusing the participant instances from source.
     * @param self if true, we only take the first participant and duplicate it in the Binary interaction. We then set the stoichiometry to 0 for the second interactor
     * @throws IllegalArgumentException if the source has more than two participants or more than one participant when self is true
     */
    public static void copyAndOverrideBasicParticipantsToBinary(Interaction source, BinaryInteraction target, boolean createNewParticipant, boolean self){
        if (source != null && target != null){
            if (source.getParticipants().size() > 2){
                throw new IllegalArgumentException("We cannot copy the participants from the source because it contains more than 2 participants : " +source.getParticipants().size());
            }
            else if (source.getParticipants().size() != 1 && self){
                throw new IllegalArgumentException("We cannot copy the participants from the source because it is a self interaction containing more than one participant");
            }

            if (source.getParticipants().isEmpty()){
                target.setParticipantA(null);
                target.setParticipantB(null);
            }
            else {
                Iterator<? extends Participant> iterator = source.getParticipants().iterator();

                if (!createNewParticipant){
                    Participant first = iterator.next();
                    target.setParticipantA(first);
                    if (self){
                        Participant clone = new DefaultParticipant(first.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantProperties(first, clone, true);
                        // second self interactor has stoichiometry 0 if necessary
                        if (first.getStoichiometry() != null){
                            clone.setStoichiometry(0);
                        }
                        target.setParticipantB(clone);
                    }
                    else if (iterator.hasNext()){
                        target.setParticipantB(iterator.next());
                    }
                    else {
                        target.setParticipantB(null);
                    }
                }
                else {
                    Participant first = iterator.next();
                    Participant clone = new DefaultParticipant(first.getInteractor());
                    ParticipantCloner.copyAndOverrideParticipantProperties(first, clone, true);
                    target.setParticipantA(clone);

                    if (self){
                        Participant clone2 = new DefaultParticipant(first.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantProperties(first, clone2, true);
                        // second self interactor has stoichiometry 0 if necessary
                        if (first.getStoichiometry() != null){
                            clone2.setStoichiometry(0);
                        }
                        target.setParticipantB(clone2);
                    }
                    else if (iterator.hasNext()){
                        Participant second = iterator.next();
                        Participant clone2 = new DefaultParticipant(second.getInteractor());
                        ParticipantCloner.copyAndOverrideParticipantProperties(second, clone2, true);
                        target.setParticipantB(clone2);
                    }
                    else {
                        target.setParticipantB(null);
                    }
                }
            }
        }
    }
}
