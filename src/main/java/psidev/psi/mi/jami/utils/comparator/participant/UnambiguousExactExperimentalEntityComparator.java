package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureEvidenceComparator;

/**
 * Unambiguous exact Experimental entity comparator.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactExperimentalEntityComparator extends ExperimentalEntityComparator {

    private static UnambiguousExactExperimentalEntityComparator unambiguousExactExperimentalParticipantComparator;

    /**
     * Creates a new UnambiguousExactExperimentalEntityComparator. It will use a UnambiguousExactParticipantBaseComparator to compare
     * the basic properties of a participant, a UnambiguousCvTermComparator to compare experimental roles, preparations and identification methods
     * and a UnambiguousOrganismComparator to compare expressed in organisms
     */
    public UnambiguousExactExperimentalEntityComparator() {
        super(new UnambiguousExactEntityBaseComparator(),
                new UnambiguousFeatureEvidenceComparator());
    }

    @Override
    public UnambiguousExactEntityBaseComparator getParticipantBaseComparator() {
        return (UnambiguousExactEntityBaseComparator) super.getParticipantBaseComparator();
    }

    @Override
    /**
     */
    public int compare(ExperimentalEntity experimentalParticipant1, ExperimentalEntity experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * Use UnambiguousExperimentalEntityComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalEntity experimentalParticipant1, ExperimentalEntity component2){
        if (unambiguousExactExperimentalParticipantComparator == null){
            unambiguousExactExperimentalParticipantComparator = new UnambiguousExactExperimentalEntityComparator();
        }

        return unambiguousExactExperimentalParticipantComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
