package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default experimental participant comparator based on the interactor only.
 * It will compare the basic properties of aninteractor using DefaultInteractorComparator.
 *
 * This comparator will ignore all the other properties of an experimental participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultParticipantEvidenceInteractorComparator extends ParticipantInteractorComparator<ParticipantEvidence> {

    private static DefaultParticipantEvidenceInteractorComparator defaultExperimentalParticipantInteractorComparator;

    /**
     * Creates a new DefaultParticipantEvidenceInteractorComparator. It will use a DefaultInteractorComparator to compare
     * the basic properties of a interactor.
     */
    public DefaultParticipantEvidenceInteractorComparator() {
        super(new DefaultInteractorComparator());
    }

    @Override
    public DefaultInteractorComparator getInteractorComparator() {
        return (DefaultInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the basic properties of aninteractor using DefaultInteractorComparator.
     *
     * This comparator will ignore all the other properties of an experimental participant.
     */
    public int compare(ParticipantEvidence experimentalParticipant1, ParticipantEvidence experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * Use DefaultParticipantEvidenceInteractorComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ParticipantEvidence experimentalParticipant1, ParticipantEvidence component2){
        if (defaultExperimentalParticipantInteractorComparator == null){
            defaultExperimentalParticipantInteractorComparator = new DefaultParticipantEvidenceInteractorComparator();
        }

        return defaultExperimentalParticipantInteractorComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
