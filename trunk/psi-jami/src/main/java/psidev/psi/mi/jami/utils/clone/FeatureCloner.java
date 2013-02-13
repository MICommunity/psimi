package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Feature;

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
    public static void copyAndOverrideFeatureProperties(Feature source, Feature target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());
            target.setType(source.getType());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getBindingFeatures().clear();
            target.getBindingFeatures().addAll(source.getBindingFeatures());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getRanges().clear();
            target.getRanges().addAll(source.getRanges());
        }
    }
}
