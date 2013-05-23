package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantEvidenceComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultParticipantEvidence extends DefaultParticipant<Interactor> implements ParticipantEvidence {

    private CvTerm experimentalRole;
    private CvTerm identificationMethod;
    private Collection<CvTerm> experimentalPreparations;
    private Organism expressedIn;
    private Collection<Confidence> confidences;
    private Collection<Parameter> parameters;
    private InteractionEvidence interactionEvidence;
    private Collection<FeatureEvidence> featureEvidences;

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interactor);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        if(expRole == null){
           this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interactor);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    protected void initialiseExperimentalPreparations() {
        this.experimentalPreparations = new ArrayList<CvTerm>();
    }

    protected void initialiseConfidences() {
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseParameters() {
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseFeatureEvidences(){
        this.featureEvidences = new ArrayList<FeatureEvidence>();
    }

    protected void initialiseFeatureEvidencesWith(Collection<FeatureEvidence> features){
        if (features == null){
            this.featureEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.featureEvidences = features;
        }
    }

    protected void initialiseExperimentalPreparationsWith(Collection<CvTerm> expPreparations) {
        if (expPreparations == null){
            this.experimentalPreparations = Collections.EMPTY_LIST;
        }
        else {
            this.experimentalPreparations = expPreparations;
        }
    }

    protected void initialiseConfidencesWith(Collection<Confidence> confidences) {
        if (confidences == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else {
            this.confidences = confidences;
        }
    }

    protected void initialiseParametersWith(Collection<Parameter> parameters) {
        if (parameters == null){
            this.parameters = Collections.EMPTY_LIST;
        }
        else {
            this.parameters = parameters;
        }
    }

    public CvTerm getExperimentalRole() {
        return this.experimentalRole;
    }

    public void setExperimentalRole(CvTerm expRole) {
        if (expRole == null){
           this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
    }

    public CvTerm getIdentificationMethod() {
        return this.identificationMethod;
    }

    public void setIdentificationMethod(CvTerm identificationMethod) {
        this.identificationMethod = identificationMethod;
    }

    public Collection<CvTerm> getExperimentalPreparations() {
        if (experimentalPreparations == null){
            initialiseExperimentalPreparations();
        }
        return this.experimentalPreparations;
    }

    public Organism getExpressedInOrganism() {
        return this.expressedIn;
    }

    public void setExpressedInOrganism(Organism organism) {
        this.expressedIn = organism;
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseConfidences();
        }
        return this.confidences;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseParameters();
        }
        return this.parameters;
    }

    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction) {
        if (interaction != null){
            interaction.addParticipantEvidence(this);
        }
        else {
            this.interactionEvidence = null;
        }
    }

    public InteractionEvidence getInteractionEvidence() {
        return this.interactionEvidence;
    }

    public void setInteractionEvidence(InteractionEvidence interaction) {
        this.interactionEvidence = interaction;
    }

    public Collection<? extends FeatureEvidence> getFeatureEvidences() {
        if (featureEvidences == null){
            initialiseFeatureEvidences();
        }
        return featureEvidences;
    }

    public boolean addFeatureEvidence(FeatureEvidence feature) {

        if (feature == null){
            return false;
        }

        if (featureEvidences.add(feature)){
            feature.setParticipantEvidence(this);
            return true;
        }
        return false;
    }

    public boolean removeFeatureEvidence(FeatureEvidence feature) {

        if (feature == null){
            return false;
        }
        if (featureEvidences == null){
            initialiseFeatureEvidences();
        }
        if (featureEvidences.remove(feature)){
            feature.setParticipantEvidence(null);
            return true;
        }
        return false;
    }

    public boolean addAllFeatureEvidences(Collection<? extends FeatureEvidence> features) {
        if (features == null){
            return false;
        }
        if (featureEvidences == null){
            initialiseFeatureEvidences();
        }
        boolean added = false;
        for (FeatureEvidence feature : features){
            if (addFeatureEvidence(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllFeatureEvidences(Collection<? extends FeatureEvidence> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (FeatureEvidence feature : features){
            if (removeFeatureEvidence(feature)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public String toString() {
        return super.toString() + (experimentalRole != null ? ", " + experimentalRole.toString() : "") + (expressedIn != null ? ", " + expressedIn.toString() : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ParticipantEvidence)){
            return false;
        }

        // use UnambiguousExactExperimentalParticipant comparator for equals
        return UnambiguousExactParticipantEvidenceComparator.areEquals(this, (ParticipantEvidence) o);
    }
}
