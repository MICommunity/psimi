package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for FeatureEvidence
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the FeatureEvidence object is a complex object.
 * To compare FeatureEvidence objects, you can use some comparators provided by default:
 * - DefaultFeatureEvidenceComparator
 * - UnambiguousFeatureEvidenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultFeatureEvidence extends AbstractFeature<ExperimentalEntity, FeatureEvidence> implements FeatureEvidence {
    private Collection<CvTerm> detectionMethods;

    public DefaultFeatureEvidence(ExperimentalEntity participant) {
        super();
        setParticipant(participant);
    }

    public DefaultFeatureEvidence(ExperimentalEntity participant, String shortName, String fullName) {
        super(shortName, fullName);
        setParticipant(participant);
    }

    public DefaultFeatureEvidence(ExperimentalEntity participant, CvTerm type) {
        super(type);
        setParticipant(participant);
    }

    public DefaultFeatureEvidence(ExperimentalEntity participant, String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
        setParticipant(participant);
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

    protected void initialiseDetectionMethods(){
        this.detectionMethods = new ArrayList<CvTerm>();
    }

    protected void initialiseDetectionMethodsWith(Collection<CvTerm> methods){
        if (methods == null){
            this.detectionMethods = Collections.EMPTY_LIST;
        }
        else {
            this.detectionMethods = methods;
        }
    }

    public Collection<CvTerm> getDetectionMethods() {
        if (detectionMethods == null){
            initialiseDetectionMethods();
        }
        return this.detectionMethods;
    }
}
