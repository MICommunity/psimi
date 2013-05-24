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
     * The collection of feature detection methods. Each feature detectionMethod is a controlled vocabulary term.
     * The collection cannot be null. If the featureEvidence does not have any detection methods, it should return an empty collection.
     * Ex: autoradiography, predetermined feature, ...
     * @return the collection of detection methods for this feature
     */
    public Collection<CvTerm> getDetectionMethods();

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
     * If participant is null, it will remove this featureEvidence from the previous participant attached to this feature
     * @param participant : participant
     */
    public void setParticipantEvidenceAndAddFeature(ParticipantEvidence participant);

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public Collection<FeatureEvidence> getLinkedFeatureEvidences();
}
