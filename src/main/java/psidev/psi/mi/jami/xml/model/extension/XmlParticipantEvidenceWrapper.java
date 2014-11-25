package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntryContext;

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
public class XmlParticipantEvidenceWrapper implements ModelledParticipant, ExtendedPsiXmlParticipant<ModelledInteraction, ModelledFeature>,
        FileSourceContext{

    private ExtendedPsiXmlParticipantEvidence participant;
    private Collection<ModelledFeature> modelledFeatures;
    private ModelledInteraction parent;

    public XmlParticipantEvidenceWrapper(ExtendedPsiXmlParticipantEvidence part, XmlInteractionEvidenceComplexWrapper wrapper){
        if (part == null){
            throw new IllegalArgumentException("A participant evidence wrapper needs a non null participant");
        }
        this.participant = part;
        setInteraction(wrapper);
        // register participant as complex participant
        XmlEntryContext.getInstance().registerComplexParticipant(participant.getId(), this);
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
    public Collection<CausalRelationship> getCausalRelationships() {
        return this.participant.getCausalRelationships();
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
    public void setChangeListener(EntityInteractorChangeListener listener) {
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
    public EntityInteractorChangeListener getChangeListener() {
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
        if (this.parent == null && this.participant.getInteraction() instanceof ExtendedPsiXmlInteractionEvidence){
            this.parent = new XmlInteractionEvidenceComplexWrapper((ExtendedPsiXmlInteractionEvidence)this.participant.getInteraction());
        }
        return this.parent;
    }

    @Override
    public void setInteraction(ModelledInteraction interaction) {
        this.parent = interaction;
    }

    public ExtendedPsiXmlParticipantEvidence getWrappedParticipant(){
        return this.participant;
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return ((FileSourceContext)participant).getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator locator) {
        ((FileSourceContext)participant).setSourceLocator(locator);
    }

    @Override
    public int getId() {
        return participant.getId();
    }

    @Override
    public void setId(int id) {
        participant.setId(id);
        // register participant as complex participant
        XmlEntryContext.getInstance().registerComplexParticipant(participant.getId(), this);
    }

    @Override
    public String getShortName() {
        return participant.getShortName();
    }

    @Override
    public void setShortName(String name) {
        participant.setShortName(name);
    }

    @Override
    public String getFullName() {
        return participant.getShortName();
    }

    @Override
    public void setFullName(String name) {
        participant.setShortName(name);
    }

    protected void initialiseFeatures(){
        this.modelledFeatures = new ArrayList<ModelledFeature>();
        for (FeatureEvidence feature : this.participant.getFeatures()){
            this.modelledFeatures.add(new XmlFeatureEvidenceWrapper((ExtendedPsiXmlFeatureEvidence)feature, this));
        }
    }
}
