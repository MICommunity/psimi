package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureEvidenceComparator;

/**
 * Default implementation for FeatureEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultFeatureEvidence extends DefaultFeature<FeatureEvidence, ParticipantEvidence> implements FeatureEvidence {
    protected CvTerm detectionMethod;

    public DefaultFeatureEvidence(ParticipantEvidence participant) {
        super(participant);
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, CvTerm type) {
        super(participant, type);
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public DefaultFeatureEvidence(ParticipantEvidence participant, CvTerm type, CvTerm detectionMethod) {
        super(participant, type);
        this.detectionMethod = detectionMethod;
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

    public CvTerm getDetectionMethod() {
        return this.detectionMethod;
    }

    public void setDetectionMethod(CvTerm method) {
        this.detectionMethod = method;
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
