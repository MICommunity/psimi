package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * Default exact experimental participant comparator based on the interactor only.
 * It will compare the basic properties of aninteractor using DefaultExactInteractorComparator.
 *
 * This comparator will ignore all the other properties of an experimental participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultExactParticipantEvidenceInteractorComparator extends ParticipantInteractorComparator<ParticipantEvidence> {

    private static DefaultExactParticipantEvidenceInteractorComparator defaultExactExperimentalParticipantInteractorComparator;

    /**
     * Creates a new DefaultExactParticipantEvidenceInteractorComparator. It will use a DefaultExactInteractorComparator to compare
     * the basic properties of a interactor.
     */
    public DefaultExactParticipantEvidenceInteractorComparator() {
        super(new DefaultExactInteractorComparator());
    }

    @Override
    public DefaultExactInteractorComparator getInteractorComparator() {
        return (DefaultExactInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the basic properties of aninteractor using DefaultExactInteractorComparator.
     *
     * This comparator will ignore all the other properties of an experimental participant.
     */
    public int compare(ParticipantEvidence experimentalParticipant1, ParticipantEvidence experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * Use DefaultExactParticipantEvidenceInteractorComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ParticipantEvidence experimentalParticipant1, ParticipantEvidence component2){
        if (defaultExactExperimentalParticipantInteractorComparator == null){
            defaultExactExperimentalParticipantInteractorComparator = new DefaultExactParticipantEvidenceInteractorComparator();
        }

        return defaultExactExperimentalParticipantInteractorComparator.compare(experimentalParticipant1, component2) == 0;
    }
}