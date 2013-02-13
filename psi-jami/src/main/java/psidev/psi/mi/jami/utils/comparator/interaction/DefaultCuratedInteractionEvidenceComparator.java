package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.experiment.DefaultExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultExperimentalParticipantComparator;

/**
 * Default curated InteractionEvidenceComparator.
 *
 * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
 * the experiment using DefaultExperimentComparator. If the experiments are the same, it will compare the parameters using DefaultParameterComparator.
 * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
 * If the experimental interaction properties are the same, it will compare the basic interaction properties using DefaultCuratedInteractionBaseComparator<ExperimentalParticipant>.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCuratedInteractionEvidenceComparator extends InteractionEvidenceComparator {

    private static DefaultCuratedInteractionEvidenceComparator defaultCuratedExperimentalInteractionComparator;

    /**
     * Creates a new DefaultCuratedInteractionEvidenceComparator. It will use a DefaultCuratedInteractionBaseComparator to
     * compare basic interaction properties, DefaultParameterComparator to compare parameters, DefaultExperimentComparator to compare experiments
     */
    public DefaultCuratedInteractionEvidenceComparator() {
        super(new CuratedInteractionBaseComparator<ExperimentalParticipant>(new DefaultExperimentalParticipantComparator(), new DefaultCvTermComparator()),
                new DefaultExperimentComparator(), new DefaultParameterComparator());
    }

    @Override
    public DefaultExperimentComparator getExperimentComparator() {
        return (DefaultExperimentComparator) this.experimentComparator;
    }

    @Override
    public CuratedInteractionBaseComparator<ExperimentalParticipant> getInteractionComparator() {
        return (CuratedInteractionBaseComparator<ExperimentalParticipant>) this.interactionComparator;
    }

    @Override
    /**
     * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
     * the experiment using DefaultExperimentComparator. If the experiments are the same, it will compare the parameters using DefaultParameterComparator.
     * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
     * If the experimental interaction properties are the same, it will compare the basic interaction properties using DefaultCuratedInteractionBaseComparator<ExperimentalParticipant>.
     *
     **/
    public int compare(InteractionEvidence interaction1, InteractionEvidence interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultCuratedInteractionEvidenceComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(InteractionEvidence interaction1, InteractionEvidence interaction2){
        if (defaultCuratedExperimentalInteractionComparator == null){
            defaultCuratedExperimentalInteractionComparator = new DefaultCuratedInteractionEvidenceComparator();
        }

        return defaultCuratedExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
