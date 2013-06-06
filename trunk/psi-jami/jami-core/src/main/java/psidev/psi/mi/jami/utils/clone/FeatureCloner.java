package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ModelledFeature;

/**
 * This class will clone/copy properties of a feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/02/13</pre>
 */

public class FeatureCloner {

    /***
     * This method will copy properties of Feature source in Feature target and will override all the other properties of Target feature.
     * Only the participant is not copied
     * @param source
     * @param target
     */
    public static void copyAndOverrideFeatureEvidenceProperties(FeatureEvidence source, FeatureEvidence target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());
            target.setType(source.getType());
            target.setInteractionDependency(source.getInteractionDependency());
            target.setInteractionEffect(source.getInteractionEffect());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getLinkedFeatureEvidences().clear();
            target.getLinkedFeatureEvidences().addAll(source.getLinkedFeatureEvidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getRanges().clear();
            target.getRanges().addAll(source.getRanges());
            target.getDetectionMethods().clear();
            target.getDetectionMethods().addAll(source.getDetectionMethods());
        }
    }

    /***
     * This method will copy properties of modelled Feature source in modelled Feature target and will override all the other properties of modelled Target feature.
     * Only the participant is not copied
     * @param source
     * @param target
     */
    public static void copyAndOverrideModelledFeaturesProperties(ModelledFeature source, ModelledFeature target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());
            target.setType(source.getType());
            target.setInteractionDependency(source.getInteractionDependency());
            target.setInteractionEffect(source.getInteractionEffect());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getLinkedModelledFeatures().clear();
            target.getLinkedModelledFeatures().addAll(source.getLinkedModelledFeatures());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getRanges().clear();
            target.getRanges().addAll(source.getRanges());
        }
    }

    /***
     * This method will copy properties of Feature source in Feature target and will override all the other properties of Target feature.
     * Only the participant is not copied
     * @param source
     * @param target
     */
    public static void copyAndOverrideFeaturesProperties(Feature source, Feature target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());
            target.setType(source.getType());
            target.setInteractionDependency(source.getInteractionDependency());
            target.setInteractionEffect(source.getInteractionEffect());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getRanges().clear();
            target.getRanges().addAll(source.getRanges());
        }
    }
}
