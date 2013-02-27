package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * The form of a molecule that was actually used to experimentally demonstrate the interaction, that may differ
 * from the sequence described by the identifying accession number.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/12/12</pre>
 */

public interface FeatureEvidence extends Feature {

    /**
     * The feature detection method. It is a controlled vocabulary term and can be null.
     * Ex: autoradiography, predetermined feature, ...
     * @return the detection method
     */
    public CvTerm getDetectionMethod();

    /**
     * Sets the feature detection method
     * @param method : detection method
     */
    public void setDetectionMethod(CvTerm method);

    /**
     * The participant to which the feature is attached.
     * It can be null if the feature is not attached to any participants.
     * @return the participant
     */
    public ParticipantEvidence getParticipantEvidence();

    /**
     * Sets the participant.
     * @param participant : participant
     */
    public void setParticipantEvidence(ParticipantEvidence participant);

    /**
     * Sets the participant and add this feature to its list of features
     * @param participant : participant
     */
    public void setParticipantEvidenceAndAddFeature(ParticipantEvidence participant);

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public Collection<? extends FeatureEvidence> getBindingSiteEvidences();

    /**
     * This method will add the feature as a binding site evidence
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean addBindingSiteEvidence(FeatureEvidence feature);

    /**
     * This method will remove the feature from the binding site evidences.
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeBindingSiteEvidence(FeatureEvidence feature);

    /**
     * This method will add all features as binding site evidence
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean addAllBindingSiteEvidences(Collection<? extends FeatureEvidence> features);

    /**
     * This method will remove all the features from binding site evidences
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllBindingSiteEvidences(Collection<? extends FeatureEvidence> features);
}
