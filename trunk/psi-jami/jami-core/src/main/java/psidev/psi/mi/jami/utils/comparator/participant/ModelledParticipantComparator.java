package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basic biological participant comparator.
 * It will compare the basic properties of a biological participant using ParticipantBaseComparator.
 *
 * If the participants are both ModelledParticipantPool, it will use ModelledParticipantPoolComparator to compare them
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ModelledParticipantComparator implements CustomizableModelledParticipantComparator<ModelledParticipant> {

    protected ModelledParticipantPoolComparator poolComparator;

    protected boolean checkComplexesAsInteractors = true;

    protected Map<Complex, Set<Interactor>> processedComplexes;

    /**
     * Creates a new ComponentComparator
     */
    public ModelledParticipantComparator(ModelledParticipantPoolComparator poolComparator){
        if (poolComparator == null){
            throw new IllegalArgumentException("The participant pool comparator is required to compare basic participant properties, features and participant pools. It cannot be null");
        }
        this.poolComparator = poolComparator;
        this.processedComplexes = new IdentityHashMap<Complex, Set<Interactor>>();
    }

    public ModelledParticipantComparator(){
        this.processedComplexes = new IdentityHashMap<Complex, Set<Interactor>>();
    }

    public ModelledParticipantPoolComparator getParticipantPoolComparator() {
        return poolComparator;
    }

    public void setPoolComparator(ModelledParticipantPoolComparator poolComparator) {
        this.poolComparator = poolComparator;
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
     * It will compare the basic properties of a biological participant using ParticipantBaseComparator.
     *
     * If the participants are both ModelledParticipantPool, it will use ModelledParticipantPoolComparator to compare them
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
        // both are biological participants
        boolean isBiologicalParticipant1 = bioParticipant1 instanceof ModelledParticipantPool;
        boolean isBiologicalParticipant2 = bioParticipant2 instanceof ModelledParticipantPool;
        if (isBiologicalParticipant1 && isBiologicalParticipant2){
            return this.poolComparator.compare((ModelledParticipantPool) bioParticipant1,
                    (ModelledParticipantPool) bioParticipant2);
        }
        // the biological participant is before
        else if (isBiologicalParticipant1){
            return BEFORE;
        }
        else if (isBiologicalParticipant2){
            return AFTER;
        }
        else {
            if (this.poolComparator == null || this.poolComparator.getParticipantBaseComparator() == null){
                throw new IllegalStateException("The participant base comparator is required to compare basic participant properties. It cannot be null");
            }
            else{
                this.poolComparator.getParticipantBaseComparator().setIgnoreInteractors(false);
            }

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

                if (!checkComplexesAsInteractors){
                    checkIfComplexAlreadyProcessed(bioParticipant1, bioParticipant2);
                    checkIfComplexAlreadyProcessed(bioParticipant2, bioParticipant1);
                }

                int comp = poolComparator.getParticipantBaseComparator().compare(bioParticipant1, bioParticipant2);
                if (comp != 0){
                    return comp;
                }

                // then compares the features
                Collection<ModelledFeature> features1 = bioParticipant1.getFeatures();
                Collection<ModelledFeature> features2 = bioParticipant2.getFeatures();

                return poolComparator.getFeatureCollectionComparator().compare(features1, features2);
            }
        }
    }

    private void checkIfComplexAlreadyProcessed(ModelledParticipant bioParticipant1, ModelledParticipant bioParticipant2) {
        Complex complex = null;
        if (bioParticipant1.getInteractor() instanceof Complex){
            complex = (Complex) bioParticipant1.getInteractor();
        }

        // we already processed complex1 as first interactor
        if (complex != null && this.processedComplexes.containsKey(complex)){
            Set<Interactor> interactorSet = this.processedComplexes.get(complex);
            // already processed this pair
            if (interactorSet.contains(bioParticipant2.getInteractor())){
                poolComparator.getParticipantBaseComparator().setIgnoreInteractors(true);
            }
            else{
                interactorSet.add(bioParticipant2.getInteractor());
            }
        }
    }
}
