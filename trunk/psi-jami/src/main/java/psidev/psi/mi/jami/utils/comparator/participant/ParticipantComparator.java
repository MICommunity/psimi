package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.InteractorComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic participant comparator.
 * It will first compare the interactors using InteractorComparator. If both interactors are the same,
 * it will compare the biological roles using AbstractCvTermComparator. If both biological roles are the same, it
 * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
 * it will compare the features using a Comparator<Feature>. If both participants have the same features, it will look at
 * the participant parameters using ParameterComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class ParticipantComparator<T extends Feature> extends ParticipantInteractorComparator {

    protected AbstractCvTermComparator cvTermComparator;
    protected FeatureCollectionComparator featureCollectionComparator;
    protected ParameterCollectionComparator parameterCollectionComparator;

    /**
     * Creates a new ParticipantComparator
     * @param interactorComparator : interactor comparator required for comparing the molecules
     * @param cvTermComparator : CvTerm comparator required for comparing biological roles
     * @param featureComparator : FeatureComparator required for comparing participant features
     * @param parameterComparator: ParameterComparator required for comparing participant features
     */
    public ParticipantComparator(InteractorComparator interactorComparator, AbstractCvTermComparator cvTermComparator,
                                 Comparator<T> featureComparator, ParameterComparator parameterComparator){

        super(interactorComparator);

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare biological roles. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
        if (featureComparator == null){
            throw new IllegalArgumentException("The feature comparator is required to compare participant features. It cannot be null");
        }
        this.featureCollectionComparator = new FeatureCollectionComparator(featureComparator);
        if (parameterComparator == null){
            throw new IllegalArgumentException("The parameter comparator is required to compare participant parameters. It cannot be null");
        }
        this.parameterCollectionComparator = new ParameterCollectionComparator(parameterComparator);
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public FeatureCollectionComparator getFeatureCollectionComparator() {
        return featureCollectionComparator;
    }

    public ParameterCollectionComparator getParameterCollectionComparator() {
        return parameterCollectionComparator;
    }

    /**
     * It will first compare the interactors using InteractorComparator. If both interactors are the same,
     * it will compare the biological roles using AbstractCvTermComparator. If both biological roles are the same, it
     * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
     * it will compare the features using a Comparator<Feature>. If both participants have the same features, it will look at
     * the participant parameters using ParameterComparator.
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
            int stc1 = participant1.getStoichiometry();
            int stc2 = participant2.getStoichiometry();

            if (stc1 < stc2){
                return BEFORE;
            }
            else if (stc1 > stc2){
                return AFTER;
            }

            // then compares the features
            Collection<T> features1 = participant1.getFeatures();
            Collection<T> features2 = participant2.getFeatures();

            comp = featureCollectionComparator.compare(features1, features2);
            if (comp != 0){
                return comp;
            }

            // then compares the parameters
            Collection<Parameter> param1 = participant1.getParameters();
            Collection<Parameter> param2 = participant2.getParameters();

            return parameterCollectionComparator.compare(param1, param2);
        }
    }
}
