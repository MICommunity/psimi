package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalExperimentEnricher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.utils.comparator.experiment.DefaultVariableParameterComparator;

import java.util.Iterator;

/**
 * Provides full enrichment of experiment.
 *
 * - enrich minimal properties of experiment. See MinimalExperimentEnricher
 * - enrich xrefs
 * - enrich annotations
 * - enrich confidences
 * - enrich variable parameters
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullExperimentEnricher extends MinimalExperimentEnricher {

    public FullExperimentEnricher(){
        super();
    }

    protected void processOtherProperties(Experiment experimentToEnrich) throws EnricherException{
        // do nothing
    }

    protected void processOtherProperties(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException{

        processXrefs(experimentToEnrich, objectSource);
        processAnnotations(experimentToEnrich, objectSource);
        processConfidences(experimentToEnrich, objectSource);
        processVariableParameters(experimentToEnrich, objectSource);

        processOtherProperties(experimentToEnrich);
    }

    protected void processXrefs(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException{
        EnricherUtils.mergeXrefs(experimentToEnrich, experimentToEnrich.getXrefs(), objectSource.getXrefs(), false, false,
                getExperimentEnricherListener(), null);
    }

    protected void processAnnotations(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException{
        EnricherUtils.mergeAnnotations(experimentToEnrich, experimentToEnrich.getAnnotations(), objectSource.getAnnotations(), false,
                getExperimentEnricherListener());
    }

    protected void processConfidences(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException{
        EnricherUtils.mergeConfidences(experimentToEnrich, experimentToEnrich.getConfidences(), objectSource.getConfidences(), false,
                getExperimentEnricherListener());
    }

    protected void processVariableParameters(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException{
        mergerVariableParameters(experimentToEnrich, objectSource, false);
    }

    protected void mergerVariableParameters(Experiment experimentToEnrich, Experiment objectSource, boolean remove){
        Iterator<VariableParameter> variableParamIterator = experimentToEnrich.getVariableParameters().iterator();
        // remove parameters in experimentToEnrich that are not in fetchedExperiment
        if (remove){
            while(variableParamIterator.hasNext()){
                VariableParameter param = variableParamIterator.next();

                boolean containsParam = false;
                for (VariableParameter param2 : objectSource.getVariableParameters()){
                    // identical parameter
                    if (DefaultVariableParameterComparator.areEquals(param, param2)){
                        containsParam = true;
                        break;
                    }
                }
                // remove parameter not in second list
                if (!containsParam){
                    param.setExperiment(null);
                    variableParamIterator.remove();
                    if (getExperimentEnricherListener() != null){
                        getExperimentEnricherListener().onRemovedVariableParameter(experimentToEnrich, param);
                    }
                }
            }
        }

        // add parameters from fetchedExperiment that are not in toEnrichExperiment
        variableParamIterator = objectSource.getVariableParameters().iterator();
        while(variableParamIterator.hasNext()){
            VariableParameter param = variableParamIterator.next();
            boolean containsParam = false;
            for (VariableParameter param2 : experimentToEnrich.getVariableParameters()){
                // identical param
                if (DefaultVariableParameterComparator.areEquals(param, param2)){
                    containsParam = true;
                    break;
                }
            }
            // add missing confidence not in second list
            if (!containsParam){
                experimentToEnrich.addVariableParameter(param);
                if (getExperimentEnricherListener() != null){
                    getExperimentEnricherListener().onAddedVariableParameter(experimentToEnrich, param);
                }
            }
        }
    }
}
