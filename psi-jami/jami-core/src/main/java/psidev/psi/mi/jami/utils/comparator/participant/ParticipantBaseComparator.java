package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.InteractorBaseComparator;

import java.util.Comparator;

/**
 * Basic participant comparator.
 * It will first compare the interactors using InteractorComparator. If both interactors are the same,
 * it will compare the biological roles using AbstractCvTermComparator. If both biological roles are the same, it
 * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
 * it will compare the features using a Comparator<Feature>.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class ParticipantBaseComparator<T extends Feature> extends ParticipantInteractorComparator {

    protected AbstractCvTermComparator cvTermComparator;
    protected StoichiometryComparator stoichiometryComparator;

    /**
     * Creates a new ParticipantBaseComparator
     * @param interactorComparator : interactor comparator required for comparing the molecules
     * @param cvTermComparator : CvTerm comparator required for comparing biological roles
     * @param featureComparator : FeatureBaseComparator required for comparing participant features
     */
    public ParticipantBaseComparator(InteractorBaseComparator interactorComparator, AbstractCvTermComparator cvTermComparator,
                                     Comparator<T> featureComparator){

        super(interactorComparator);

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare biological roles. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
        if (featureComparator == null){
            throw new IllegalArgumentException("The feature comparator is required to compare participant features. It cannot be null");
        }

        this.stoichiometryComparator = new StoichiometryComparator();
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public StoichiometryComparator getStoichiometryComparator() {
        return stoichiometryComparator;
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

        if (participant1 == null && participant2 == null){
            return EQUAL;
        }
        else if (participant1 == null){
            return AFTER;
        }
        else if (participant2 == null){
            return BEFORE;
        }
        else {
            // first compares interactors
            Interactor interactor1 = participant1.getInteractor();
            Interactor interactor2 = participant2.getInteractor();

            int comp = interactorComparator.compare(interactor1, interactor2);
            if (comp != 0){
                return comp;
            }

            // then compares the biological role
            CvTerm role1 = participant1.getBiologicalRole();
            CvTerm role2 = participant2.getBiologicalRole();

            comp = cvTermComparator.compare(role1, role2);
            if (comp != 0){
                return comp;
            }

            // then compares the stoichiometry
            Stoichiometry stc1 = participant1.getStoichiometry();
            Stoichiometry stc2 = participant2.getStoichiometry();

            return stoichiometryComparator.compare(stc1, stc2);
        }
    }
}
