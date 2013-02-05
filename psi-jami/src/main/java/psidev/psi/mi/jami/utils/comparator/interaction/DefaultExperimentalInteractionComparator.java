package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.ExperimentalInteraction;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.experiment.DefaultExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultExperimentalParticipantComparator;

/**
 * Default ExperimentalInteractionComparator.
 *
 * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
 * the experiment using DefaultExperimentComparator. If the experiments are the same, it will compare the parameters using DefaultParameterComparator.
 * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
 * If the experimental interaction properties are the same, it will compare the basic interaction properties using DefaultInteractionBaseComparator<ExperimentalParticipant>.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultExperimentalInteractionComparator extends ExperimentalInteractionComparator{

    private static DefaultExperimentalInteractionComparator defaultExperimentalInteractionComparator;

    /**
     * Creates a new DefaultExperimentalInteractionComparator. It will use a DefaultExactInteractionBaseComparator to
     * compare basic interaction properties, DefaultParameterComparator to compare parameters, DefaultExperimentComparator to compare experiments
     */
    public DefaultExperimentalInteractionComparator() {
        super(new InteractionBaseComparator<ExperimentalParticipant>(new DefaultExperimentalParticipantComparator(), new DefaultCvTermComparator()),
                new DefaultExperimentComparator(), new DefaultParameterComparator());
    }

    @Override
    public DefaultExperimentComparator getExperimentComparator() {
        return (DefaultExperimentComparator) this.experimentComparator;
    }

    @Override
    /**
     * It will first compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare
     * the experiment using DefaultExperimentComparator. If the experiments are the same, it will compare the parameters using DefaultParameterComparator.
     * If the parameters are the same, it will compare the inferred boolean value (Inferred interactions will always come after).
     * If the experimental interaction properties are the same, it will compare the basic interaction properties using DefaultInteractionBaseComparator<ExperimentalParticipant>.
     **/
    public int compare(ExperimentalInteraction interaction1, ExperimentalInteraction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use DefaultExperimentalInteractionComparator to know if two experimental interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(ExperimentalInteraction interaction1, ExperimentalInteraction interaction2){
        if (defaultExperimentalInteractionComparator == null){
            defaultExperimentalInteractionComparator = new DefaultExperimentalInteractionComparator();
        }

        return defaultExperimentalInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
