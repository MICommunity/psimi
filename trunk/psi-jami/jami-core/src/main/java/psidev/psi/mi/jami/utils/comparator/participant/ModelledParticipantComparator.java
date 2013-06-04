package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.ModelledFeatureComparator;

import java.util.Collection;

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

public class ModelledParticipantComparator implements CustomizableModelledParticipantComparator {

    protected ParticipantBaseComparator participantBaseComparator;
    protected FeatureCollectionComparator featureCollectionComparator;

    protected boolean checkComplexesAsInteractors = true;

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

    public boolean isCheckComplexesAsInteractors() {
        return checkComplexesAsInteractors;
    }

    public void setCheckComplexesAsInteractors(boolean checkComplexesAsInteractors) {
        this.checkComplexesAsInteractors = checkComplexesAsInteractors;
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
        else{
            participantBaseComparator.setIgnoreInteractors(false);
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

            if (!checkComplexesAsInteractors){
                // the bioparticipant 1 contains a complex that self interacts
                if (bioParticipant1.getInteractor() == bioParticipant1.getModelledInteraction()){
                    // the bioparticipant 2 contains a complex that self interacts
                    if (bioParticipant2.getInteractor() == bioParticipant2.getModelledInteraction()){
                        participantBaseComparator.setIgnoreInteractors(true);
                    }
                    // the bioparticipant 2 is not self, it comes after the self participant
                    else {
                        return BEFORE;
                    }
                }
                // the bioparticipant 2 contains a complex that self interacts, comes before
                else if (bioParticipant2.getInteractor() == bioParticipant2.getModelledInteraction()){
                    return AFTER;
                }
            }

            int comp = participantBaseComparator.compare(bioParticipant1, bioParticipant2);
            if (comp != 0){
                return comp;
            }

            // then compares the features
            Collection<ModelledFeature> features1 = bioParticipant1.getFeatures();
            Collection<ModelledFeature> features2 = bioParticipant2.getFeatures();

            return featureCollectionComparator.compare(features1, features2);
        }
    }
}
