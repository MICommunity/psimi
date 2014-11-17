package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.reference.AbstractFeatureRef;

import java.util.Collection;

/**
 * Xml wrapper for basic features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public class XmlFeatureWrapper implements ModelledFeature, ExtendedPsiXmlFeature<ModelledEntity, ModelledFeature>, FileSourceContext {
    private ExtendedPsiXmlFeature<Participant, Feature> feature;
    private ModelledEntity parent;
    private SynchronizedLinkedFeatureList linkedFeatures;

    public XmlFeatureWrapper(ExtendedPsiXmlFeature part, ModelledEntity parent){
        if (part == null){
            throw new IllegalArgumentException("A feature wrapper needs a non null feature");
        }
        this.feature = part;
        this.parent = parent;
        // register feature as complex feature
        XmlEntryContext.getInstance().registerComplexFeature(this.feature.getId(), this);
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
    public CvTerm getRole() {
        return this.feature.getRole();
    }

    @Override
    public void setRole(CvTerm effect) {
        this.feature.setRole(effect);
    }

    @Override
    public ModelledEntity getParticipant() {
        if (parent == null && this.feature.getParticipant() instanceof ExtendedPsiXmlParticipant){
            this.parent = new XmlParticipantWrapper((ExtendedPsiXmlParticipant)this.feature.getParticipant(), null);
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

    public ExtendedPsiXmlFeature<Participant,Feature> getWrappedFeature(){
        return this.feature;
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return ((FileSourceContext)this.feature).getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator locator) {
        ((FileSourceContext)this.feature).setSourceLocator(locator);
    }

    @Override
    public String toString() {
        return this.feature.toString();
    }

    protected void initialiseLinkedFeatures(){
        this.linkedFeatures = new SynchronizedLinkedFeatureList();
        for (Feature feature : this.feature.getLinkedFeatures()){
            ExtendedPsiXmlFeature extendedFeature = (ExtendedPsiXmlFeature)feature;
            this.linkedFeatures.addOnly(new FeatureRef(extendedFeature.getId()));
        }
    }

    @Override
    public int getId() {
        return this.feature.getId();
    }

    @Override
    public void setId(int id) {
        this.feature.setId(id);
        // register feature as complex feature
        XmlEntryContext.getInstance().registerComplexFeature(id, this);
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


    private class FeatureRef extends AbstractFeatureRef<ModelledEntity, ModelledFeature> implements ModelledFeature{
        public FeatureRef(int ref) {
            super(ref);
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsComplexFeature(this.ref)){
                ModelledFeature f = parsedObjects.getComplexFeature(this.ref);
                if (f != null){
                    linkedFeatures.remove(this);
                    linkedFeatures.add(f);
                    return true;
                }
            }
            else if (parsedObjects.containsFeature(this.ref)){
                Feature f = parsedObjects.getFeature(this.ref);
                if (f != null){
                    ModelledFeature reloadedObject = parsedObjects.registerModelledFeatureLoadedFrom(f);
                    if (reloadedObject != null){
                        linkedFeatures.remove(this);
                        linkedFeatures.add(reloadedObject);
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Feature Reference in inferred participant: "+(ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString()));
        }

        @Override
        protected void initialiseFeatureDelegate() {
            XmlModelledFeature modelled = new XmlModelledFeature();
            modelled.setId(this.ref);
            setDelegate(modelled);
        }

        public FileSourceLocator getSourceLocator() {
            return XmlFeatureWrapper.this.getSourceLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of a feature ref");
        }
    }
}
