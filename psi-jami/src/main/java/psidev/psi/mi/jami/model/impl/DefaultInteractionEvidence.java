package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionEvidenceComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

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

public class DefaultInteractionEvidence extends DefaultInteraction<ParticipantEvidence> implements InteractionEvidence {

    private Xref imexId;
    private Experiment experiment;
    private String availability;
    private Collection<Parameter> parameters;
    private boolean isInferred = false;

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

    protected void initialiseParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseParametersWith(Collection<Parameter> parameters){
        if (parameters == null){
            this.parameters = Collections.EMPTY_LIST;
        }
        else {
            this.parameters = parameters;
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
            CvTerm imexDatabase = CvTermFactory.createImexDatabase();
            CvTerm imexPrimaryQualifier = CvTermFactory.createImexPrimaryQualifier();
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
            this.experiment.getInteractions().add(this);
        }
    }

    public String getAvailability() {
        return this.availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseParameters();
        }
        return this.parameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
    }

    public boolean addParticipantEvidence(ParticipantEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (getParticipants().add(evidence)){
            evidence.setInteraction(this);
            return true;
        }
        return false;
    }

    public boolean removeParticipantEvidence(ParticipantEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (getParticipants().remove(evidence)){
            evidence.setInteraction(null);
            return true;
        }
        return false;
    }

    public boolean addAllParticipantEvidences(Collection<? extends ParticipantEvidence> evidences) {
        if (evidences == null){
            return false;
        }

        boolean added = false;
        for (ParticipantEvidence p : evidences){
            if (addParticipant(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllParticipantEvidences(Collection<? extends ParticipantEvidence> evidences) {
        if (evidences == null){
            return false;
        }

        boolean removed = false;
        for (ParticipantEvidence p : evidences){
            if (removeParticipant(p)){
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
