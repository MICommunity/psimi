package psidev.psi.mi.jami.xml.cache;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.extension.*;

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
    private Map<Integer, ModelledEntity> mapOfReferencedComplexParticipants;
    private Map<Integer, ModelledFeature> mapOfReferencedComplexFeatures;
    private Map<Integer, Interaction> mapOfReferencedInteractions;
    private Map<Integer, Experiment> mapOfReferencedExperiments;
    private Map<Integer, Interactor> mapOfReferencedInteractors;
    private Map<Integer, Entity> mapOfReferencedParticipants;
    private Map<Integer, Feature> mapOfReferencedFeatures;
    private Map<Integer, VariableParameterValue> mapOfReferencedVariableParameterValues;
    private Map<Integer, AbstractAvailability> mapOfReferencedAvailabilities;

    public InMemoryPsiXmlCache(){
        this.mapOfReferencedAvailabilities = new HashMap<Integer, AbstractAvailability>();
        this.mapOfReferencedExperiments = new HashMap<Integer, Experiment>();
        this.mapOfReferencedFeatures = new HashMap<Integer, Feature>();
        this.mapOfReferencedInteractions = new HashMap<Integer, Interaction>();
        this.mapOfReferencedInteractors = new HashMap<Integer, Interactor>();
        this.mapOfReferencedParticipants = new HashMap<Integer, Entity>();
        this.mapOfReferencedVariableParameterValues = new HashMap<Integer, VariableParameterValue>();
        this.mapOfReferencedComplexes = new HashMap<Integer, Complex>();
        this.mapOfReferencedComplexParticipants = new HashMap<Integer, ModelledEntity>();
        this.mapOfReferencedComplexFeatures = new HashMap<Integer, ModelledFeature>();
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
    public void registerParticipant(int id, Entity object) {
        this.mapOfReferencedParticipants.put(id, object);
    }

    @Override
    public Entity getParticipant(int id) {
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
    public void registerComplexParticipant(int id, ModelledEntity object) {
        this.mapOfReferencedComplexParticipants.put(id, object);
    }

    @Override
    public ModelledEntity getComplexParticipant(int id) {
        ModelledEntity object = this.mapOfReferencedComplexParticipants.get(id);
        if (object != null){
            return object;
        }
        else{
            Entity object2 = this.mapOfReferencedParticipants.get(id);
            if (object2 instanceof ModelledEntity){
                return (ModelledEntity)object2;
            }
        }
        return null;
    }

    @Override
    public void registerComplexFeature(int id, ModelledFeature object) {
        this.mapOfReferencedComplexFeatures.put(id, object);
    }

    @Override
    public ModelledFeature getComplexFeature(int id) {
        ModelledFeature object = this.mapOfReferencedComplexFeatures.get(id);
        if (object != null){
            return object;
        }
        else{
            Feature object2 = this.mapOfReferencedFeatures.get(id);
            if (object2 instanceof ModelledFeature){
                return (ModelledFeature)object2;
            }
        }
        return null;
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
        this.mapOfReferencedComplexParticipants.clear();
        this.mapOfReferencedComplexFeatures.clear();
    }

    @Override
    public void close() {
        clear();
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

    @Override
    public boolean containsComplexParticipant(int id) {
        return this.mapOfReferencedComplexParticipants.containsKey(id);
    }

    @Override
    public boolean containsComplexFeature(int id) {
        return this.mapOfReferencedComplexFeatures.containsKey(id);
    }


    @Override
    public ModelledFeature registerModelledFeatureLoadedFrom(Feature f) {
        Entity parent = f.getParticipant();
        ModelledEntity registeredEntity = null;

        if (parent != null){
            registeredEntity = registerModelledParticipantLoadedFrom(parent);
            if (containsComplexFeature(((ExtendedPsiXmlFeature)f).getId())){
                return getComplexFeature(((ExtendedPsiXmlFeature)f).getId());
            }
        }

        if (f instanceof ExtendedPsiXmlFeatureEvidence){
            return new XmlFeatureEvidenceWrapper((ExtendedPsiXmlFeatureEvidence)f, registeredEntity);
        }
        else if (f instanceof ModelledFeature){
            return (ModelledFeature)f;
        }
        else {
            return new XmlFeatureWrapper((ExtendedPsiXmlFeature)f, registeredEntity);
        }
    }

    @Override
    public ModelledEntity registerModelledParticipantLoadedFrom(Entity f) {
        if (f instanceof ParticipantCandidate){
            ParticipantPool parent = ((ParticipantCandidate)f).getParentPool();
            ModelledParticipantPool registeredEntity = null;

            if (parent != null){
                registeredEntity = (ModelledParticipantPool)registerModelledParticipantLoadedFrom(parent);
                if (containsComplexParticipant(((ExtendedPsiXmlEntity) f).getId())){
                    return getComplexParticipant(((ExtendedPsiXmlEntity) f).getId());
                }
            }

            if (f instanceof ExperimentalParticipantCandidate){
                return new XmlExperimentalParticipantCandidateWrapper((ExperimentalParticipantCandidate)f, registeredEntity);
            }
            else if (f instanceof ModelledParticipantCandidate){
                return (ModelledParticipantCandidate)f;
            }
            else {
                return new XmlParticipantCandidateWrapper((ParticipantCandidate)f, registeredEntity);
            }
        }
        else {
            Interaction parent = ((Participant)f).getInteraction();
            Complex registeredComplex = null;

            if (parent != null){
                registeredComplex = registerComplexLoadedFrom(parent);
                if (containsComplexParticipant(((ExtendedPsiXmlEntity) f).getId())){
                    return getComplexParticipant(((ExtendedPsiXmlEntity) f).getId());
                }
            }

            if (f instanceof ExperimentalParticipantPool){
                return new XmlExperimentalParticipantPoolWrapper((ExperimentalParticipantPool)f, (XmlInteractionEvidenceComplexWrapper)registeredComplex);
            }
            else if (f instanceof ExtendedPsiXmlParticipantEvidence){
                return new XmlParticipantEvidenceWrapper((ExtendedPsiXmlParticipantEvidence)f, (XmlInteractionEvidenceComplexWrapper)registeredComplex);
            }
            else if (f instanceof ModelledParticipant){
                return (ModelledParticipant)f;
            }
            else if (f instanceof ParticipantPool){
                return new XmlParticipantPoolWrapper((ParticipantPool)f, registeredComplex);
            }
            else {
                return new XmlParticipantWrapper((ExtendedPsiXmlParticipant)f, registeredComplex);
            }
        }
    }

    @Override
    public Complex registerComplexLoadedFrom(Interaction f) {

        // convert interaction evidence in a complex
        if (f instanceof ExtendedPsiXmlInteractionEvidence){
            return new XmlInteractionEvidenceComplexWrapper((ExtendedPsiXmlInteractionEvidence)f);
        }
        // wrap modelled interaction
        else if (f instanceof ExtendedPsiXmlModelledInteraction){
            return new XmlModelledInteractionComplexWrapper((ExtendedPsiXmlModelledInteraction)f);
        }
        // wrap basic interaction
        else if (f instanceof ExtendedPsiXmlInteraction){
            return new XmlBasicInteractionComplexWrapper((ExtendedPsiXmlInteraction)f);
        }
        else{
            return null;
        }
    }
}
