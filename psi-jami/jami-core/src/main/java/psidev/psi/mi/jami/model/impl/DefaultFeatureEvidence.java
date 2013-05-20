package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureEvidenceComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for FeatureEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultFeatureEvidence extends DefaultFeature<FeatureEvidence> implements FeatureEvidence {
    private CvTerm detectionMethod;
    private ParticipantEvidence participantEvidence;
    private Collection<FeatureEvidence> bindingSiteEvidences;

    public DefaultFeatureEvidence(ParticipantEvidence participant) {
        super();
        this.participantEvidence = participant;
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName) {
        super(shortName, fullName);
        this.participantEvidence = participant;
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, CvTerm type) {
        super(type);
        this.participantEvidence = participant;
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
        this.participantEvidence = participant;
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, CvTerm type, CvTerm detectionMethod) {
        super(type);
        this.detectionMethod = detectionMethod;
        this.participantEvidence = participant;
    }

    public DefaultFeatureEvidence() {
        super();
    }

    public DefaultFeatureEvidence(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultFeatureEvidence(CvTerm type) {
        super(type);
    }

    public DefaultFeatureEvidence(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public DefaultFeatureEvidence(CvTerm type, CvTerm detectionMethod) {
        super(type);
        this.detectionMethod = detectionMethod;
    }

    public DefaultFeatureEvidence(String shortName, String fullName, CvTerm type, CvTerm detectionMethod) {
        super(shortName, fullName, type);
        this.detectionMethod = detectionMethod;
    }

    protected void initialiseBindingSiteEvidences(){
        this.bindingSiteEvidences = new ArrayList<FeatureEvidence>();
    }

    protected void initialiseBindingSiteEvidencesWith(Collection<FeatureEvidence> features){
        if (features == null){
            this.bindingSiteEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.bindingSiteEvidences = features;
        }
    }

    public Collection<? extends FeatureEvidence> getBindingSiteEvidences() {
        if(bindingSiteEvidences == null){
            initialiseBindingSiteEvidences();
        }
        return this.bindingSiteEvidences;
    }

    public boolean addBindingSiteEvidence(FeatureEvidence feature) {
        if (feature == null){
            return false;
        }
        if(bindingSiteEvidences == null){
            initialiseBindingSiteEvidences();
        }

        return bindingSiteEvidences.add(feature);
    }

    public boolean removeBindingSiteEvidence(FeatureEvidence feature) {
        if (feature == null){
            return false;
        }
        if(bindingSiteEvidences == null){
            initialiseBindingSiteEvidences();
        }

        return bindingSiteEvidences.add(feature);
    }

    public boolean addAllBindingSiteEvidences(Collection<? extends FeatureEvidence> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (FeatureEvidence feature : features){
            if (addBindingSiteEvidence(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllBindingSiteEvidences(Collection<? extends FeatureEvidence> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (FeatureEvidence feature : features){
            if (removeBindingSiteEvidence(feature)){
                added = true;
            }
        }
        return added;
    }

    public CvTerm getDetectionMethod() {
        return this.detectionMethod;
    }

    public void setDetectionMethod(CvTerm method) {
        this.detectionMethod = method;
    }

    public ParticipantEvidence getParticipantEvidence() {
        return this.participantEvidence;
    }

    public void setParticipantEvidence(ParticipantEvidence participant) {
        this.participantEvidence = participant;
    }

    public void setParticipantEvidenceAndAddFeature(ParticipantEvidence participant) {

        if (participant != null){
            this.participantEvidence.addFeatureEvidence(this);
        }
        else{
            this.participantEvidence = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof FeatureEvidence)){
            return false;
        }

        // use UnambiguousExperimentalFeature comparator for equals
        return UnambiguousFeatureEvidenceComparator.areEquals(this, (FeatureEvidence) o);
    }

    @Override
    public String toString() {
        return (detectionMethod != null ? detectionMethod.toString() + ", " : "") + super.toString();
    }
}
