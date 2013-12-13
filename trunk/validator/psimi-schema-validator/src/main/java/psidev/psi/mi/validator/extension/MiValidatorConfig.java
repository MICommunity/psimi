package psidev.psi.mi.validator.extension;

/**
 * MiValidatorConfig
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class MiValidatorConfig {

    private final String experimentCrossReference2CrossReferenceType = "/experimentCrossReference2CrossRefType.tsv";
    private final String interactionCrossReference2CrossReferenceType = "/interactionCrossReference2CrossRefType.tsv";
    private final String interactorCrossReference2CrossReferenceType = "/interactorCrossReference2CrossRefType.tsv";
    private final String featureCrossReference2CrossReferenceType = "/featureCrossReference2CrossRefType.tsv";
    private final String featureType2FeatureDetectionMethod = "/featureType2FeatureDetectionMethod.tsv";
    private final String featureType2FeatureRange = "/featureType2FeatureRangeStatus.tsv";
    private final String interactionDetectionMethod2BiologicalRole = "/InteractionDetectionMethod2BiologicalRole.tsv";
    private final String interactionDetectionMethod2ExperimentalRole = "/InteractionDetectionMethod2ExperimentRole.tsv";
    private final String interactionDetectionMethod2InteractionType = "/InteractionDetection2InteractionTypes.tsv";
    private final String interactionDetectionMethod2ParticipantIdentificationMethod = "/InteractionDetectionMethod2ParticipantIdentificationMethod.tsv";

    public MiValidatorConfig() {
    }

    public String getExperimentCrossReference2CrossReferenceType() {
        return experimentCrossReference2CrossReferenceType;
    }

    public String getInteractionCrossReference2CrossReferenceType() {
        return interactionCrossReference2CrossReferenceType;
    }

    public String getInteractorCrossReference2CrossReferenceType() {
        return interactorCrossReference2CrossReferenceType;
    }

    public String getFeatureCrossReference2CrossReferenceType() {
        return featureCrossReference2CrossReferenceType;
    }

    public String getFeatureType2FeatureDetectionMethod() {
        return featureType2FeatureDetectionMethod;
    }

    public String getFeatureType2FeatureRange() {
        return featureType2FeatureRange;
    }

    public String getInteractionDetectionMethod2BiologicalRole() {
        return interactionDetectionMethod2BiologicalRole;
    }

    public String getInteractionDetectionMethod2ExperimentalRole() {
        return interactionDetectionMethod2ExperimentalRole;
    }

    public String getInteractionDetectionMethod2InteractionType() {
        return interactionDetectionMethod2InteractionType;
    }

    public String getInteractionDetectionMethod2ParticipantIdentificationMethod() {
        return interactionDetectionMethod2ParticipantIdentificationMethod;
    }
}
