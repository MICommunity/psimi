package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ModelledBinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class DefaultModelledBinaryInteraction extends DefaultBinaryInteraction<ModelledParticipant> implements ModelledBinaryInteraction{

    private Collection<InteractionEvidence> interactionEvidences;
    private Source source;
    private Collection<ModelledConfidence> modelledConfidences;
    private Collection<ModelledParameter> modelledParameters;
    private Collection<CooperativeEffect> cooperativeEffects;

    public DefaultModelledBinaryInteraction() {
        super();
    }

    public DefaultModelledBinaryInteraction(String shortName) {
        super(shortName);
    }

    public DefaultModelledBinaryInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public DefaultModelledBinaryInteraction(ModelledParticipant participantA, ModelledParticipant participantB) {
        super(participantA, participantB);
    }

    public DefaultModelledBinaryInteraction(String shortName, ModelledParticipant participantA, ModelledParticipant participantB) {
        super(shortName, participantA, participantB);
    }

    public DefaultModelledBinaryInteraction(String shortName, CvTerm type, ModelledParticipant participantA, ModelledParticipant participantB) {
        super(shortName, type, participantA, participantB);
    }

    public DefaultModelledBinaryInteraction(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public DefaultModelledBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public DefaultModelledBinaryInteraction(ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public DefaultModelledBinaryInteraction(String shortName, ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public DefaultModelledBinaryInteraction(String shortName, CvTerm type, ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    protected void initialiseInteractionEvidences(){
        this.interactionEvidences = new ArrayList<InteractionEvidence>();
    }

    protected void initialiseCooperativeEffects(){
        this.cooperativeEffects = new ArrayList<CooperativeEffect>();
    }

    protected void initialiseCooperativeEffectsWith(Collection<CooperativeEffect> cooperativeEffects){
        if (cooperativeEffects == null){
            this.cooperativeEffects = Collections.EMPTY_LIST;
        }
        else{
            this.cooperativeEffects = cooperativeEffects;
        }
    }

    protected void initialiseModelledConfidences(){
        this.modelledConfidences = new ArrayList<ModelledConfidence>();
    }

    protected void initialiseModelledConfidencesWith(Collection<ModelledConfidence> confidences){
        if (confidences == null){
            this.modelledConfidences = Collections.EMPTY_LIST;
        }
        else {
            this.modelledConfidences = confidences;
        }
    }

    protected void initialiseInteractionEvidencesWith(Collection<InteractionEvidence> evidences){
        if (evidences == null){
            this.interactionEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.interactionEvidences = evidences;
        }
    }

    protected void initialiseModelledParameters(){
        this.modelledParameters = new ArrayList<ModelledParameter>();
    }

    protected void initialiseModelledParametersWith(Collection<ModelledParameter> parameters){
        if (parameters == null){
            this.modelledParameters = Collections.EMPTY_LIST;
        }
        else {
            this.modelledParameters = parameters;
        }
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (interactionEvidences == null){
            initialiseInteractionEvidences();
        }
        return this.interactionEvidences;
    }

    /**
     * The collection of participants for this binary interaction.
     * It cannot be changed.
     * @return
     */
    public Collection<ModelledParticipant> getParticipants() {
        if (getParticipantA() == null && getParticipantB() == null){
            return Collections.EMPTY_LIST;
        }
        else if (getParticipantB() == null){
            return Arrays.asList(getParticipantA());
        }
        else if (getParticipantA() == null){
            return Arrays.asList(getParticipantB());
        }
        else{
            return Arrays.asList(getParticipantA(), getParticipantB());
        }
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * Adds a new ModelledParticipant and set the modelledInteraction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param part
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
    public boolean addModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }
        if (getParticipantB() != null && getParticipantA() != null){
            throw new IllegalArgumentException("A ModelledBinaryInteraction cannot have more than two participants.");
        }
        else if (getParticipantB() != null){
            part.setModelledInteraction(this);
            setParticipantA(part);
            return true;
        }
        else{
            part.setModelledInteraction(this);
            setParticipantA(part);
            return true;
        }
    }

    /**
     * Removes the modelledParticipant from this binary interaction
     * @param part
     * @return
     */
    public boolean removeModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }

        if (getParticipantA() != null && getParticipantA().equals(part)){
            part.setModelledInteraction(null);
            setParticipantA(null);
            return true;
        }
        else if (getParticipantB() != null && getParticipantB().equals(part)){
            part.setModelledInteraction(null);
            setParticipantB(null);
            return true;
        }
        return false;
    }

    /**
     * Adds the participants and set the modelledInteraction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param participants
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
    public boolean addAllModelledParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }
        if (participants.size() > 2){
            throw new IllegalArgumentException("A ModelledBinaryInteraction cannot have more than two participants and we try to add " + participants.size() + " participants");
        }

        boolean added = false;
        for (ModelledParticipant p : participants){
            if (addModelledParticipant(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllModelledParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (ModelledParticipant p : participants){
            if (removeModelledParticipant(p)){
                removed = true;
            }
        }
        return removed;
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        if (modelledConfidences == null){
            initialiseModelledConfidences();
        }
        return this.modelledConfidences;
    }

    public Collection<ModelledParameter> getModelledParameters() {
        if (modelledParameters == null){
            initialiseModelledParameters();
        }
        return this.modelledParameters;
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        if (cooperativeEffects == null){
            initialiseCooperativeEffects();
        }
        return this.cooperativeEffects;
    }
}
