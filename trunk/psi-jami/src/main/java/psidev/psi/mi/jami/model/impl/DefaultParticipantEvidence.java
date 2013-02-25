package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantEvidenceComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for Participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultParticipantEvidence extends DefaultParticipant<InteractionEvidence, Interactor, FeatureEvidence> implements ParticipantEvidence {

    protected CvTerm experimentalRole;
    protected CvTerm identificationMethod;
    protected Collection<CvTerm> experimentalPreparations;
    protected Organism expressedIn;
    protected Collection<Confidence> confidences;
    protected Collection<Parameter> parameters;

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interaction, interactor);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, stoichiometry);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        if(expRole == null){
           this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(InteractionEvidence interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interactor);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipantEvidence(Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
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
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
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
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
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
        initializeExperimentalPreparations();
        initializeConfidences();
        initializeParameters();
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
        return this.experimentalPreparations;
    }

    public Organism getExpressedInOrganism() {
        return this.expressedIn;
    }

    public void setExpressedInOrganism(Organism organism) {
        this.expressedIn = organism;
    }

    public Collection<Confidence> getConfidences() {
        return this.confidences;
    }

    public Collection<Parameter> getParameters() {
        return this.parameters;
    }

    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction) {
        this.interaction = interaction;

        if (interaction != null){
            interaction.addParticipant(this);
        }
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
