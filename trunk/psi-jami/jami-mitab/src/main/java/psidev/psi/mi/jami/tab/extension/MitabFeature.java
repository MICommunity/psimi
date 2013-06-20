package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultFeature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A MitabFeature is a feature in MITAB with some free text.
 *
 * It can be ModelledFeature of FeatureEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class MitabFeature extends DefaultFeature implements FileSourceContext, FeatureEvidence, ModelledFeature{

    private String text;
    private FileSourceLocator sourceLocator;
    private Collection<CvTerm> detectionMethods;
    private ParticipantEvidence participantEvidence;
    private Collection<FeatureEvidence> bindingSiteEvidences;
    private ModelledParticipant modelledParticipant;
    private Collection<ModelledFeature> bindingFeatures;

    public MitabFeature() {
        super();
    }

    public MitabFeature(CvTerm type) {
        super(type);
    }

    public MitabFeature(String type) {
        super(new MitabCvTerm(type));
    }

    public MitabFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    protected void initialiseBindingSiteEvidences(){
        this.bindingSiteEvidences = new ArrayList<FeatureEvidence>();
    }

    protected void initialiseDetectionMethods(){
        this.detectionMethods = new ArrayList<CvTerm>();
    }

    protected void initialiseBindingSiteEvidencesWith(Collection<FeatureEvidence> features){
        if (features == null){
            this.bindingSiteEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.bindingSiteEvidences = features;
        }
    }

    protected void initialiseDetectionMethodsWith(Collection<CvTerm> methods){
        if (methods == null){
            this.detectionMethods = Collections.EMPTY_LIST;
        }
        else {
            this.detectionMethods = methods;
        }
    }

    public Collection<FeatureEvidence> getLinkedFeatureEvidences() {
        if(bindingSiteEvidences == null){
            initialiseBindingSiteEvidences();
        }
        return this.bindingSiteEvidences;
    }

    public Collection<CvTerm> getDetectionMethods() {
        if (detectionMethods == null){
            initialiseDetectionMethods();
        }
        return this.detectionMethods;
    }

    public ParticipantEvidence getParticipantEvidence() {
        return this.participantEvidence;
    }

    public void setParticipantEvidence(ParticipantEvidence participant) {
        this.participantEvidence = participant;
    }

    public void setParticipantEvidenceAndAddFeature(ParticipantEvidence participant) {
        if (this.participantEvidence != null){
            this.participantEvidence.removeFeatureEvidence(this);
        }
        if (participant != null){
            participant.addFeatureEvidence(this);
        }
    }

    protected void initialiseBindingFeatures(){
        this.bindingFeatures = new ArrayList<ModelledFeature>();
    }

    protected void initialiseBindingFeaturesWith(Collection<ModelledFeature> features){
        if (features == null){
            this.bindingFeatures = Collections.EMPTY_LIST;
        }
        else {
            this.bindingFeatures = features;
        }
    }

    public ModelledParticipant getModelledParticipant() {
        return this.modelledParticipant;
    }

    public void setModelledParticipant(ModelledParticipant participant) {
        this.modelledParticipant = participant;
    }

    public void setModelledParticipantAndAddFeature(ModelledParticipant participant) {
        if (this.modelledParticipant != null){
            this.modelledParticipant.removeModelledFeature(this);
        }

        if (participant != null){
            participant.addModelledFeature(this);
        }
    }

    public Collection<ModelledFeature> getLinkedModelledFeatures() {
        if(bindingFeatures == null){
            initialiseBindingFeatures();
        }
        return this.bindingFeatures;
    }
}
