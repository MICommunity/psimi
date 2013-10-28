package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Xml wrapper for feature evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlModelledFeatureWrapper extends XmlModelledFeature{
    private FeatureEvidence feature;

    public XmlModelledFeatureWrapper(FeatureEvidence part, XmlModelledParticipantWrapper wrapper){
        if (part == null){
            throw new IllegalArgumentException("A feature evidence wrapper needs a non null feature");
        }
        this.feature = part;
        super.setParticipant(wrapper);
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
    protected void initialiseLinkedFeatures(){
        Collection<ModelledFeature> modelledFeatures = new ArrayList<ModelledFeature>(this.feature.getLinkedFeatures().size());
        for (FeatureEvidence feature : this.feature.getLinkedFeatures()){
            if (feature.getParticipant() != null){
                ExperimentalEntity entityEvidence = feature.getParticipant();
                if (entityEvidence instanceof ParticipantEvidence){
                    ParticipantEvidence partEvidence = (ParticipantEvidence) entityEvidence;
                    if (partEvidence.getInteraction() != null){
                        InteractionEvidence interaction = partEvidence.getInteraction();
                        modelledFeatures.add(new XmlModelledFeatureWrapper(feature, new XmlModelledParticipantWrapper(partEvidence, new XmlInteractionEvidenceWrapper(interaction))));
                    }
                    else{
                        modelledFeatures.add(new XmlModelledFeatureWrapper(feature, new XmlModelledParticipantWrapper(partEvidence, null)));
                    }
                }
                else{
                    modelledFeatures.add(new XmlModelledFeatureWrapper(feature, null));
                }
            }
            else{
                modelledFeatures.add(new XmlModelledFeatureWrapper(feature, null));
            }
        }
        super.initialiseLinkedFeaturesWith(modelledFeatures);
    }
}
