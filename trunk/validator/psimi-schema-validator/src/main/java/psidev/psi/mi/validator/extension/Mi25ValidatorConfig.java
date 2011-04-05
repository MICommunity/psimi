package psidev.psi.mi.validator.extension;

/**
 * Mi25ValidatorConfig
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class Mi25ValidatorConfig{

    private final String crossReference2CrossReferenceType = "http://www.ebi.ac.uk/~intact/psi-validator/dependencies/crossReference2Location2CrossRefType.tsv";
    private final String featureType2FeatureDetectionMethod = "http://www.ebi.ac.uk/~intact/psi-validator/dependencies/featureType2FeatureDetectionMethod.tsv";
    private final String featureType2FeatureRange = "http://www.ebi.ac.uk/~intact/psi-validator/dependencies/featureType2FeatureRangeStatus.tsv";
    private final String interactionDetectionMethod2BiologicalRole = "http://www.ebi.ac.uk/~intact/psi-validator/dependencies/interactionDetectionMethod2BiologicalRole.tsv";
    private final String interactionDetectionMethod2ExperimentalRole = "http://www.ebi.ac.uk/~intact/psi-validator/dependencies/interactionDetectionMethod2ExperimentalRole.tsv";
    private final String interactionDetectionMethod2InteractionType = "http://www.ebi.ac.uk/~intact/psi-validator/dependencies/interactionDetectionMethod2InteractionTypes.tsv";
    private final String interactionDetectionMethod2ParticipantIdentificationMethod = "http://www.ebi.ac.uk/~intact/psi-validator/dependencies/interactionDetectionMethod2ParticipantIdentificationMethod.tsv";

    public Mi25ValidatorConfig() {
    }

    public String getCrossReference2CrossReferenceType() {
        return crossReference2CrossReferenceType;
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
