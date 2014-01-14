package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Xml wrapper for feature evidences.
 *
 * The new linked features added to this wrapper are NOT added to the wrapped feature evidence
 * because they are incompatibles.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlFeatureEvidenceWrapper implements ModelledFeature{
    private FeatureEvidence feature;
    private ModelledEntity parent;
    private Collection<ModelledFeature> linkedFeatures;

    public XmlFeatureEvidenceWrapper(FeatureEvidence part, ModelledEntity wrapper){
        if (part == null){
            throw new IllegalArgumentException("A feature evidence wrapper needs a non null feature");
        }
        this.feature = part;
        this.parent = wrapper;
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
        if (this.parent == null && this.feature.getParticipant()instanceof ParticipantEvidence){
            this.parent = new XmlParticipantEvidenceWrapper((ParticipantEvidence)this.feature.getParticipant(), null);
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

    @Override
    public String toString() {
        return this.feature.toString();
    }

    public FeatureEvidence getWrappedFeature(){
        return this.feature;
    }

    protected void initialiseLinkedFeatures(){
        this.linkedFeatures = new ArrayList<ModelledFeature>(this.feature.getLinkedFeatures().size());
        for (FeatureEvidence feature : this.feature.getLinkedFeatures()){
            this.linkedFeatures.add(new XmlFeatureWrapper(feature, null));
        }
    }
}
