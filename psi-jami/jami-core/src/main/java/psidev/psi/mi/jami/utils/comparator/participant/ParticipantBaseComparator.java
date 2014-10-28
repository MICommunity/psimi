package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

import java.util.Comparator;

/**
 * Basic participant comparator.
 * It will first compare the interactors/stoichiometry/features using EntityBaseComparator. If both interactors are the same,
 * it will compare the biological roles using AbstractCvTermComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class ParticipantBaseComparator implements Comparator<Participant> {

    private Comparator<CvTerm> cvTermComparator;
    private EntityBaseComparator entityComparator;

    /**
     * Creates a new ParticipantBaseComparator
     * @param interactorComparator : interactor comparator required for comparing the molecules
     * @param cvTermComparator : CvTerm comparator required for comparing biological roles
     */
    public ParticipantBaseComparator(EntityBaseComparator interactorComparator, Comparator<CvTerm> cvTermComparator){

        if (interactorComparator == null){
            throw new IllegalArgumentException("The Entity base comparator is required to compare interactors, stoichiometry and features. It cannot be null");
        }
        this.entityComparator = interactorComparator;

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare biological roles. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
    }

    public Comparator<CvTerm> getCvTermComparator() {
        return cvTermComparator;
    }

    public EntityBaseComparator getEntityBaseComparator() {
        return entityComparator;
    }

    public boolean isIgnoreInteractors() {
        return entityComparator.isIgnoreInteractors();
    }

    public void setIgnoreInteractors(boolean ignoreInteractors) {
        this.entityComparator.setIgnoreInteractors(ignoreInteractors);
    }

    /**
     * It will first compare the interactors using InteractorComparator. If both interactors are the same,
     * it will compare the biological roles using AbstractCvTermComparator. If both biological roles are the same, it
     * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
     * it will compare the features using a Comparator<Feature>.
     *
     * This comparator will ignore all the other properties of a participant.
     * @param participant1
     * @param participant2
     * @return
     */
    public int compare(Participant participant1, Participant participant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (participant1 == participant2){
            return EQUAL;
        }
        else if (participant1 == null){
            return AFTER;
        }
        else if (participant2 == null){
            return BEFORE;
        }
        else {
            int comp = this.entityComparator.compare(participant1, participant2);
            // first compares interactors
            if (comp != 0){
                return comp;
            }

            // then compares the biological role
            CvTerm role1 = participant1.getBiologicalRole();
            CvTerm role2 = participant2.getBiologicalRole();

            return cvTermComparator.compare(role1, role2);
        }
    }
}
