package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalFeature;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousExperimentalFeatureComparator;

/**
 * Default implementation for ExperimentalFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultExperimentalFeature extends DefaultFeature<ExperimentalFeature, ExperimentalParticipant> implements ExperimentalFeature{
    private CvTerm detectionMethod;

    public DefaultExperimentalFeature(ExperimentalParticipant participant) {
        super(participant);
    }

    public DefaultExperimentalFeature(ExperimentalParticipant participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public DefaultExperimentalFeature(ExperimentalParticipant participant, CvTerm type) {
        super(participant, type);
    }

    public DefaultExperimentalFeature(ExperimentalParticipant participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public DefaultExperimentalFeature(ExperimentalParticipant participant, CvTerm type, CvTerm detectionMethod) {
        super(participant, type);
        this.detectionMethod = detectionMethod;
    }

    public DefaultExperimentalFeature() {
        super();
    }

    public DefaultExperimentalFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultExperimentalFeature(CvTerm type) {
        super(type);
    }

    public DefaultExperimentalFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public DefaultExperimentalFeature(CvTerm type, CvTerm detectionMethod) {
        super(type);
        this.detectionMethod = detectionMethod;
    }

    public DefaultExperimentalFeature(String shortName, String fullName, CvTerm type, CvTerm detectionMethod) {
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

        if (!(o instanceof ExperimentalFeature)){
            return false;
        }

        // use UnambiguousExperimentalFeature comparator for equals
        return UnambiguousExperimentalFeatureComparator.areEquals(this, (ExperimentalFeature) o);
    }

    @Override
    public String toString() {
        return (detectionMethod != null ? detectionMethod.toString() + ", " : "") + super.toString();
    }
}
