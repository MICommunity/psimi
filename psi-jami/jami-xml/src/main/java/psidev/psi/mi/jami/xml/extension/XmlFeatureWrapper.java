package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import java.util.Collection;

/**
 * Xml wrapper for basic features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public class XmlFeatureWrapper implements ModelledFeature{
    private Feature<Participant, Feature> feature;
    private ModelledEntity parent;
    private SynchronizedLinkedFeatureList linkedFeatures;

    public XmlFeatureWrapper(Feature part, ModelledEntity parent){
        if (part == null){
            throw new IllegalArgumentException("A feature wrapper needs a non null feature");
        }
        this.feature = part;
        this.parent = parent;
    }

    @Override
    public String getShortName() {
        return this.feature.getShortName();
    }

    @Override
    public void setShortName(String name) {
        this.feature.setShortName(name);
    }

    @Override
    public String getFullName() {
        return this.feature.getFullName();
    }

    @Override
    public void setFullName(String name) {
        this.feature.setFullName(name);
    }

    @Override
    public String getInterpro() {
        return this.feature.getInterpro();
    }

    @Override
    public void setInterpro(String interpro) {
        this.feature.setInterpro(interpro);
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return this.feature.getIdentifiers();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.feature.getXrefs();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.feature.getAnnotations();
    }

    @Override
    public CvTerm getType() {
        return this.feature.getType();
    }

    @Override
    public void setType(CvTerm type) {
        this.feature.setType(type);
    }

    @Override
    public Collection<Range> getRanges() {
        return this.feature.getRanges();
    }

    @Override
    public CvTerm getInteractionEffect() {
        return this.feature.getInteractionEffect();
    }

    @Override
    public void setInteractionEffect(CvTerm effect) {
        this.feature.setInteractionEffect(effect);
    }

    @Override
    public CvTerm getInteractionDependency() {
        return this.feature.getInteractionDependency();
    }

    @Override
    public void setInteractionDependency(CvTerm interactionDependency) {
        this.feature.setInteractionDependency(interactionDependency);
    }

    @Override
    public ModelledEntity getParticipant() {
        if (parent == null && this.feature.getParticipant() != null){
            this.parent = new XmlParticipantWrapper(this.feature.getParticipant(), null);
        }
        return this.parent;
    }

    @Override
    public void setParticipant(ModelledEntity participant) {
        this.parent = participant;
    }

    @Override
    public void setParticipantAndAddFeature(ModelledEntity participant) {
        if (this.parent != null){
            this.parent.removeFeature(this);
        }

        if (participant != null){
            participant.addFeature(this);
        }
    }

    @Override
    public Collection<Alias> getAliases() {
        return this.feature.getAliases();
    }

    @Override
    public Collection<ModelledFeature> getLinkedFeatures() {
        if (this.linkedFeatures == null){
            initialiseLinkedFeatures();
        }
        return this.linkedFeatures;
    }

    public Feature<Participant,Feature> getWrappedFeature(){
        return this.feature;
    }

    @Override
    public String toString() {
        return this.feature.toString();
    }

    protected void initialiseLinkedFeatures(){
        this.linkedFeatures = new SynchronizedLinkedFeatureList();
        for (Feature feature : this.feature.getLinkedFeatures()){
            this.linkedFeatures.addOnly(new XmlFeatureWrapper(feature, null));
        }
    }

    ////////////////////////////////////// classes
    private class SynchronizedLinkedFeatureList extends AbstractListHavingProperties<ModelledFeature> {

        private SynchronizedLinkedFeatureList() {
        }

        @Override
        protected void processAddedObjectEvent(ModelledFeature added) {
            feature.getLinkedFeatures().add(added);
        }

        @Override
        protected void processRemovedObjectEvent(ModelledFeature removed) {
            feature.getLinkedFeatures().remove(removed);
        }

        @Override
        protected void clearProperties() {
            feature.getLinkedFeatures().clear();
        }
    }
}
