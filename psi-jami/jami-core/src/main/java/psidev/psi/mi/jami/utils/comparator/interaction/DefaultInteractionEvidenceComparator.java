package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.comparator.experiment.DefaultExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultParticipantEvidenceComparator;

/**
 * Default InteractionEvidenceComparator.
 *
 * It will first compare the basic interaction properties using DefaultInteractionBaseComparator.
 * It will then compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare the negative properties.
 * A negative interaction will come after a positive interaction. it will compare
 * the experiment using DefaultExperimentComparator. If the experiments are the same, it will compare the participants using DefaultParticipantEvidenceComparator. Then, it will compare the parameters using DefaultParameterComparator.
 * If the parameters are the same, it will first compare the experimental variableParameters using VariableParameterValueSetComparator and then it will compare the inferred boolean value (Inferred interactions will always come after).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultInteractionEvidenceComparator extends InteractionEvidenceComparator {

    private static DefaultInteractionEvidenceComparator defaultExperimentalInteractionComparator;

    /**
     * Creates a new DefaultInteractionEvidenceComparator. It will use a DefaultExactInteractionBaseComparator to
     * compare basic interaction properties, DefaultParameterComparator to compare parameters, DefaultExperimentComparator to compare experiments
     */
    public DefaultInteractionEvidenceComparator() {
        super(new DefaultParticipantEvidenceComparator(), new DefaultInteractionBaseComparator(),
                new DefaultExperimentComparator(), new DefaultParameterComparator());
    }

    @Override
    public DefaultExperimentComparator getExperimentComparator() {
        return (DefaultExperimentComparator) this.experimentComparator;
    }

    @Override
    public DefaultInteractionBaseComparator getInteractionBaseComparator() {
        return (DefaultInteractionBaseComparator) super.getInteractionBaseComparator();
    }

    @Override
    /**
     * It will first compare the basic interaction properties using DefaultInteractionBaseComparator.
     * It will then compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare the negative properties.
     * A negative interaction will come after a positive interaction. it will compare
     * the experiment using DefaultExperimentComparator. If the experiments are the same, it will compare the participants using DefaultParticipantEvidenceComparator. Then, it will compare the parameters using DefaultParameterComparator.
     * If the parameters are the same, it will first compare the experimental variableParameters using VariableParameterValueSetComparator and then it will compare the inferred boolean value (Inferred interactions will always come after).
     **/
    public int compare(InteractionEvidence interaction1, InteractionEvidence interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultInteractionEvidenceComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(InteractionEvidence interaction1, InteractionEvidence interaction2){
        if (defaultExperimentalInteractionComparator == null){
            defaultExperimentalInteractionComparator = new DefaultInteractionEvidenceComparator();
        }

        return defaultExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
