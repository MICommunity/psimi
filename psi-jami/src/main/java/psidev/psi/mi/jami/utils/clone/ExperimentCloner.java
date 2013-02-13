package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Experiment;

/**
 * Utility class for cloning experiments
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ExperimentCloner {

    /***
     * This method will copy properties of experiment source in experiment target and will override all the other properties of Target experiment.
     * This method will ignore interaction evidences
     * @param source
     * @param target
     */
    public static void copyAndOverrideExperimentProperties(Experiment source, Experiment target){
        if (source != null && target != null){
            target.setHostOrganism(source.getHostOrganism());
            target.setInteractionDetectionMethod(source.getInteractionDetectionMethod());
            target.setPublication(source.getPublication());
            target.setShortLabel(source.getShortLabel());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
        }
    }
}
