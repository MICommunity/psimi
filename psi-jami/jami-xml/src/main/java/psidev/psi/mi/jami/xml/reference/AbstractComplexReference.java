package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;
import java.util.Date;

/**
 * Abstract class for references to a complex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractComplexReference extends AbstractInteractorReference implements Complex{
    public AbstractComplexReference(int ref) {
        super(ref);
    }

    public String getPhysicalProperties() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have a physical property for complex id "+ref);
    }

    public void setPhysicalProperties(String properties) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot set a physical property for complex id "+ref);
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have interaction evidences for complex id "+ref);
    }

    public Source getSource() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have a source for complex id "+ref);
    }

    public void setSource(Source source) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot set the source for complex id "+ref);
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have modelled confidences for complex id "+ref);
    }

    public Collection<Parameter> getModelledParameters() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have modelled parameters for complex id "+ref);
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have cooperative effects for complex id "+ref);
    }

    public String getRigid() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have a rigid for complex id "+ref);
    }

    public void setRigid(String rigid) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot set the rigid for complex id "+ref);
    }

    public Date getUpdatedDate() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have an update date for complex id "+ref);
    }

    public void setUpdatedDate(Date updated) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot set the update date for complex id "+ref);
    }

    public Date getCreatedDate() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have a created date for complex id "+ref);
    }

    public void setCreatedDate(Date created) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot set a created date for complex id "+ref);
    }

    public CvTerm getInteractionType() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have an interaction type for complex id "+ref);
    }

    public void setInteractionType(CvTerm term) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot set an interaction type for complex id "+ref);
    }

    public Collection<ModelledParticipant> getParticipants() {
        throw new IllegalStateException("The complex reference is not resolved and we don't have participants for complex id "+ref);
    }

    public boolean addParticipant(ModelledParticipant part) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot add a participant for complex id "+ref);
    }

    public boolean removeParticipant(ModelledParticipant part) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot remove the participant for complex id "+ref);
    }

    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot add all the participants for complex id "+ref);
    }

    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        throw new IllegalStateException("The complex reference is not resolved and we cannot remove the participants for complex id "+ref);
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return super.getAnnotations();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return super.getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return super.getXrefs();
    }

    @Override
    public Collection<Alias> getAliases() {
        return super.getAliases();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return super.getIdentifiers();
    }

    @Override
    public String toString() {
        return "Interaction Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
