package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Experiment;
import psidev.psi.mi.jami.xml.extension.XmlExperiment;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for an ModelledInteractionRef
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractExperimentRef extends AbstractXmlIdReference implements ExtendedPsi25Experiment{
    private static final Logger logger = Logger.getLogger("AbstractFeatureRef");
    private ExtendedPsi25Experiment delegate;

    public AbstractExperimentRef(int ref) {
        super(ref);
    }

    public Publication getPublication() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getPublication();
    }

    public void setPublication(Publication publication) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setPublication(publication);
    }

    public void setPublicationAndAddExperiment(Publication publication) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setPublicationAndAddExperiment(publication);
    }

    public Collection<Xref> getXrefs() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getAnnotations();
    }

    public Collection<Confidence> getConfidences() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getConfidences();
    }

    public CvTerm getInteractionDetectionMethod() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getInteractionDetectionMethod();
    }

    public void setInteractionDetectionMethod(CvTerm term) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setInteractionDetectionMethod(term);
    }

    public Organism getHostOrganism() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getHostOrganism();
    }

    public void setHostOrganism(Organism organism) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setHostOrganism(organism);
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getInteractionEvidences();
    }

    public boolean addInteractionEvidence(InteractionEvidence evidence) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.addInteractionEvidence(evidence);
    }

    public boolean removeInteractionEvidence(InteractionEvidence evidence) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.removeInteractionEvidence(evidence);
    }

    public boolean addAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.addAllInteractionEvidences(evidences);
    }

    public boolean removeAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.removeAllInteractionEvidences(evidences);
    }

    public Collection<VariableParameter> getVariableParameters() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getVariableParameters();
    }

    public boolean addVariableParameter(VariableParameter variableParameter) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.addVariableParameter(variableParameter);
    }

    public boolean removeVariableParameter(VariableParameter variableParameter) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.removeVariableParameter(variableParameter);
    }

    public boolean addAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.addAllVariableParameters(variableParameters);
    }

    public boolean removeAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.removeAllVariableParameters(variableParameters);
    }

    @Override
    public void setFeatureDetectionMethod(CvTerm method) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setFeatureDetectionMethod(method);
    }

    @Override
    public CvTerm getFeatureDetectionMethod() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getFeatureDetectionMethod();
    }

    @Override
    public void setParticipantIdentificationMethod(CvTerm method) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setParticipantIdentificationMethod(method);
    }

    @Override
    public CvTerm getParticipantIdentificationMethod() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getParticipantIdentificationMethod();
    }

    @Override
    public List<Organism> getHostOrganisms() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getHostOrganisms();
    }

    @Override
    public void setId(int id) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setId(id);
    }

    @Override
    public int getId() {
        return this.delegate != null ? this.delegate.getId() : this.ref;
    }

    @Override
    public String getShortName() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getShortName();
    }

    @Override
    public void setShortName(String name) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setShortName(name);
    }

    @Override
    public String getFullName() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getFullName();
    }

    @Override
    public void setFullName(String name) {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        this.delegate.setFullName(name);
    }

    @Override
    public <A extends Alias> Collection<A> getAliases() {
        logger.log(Level.WARNING, "The experiment reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseExperimentDelegate();
        }
        return this.delegate.getAliases();
    }

    @Override
    public String toString() {
        return "Experiment Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
    protected void initialiseExperimentDelegate(){
        this.delegate = new XmlExperiment();
        this.delegate.setId(this.ref);
    }

    protected ExtendedPsi25Experiment getDelegate() {
        return delegate;
    }
}
