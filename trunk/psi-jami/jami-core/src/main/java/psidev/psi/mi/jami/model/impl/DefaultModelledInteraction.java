package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactModelledInteractionComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implemntation for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultModelledInteraction extends DefaultInteraction implements ModelledInteraction{

    private Collection<InteractionEvidence> interactionEvidences;
    private Source source;
    private Collection<ModelledParticipant> modelledParticipants;
    private Collection<ModelledConfidence> modelledConfidences;
    private Collection<ModelledParameter> modelledParameters;
    private Collection<CooperativeEffect> cooperativeEffects;

    public DefaultModelledInteraction() {
        super();
    }

    public DefaultModelledInteraction(String shortName) {
        super(shortName);
    }

    public DefaultModelledInteraction(String shortName, Source source) {
        super(shortName, source);
    }

    public DefaultModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public DefaultModelledInteraction(String shortName, Source source, CvTerm type) {
        this(shortName, type);
        this.source = source;
    }

    protected void initialiseModelledParticipants(){
        this.modelledParticipants = new ArrayList<ModelledParticipant>();
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

    protected void initialiseModelledParticipantsWith(Collection<ModelledParticipant> participants){
        if (participants == null){
            this.modelledParticipants = Collections.EMPTY_LIST;
        }
        else {
            this.modelledParticipants = participants;
        }
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (interactionEvidences == null){
            initialiseInteractionEvidences();
        }
        return this.interactionEvidences;
    }

    public Collection<? extends ModelledParticipant> getModelledParticipants() {
        if (modelledParticipants == null){
            initialiseModelledParticipants();
        }
        return this.modelledParticipants;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean addModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }
        if (modelledParticipants == null){
            initialiseModelledParticipants();
        }
        if (modelledParticipants.add(part)){
            part.setModelledInteraction(this);
            return true;
        }
        return false;
    }

    public boolean removeModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }
        if (modelledParticipants == null){
            initialiseModelledParticipants();
        }
        if (modelledParticipants.remove(part)){
            part.setModelledInteraction(null);
            return true;
        }
        return false;
    }

    public boolean addAllModelledParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
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

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ModelledInteraction)){
            return false;
        }

        // use UnambiguousExactModelledInteraction comparator for equals
        return UnambiguousExactModelledInteractionComparator.areEquals(this, (ModelledInteraction) o);
    }
}
