package psidev.psi.mi.jami.xml.cache;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.extension.AbstractAvailability;

import java.util.HashMap;
import java.util.Map;

/**
 * PsiXmlIdCache that stores objects in memory using a map
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/11/13</pre>
 */

public class InMemoryPsiXmlCache implements PsiXmlIdCache {
    private Map<Integer, Complex> mapOfReferencedComplexes;
    private Map<Integer, Interaction> mapOfReferencedInteractions;
    private Map<Integer, Experiment> mapOfReferencedExperiments;
    private Map<Integer, Interactor> mapOfReferencedInteractors;
    private Map<Integer, Participant> mapOfReferencedParticipants;
    private Map<Integer, Feature> mapOfReferencedFeatures;
    private Map<Integer, VariableParameterValue> mapOfReferencedVariableParameterValues;
    private Map<Integer, AbstractAvailability> mapOfReferencedAvailabilities;

    public InMemoryPsiXmlCache(){
        this.mapOfReferencedAvailabilities = new HashMap<Integer, AbstractAvailability>();
        this.mapOfReferencedExperiments = new HashMap<Integer, Experiment>();
        this.mapOfReferencedFeatures = new HashMap<Integer, Feature>();
        this.mapOfReferencedInteractions = new HashMap<Integer, Interaction>();
        this.mapOfReferencedInteractors = new HashMap<Integer, Interactor>();
        this.mapOfReferencedParticipants = new HashMap<Integer, Participant>();
        this.mapOfReferencedVariableParameterValues = new HashMap<Integer, VariableParameterValue>();
        this.mapOfReferencedComplexes = new HashMap<Integer, Complex>();
    }

    @Override
    public void registerAvailability(int id, AbstractAvailability object) {
        this.mapOfReferencedAvailabilities.put(id, object);
    }

    @Override
    public AbstractAvailability getAvailability(int id) {
        return this.mapOfReferencedAvailabilities.get(id);
    }

    @Override
    public void registerExperiment(int id, Experiment object) {
        this.mapOfReferencedExperiments.put(id, object);
    }

    @Override
    public Experiment getExperiment(int id) {
        return this.mapOfReferencedExperiments.get(id);
    }

    @Override
    public void registerInteraction(int id, Interaction object) {
        this.mapOfReferencedInteractions.put(id, object);
    }

    @Override
    public Interaction getInteraction(int id) {
        return this.mapOfReferencedInteractions.get(id);
    }

    @Override
    public void registerInteractor(int id, Interactor object) {
        this.mapOfReferencedInteractors.put(id, object);
    }

    @Override
    public Interactor getInteractor(int id) {
        return this.mapOfReferencedInteractors.get(id);
    }

    @Override
    public void registerParticipant(int id, Participant object) {
        this.mapOfReferencedParticipants.put(id, object);
    }

    @Override
    public Participant getParticipant(int id) {
        return this.mapOfReferencedParticipants.get(id);
    }

    @Override
    public void registerFeature(int id, Feature object) {
        this.mapOfReferencedFeatures.put(id, object);
    }

    @Override
    public Feature getFeature(int id) {
        return this.mapOfReferencedFeatures.get(id);
    }

    @Override
    public void registerComplex(int id, Complex object) {
        this.mapOfReferencedComplexes.put(id, object);
    }

    @Override
    public Complex getComplex(int id) {
        Complex object = this.mapOfReferencedComplexes.get(id);
        if (object != null){
            return object;
        }
        else{
            Interaction object2 = this.mapOfReferencedInteractions.get(id);
            if (object2 instanceof Complex){
                return (Complex)object2;
            }
        }
        return null;
    }

    @Override
    public void registerVariableParameterValue(int id, VariableParameterValue object) {
        this.mapOfReferencedVariableParameterValues.put(id, object);
    }

    @Override
    public VariableParameterValue getVariableParameterValue(int id) {
        return this.mapOfReferencedVariableParameterValues.get(id);
    }


    @Override
    public void clear() {
        this.mapOfReferencedVariableParameterValues.clear();
        this.mapOfReferencedFeatures.clear();
        this.mapOfReferencedParticipants.clear();
        this.mapOfReferencedInteractions.clear();
        this.mapOfReferencedInteractors.clear();
        this.mapOfReferencedAvailabilities.clear();
        this.mapOfReferencedExperiments.clear();
        this.mapOfReferencedComplexes.clear();
    }

    @Override
    public boolean containsExperiment(int id) {
        return this.mapOfReferencedExperiments.containsKey(id);
    }

    @Override
    public boolean containsAvailability(int id) {
        return this.mapOfReferencedAvailabilities.containsKey(id);
    }

    @Override
    public boolean containsInteraction(int id) {
        return this.mapOfReferencedInteractions.containsKey(id);
    }

    @Override
    public boolean containsInteractor(int id) {
        return this.mapOfReferencedInteractors.containsKey(id);
    }

    @Override
    public boolean containsParticipant(int id) {
        return this.mapOfReferencedParticipants.containsKey(id);
    }

    @Override
    public boolean containsFeature(int id) {
        return this.mapOfReferencedFeatures.containsKey(id);
    }

    @Override
    public boolean containsVariableParameter(int id) {
        return this.mapOfReferencedVariableParameterValues.containsKey(id);
    }

    @Override
    public boolean containsComplex(int id) {
        return this.mapOfReferencedComplexes.containsKey(id);
    }
}
