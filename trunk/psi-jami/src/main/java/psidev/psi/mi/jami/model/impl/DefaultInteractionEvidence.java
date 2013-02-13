package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionEvidenceComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultInteractionEvidence extends DefaultInteraction<ParticipantEvidence> implements InteractionEvidence {

    protected Xref imexId;
    protected Experiment experiment;
    protected String availability;
    protected Collection<Parameter> parameters;
    protected boolean isInferred = false;

    public DefaultInteractionEvidence(Experiment experiment) {
        super();
        initializeParameters();

        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName) {
        super(shortName);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Source source) {
        super(shortName, source);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, CvTerm type) {
        super(shortName, type);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Source source, CvTerm type) {
        super(shortName, source, type);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public DefaultInteractionEvidence(Experiment experiment, Xref imexId) {
        super();
        initializeParameters();

        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Xref imexId) {
        super(shortName);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Source source, Xref imexId) {
        super(shortName, source);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, CvTerm type, Xref imexId) {
        super(shortName, type);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(Experiment experiment, String shortName, Source source, CvTerm type, Xref imexId) {
        super(shortName, source, type);
        initializeParameters();
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, Source source, CvTerm type) {
        super(shortName, source, type);
        initializeParameters();
    }

    public DefaultInteractionEvidence(Xref imexId) {
        super();
        initializeParameters();
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, Xref imexId) {
        super(shortName);
        initializeParameters();
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, Source source, Xref imexId) {
        super(shortName, source);
        initializeParameters();
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, CvTerm type, Xref imexId) {
        super(shortName, type);
        initializeParameters();
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence(String shortName, Source source, CvTerm type, Xref imexId) {
        super(shortName, source, type);
        initializeParameters();
        this.xrefs.add(imexId);
    }

    public DefaultInteractionEvidence() {
        super();
        initializeParameters();
    }

    public DefaultInteractionEvidence(String shortName) {
        super(shortName);
        initializeParameters();
    }

    public DefaultInteractionEvidence(String shortName, Source source) {
        super(shortName, source);
        initializeParameters();
    }

    public DefaultInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
        initializeParameters();
    }

    protected void initializeParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new ExperimentalInteractionXrefList();
    }

    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        // add new imex if not null
        if (identifier != null){
            CvTerm imexDatabase = CvTermFactory.createImexDatabase();
            CvTerm imexPrimaryQualifier = CvTermFactory.createImexPrimaryQualifier();
            // first remove old doi if not null
            if (this.imexId != null){
                this.xrefs.remove(this.imexId);
            }
            this.imexId = new DefaultXref(imexDatabase, identifier, imexPrimaryQualifier);
            this.xrefs.add(this.imexId);
        }
        else {
            throw new IllegalArgumentException("The imex id has to be non null.");
        }
    }

    public Experiment getExperiment() {
        return this.experiment;
    }

    public void setExperiment(Experiment experiment) {
        if (experiment == null){
            throw new IllegalArgumentException("The Experiment is required for an InteractionEvidence, it cannot be null.");
        }
        this.experiment = experiment;
    }

    public String getAvailability() {
        return this.availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Collection<Parameter> getParameters() {
        return this.parameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
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

            // the added identifier is imex and the current imex is not set
            if (imexId == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
                // the added xref is imex-primary
                if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                    imexId = added;
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            // the removed identifier is pubmed
            if (imexId == removed){
                imexId = null;
            }
        }

        @Override
        protected void clearProperties() {
            imexId = null;
        }
    }
}
