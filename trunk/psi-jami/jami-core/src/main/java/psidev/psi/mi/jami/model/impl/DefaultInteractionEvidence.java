package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionEvidenceComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultInteractionEvidence extends DefaultInteraction implements InteractionEvidence {

    private Xref imexId;
    private Experiment experiment;
    private String availability;
    private Collection<Parameter> experimentalParameters;
    private boolean isInferred = false;
    private Collection<ParticipantEvidence> participantEvidences;
    private Collection<Confidence> experimentalConfidences;
    private boolean isNegative;

    private Collection<VariableParameterValueSet> variableParameterValueSets;

    public DefaultInteractionEvidence(Experiment experiment) {
        super();

        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName) {
        super(shortName);
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Source source) {
        super(shortName, source);
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, CvTerm type) {
        super(shortName, type);
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, Xref imexId) {
        super();
        this.experiment = experiment;
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Xref imexId) {
        super(shortName);
        this.experiment = experiment;
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Source source, Xref imexId) {
        super(shortName, source);
        this.experiment = experiment;
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, CvTerm type, Xref imexId) {
        super(shortName, type);
        this.experiment = experiment;
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence(Xref imexId) {
        super();
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, Xref imexId) {
        super(shortName);
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, Source source, Xref imexId) {
        super(shortName, source);
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, CvTerm type, Xref imexId) {
        super(shortName, type);
        getXrefs().add(imexId);
    }

    public DefaultInteractionEvidence() {
        super();
    }

    public DefaultInteractionEvidence(String shortName) {
        super(shortName);
    }

    public DefaultInteractionEvidence(String shortName, Source source) {
        super(shortName, source);
    }

    public DefaultInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    protected void initialiseExperimentalConfidences(){
        this.experimentalConfidences = new ArrayList<Confidence>();
    }

    protected void initialiseExperimentalConfidencesWith(Collection<Confidence> confidences){
        if (confidences == null){
            this.experimentalConfidences = Collections.EMPTY_LIST;
        }
        else {
            this.experimentalConfidences = confidences;
        }
    }

    protected void initialiseVariableParameterValueSets(){
        this.variableParameterValueSets = new ArrayList<VariableParameterValueSet>();
    }

    protected void initialiseVariableParameterValueSetsWith(Collection<VariableParameterValueSet> variableValues){
        if (variableValues == null){
            this.variableParameterValueSets = Collections.EMPTY_LIST;
        }
        else {
            this.variableParameterValueSets = variableValues;
        }
    }

    protected void initialiseParticipantEvidences(){
        this.participantEvidences = new ArrayList<ParticipantEvidence>();
    }

    protected void initialiseExperimentalParameters(){
        this.experimentalParameters = new ArrayList<Parameter>();
    }

    protected void initialiseExperimentalParametersWith(Collection<Parameter> parameters){
        if (parameters == null){
            this.experimentalParameters = Collections.EMPTY_LIST;
        }
        else {
            this.experimentalParameters = parameters;
        }
    }

    protected void initialiseParticipantEvidencesWith(Collection<ParticipantEvidence> participants){
        if (participants == null){
            this.participantEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.participantEvidences = participants;
        }
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new ExperimentalInteractionXrefList());
    }

    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        // add new imex if not null
        if (identifier != null){
            ExperimentalInteractionXrefList interactionXrefs = (ExperimentalInteractionXrefList) getXrefs();
            CvTerm imexDatabase = CvTermUtils.createImexDatabase();
            CvTerm imexPrimaryQualifier = CvTermUtils.createImexPrimaryQualifier();
            // first remove old doi if not null
            if (this.imexId != null){
                interactionXrefs.removeOnly(this.imexId);
            }
            this.imexId = new DefaultXref(imexDatabase, identifier, imexPrimaryQualifier);
            interactionXrefs.addOnly(this.imexId);
        }
        else {
            throw new IllegalArgumentException("The imex id has to be non null.");
        }
    }

    public Experiment getExperiment() {
        return this.experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        this.experiment = experiment;

        if (experiment != null){
            this.experiment.getInteractionEvidences().add(this);
        }
    }

    public Collection<VariableParameterValueSet> getVariableParameterValues() {

        if (variableParameterValueSets == null){
            initialiseVariableParameterValueSets();
        }
        return this.variableParameterValueSets;
    }

    public Collection<Confidence> getExperimentalConfidences() {
        if (experimentalConfidences == null){
            initialiseExperimentalConfidences();
        }
        return this.experimentalConfidences;
    }

    public String getAvailability() {
        return this.availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public boolean isNegative() {
        return this.isNegative;
    }

    public void setNegative(boolean negative) {
        this.isNegative = negative;
    }

    public Collection<Parameter> getExperimentalParameters() {
        if (experimentalParameters == null){
            initialiseExperimentalParameters();
        }
        return this.experimentalParameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
    }

    public Collection<? extends ParticipantEvidence> getParticipantEvidences() {
        if (participantEvidences == null){
            initialiseParticipantEvidences();
        }
        return this.participantEvidences;
    }

    public boolean addParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (participantEvidences == null){
            initialiseParticipantEvidences();
        }
        if (participantEvidences.add(part)){
            part.setInteractionEvidence(this);
            return true;
        }
        return false;
    }

    public boolean removeParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (participantEvidences == null){
            initialiseParticipantEvidences();
        }
        if (participantEvidences.remove(part)){
            part.setInteractionEvidence(null);
            return true;
        }
        return false;
    }

    public boolean addAllParticipantEvidences(Collection<? extends ParticipantEvidence> participants) {
        if (participants == null){
            return false;
        }

        boolean added = false;
        for (ParticipantEvidence p : participants){
            if (addParticipantEvidence(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllParticipantEvidences(Collection<? extends ParticipantEvidence> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (ParticipantEvidence p : participants){
            if (removeParticipantEvidence(p)){
                removed = true;
            }
        }
        return removed;
    }

    protected void processAddedXrefEvent(Xref added) {

        // the added identifier is imex and the current imex is not set
        if (imexId == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
            // the added xref is imex-primary
            if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                imexId = added;
            }
        }
    }

    protected void processRemovedXrefEvent(Xref removed) {
        // the removed identifier is pubmed
        if (imexId != null && imexId.equals(removed)){
            imexId = null;
        }
    }

    protected void clearPropertiesLinkedToXrefs() {
        imexId = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof InteractionEvidence)){
            return false;
        }

        // use UnambiguousExactExperimentalInteraction comparator for equals
        return UnambiguousExactInteractionEvidenceComparator.areEquals(this, (InteractionEvidence) o);
    }

    @Override
    public String toString() {
        return imexId != null ? imexId.getId() : super.toString();
    }

    /**
     * Experimental interaction Xref list
     */
    private class ExperimentalInteractionXrefList extends AbstractListHavingPoperties<Xref> {
        public ExperimentalInteractionXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

            processAddedXrefEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedXrefEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToXrefs();
        }
    }
}
