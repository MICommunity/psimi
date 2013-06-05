package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for BinaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class DefaultBinaryInteractionEvidence extends DefaultBinaryInteraction<ParticipantEvidence> implements BinaryInteractionEvidence{

    private Xref imexId;
    private Experiment experiment;
    private String availability;
    private Collection<Parameter> parameters;
    private boolean isInferred = false;
    private Collection<Confidence> confidences;
    private boolean isNegative;
    private Collection<VariableParameterValueSet> variableParameterValueSets;

    public DefaultBinaryInteractionEvidence() {
        super();
    }

    public DefaultBinaryInteractionEvidence(String shortName) {
        super(shortName);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public DefaultBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(participantA, participantB);
    }

    public DefaultBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, participantA, participantB);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, type, participantA, participantB);
    }

    public DefaultBinaryInteractionEvidence(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    protected void initialiseExperimentalConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseExperimentalConfidencesWith(Collection<Confidence> confidences){
        if (confidences == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else {
            this.confidences = confidences;
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

    protected void initialiseExperimentalParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseExperimentalParametersWith(Collection<Parameter> parameters){
        if (parameters == null){
            this.parameters = Collections.EMPTY_LIST;
        }
        else {
            this.parameters = parameters;
        }
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new ExperimentalBinaryInteractionXrefList());
    }

    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        // add new imex if not null
        if (identifier != null){
            ExperimentalBinaryInteractionXrefList interactionXrefs = (ExperimentalBinaryInteractionXrefList) getXrefs();
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
        if (this.experiment != null){
            this.experiment.removeInteractionEvidence(this);
        }

        if (experiment != null){
            experiment.addInteractionEvidence(this);
        }
    }

    public Collection<VariableParameterValueSet> getVariableParameterValues() {

        if (variableParameterValueSets == null){
            initialiseVariableParameterValueSets();
        }
        return this.variableParameterValueSets;
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseExperimentalConfidences();
        }
        return this.confidences;
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

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseExperimentalParameters();
        }
        return this.parameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
    }

    /**
     * The collection of participants for this binary interaction.
     * It cannot be changed.
     * @return
     */
    public Collection<ParticipantEvidence> getParticipants() {
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

    /**
     * Adds a new ParticipantEvidence and set the interactionEvidence of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param part
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
    public boolean addParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (getParticipantB() != null && getParticipantA() != null){
            throw new IllegalArgumentException("A BinaryInteractionEvidence cannot have more than two participants.");
        }
        else if (getParticipantB() != null){
            part.setInteractionEvidence(this);
            setParticipantA(part);
            return true;
        }
        else{
            part.setInteractionEvidence(this);
            setParticipantA(part);
            return true;
        }
    }

    /**
     * Removes the participantEvidence from this binary interaction
     * @param part
     * @return
     */
    public boolean removeParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }

        if (getParticipantA() != null && getParticipantA().equals(part)){
            part.setInteractionEvidence(null);
            setParticipantA(null);
            return true;
        }
        else if (getParticipantB() != null && getParticipantB().equals(part)){
            part.setInteractionEvidence(null);
            setParticipantB(null);
            return true;
        }
        return false;
    }

    /**
     * Adds the participants and set the interactionEvidence of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param participants
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
    public boolean addAllParticipantEvidences(Collection<? extends ParticipantEvidence> participants) {
        if (participants == null){
            return false;
        }
        if (participants.size() > 2){
            throw new IllegalArgumentException("A BinaryInteractionEvidence cannot have more than two participants and we try to add " + participants.size() + " participants");
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
    public String toString() {
        return imexId != null ? imexId.getId() : super.toString();
    }

    /**
     * Experimental interaction Xref list
     */
    private class ExperimentalBinaryInteractionXrefList extends AbstractListHavingPoperties<Xref> {
        public ExperimentalBinaryInteractionXrefList(){
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
