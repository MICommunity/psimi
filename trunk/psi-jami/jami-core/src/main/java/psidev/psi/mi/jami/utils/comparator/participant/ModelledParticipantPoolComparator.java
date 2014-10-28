package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.ModelledFeatureComparator;

import java.util.*;

/**
 * Basic biological participant pool comparator.
 * It will compare the basic properties of a biological participant pool using ParticipantBaseComparator.
 * Then features will be compared with ModelledFeatureComparator.
 *
 * All the participant candidates will be compared using ModelledEntityComparator
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ModelledParticipantPoolComparator implements Comparator<ModelledParticipantPool> {

    private ParticipantBaseComparator participantBaseComparator;
    private CollectionComparator<ModelledFeature> featureCollectionComparator;
    private Comparator<ModelledEntity> modelledEntityComparator;
    private CollectionComparator<ModelledEntity> modelledEntityCollectionComparator;

    /**
     * Creates a new ComponentComparator
     */
    public ModelledParticipantPoolComparator(ParticipantBaseComparator participantBaseComparator,
                                             ModelledFeatureComparator featureComparator,
                                             ModelledEntityComparator entityComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantBaseComparator = participantBaseComparator;
        if (featureComparator == null){
            throw new IllegalArgumentException("The modelled feature comparator is required to compare modelled features. It cannot be null");
        }
        this.featureCollectionComparator = new FeatureCollectionComparator(featureComparator);
        if (entityComparator == null){
            throw new IllegalArgumentException("The modelled entity comparator is required to compare participant candidates of a pool. It cannot be null");
        }
        this.modelledEntityComparator = entityComparator;
        this.modelledEntityCollectionComparator = new CollectionComparator<ModelledEntity>(this.modelledEntityComparator);
    }

    /**
     * Creates a new ComponentComparator
     */
    public ModelledParticipantPoolComparator(ParticipantBaseComparator participantBaseComparator,
                                             CollectionComparator<ModelledFeature> featureComparator,
                                             CollectionComparator<ModelledEntity> entityComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantBaseComparator = participantBaseComparator;
        if (featureComparator == null){
            throw new IllegalArgumentException("The modelled feature comparator is required to compare modelled features. It cannot be null");
        }
        this.featureCollectionComparator = featureComparator;
        if (entityComparator == null){
            throw new IllegalArgumentException("The modelled entity comparator is required to compare participant candidates of a pool. It cannot be null");
        }
        this.modelledEntityCollectionComparator = entityComparator;
        this.modelledEntityComparator = entityComparator.getObjectComparator();

    }

    public Comparator<ModelledEntity> getModelledEntityComparator() {
        return modelledEntityComparator;
    }

    public ParticipantBaseComparator getParticipantBaseComparator() {
        return participantBaseComparator;
    }

    public CollectionComparator<ModelledFeature> getFeatureCollectionComparator() {
        return featureCollectionComparator;
    }

    /**
     * It will compare the basic properties of a biological participant pool using ParticipantBaseComparator.
     * Then features will be compared with ModelledFeatureComparator.
     *
     * All the participant candidates will be compared using ModelledEntityComparator
     *
     * This comparator will ignore all the other properties of a biological participant.
     * @param bioParticipant1
     * @param bioParticipant2
     * @return
     */
    public int compare(ModelledParticipantPool bioParticipant1, ModelledParticipantPool bioParticipant2) {
        if (participantBaseComparator == null || this.modelledEntityComparator == null){
            throw new IllegalStateException("The participant base comparator is required to compare basic participant properties. It cannot be null");
        }
        else{
            participantBaseComparator.setIgnoreInteractors(true);
        }

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (bioParticipant1 == bioParticipant2){
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
            Collection<ModelledFeature> features1 = bioParticipant1.getFeatures();
            Collection<ModelledFeature> features2 = bioParticipant2.getFeatures();

            comp = featureCollectionComparator.compare(features1, features2);
            if (comp != 0){
                return comp;
            }

            return this.modelledEntityCollectionComparator.compare(bioParticipant1, bioParticipant2);
        }
    }
}
