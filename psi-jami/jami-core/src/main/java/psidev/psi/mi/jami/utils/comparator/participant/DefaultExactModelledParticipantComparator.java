package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorBaseComparator;

/**
 * Default exact biological participant comparator.
 * It will compare the basic properties of a biological participant using DefaultExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultExactModelledParticipantComparator extends ModelledParticipantComparator {

    private static DefaultExactModelledParticipantComparator defaultExactBiologicalParticipantComparator;

    /**
     * Creates a new DefaultExactModelledParticipantComparator. It will use a DefaultExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public DefaultExactModelledParticipantComparator() {
        super(new ParticipantBaseComparator<ModelledFeature>(new DefaultInteractorBaseComparator(), new DefaultCvTermComparator(), new DefaultModelledFeatureComparator()));
    }

    @Override
    public ParticipantBaseComparator<ModelledFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<ModelledFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using DefaultExactParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(ModelledParticipant component1, ModelledParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultExactModelledParticipantComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledParticipant component1, ModelledParticipant component2){
        if (defaultExactBiologicalParticipantComparator == null){
            defaultExactBiologicalParticipantComparator = new DefaultExactModelledParticipantComparator();
        }

        return defaultExactBiologicalParticipantComparator.compare(component1, component2) == 0;
    }
}
