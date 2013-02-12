package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactExperimentalParticipantComparator;
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

public class DefaultExperimentalParticipant extends DefaultParticipant<ExperimentalInteraction, Interactor, ExperimentalFeature> implements ExperimentalParticipant {

    private CvTerm experimentalRole;
    private CvTerm identificationMethod;
    private Collection<CvTerm> experimentalPreparations;
    private Organism expressedIn;
    private Collection<Confidence> confidences;
    private Collection<Parameter> parameters;

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interaction, interactor);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, stoichiometry);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole);
        initializeCollections();
        if(expRole == null){
           this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultExperimentalParticipant(Interactor interactor, CvTerm participantIdentificationMethod) {
        super(interactor);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(Interactor interactor, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(Interactor interactor, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, stoichiometry);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(Interactor interactor, CvTerm bioRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        initializeCollections();
        this.identificationMethod = participantIdentificationMethod;
        this.experimentalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipant(Interactor interactor, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultExperimentalParticipant(Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultExperimentalParticipant(Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    public DefaultExperimentalParticipant(Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(interactor, bioRole, stoichiometry);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        this.identificationMethod = participantIdentificationMethod;
    }

    private void initializeCollections() {
        this.experimentalPreparations = new ArrayList<CvTerm>();
        this.confidences = new ArrayList<Confidence>();
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

    @Override
    public String toString() {
        return super.toString() + (experimentalRole != null ? ", " + experimentalRole.toString() : "") + (expressedIn != null ? ", " + expressedIn.toString() : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ExperimentalParticipant)){
            return false;
        }

        // use UnambiguousExactExperimentalParticipant comparator for equals
        return UnambiguousExactExperimentalParticipantComparator.areEquals(this, (ExperimentalParticipant) o);
    }
}
