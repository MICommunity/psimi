package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * General enricher for entities and participants that can use sub enrichers for enriching specific interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/14</pre>
 */

public class CompositeEntityEnricher implements ParticipantEnricher<Entity, Feature>{

    private ParticipantEnricher<Entity,Feature> entityBaseEnricher;
    private ParticipantEnricher<ModelledEntity,ModelledFeature> modelledEntityEnricher;
    private ParticipantEnricher<ExperimentalEntity,FeatureEvidence> experimentalEntityEnricher;
    private ParticipantEnricher<ModelledEntityPool,ModelledFeature> modelledEntityPoolEnricher;
    private ParticipantEnricher<ExperimentalEntityPool,FeatureEvidence> experimentalEntityPoolEnricher;

    public CompositeEntityEnricher(ParticipantEnricher<Entity,Feature> entityBaseEnricher){
        super();
        if (entityBaseEnricher == null){
            throw new IllegalArgumentException("At least the entity base enricher is needed and cannot be null") ;
        }
        this.entityBaseEnricher = entityBaseEnricher;
    }

    public ParticipantEnricher<Entity, Feature> getEntityBaseEnricher() {
        return entityBaseEnricher;
    }

    public ParticipantEnricher<ModelledEntity, ModelledFeature> getModelledEntityEnricher() {
        return modelledEntityEnricher;
    }

    public void setModelledEntityEnricher(ParticipantEnricher<ModelledEntity, ModelledFeature> modelledEntityEnricher) {
        this.modelledEntityEnricher = modelledEntityEnricher;
    }

    public ParticipantEnricher<ExperimentalEntity, FeatureEvidence> getExperimentalEntityEnricher() {
        return experimentalEntityEnricher;
    }

    public void setExperimentalEntityEnricher(ParticipantEnricher<ExperimentalEntity, FeatureEvidence> experimentalEntityEnricher) {
        this.experimentalEntityEnricher = experimentalEntityEnricher;
    }

    public ParticipantEnricher<ModelledEntityPool, ModelledFeature> getModelledEntityPoolEnricher() {
        return modelledEntityPoolEnricher;
    }

    public void setModelledEntityPoolEnricher(ParticipantEnricher<ModelledEntityPool, ModelledFeature> modelledEntityPoolEnricher) {
        this.modelledEntityPoolEnricher = modelledEntityPoolEnricher;
    }

    public ParticipantEnricher<ExperimentalEntityPool, FeatureEvidence> getExperimentalEntityPoolEnricher() {
        return experimentalEntityPoolEnricher;
    }

    public void setExperimentalEntityPoolEnricher(ParticipantEnricher<ExperimentalEntityPool, FeatureEvidence> experimentalEntityPoolEnricher) {
        this.experimentalEntityPoolEnricher = experimentalEntityPoolEnricher;
    }

    public void enrich(Entity object) throws EnricherException {
        if(object == null)
            throw new IllegalArgumentException("Cannot enrich a null entity.");
        if (object instanceof ExperimentalEntity){
            if (object instanceof ExperimentalEntityPool && this.experimentalEntityPoolEnricher != null){
               this.experimentalEntityPoolEnricher.enrich((ExperimentalEntityPool)object);
            }
            else if (this.experimentalEntityEnricher != null){
               this.experimentalEntityEnricher.enrich((ExperimentalEntity)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else if (object instanceof ModelledEntity){
            if (object instanceof ModelledEntityPool && this.modelledEntityPoolEnricher != null){
                this.modelledEntityPoolEnricher.enrich((ModelledEntityPool)object);
            }
            else if (this.modelledEntityEnricher != null){
                this.modelledEntityEnricher.enrich((ModelledEntity)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else{
            this.entityBaseEnricher.enrich(object);
        }
    }

    public void enrich(Collection<Entity> objects) throws EnricherException {
        if(objects == null)
            throw new IllegalArgumentException("Cannot enrich a null collection of interactors.");

        for (Entity object : objects){
            enrich(object);
        }
    }

    public void enrich(Entity object, Entity objectSource) throws EnricherException {
        if (object instanceof ExperimentalEntity && objectSource instanceof ExperimentalEntity){
            if (object instanceof ExperimentalEntityPool && objectSource instanceof  ExperimentalEntity && this.experimentalEntityPoolEnricher != null){
                this.experimentalEntityPoolEnricher.enrich((ExperimentalEntityPool)object, (ExperimentalEntityPool)objectSource);
            }
            else if (this.experimentalEntityEnricher != null){
                this.experimentalEntityEnricher.enrich((ExperimentalEntity)object, (ExperimentalEntity)objectSource);
            }
            else{
                this.entityBaseEnricher.enrich(object, objectSource);
            }
        }
        else if (object instanceof ModelledEntity && objectSource instanceof ModelledEntity){
            if (object instanceof ModelledEntityPool && objectSource instanceof ModelledEntityPool && this.modelledEntityPoolEnricher != null){
                this.modelledEntityPoolEnricher.enrich((ModelledEntityPool)object, (ModelledEntityPool)objectSource);
            }
            else if (this.modelledEntityEnricher != null){
                this.modelledEntityEnricher.enrich((ModelledEntity)object, (ModelledEntity)objectSource);
            }
            else{
                this.entityBaseEnricher.enrich(object, objectSource);
            }
        }
        else{
            this.entityBaseEnricher.enrich(object);
        }
    }

    public CompositeInteractorEnricher getInteractorEnricher() {
        return this.entityBaseEnricher.getInteractorEnricher();
    }

    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return this.entityBaseEnricher.getCvTermEnricher();
    }

    public FeatureEnricher getFeatureEnricher() {
        return this.entityBaseEnricher.getFeatureEnricher();
    }

    public ParticipantEnricherListener getParticipantEnricherListener() {
        return this.entityBaseEnricher.getParticipantEnricherListener();
    }
}
