package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalFeature;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultExperimentalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.DefaultParameterComparator;

/**
 * Default exact Experimental participant comparator.
 *
 * It will first compares experimental roles using DefaultCvTermComparator. If both experimental roles are equals, it
 * will look at the identification methods using DefaultCvTermComparator. If both identification methods are equals, it will
 * look at the experimental preparations using DefaultCvTermComparator. If both experimental preparations are equals, it will
 * look at the expressed in organisms using DefaultOrganismComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactExperimentalParticipantComparator extends ExperimentalParticipantComparator {

    private static DefaultExactExperimentalParticipantComparator defaultExactExperimentalParticipantComparator;

    /**
     * Creates a new DefaultExactExperimentalParticipantComparator. It will use a DefaultExactParticipantComparator to compare
     * the basic properties of a participant, a DefaultCvTermComparator to compare experimental roles, preparations and identification methods
     * and a DefaultOrganismComparator to compare expressed in Organisms
     */
    public DefaultExactExperimentalParticipantComparator() {
        super(new ParticipantComparator<ExperimentalFeature>(new DefaultExactInteractorComparator(), new DefaultCvTermComparator(), new DefaultExperimentalFeatureComparator()), new DefaultCvTermComparator(), new DefaultOrganismComparator(), new DefaultParameterComparator());
    }

    @Override
    public ParticipantComparator<ExperimentalFeature> getParticipantComparator() {
        return (ParticipantComparator<ExperimentalFeature>) this.participantComparator;
    }

    @Override
    public DefaultOrganismComparator getOrganismComparator() {
        return (DefaultOrganismComparator) this.organismComparator;
    }

    @Override
    /**
     * It will first compares experimental roles using DefaultCvTermComparator. If both experimental roles are equals, it
     * will look at the identification methods using DefaultCvTermComparator. If both identification methods are equals, it will
     * look at the experimental preparations using DefaultCvTermComparator. If both experimental preparations are equals, it will
     * look at the expressed in organisms using DefaultOrganismComparator.
     */
    public int compare(ExperimentalParticipant experimentalParticipant1, ExperimentalParticipant experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * Use DefaultExactExperimentalParticipantComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalParticipant experimentalParticipant1, ExperimentalParticipant component2){
        if (defaultExactExperimentalParticipantComparator == null){
            defaultExactExperimentalParticipantComparator = new DefaultExactExperimentalParticipantComparator();
        }

        return defaultExactExperimentalParticipantComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
