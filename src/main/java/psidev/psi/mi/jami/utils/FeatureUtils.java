package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultFeature;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;

import java.util.Collection;

/**
 * Utility class for features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/12/13</pre>
 */

public class FeatureUtils {

    public static Feature createDefaultFeature(String name, CvTerm featureType, Range range) {

        Feature feature = new DefaultFeature(name, null, featureType);
        if (range != null){
            feature.getRanges().add(range);
        }
        return feature;
    }

    public static Feature createDefaultFeature(String name, CvTerm featureType, Collection<Range> ranges) {

        Feature feature = new DefaultFeature(name, null, featureType);
        if (ranges != null){
            feature.getRanges().addAll(ranges);
        }
        return feature;
    }

    public static ModelledFeature createModelledFeature(String name, CvTerm featureType, Range range) {

        ModelledFeature feature = new DefaultModelledFeature(name, null, featureType);
        if (range != null){
            feature.getRanges().add(range);
        }
        return feature;
    }

    public static ModelledFeature createModelledFeature(String name, CvTerm featureType, Collection<Range> ranges) {

        ModelledFeature feature = new DefaultModelledFeature(name, null, featureType);
        if (ranges != null){
            feature.getRanges().addAll(ranges);
        }
        return feature;
    }

    public static FeatureEvidence createFeatureEvidence(String name, CvTerm featureType, Range range) {

        FeatureEvidence feature = new DefaultFeatureEvidence(name, null, featureType);
        if (range != null){
            feature.getRanges().add(range);
        }
        return feature;
    }

    public static FeatureEvidence createFeatureEvidence(String name, CvTerm featureType, Collection<Range> ranges) {

        FeatureEvidence feature = new DefaultFeatureEvidence(name, null, featureType);
        if (ranges != null){
            feature.getRanges().addAll(ranges);
        }
        return feature;
    }

    public static FeatureEvidence createFeatureEvidence(String name, CvTerm featureType, CvTerm detectionMethod, Range range) {

        FeatureEvidence feature = new DefaultFeatureEvidence(name, null, featureType);
        if (detectionMethod != null){
            feature.getDetectionMethods().add(detectionMethod);
        }
        if (range != null){
            feature.getRanges().add(range);
        }
        return feature;
    }

    public static FeatureEvidence createFeatureEvidence(String name, CvTerm featureType, CvTerm detectionMethod, Collection<Range> ranges) {

        FeatureEvidence feature = new DefaultFeatureEvidence(name, null, featureType);
        if (detectionMethod != null){
            feature.getDetectionMethods().add(detectionMethod);
        }
        if (ranges != null){
            feature.getRanges().addAll(ranges);
        }
        return feature;
    }

    public static FeatureEvidence createFeatureEvidence(String name, CvTerm featureType, Collection<CvTerm> detectionMethods, Range range) {

        FeatureEvidence feature = new DefaultFeatureEvidence(name, null, featureType);
        if (detectionMethods != null){
            feature.getDetectionMethods().addAll(detectionMethods);
        }
        if (range != null){
            feature.getRanges().add(range);
        }
        return feature;
    }

    public static FeatureEvidence createFeatureEvidence(String name, CvTerm featureType, Collection<CvTerm> detectionMethods, Collection<Range> ranges) {

        FeatureEvidence feature = new DefaultFeatureEvidence(name, null, featureType);
        if (detectionMethods != null){
            feature.getDetectionMethods().addAll(detectionMethods);
        }
        if (ranges != null){
            feature.getRanges().addAll(ranges);
        }
        return feature;
    }

    /**
     *
     * @param annotation
     * @return true if the annotation is used to describe the role of a feature in an interaction
     */
    public static boolean isFeatureRole(Annotation annotation) {

        // we have an intercation dependency
        return (AnnotationUtils.doesAnnotationHaveTopic(annotation, Feature.PREREQUISITE_PTM_MI, Feature.PREREQUISITE_PTM)
                || AnnotationUtils.doesAnnotationHaveTopic(annotation, Feature.RESULTING_PTM_MI, Feature.RESULTING_PTM)
                || AnnotationUtils.doesAnnotationHaveTopic(annotation, Feature.RESULTING_CLEAVAGE_MI, Feature.RESULTING_CLEAVAGE)
                || AnnotationUtils.doesAnnotationHaveTopic(annotation, Feature.DECREASING_PTM_MI, Feature.DECREASING_PTM)
                || AnnotationUtils.doesAnnotationHaveTopic(annotation, Feature.INCREASING_PTM_MI, Feature.INCREASING_PTM)
                || AnnotationUtils.doesAnnotationHaveTopic(annotation, Feature.DISRUPTING_PTM_MI, Feature.DISRUPTING_PTM)
                || AnnotationUtils.doesAnnotationHaveTopic(annotation, Feature.OBSERVED_PTM, Feature.OBSERVED_PTM_MI));
    }
}
