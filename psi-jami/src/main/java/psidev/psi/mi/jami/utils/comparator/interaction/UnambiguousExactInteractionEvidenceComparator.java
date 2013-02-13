package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantEvidenceComparator;

/**
 * Unambiguous exact InteractionEvidenceComparator.
 *
 * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
 * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the parameters using UnambiguousParameterComparator.
 * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
 * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousExactInteractionBaseComparator<ParticipantEvidence>.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousExactInteractionEvidenceComparator extends InteractionEvidenceComparator {

    private static UnambiguousExactInteractionEvidenceComparator unambiguousExactExperimentalInteractionComparator;

    /**
     * Creates a new UnambiguousExactInteractionEvidenceComparator. It will use a UnambiguousExactInteractionBaseComparator to
     * compare basic interaction properties, UnambiguousParameterComparator to compare parameters, UnambiguousExperimentComparator to compare experiments
     */
    public UnambiguousExactInteractionEvidenceComparator() {
        super(new InteractionBaseComparator<ParticipantEvidence>(new UnambiguousExactParticipantEvidenceComparator(), new UnambiguousCvTermComparator()),
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
     * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousExactInteractionBaseComparator<ParticipantEvidence>.
     *
     *
     **/
    public int compare(InteractionEvidence interaction1, InteractionEvidence interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactInteractionEvidenceComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(InteractionEvidence interaction1, InteractionEvidence interaction2){
        if (unambiguousExactExperimentalInteractionComparator == null){
            unambiguousExactExperimentalInteractionComparator = new UnambiguousExactInteractionEvidenceComparator();
        }

        return unambiguousExactExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
