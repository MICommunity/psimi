package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Abstract class for an ExperimentRef
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractExperimentRef extends AbstractXmlIdReference implements Experiment{

    public AbstractExperimentRef(int ref) {
        super(ref);
    }

    public Publication getPublication() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have a publication for experiment id "+ref);
    }

    public void setPublication(Publication publication) {
        throw new IllegalStateException("The experiment reference is not resolved and wecannot set a publication for experiment id "+ref);
    }

    public void setPublicationAndAddExperiment(Publication publication) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot set a publication for experiment id "+ref);
    }

    public Collection<Xref> getXrefs() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have xrefs for experiment id "+ref);
    }

    public Collection<Annotation> getAnnotations() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have annotations for experiment id "+ref);
    }

    public Collection<Confidence> getConfidences() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have confidences for experiment id "+ref);
    }

    public CvTerm getInteractionDetectionMethod() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have an interaction detection method for experiment id "+ref);
    }

    public void setInteractionDetectionMethod(CvTerm term) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot set an interaction detection method for experiment id "+ref);
    }

    public Organism getHostOrganism() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have a host prganism for experiment id "+ref);
    }

    public void setHostOrganism(Organism organism) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot set a host organism for experiment id "+ref);
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have interaction evidences for experiment id "+ref);
    }

    public boolean addInteractionEvidence(InteractionEvidence evidence) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot add interaction evidence for experiment id "+ref);
    }

    public boolean removeInteractionEvidence(InteractionEvidence evidence) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot remove interaction evidence for experiment id "+ref);
    }

    public boolean addAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot add interaction evidences for experiment id "+ref);
    }

    public boolean removeAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot remove interaction evidences for experiment id "+ref);
    }

    public Collection<VariableParameter> getVariableParameters() {
        throw new IllegalStateException("The experiment reference is not resolved and we don't have variable parameters for experiment id "+ref);
    }

    public boolean addVariableParameter(VariableParameter variableParameter) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot add variable parameters for experiment id "+ref);
    }

    public boolean removeVariableParameter(VariableParameter variableParameter) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot remove a variable parameter for experiment id "+ref);
    }

    public boolean addAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot add variable parameters for experiment id "+ref);
    }

    public boolean removeAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        throw new IllegalStateException("The experiment reference is not resolved and we cannot remove variable parameters for experiment id "+ref);
    }

    @Override
    public boolean isComplexReference() {
        return false;
    }

    @Override
    public String toString() {
        return "Experiment Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
