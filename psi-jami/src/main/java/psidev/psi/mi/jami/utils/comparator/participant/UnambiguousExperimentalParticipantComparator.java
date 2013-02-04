package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalFeature;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousExperimentalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorComparator;
import psidev.psi.mi.jami.utils.comparator.organism.UnambiguousOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;

/**
 * Unambiguous Experimental participant comparator.
 *
 * It will first compares experimental roles using UnambiguousCvTermComparator. If both experimental roles are equals, it
 * will look at the identification methods using UnambiguousCvTermComparator. If both identification methods are equals, it will
 * look at the experimental preparations using UnambiguousCvTermComparator. If both experimental preparations are equals, it will
 * look at the expressed in organisms using UnambiguousOrganismComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExperimentalParticipantComparator extends ExperimentalParticipantComparator{

    private static UnambiguousExperimentalParticipantComparator unambiguousExperimentalParticipantComparator;

    /**
     * Creates a new UnambiguousExperimentalParticipantComparator. It will use a UnambiguousParticipantBaseComparator to compare
     * the basic properties of a participant, a UnambiguousCvTermComparator to compare experimental roles, preparations and identification methods
     * and a UnambiguousOrganismComparator to compare expressed in organisms
     */
    public UnambiguousExperimentalParticipantComparator() {
        super(new ParticipantBaseComparator<ExperimentalFeature>(new UnambiguousInteractorComparator(), new UnambiguousCvTermComparator(), new UnambiguousExperimentalFeatureComparator()), new UnambiguousCvTermComparator(), new UnambiguousOrganismComparator(), new UnambiguousParameterComparator());
    }

    @Override
    public ParticipantBaseComparator<ExperimentalFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<ExperimentalFeature>) this.participantComparator;
    }

    @Override
    public UnambiguousOrganismComparator getOrganismComparator() {
        return (UnambiguousOrganismComparator) this.organismComparator;
    }

    @Override
    /**
     * It will first compares experimental roles using UnambiguousCvTermComparator. If both experimental roles are equals, it
     * will look at the identification methods using UnambiguousCvTermComparator. If both identification methods are equals, it will
     * look at the experimental preparations using UnambiguousCvTermComparator. If both experimental preparations are equals, it will
     * look at the expressed in organisms using UnambiguousOrganismComparator.
     */
    public int compare(ExperimentalParticipant experimentalParticipant1, ExperimentalParticipant experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * Use UnambiguousExperimentalParticipantComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalParticipant experimentalParticipant1, ExperimentalParticipant component2){
        if (unambiguousExperimentalParticipantComparator == null){
            unambiguousExperimentalParticipantComparator = new UnambiguousExperimentalParticipantComparator();
        }

        return unambiguousExperimentalParticipantComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
