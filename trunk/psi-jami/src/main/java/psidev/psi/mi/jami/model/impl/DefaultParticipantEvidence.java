package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantEvidenceComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

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

public class DefaultParticipantEvidence extends DefaultParticipant<Interactor, FeatureEvidence> implements ParticipantEvidence {

    private CvTerm experimentalRole;
    private CvTerm identificationMethod;
    private Collection<CvTerm> experimentalPreparations;
    private Organism expressedIn;
    private Collection<Confidence> confidences;
    private Collection<Parameter> parameters;
    private InteractionEvidence interactionEvidence;

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interactor);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        if(expRole == null){
           this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
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
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
        this.interactionEvidence = interaction;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
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
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    protected void initializeExperimentalPreparations() {
        this.experimentalPreparations = new ArrayList<CvTerm>();
    }

    protected void initializeConfidences() {
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initializeParameters() {
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initializeExperimentalPreparationsWith(Collection<CvTerm> expPreparations) {
        if (expPreparations == null){
            this.experimentalPreparations = Collections.EMPTY_LIST;
        }
        else {
            this.experimentalPreparations = expPreparations;
        }
    }

    protected void initializeConfidencesWith(Collection<Confidence> confidences) {
        if (confidences == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else {
            this.confidences = confidences;
        }
    }

    protected void initializeParametersWith(Collection<Parameter> parameters) {
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
           this.experimentalRole = CvTermFactory.createUnspecifiedRole();
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
            initializeExperimentalPreparations();
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
            initializeConfidences();
        }
        return this.confidences;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initializeParameters();
        }
        return this.parameters;
    }

    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction) {
        if (interaction != null){
            interaction.addParticipant(this);
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

    public boolean addFeatureEvidence(FeatureEvidence feature) {
        return addFeature(feature);
    }

    public boolean removeFeatureEvidence(FeatureEvidence feature) {
        return removeFeature(feature);
    }

    @Override
    public boolean addFeature(FeatureEvidence feature) {

        if (super.addFeature(feature)){
            feature.setParticipantEvidence(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFeature(FeatureEvidence feature) {

        if (super.removeFeature(feature)){
            feature.setParticipantEvidence(null);
            return true;
        }
        return false;
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
