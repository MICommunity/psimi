package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.experiment.ExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic ExperimentalInteractionComparator.
 *
 * It will first compares the IMEx identifiers if bothe IMEx ids are set. If at least one IMEx id is not set, it will compare
 * the experiment using ExperimentComparator. If the experiments are the same, it will compare the parameters using ParameterComparator.
 * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
 * If the experimental interaction properties are the same, it will compare the basic interaction properties using InteractionComparator<ExperimentalParticipant>.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class ExperimentalInteractionComparator implements Comparator<ExperimentalInteraction> {

    protected InteractionComparator<ExperimentalParticipant> interactionComparator;
    protected ExperimentComparator experimentComparator;
    protected ParameterCollectionComparator parameterCollectionComparator;

    /**
     * Creates a new ExperimentalInteractionComparator.
     * @param interactionComparator : required to compare basic interaction properties
     * @param experimentComparator : required to compare experiments
     * @param parameterComparator : required to compare parameters
     */
    public ExperimentalInteractionComparator(InteractionComparator<ExperimentalParticipant> interactionComparator, ExperimentComparator experimentComparator,
                                             ParameterComparator parameterComparator){
        if (interactionComparator == null){
            throw new IllegalArgumentException("The Interaction comparator is required to compare basic interaction properties. It cannot be null");
        }
        this.interactionComparator = interactionComparator;

        if (experimentComparator == null){
            throw new IllegalArgumentException("The Experiment comparator is required to compare experiment where the interaction has been determined. It cannot be null");
        }
        this.experimentComparator = experimentComparator;

        if (parameterComparator == null){
            throw new IllegalArgumentException("The Parameter comparator is required to compare parameters of the interaction. It cannot be null");
        }
        this.parameterCollectionComparator = new ParameterCollectionComparator(parameterComparator);
    }

    public ParameterCollectionComparator getParameterCollectionComparator() {
        return parameterCollectionComparator;
    }

    public InteractionComparator<ExperimentalParticipant> getInteractionComparator() {
        return interactionComparator;
    }

    public ExperimentComparator getExperimentComparator() {
        return experimentComparator;
    }

    /**
     * It will first compares the IMEx identifiers if bothe IMEx ids are set. If at least one IMEx id is not set, it will compare
     * the experiment using ExperimentComparator. If the experiments are the same, it will compare the parameters using ParameterComparator.
     * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
     * If the experimental interaction properties are the same, it will compare the basic interaction properties using InteractionComparator<ExperimentalParticipant>.
     * @param experimentalInteraction1
     * @param experimentalInteraction2
     * @return
     */
    public int compare(ExperimentalInteraction experimentalInteraction1, ExperimentalInteraction experimentalInteraction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experimentalInteraction1 == null && experimentalInteraction2 == null){
            return EQUAL;
        }
        else if (experimentalInteraction1 == null){
            return AFTER;
        }
        else if (experimentalInteraction2 == null){
            return BEFORE;
        }
        else {
            // first compares the IMEx id
            String imex1 = experimentalInteraction1.getImexId();
            String imex2 = experimentalInteraction2.getImexId();

            if (imex1 != null && imex2 != null){
                return imex1.compareTo(imex2);
            }

            // If at least one IMEx id is not set, will compare the experiment
            Experiment exp1 = experimentalInteraction1.getExperiment();
            Experiment exp2 = experimentalInteraction2.getExperiment();

            int comp = experimentComparator.compare(exp1, exp2);
            if (comp != 0){
                return comp;
            }

            // If experiments are the same, compare parameters
            Collection<Parameter> parameters1 = experimentalInteraction1.getParameters();
            Collection<Parameter> parameters2 = experimentalInteraction2.getParameters();

            comp = parameterCollectionComparator.compare(parameters1, parameters2);
            if (comp != 0){
                return comp;
            }

            // check if inferred
            boolean inferred1 = experimentalInteraction1.isInferred();
            boolean inferred2 = experimentalInteraction2.isInferred();

            if (inferred1 == inferred2){
                return interactionComparator.compare(experimentalInteraction1, experimentalInteraction2);
            }
            else if (inferred1){
                return AFTER;
            }
            else if (inferred2){
                return BEFORE;
            }
            else {
                return interactionComparator.compare(experimentalInteraction1, experimentalInteraction2);
            }
        }
    }
}
