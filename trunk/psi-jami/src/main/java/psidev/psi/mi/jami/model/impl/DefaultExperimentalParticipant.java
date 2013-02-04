package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactExperimentalParticipantComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation for Participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultExperimentalParticipant extends DefaultParticipant<Interaction, Interactor, ExperimentalFeature> implements ExperimentalParticipant {

    private CvTerm experimentalRole;
    private Set<CvTerm> identificationMethods;
    private Set<CvTerm> experimentalPreparations;
    private Organism expressedIn;
    private Set<Confidence> confidences;
    private Set<Parameter> parameters;

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor) {
        super(interaction, interactor);
        initializeCollections();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole) {
        super(interaction, interactor, bioRole);
        initializeCollections();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, Integer stoichiometry) {
        super(interaction, interactor, stoichiometry);
        initializeCollections();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeCollections();
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole) {
        super(interaction, interactor, bioRole);
        initializeCollections();
        if(expRole == null){
           this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Organism expressedIn) {
        super(interaction, interactor, bioRole);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
    }

    public DefaultExperimentalParticipant(ExperimentalInteraction interaction, Interactor interactor, CvTerm bioRole, CvTerm expRole, Integer stoichiometry, Organism expressedIn) {
        super(interaction, interactor, bioRole, stoichiometry);
        initializeCollections();
        if(expRole == null){
            this.experimentalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
    }

    private void initializeCollections() {
        this.identificationMethods = new HashSet<CvTerm>();
        this.experimentalPreparations = new HashSet<CvTerm>();
        this.confidences = new HashSet<Confidence>();
        this.parameters = new HashSet<Parameter>();
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

    public Set<CvTerm> getIdentificationMethods() {
        return this.identificationMethods;
    }

    public Set<CvTerm> getExperimentalPreparations() {
        return this.experimentalPreparations;
    }

    public Organism getExpressedInOrganism() {
        return this.expressedIn;
    }

    public void setExpressedInOrganism(Organism organism) {
        this.expressedIn = organism;
    }

    public Set<Confidence> getConfidences() {
        return this.confidences;
    }

    public Set<Parameter> getParameters() {
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
