package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExperimentalParticipantComparator;

/**
 * Unambiguous curated InteractionEvidenceComparator.
 *
 * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
 * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the parameters using UnambiguousParameterComparator.
 * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
 * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousCuratedInteractionBaseComparator<ExperimentalParticipant>.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousCuratedInteractionEvidenceComparator extends InteractionEvidenceComparator {

    private static UnambiguousCuratedInteractionEvidenceComparator unambiguousCuratedExperimentalInteractionComparator;

    /**
     * Creates a new UnambiguousCuratedInteractionEvidenceComparator. It will use a UnambiguousCuratedInteractionBaseComparator to
     * compare basic interaction properties, UnambiguousParameterComparator to compare parameters, UnambiguousExperimentComparator to compare experiments
     */
    public UnambiguousCuratedInteractionEvidenceComparator() {
        super(new CuratedInteractionBaseComparator<ExperimentalParticipant>(new UnambiguousExperimentalParticipantComparator(), new UnambiguousCvTermComparator()),
                new UnambiguousExperimentComparator(), new UnambiguousParameterComparator());
    }

    @Override
    public UnambiguousExperimentComparator getExperimentComparator() {
        return (UnambiguousExperimentComparator) this.experimentComparator;
    }

    @Override
    public CuratedInteractionBaseComparator<ExperimentalParticipant> getInteractionComparator() {
        return (CuratedInteractionBaseComparator<ExperimentalParticipant>) this.interactionComparator;
    }

    @Override
    /**
     * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
     * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the parameters using UnambiguousParameterComparator.
     * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
     * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousCuratedInteractionBaseComparator<ExperimentalParticipant>.
     *
     *
     **/
    public int compare(InteractionEvidence interaction1, InteractionEvidence interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousCuratedInteractionEvidenceComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(InteractionEvidence interaction1, InteractionEvidence interaction2){
        if (unambiguousCuratedExperimentalInteractionComparator == null){
            unambiguousCuratedExperimentalInteractionComparator = new UnambiguousCuratedInteractionEvidenceComparator();
        }

        return unambiguousCuratedExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
