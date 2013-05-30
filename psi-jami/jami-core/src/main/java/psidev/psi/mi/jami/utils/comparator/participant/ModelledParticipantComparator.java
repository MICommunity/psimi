package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.ModelledFeatureComparator;

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

    protected ParticipantBaseComparator participantBaseComparator;
    protected FeatureCollectionComparator featureCollectionComparator;

    /**
     * Creates a new ComponentComparator
     */
    public ModelledParticipantComparator(ModelledFeatureComparator featureComparator){

        if (featureComparator == null){
            throw new IllegalArgumentException("The modelled feature comparator is required to compare modelled features. It cannot be null");
        }
        this.featureCollectionComparator = new FeatureCollectionComparator(featureComparator);
    }

    public ParticipantBaseComparator getParticipantBaseComparator() {
        return participantBaseComparator;
    }

    public void setParticipantBaseComparator(ParticipantBaseComparator participantBaseComparator) {
        this.participantBaseComparator = participantBaseComparator;
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
        if (participantBaseComparator == null){
            throw new IllegalStateException("The participant base comparator is required to compare basic participant properties. It cannot be null");
        }
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
            int comp = participantBaseComparator.compare(bioParticipant1, bioParticipant2);
            if (comp != 0){
                return comp;
            }

            // then compares the features
            Collection<ModelledFeature> features1 = bioParticipant1.getModelledFeatures();
            Collection<ModelledFeature> features2 = bioParticipant2.getModelledFeatures();

            return featureCollectionComparator.compare(features1, features2);
        }
    }
}
