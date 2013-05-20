package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousParticipantEvidenceComparator;

/**
 * Unambiguous InteractionEvidenceComparator.
 *
 * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
 * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the parameters using UnambiguousParameterComparator.
 * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
 * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousInteractionBaseComparator<ParticipantEvidence>.
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
        super(new UnambiguousParticipantEvidenceComparator(), new InteractionBaseComparator(new UnambiguousCvTermComparator()),
                new UnambiguousExperimentComparator(), new UnambiguousParameterComparator());
    }

    @Override
    public UnambiguousExperimentComparator getExperimentComparator() {
        return (UnambiguousExperimentComparator) this.experimentComparator;
    }

    @Override
    /**
     * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
     * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the parameters using UnambiguousParameterComparator.
     * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
     * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousInteractionBaseComparator<ParticipantEvidence>.
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
