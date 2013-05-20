package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.ComponentFeature;
import psidev.psi.mi.jami.model.FeatureEvidence;

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

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getBindingSiteEvidences().clear();
            target.addAllBindingSiteEvidences(source.getBindingSiteEvidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getRanges().clear();
            target.getRanges().addAll(source.getRanges());
        }
    }

    public static void copyAndOverrideComponentFeaturesProperties(ComponentFeature source, ComponentFeature target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());
            target.setType(source.getType());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getBindingSites().clear();
            target.addAllBindingSites(source.getBindingSites());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getRanges().clear();
            target.getRanges().addAll(source.getRanges());
        }
    }
}
