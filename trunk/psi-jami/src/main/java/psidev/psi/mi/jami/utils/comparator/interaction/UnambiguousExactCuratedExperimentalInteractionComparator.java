package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ExperimentalInteraction;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactExperimentalParticipantComparator;

/**
 * Unambiguous exact curated ExperimentalInteractionComparator.
 *
 * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
 * the experiment using UnambiguousExperimentComparator. If the experiments are the same, it will compare the parameters using UnambiguousParameterComparator.
 * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
 * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousExactCuratedInteractionBaseComparator<ExperimentalParticipant>.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class UnambiguousExactCuratedExperimentalInteractionComparator extends ExperimentalInteractionComparator {

    private static UnambiguousExactCuratedExperimentalInteractionComparator unambiguousExactCuratedExperimentalInteractionComparator;

    /**
     * Creates a new UnambiguousExactCuratedExperimentalInteractionComparator. It will use a UnambiguousExactCuratedInteractionBaseComparator to
     * compare basic interaction properties, UnambiguousParameterComparator to compare parameters, UnambiguousExperimentComparator to compare experiments
     */
    public UnambiguousExactCuratedExperimentalInteractionComparator() {
        super(new CuratedInteractionBaseComparator<ExperimentalParticipant>(new UnambiguousExactExperimentalParticipantComparator(), new UnambiguousCvTermComparator()),
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
     * If the experimental interaction properties are the same, it will compare the basic interaction properties using UnambiguousExactCuratedInteractionBaseComparator<ExperimentalParticipant>.
     *
     *
     **/
    public int compare(ExperimentalInteraction interaction1, ExperimentalInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactCuratedExperimentalInteractionComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(ExperimentalInteraction interaction1, ExperimentalInteraction interaction2){
        if (unambiguousExactCuratedExperimentalInteractionComparator == null){
            unambiguousExactCuratedExperimentalInteractionComparator = new UnambiguousExactCuratedExperimentalInteractionComparator();
        }

        return unambiguousExactCuratedExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
