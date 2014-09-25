package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousCuratedExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantEvidenceComparator;

/**
 * Unambiguous exact curated InteractionEvidenceComparator.
 *
 * It will first compare the basic interaction properties using UnambiguousCuratedInteractionBaseComparator.
 * It will then compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare the negative properties.
 * A negative interaction will come after a positive interaction. it will compare
 * the experiment using UnambiguousCuratedExperimentComparator. If the experiments are the same, it will compare the participants using UnambiguousExactParticipantEvidenceComparator. Then it will compare the parameters using UnambiguousParameterComparator.
 * If the parameters are the same, it will first compare the experimental variableParameters using VariableParameterValueSetComparator and then it will compare the inferred boolean value (Inferred interactions will always come after).
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactCuratedInteractionEvidenceComparator extends InteractionEvidenceComparator {

    private static UnambiguousExactCuratedInteractionEvidenceComparator unambiguousExactCuratedExperimentalInteractionComparator;

    /**
     * Creates a new UnambiguousExactCuratedInteractionEvidenceComparator. It will use a UnambiguousCuratedInteractionBaseComparator to
     * compare basic interaction properties, UnambiguousParameterComparator to compare parameters, UnambiguousCuratedExperimentComparator to compare experiments
     */
    public UnambiguousExactCuratedInteractionEvidenceComparator() {
        super(new UnambiguousExactParticipantEvidenceComparator(), new UnambiguousCuratedInteractionBaseComparator(),
                new UnambiguousCuratedExperimentComparator(), new UnambiguousParameterComparator());
    }

    @Override
    public UnambiguousCuratedExperimentComparator getExperimentComparator() {
        return (UnambiguousCuratedExperimentComparator) this.experimentComparator;
    }

    @Override
    public UnambiguousCuratedInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousCuratedInteractionBaseComparator) super.getInteractionBaseComparator();
    }

    @Override
    /**
     * It will first compare the basic interaction properties using UnambiguousCuratedInteractionBaseComparator.
     * It will then compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare the negative properties.
     * A negative interaction will come after a positive interaction. it will compare
     * the experiment using UnambiguousCuratedExperimentComparator. If the experiments are the same, it will compare the participants using UnambiguousParticipantEvidenceComparator. Then it will compare the parameters using UnambiguousParameterComparator.
     * If the parameters are the same, it will first compare the experimental variableParameters using VariableParameterValueSetComparator and then it will compare the inferred boolean value (Inferred interactions will always come after).
     *
     *
     **/
    public int compare(InteractionEvidence interaction1, InteractionEvidence interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactCuratedInteractionEvidenceComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(InteractionEvidence interaction1, InteractionEvidence interaction2){
        if (unambiguousExactCuratedExperimentalInteractionComparator == null){
            unambiguousExactCuratedExperimentalInteractionComparator = new UnambiguousExactCuratedInteractionEvidenceComparator();
        }

        return unambiguousExactCuratedExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
