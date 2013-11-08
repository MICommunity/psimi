package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Abstract feature reference
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractFeatureRef extends AbstractXmlIdReference implements Feature{
    public AbstractFeatureRef(int ref) {
        super(ref);
    }

    public String getShortName() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have a shortname for feature id "+ref);
    }

    public void setShortName(String name) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the shortname for feature id "+ref);
    }

    public String getFullName() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have a fullname for feature id "+ref);
    }

    public void setFullName(String name) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the fullname for feature id "+ref);
    }

    public String getInterpro() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have an interpro identifier for feature id "+ref);
    }

    public void setInterpro(String interpro) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the interpro identifier for feature id "+ref);
    }

    public Collection<Xref> getIdentifiers() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have identifiers for feature id "+ref);
    }

    public Collection<Xref> getXrefs() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have xrefs for feature id "+ref);
    }

    public Collection<Annotation> getAnnotations() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have annotations for feature id "+ref);
    }

    public CvTerm getType() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have a type a  for feature id "+ref);
    }

    public void setType(CvTerm type) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the type for feature id "+ref);
    }

    public Collection<Range> getRanges() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have ranges for feature id "+ref);
    }

    public CvTerm getInteractionEffect() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have an interaction effect for feature id "+ref);
    }

    public void setInteractionEffect(CvTerm effect) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the interaction effect for feature id "+ref);
    }

    public CvTerm getInteractionDependency() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have an interaction dependency for feature id "+ref);
    }

    public void setInteractionDependency(CvTerm interactionDependency) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the interaction dependency for feature id "+ref);
    }

    public Entity getParticipant() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have a participant for feature id "+ref);
    }

    public void setParticipant(Entity participant) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the participant for feature id "+ref);
    }

    public void setParticipantAndAddFeature(Entity participant) {
        throw new IllegalStateException("The feature reference is not resolved and we cannot set the participant for feature id "+ref);
    }

    public Collection<Feature> getLinkedFeatures() {
        throw new IllegalStateException("The feature reference is not resolved and we don't have linked features for feature id "+ref);
    }

    @Override
    public boolean isComplexReference() {
        return false;
    }

    @Override
    public String toString() {
        return "Feature Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
