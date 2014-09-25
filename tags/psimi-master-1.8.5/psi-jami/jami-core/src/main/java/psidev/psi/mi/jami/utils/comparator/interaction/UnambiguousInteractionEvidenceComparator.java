package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousParticipantEvidenceComparator;

/**
 * Unambiguous InteractionEvidenceComparator.
 *
 * It will first compare the basic interaction properties using UnambiguousInteractionBaseComparator.
 * It will then compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare the negative properties.
 * A negative interaction will come after a positive interaction. it will compare
 * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the participants using UnambiguousParticipantEvidenceComparator. Then it will compare the parameters using UnambiguousParameterComparator.
 * If the parameters are the same, it will first compare the experimental variableParameters using VariableParameterValueSetComparator and then it will compare the inferred boolean value (Inferred interactions will always come after).
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousInteractionEvidenceComparator extends InteractionEvidenceComparator {

    private static UnambiguousInteractionEvidenceComparator unambiguousExperimentalInteractionComparator;

    /**
     * Creates a new UnambiguousInteractionEvidenceComparator. It will use a UnambiguousInteractionBaseComparator to
     * compare basic interaction properties, UnambiguousParameterComparator to compare parameters, UnambiguousExperimentComparator to compare experiments
     */
    public UnambiguousInteractionEvidenceComparator() {
        super(new UnambiguousParticipantEvidenceComparator(), new UnambiguousInteractionBaseComparator(),
                new UnambiguousExperimentComparator(), new UnambiguousParameterComparator());
    }

    @Override
    public UnambiguousExperimentComparator getExperimentComparator() {
        return (UnambiguousExperimentComparator) this.experimentComparator;
    }

    @Override
    public UnambiguousInteractionBaseComparator getInteractionBaseComparator() {
        return ( UnambiguousInteractionBaseComparator) super.getInteractionBaseComparator();
    }

    @Override
    /**
     * It will first compare the basic interaction properties using UnambiguousInteractionBaseComparator.
     * It will then compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare the negative properties.
     * A negative interaction will come after a positive interaction. it will compare
     * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the participants using UnambiguousParticipantEvidenceComparator. Then it will compare the parameters using UnambiguousParameterComparator.
     * If the parameters are the same, it will first compare the experimental variableParameters using VariableParameterValueSetComparator and then it will compare the inferred boolean value (Inferred interactions will always come after).
     *
     *
     **/
    public int compare(InteractionEvidence interaction1, InteractionEvidence interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousInteractionEvidenceComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(InteractionEvidence interaction1, InteractionEvidence interaction2){
        if (unambiguousExperimentalInteractionComparator == null){
            unambiguousExperimentalInteractionComparator = new UnambiguousInteractionEvidenceComparator();
        }

        return unambiguousExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
