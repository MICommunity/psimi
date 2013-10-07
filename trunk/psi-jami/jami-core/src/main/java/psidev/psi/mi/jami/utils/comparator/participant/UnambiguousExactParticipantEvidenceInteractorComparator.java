package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactComplexComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

/**
 * Unambiguous exact experimental participant comparator based on the interactor only.
 * It will compare the basic properties of an interactor using UnambiguousExactInteractorComparator.
 *
 * This comparator will ignore all the other properties of an experimental participant.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactParticipantEvidenceInteractorComparator extends ParticipantInteractorComparator<ExperimentalEntity> {
    private static UnambiguousExactParticipantEvidenceInteractorComparator unambiguousExactExperimentalParticipantInteractorComparator;

    /**
     * Creates a new UnambiguousExactParticipantEvidenceInteractorComparator. It will use a UnambiguousExactInteractorComparator to compare
     * the basic properties of a interactor.
     */
    public UnambiguousExactParticipantEvidenceInteractorComparator() {
        super(new UnambiguousExactInteractorComparator(new UnambiguousExactComplexComparator(new UnambiguousExactModelledParticipantInteractorComparator())));
    }

    @Override
    public UnambiguousExactInteractorComparator getInteractorComparator() {
        return (UnambiguousExactInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the basic properties of an interactor using UnambiguousInteractorComparator.
     *
     * This comparator will ignore all the other properties of an experimental participant.
     */
    public int compare(ExperimentalEntity experimentalParticipant1, ExperimentalEntity experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * It will compare the basic properties of an interactor using UnambiguousExactInteractorComparator.
     *
     * This comparator will ignore all the other properties of an experimental participant.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalEntity experimentalParticipant1, ExperimentalEntity component2){
        if (unambiguousExactExperimentalParticipantInteractorComparator == null){
            unambiguousExactExperimentalParticipantInteractorComparator = new UnambiguousExactParticipantEvidenceInteractorComparator();
        }

        return unambiguousExactExperimentalParticipantInteractorComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
