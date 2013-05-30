package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultFeatureEvidenceComparator;
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

public class DefaultExactParticipantEvidenceComparator extends ParticipantEvidenceComparator {

    private static DefaultExactParticipantEvidenceComparator defaultExactExperimentalParticipantComparator;

    /**
     * Creates a new DefaultExactParticipantEvidenceComparator. It will use a DefaultExactParticipantBaseComparator to compare
     * the basic properties of a participant, a DefaultCvTermComparator to compare experimental roles, preparations and identification methods
     * and a DefaultOrganismComparator to compare expressed in Organisms
     */
    public DefaultExactParticipantEvidenceComparator() {
        super(new DefaultExactParticipantBaseComparator(), new DefaultOrganismComparator(), new DefaultParameterComparator(), new DefaultFeatureEvidenceComparator());
    }

    @Override
    public DefaultExactParticipantBaseComparator getParticipantBaseComparator() {
        return (DefaultExactParticipantBaseComparator) this.participantBaseComparator;
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
    public int compare(ParticipantEvidence experimentalParticipant1, ParticipantEvidence experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * Use DefaultExactParticipantEvidenceComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ParticipantEvidence experimentalParticipant1, ParticipantEvidence component2){
        if (defaultExactExperimentalParticipantComparator == null){
            defaultExactExperimentalParticipantComparator = new DefaultExactParticipantEvidenceComparator();
        }

        return defaultExactExperimentalParticipantComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
