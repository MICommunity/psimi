package psidev.psi.mi.jami.xml.extension.binary;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.extension.Availability;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25InteractionEvidence;
import psidev.psi.mi.jami.xml.extension.XmlXref;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml implementation of BinaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteractionEvidence extends AbstractXmlBinaryInteraction<ParticipantEvidence> implements BinaryInteractionEvidence, ExtendedPsi25InteractionEvidence{
    private Xref imexId;
    private String availability;
    private Collection<Parameter> parameters;
    private boolean isInferred = false;
    private Collection<Confidence> confidences;
    private boolean isNegative;
    private Collection<VariableParameterValueSet> variableParameterValueSets;
    private Availability xmlAvailability;
    private Boolean isModelled;
    private List<Experiment> experiments;

    public XmlBinaryInteractionEvidence() {
    }

    public XmlBinaryInteractionEvidence(String shortName) {
        super(shortName);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public XmlBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, type, participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public XmlBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    protected void initialiseExperimentalConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseVariableParameterValueSets(){
        this.variableParameterValueSets = new ArrayList<VariableParameterValueSet>();
    }

    protected void initialiseExperimentalParameters(){
        this.parameters = new ArrayList<Parameter>();
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
            this.imexId = new XmlXref(imexDatabase, identifier, imexPrimaryQualifier);
            interactionXrefs.addOnly(this.imexId);
        }
        else {
            throw new IllegalArgumentException("The imex id has to be non null.");
        }
    }

    public Experiment getExperiment() {
        if (getExperiments().isEmpty()){
            return null;
        }
        return getExperiments().iterator().next();
    }

    public void setExperiment(Experiment experiment) {
        if (experiment != null){
            if (!getExperiments().isEmpty()){
                getExperiments().remove(0);
            }
            getExperiments().add(0, experiment);
        }
        else{
            if (!getExperiments().isEmpty()){
                this.getExperiments().remove(0);
            }
        }
    }

    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        Experiment current = getExperiment();
        if (current != null){
            current.removeInteractionEvidence(this);
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

    @Override
    public Availability getXmlAvailability() {
        return this.xmlAvailability;
    }

    @Override
    public void setXmlAvailability(Availability availability) {
        this.xmlAvailability = availability;
    }

    @Override
    public boolean isModelled() {
        return isModelled != null ? isModelled : false;
    }

    @Override
    public void setModelled(boolean modelled) {
       isModelled = modelled;
    }

    @Override
    public List<Experiment> getExperiments() {
        if (experiments == null){
           experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    /**
     * Experimental interaction Xref list
     */
    private class ExperimentalBinaryInteractionXrefList extends AbstractListHavingProperties<Xref> {
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
