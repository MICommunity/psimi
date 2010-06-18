package psidev.psi.mi.validator.extension;

import psidev.psi.tools.validator.ValidatorConfig;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class Mi25ValidatorConfig extends ValidatorConfig {

    private static final String crossReference2CrossReferenceType = "/crossReference2Location2CrossRefType.tsv";
    private static final String featureType2FeatureDetectionMethod = "/featureType2FeatureDetectionMethod.tsv";
    private static final String featureType2FeatureRange = "/featureType2FeatureRangeStatus.tsv";
    private static final String interactionDetectionMethod2BiologicalRole = "/InteractionDetectionMethod2BiologicalRole.tsv";
    private static final String interactionDetectionMethod2ExperimentalRole = "/InteractionDetectionMethod2ExperimentRole.tsv";
    private static final String interactionDetectionMethod2InteractionType = "/InteractionDetection2InteractionTypes.tsv";
    private static final String interactionDetectionMethod2ParticipantIdentificationMethod = "/InteractionDetectionMethod2ParticipantIdentificationMethod.tsv";

    public Mi25ValidatorConfig() {
        super();
    }

    public static String getCrossReference2CrossReferenceType() {
        return crossReference2CrossReferenceType;
    }

    public static String getFeatureType2FeatureDetectionMethod() {
        return featureType2FeatureDetectionMethod;
    }

    public static String getFeatureType2FeatureRange() {
        return featureType2FeatureRange;
    }

    public static String getInteractionDetectionMethod2BiologicalRole() {
        return interactionDetectionMethod2BiologicalRole;
    }

    public static String getInteractionDetectionMethod2ExperimentalRole() {
        return interactionDetectionMethod2ExperimentalRole;
    }

    public static String getInteractionDetectionMethod2InteractionType() {
        return interactionDetectionMethod2InteractionType;
    }

    public static String getInteractionDetectionMethod2ParticipantIdentificationMethod() {
        return interactionDetectionMethod2ParticipantIdentificationMethod;
    }
}
