package psidev.psi.mi.jami.model;

/**
 * The form of a molecule that was actually used to experimentally demonstrate the interaction, that may differ
 * from the sequence described by the identifying accession number.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/12/12</pre>
 */

public interface FeatureEvidence extends Feature<FeatureEvidence> {

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
}
