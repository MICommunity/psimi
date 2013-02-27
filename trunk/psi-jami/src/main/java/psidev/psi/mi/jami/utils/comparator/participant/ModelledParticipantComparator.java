package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic biological participant comparator.
 * It will compare the basic properties of a biological participant using ParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ModelledParticipantComparator implements Comparator<ModelledParticipant> {

    protected ParticipantInteractorComparator participantComparator;
    protected FeatureCollectionComparator featureCollectionComparator;

    /**
     * Creates a new ComponentComparator
     * @param participantComparator : the participant comparator required to compare basic participant properties
     */
    public ModelledParticipantComparator(ParticipantInteractorComparator participantComparator){
        if (participantComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantComparator = participantComparator;
        this.featureCollectionComparator = new FeatureCollectionComparator(participantComparator);
    }

    public ParticipantInteractorComparator getParticipantComparator() {
        return participantComparator;
    }

    public FeatureCollectionComparator getFeatureCollectionComparator() {
        return featureCollectionComparator;
    }

    /**
     * It will compare the basic properties of a biological participant using ParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     * @param bioParticipant1
     * @param bioParticipant2
     * @return
     */
    public int compare(ModelledParticipant bioParticipant1, ModelledParticipant bioParticipant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (bioParticipant1 == null && bioParticipant2 == null){
            return EQUAL;
        }
        else if (bioParticipant1 == null){
            return AFTER;
        }
        else if (bioParticipant2 == null){
            return BEFORE;
        }
        else {
            int comp = participantComparator.compare(bioParticipant1, bioParticipant2);

            // then compares the features
            Collection<? extends ModelledFeature> features1 = bioParticipant1.getModelledFeatures();
            Collection<? extends ModelledFeature> features2 = bioParticipant2.getModelledFeatures();

            return featureCollectionComparator.compare(features1, features2);
        }
    }
}
