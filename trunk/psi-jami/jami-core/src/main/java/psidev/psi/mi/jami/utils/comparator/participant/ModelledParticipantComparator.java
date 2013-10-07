package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.ModelledFeatureComparator;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

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

    protected Map<Complex, Set<Interactor>> processedComplexes;

    /**
     * Creates a new ComponentComparator
     */
    public ModelledParticipantComparator(ModelledFeatureComparator featureComparator){

        if (featureComparator == null){
            throw new IllegalArgumentException("The modelled feature comparator is required to compare modelled features. It cannot be null");
        }
        this.featureCollectionComparator = new FeatureCollectionComparator(featureComparator);
        this.processedComplexes = new IdentityHashMap<Complex, Set<Interactor>>();
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

    public void clearProcessedComplexes() {
        this.processedComplexes.clear();
    }

    /**
     * It will compare the basic properties of a biological participant using ParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     * @param bioParticipant1
     * @param bioParticipant2
     * @return
     */
    public int compare(ModelledEntity bioParticipant1, ModelledEntity bioParticipant2) {
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
                checkIfComplexAlreadyProcessed(bioParticipant1, bioParticipant2);
                checkIfComplexAlreadyProcessed(bioParticipant2, bioParticipant1);
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

    private void checkIfComplexAlreadyProcessed(ModelledEntity bioParticipant1, ModelledEntity bioParticipant2) {
        Complex complex = null;
        if (bioParticipant1.getInteractor() instanceof Complex){
            complex = (Complex) bioParticipant1.getInteractor();
        }

        // we already processed complex1 as first interactor
        if (complex != null && this.processedComplexes.containsKey(complex)){
            Set<Interactor> interactorSet = this.processedComplexes.get(complex);
            // already processed this pair
            if (interactorSet.contains(bioParticipant2.getInteractor())){
                participantBaseComparator.setIgnoreInteractors(true);
            }
            else{
                interactorSet.add(bioParticipant2.getInteractor());
            }
        }
    }
}
