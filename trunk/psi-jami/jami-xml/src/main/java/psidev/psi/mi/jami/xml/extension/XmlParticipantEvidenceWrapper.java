package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Wrapper for Xml participants
 *
 * Addeding new modelled feature to this participant will not add new feature evidences to the wrapped participant evidence as they are incompatibles.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlParticipantEvidenceWrapper implements ModelledParticipant{

    private ParticipantEvidence participant;
    private Collection<ModelledFeature> modelledFeatures;
    private ModelledInteraction parent;

    public XmlParticipantEvidenceWrapper(ParticipantEvidence part, XmlInteractionEvidenceComplexWrapper wrapper){
        if (part == null){
            throw new IllegalArgumentException("A participant evidence wrapper needs a non null participant");
        }
        this.participant = part;
        setInteraction(wrapper);
    }

    @Override
    public Collection<Alias> getAliases() {
        return this.participant.getAliases();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.participant.getXrefs();
    }

    @Override
    public Interactor getInteractor() {
        return this.participant.getInteractor();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.participant.setInteractor(interactor);
    }

    @Override
    public CausalRelationship getCausalRelationship() {
        return this.participant.getCausalRelationship();
    }

    @Override
    public void setCausalRelationship(CausalRelationship relationship) {
        this.participant.setCausalRelationship(relationship);
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.participant.getAnnotations();
    }

    @Override
    public Stoichiometry getStoichiometry() {
        return this.participant.getStoichiometry();
    }

    @Override
    public void setStoichiometry(Integer stoichiometry) {
        this.participant.setStoichiometry(stoichiometry);
    }

    @Override
    public void setStoichiometry(Stoichiometry stoichiometry) {
        this.participant.setStoichiometry(stoichiometry);
    }

    @Override
    public Collection<ModelledFeature> getFeatures() {
        if (this.modelledFeatures == null){
            initialiseFeatures();
        }
        return this.modelledFeatures;
    }

    @Override
    public void setChangeListener(ParticipantInteractorChangeListener listener) {
        this.participant.setChangeListener(listener);
    }

    @Override
    public boolean addFeature(ModelledFeature feature) {
        if (feature == null){
            return false;
        }
        if (this.modelledFeatures == null){
            initialiseFeatures();
        }
        if (this.modelledFeatures.add(feature)){
            feature.setParticipant(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFeature(ModelledFeature feature) {
        if (feature == null){
            return false;
        }
        if (this.modelledFeatures == null){
            initialiseFeatures();
        }
        if (this.modelledFeatures.remove(feature)){
            feature.setParticipant(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAllFeatures(Collection<? extends ModelledFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ModelledFeature feature : features){
            if (addFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public boolean removeAllFeatures(Collection<? extends ModelledFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ModelledFeature feature : features){
            if (removeFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public ParticipantInteractorChangeListener getChangeListener() {
        return this.participant.getChangeListener();
    }

    @Override
    public CvTerm getBiologicalRole() {
        return this.participant.getBiologicalRole();
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        this.participant.setBiologicalRole(bioRole);
    }

    @Override
    public String toString() {
        return this.participant.toString();
    }


    @Override
    public void setInteractionAndAddParticipant(ModelledInteraction interaction) {
        if (this.parent != null){
            this.parent.removeParticipant(this);
        }

        if (interaction != null){
            interaction.addParticipant(this);
        }
    }

    @Override
    public ModelledInteraction getInteraction() {
        if (this.parent == null && this.participant.getInteraction() instanceof AbstractXmlInteractionEvidence){
            this.parent = new XmlInteractionEvidenceComplexWrapper((AbstractXmlInteractionEvidence)this.participant.getInteraction());
        }
        return this.parent;
    }

    @Override
    public void setInteraction(ModelledInteraction interaction) {
        this.parent = interaction;
    }

    public ExperimentalEntity getWrappedParticipant(){
        return this.participant;
    }

    protected void initialiseFeatures(){
        this.modelledFeatures = new ArrayList<ModelledFeature>();
        for (FeatureEvidence feature : this.participant.getFeatures()){
            this.modelledFeatures.add(new XmlFeatureEvidenceWrapper(feature, this));
        }
    }
}
