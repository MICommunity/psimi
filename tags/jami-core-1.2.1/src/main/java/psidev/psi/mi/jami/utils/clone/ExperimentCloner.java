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
     * It will set the publication but will not add this experiment to the list of experiments of the publication reported in the source experiment
     * It will fully clone all the VariableParameters and set their experiment to the current experiment target
     * @param source
     * @param target
     */
    public static void copyAndOverrideExperimentProperties(Experiment source, Experiment target){
        if (source != null && target != null){
            target.setHostOrganism(source.getHostOrganism());
            target.setInteractionDetectionMethod(source.getInteractionDetectionMethod());
            target.setPublication(source.getPublication());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getConfidences().clear();
            target.getConfidences().addAll(source.getConfidences());
            target.getVariableParameters().clear();
            target.addAllVariableParameters(source.getVariableParameters());
        }
    }

    /***
     * This method will copy properties of experiment source in experiment target and will override all the other properties of Target experiment.
     * This method will also move interaction evidences from source to target
     * It will set the publication but will not add this experiment to the list of experiments of the publication reported in the source experiment
     * It will fully clone all the VariableParameters and set their experiment to the current experiment target
     * @param source
     * @param target
     */
    public static void copyAndOverrideExperimentPropertiesAndInteractionEvidences(Experiment source, Experiment target){
        if (source != null && target != null){
            target.setHostOrganism(source.getHostOrganism());
            target.setInteractionDetectionMethod(source.getInteractionDetectionMethod());
            target.setPublication(source.getPublication());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getConfidences().clear();
            target.getConfidences().addAll(source.getConfidences());
            target.getVariableParameters().clear();
            target.addAllVariableParameters(source.getVariableParameters());
            target.getInteractionEvidences().clear();
            target.addAllInteractionEvidences(source.getInteractionEvidences());
        }
    }
}
