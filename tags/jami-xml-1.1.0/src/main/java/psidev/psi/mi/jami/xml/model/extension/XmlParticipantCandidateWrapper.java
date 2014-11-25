package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import java.util.Collection;

/**
 * Wrapper for XmlParticipantCandidate so it implements ModelledParticipantCanidate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public class XmlParticipantCandidateWrapper implements ModelledParticipantCandidate, ExtendedPsiXmlEntity<ModelledFeature>, FileSourceContext {

    private ParticipantCandidate<ParticipantPool,Feature> participant;
    private SynchronizedFeatureList modelledFeatures;

    private ModelledParticipantPool parent;

    public XmlParticipantCandidateWrapper(ParticipantCandidate part, ModelledParticipantPool parent){
        if (part == null){
            throw new IllegalArgumentException("A participant candidate wrapper needs a non null participant candidate");
        }
        this.participant = part;
        this.parent = parent;
        // register participant as complex participant
        XmlEntryContext.getInstance().registerComplexParticipant(((ExtendedPsiXmlEntity)this.participant).getId(), this);
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
    public Stoichiometry getStoichiometry() {
        if (this.participant.getStoichiometry() == null
                && this.participant.getParentPool() != null){
            return this.participant.getParentPool().getStoichiometry();
        }
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

    protected void initialiseFeatures(){
        this.modelledFeatures = new SynchronizedFeatureList();
        if (!this.participant.getFeatures().isEmpty()){
            for (Feature feature : this.participant.getFeatures()){
                this.modelledFeatures.addOnly(new XmlFeatureWrapper((ExtendedPsiXmlFeature)feature, this));
            }
        }
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
    public String toString() {
        return this.participant.toString();
    }

    public ParticipantCandidate<ParticipantPool, Feature> getWrappedParticipant(){
        return this.participant;
    }

    @Override
    public ModelledParticipantPool getParentPool() {
        if (this.parent == null && this.participant.getParentPool() instanceof ParticipantPool){
            this.parent = new XmlParticipantPoolWrapper(this.participant.getParentPool(), null);
        }
        return this.parent;
    }

    @Override
    public void setParentPool(ModelledParticipantPool pool) {
        this.parent = pool;
    }

    @Override
    public int getId() {
        return ((ExtendedPsiXmlEntity)participant).getId();
    }

    @Override
    public void setId(int id) {
        ((ExtendedPsiXmlEntity)participant).setId(id);
        // register participant as complex participant
        XmlEntryContext.getInstance().registerComplexParticipant(((ExtendedPsiXmlEntity)this.participant).getId(), this);
    }

    ////////////////////////////////////// classes
    private class SynchronizedFeatureList extends AbstractListHavingProperties<ModelledFeature> {

        private SynchronizedFeatureList() {
        }

        @Override
        protected void processAddedObjectEvent(ModelledFeature added) {
            participant.getFeatures().add(added);
        }

        @Override
        protected void processRemovedObjectEvent(ModelledFeature removed) {
            participant.getFeatures().remove(removed);
        }

        @Override
        protected void clearProperties() {
            participant.getFeatures().clear();
        }
    }
}