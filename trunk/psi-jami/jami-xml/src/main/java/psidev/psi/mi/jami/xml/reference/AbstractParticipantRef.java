package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Abstract participant reference
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractParticipantRef<T extends Feature> extends AbstractXmlIdReference implements Entity<T>{
    public AbstractParticipantRef(int ref) {
        super(ref);
    }

    public Interactor getInteractor() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have an interactor for participant id "+ref);
    }

    public void setInteractor(Interactor interactor) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot set the interactor for participant id "+ref);
    }

    public CvTerm getBiologicalRole() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have a biological role for participant id "+ref);
    }

    public void setBiologicalRole(CvTerm bioRole) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot set the biological role for participant id "+ref);
    }

    public CausalRelationship getCausalRelationship() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have a causal relationship for participant id "+ref);
    }

    public void setCausalRelationship(CausalRelationship relationship) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot set a causal relationship for participant id "+ref);
    }

    public Collection<Xref> getXrefs() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have xrefs for participant id "+ref);
    }

    public Collection<Annotation> getAnnotations() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have annotations an interactor for participant id "+ref);
    }

    public Collection<Alias> getAliases() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have aliases for participant id "+ref);
    }

    public Stoichiometry getStoichiometry() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have a stoichiometry for participant id "+ref);
    }

    public void setStoichiometry(Integer stoichiometry) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot set the stoichiometry for participant id "+ref);
    }

    public void setStoichiometry(Stoichiometry stoichiometry) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot set the stoichiometry for participant id "+ref);
    }

    public Collection<T> getFeatures() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have features for participant id "+ref);
    }

    public ParticipantInteractorChangeListener getChangeListener() {
        throw new IllegalStateException("The participant reference is not resolved and we don't have a change listener for participant id "+ref);
    }

    public void setChangeListener(ParticipantInteractorChangeListener listener) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot set the change listener for participant id "+ref);
    }

    public boolean addFeature(T feature) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot add a feature for participant id "+ref);
    }

    public boolean removeFeature(T feature) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot remove a feature for participant id "+ref);
    }

    public boolean addAllFeatures(Collection<? extends T> features) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot add features for participant id "+ref);
    }

    public boolean removeAllFeatures(Collection<? extends T> features) {
        throw new IllegalStateException("The participant reference is not resolved and we cannot remove features for participant id "+ref);
    }

    @Override
    public String toString() {
        return "Participant Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
