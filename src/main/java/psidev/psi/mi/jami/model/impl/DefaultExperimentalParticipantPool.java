package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation of ExperimentalParticipantPool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/07/14</pre>
 */

public class DefaultExperimentalParticipantPool extends AbstractParticipantPool<InteractionEvidence,FeatureEvidence,ExperimentalParticipantCandidate>
implements ExperimentalParticipantPool{
    private CvTerm experimentalRole;
    private Collection<CvTerm> identificationMethods;
    private Collection<CvTerm> experimentalPreparations;
    private Organism expressedIn;
    private Collection<Confidence> confidences;
    private Collection<Parameter> parameters;

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm participantIdentificationMethod) {
        super(poolName);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(poolName, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(InteractionEvidence interaction, String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public DefaultExperimentalParticipantPool(String poolName, CvTerm participantIdentificationMethod) {
        super(poolName);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipantPool(String poolName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(poolName, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public DefaultExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public DefaultExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public DefaultExperimentalParticipantPool(String poolName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(poolName, bioRole, stoichiometry);
        if(expRole == null){
            this.experimentalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            this.experimentalRole = expRole;
        }
        this.expressedIn = expressedIn;
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public DefaultExperimentalParticipantPool(String poolName) {
        super(poolName);
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultExperimentalParticipantPool(String poolName, Stoichiometry stoichiometry) {
        super(poolName, stoichiometry);
        this.experimentalRole = CvTermUtils.createUnspecifiedRole();
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

    protected void initialiseIdentificationMethods(){
        this.identificationMethods = new ArrayList<CvTerm>();
    }

    protected void initialiseIdentificationMethodsWith(Collection<CvTerm> methods){
        if (methods == null){
            this.identificationMethods = Collections.EMPTY_LIST;
        }
        else {
            this.identificationMethods = methods;
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

    public Collection<CvTerm> getIdentificationMethods() {
        if (identificationMethods == null){
            initialiseIdentificationMethods();
        }
        return this.identificationMethods;
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

    @Override
    public String toString() {
        return super.toString() + (getExperimentalRole() != null ? ", "
                + getExperimentalRole().toString() : "")
                + (getExpressedInOrganism() != null ? ", " + getExpressedInOrganism().toString() : "");
    }
}
