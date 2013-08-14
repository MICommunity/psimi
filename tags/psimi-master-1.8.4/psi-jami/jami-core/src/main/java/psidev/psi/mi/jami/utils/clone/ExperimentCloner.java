package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameter;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValue;

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

            // for variableParameters, need to create new ones
            for (VariableParameter p : source.getVariableParameters()){
                VariableParameter newParam = new DefaultVariableParameter(p.getDescription(), target, p.getUnit());
                for (VariableParameterValue v : p.getVariableValues()){
                    newParam.getVariableValues().add(new DefaultVariableParameterValue(v.getValue(), newParam, v.getOrder()));
                }
                target.getVariableParameters().add(newParam);
            }
        }
    }
}
