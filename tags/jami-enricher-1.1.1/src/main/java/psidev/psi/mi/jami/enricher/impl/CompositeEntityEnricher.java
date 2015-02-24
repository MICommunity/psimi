package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.EntityEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * General enricher for entities and participants candidates that can use sub enrichers for enriching specific interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/14</pre>
 */

public class CompositeEntityEnricher implements EntityEnricher<Entity, Feature> {

    private EntityEnricher<Entity,Feature> entityBaseEnricher;
    private EntityEnricher<ModelledEntity,ModelledFeature> modelledEntityEnricher;
    private EntityEnricher<ExperimentalEntity,FeatureEvidence> experimentalEntityEnricher;

    public CompositeEntityEnricher(EntityEnricher<Entity, Feature> entityBaseEnricher){
        super();
        if (entityBaseEnricher == null){
            throw new IllegalArgumentException("At least the Participant base enricher is needed and cannot be null") ;
        }
        this.entityBaseEnricher = entityBaseEnricher;
    }

    public EntityEnricher<Entity, Feature> getEntityBaseEnricher() {
        return entityBaseEnricher;
    }

    public EntityEnricher<ModelledEntity,ModelledFeature> getModelledEntityEnricher() {
        return modelledEntityEnricher;
    }

    public void setModelledEntityEnricher(EntityEnricher<ModelledEntity,ModelledFeature> modelledEntityEnricher) {
        this.modelledEntityEnricher = modelledEntityEnricher;
    }

    public EntityEnricher<ExperimentalEntity,FeatureEvidence> getExperimentalEntityEnricher() {
        return experimentalEntityEnricher;
    }

    public void setExperimentalEntityEnricher(EntityEnricher<ExperimentalEntity,FeatureEvidence> experimentalEntityEnricher) {
        this.experimentalEntityEnricher = experimentalEntityEnricher;
    }

    public void enrich(Entity object) throws EnricherException {
        if(object == null)
            throw new IllegalArgumentException("Cannot enrich a null entity.");
        if (object instanceof ExperimentalEntity){
            if (this.experimentalEntityEnricher != null){
               this.experimentalEntityEnricher.enrich((ExperimentalEntity)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else if (object instanceof ModelledEntity){
            if (this.modelledEntityEnricher != null){
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
            if (this.experimentalEntityEnricher != null){
                this.experimentalEntityEnricher.enrich((ExperimentalEntity)object, (ExperimentalEntity)objectSource);
            }
            else{
                this.entityBaseEnricher.enrich(object, objectSource);
            }
        }
        else if (object instanceof ModelledEntity && objectSource instanceof ModelledEntity){
            if (this.modelledEntityEnricher != null){
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

    public FeatureEnricher getFeatureEnricher() {
        return this.entityBaseEnricher.getFeatureEnricher();
    }

    public EntityEnricherListener getParticipantEnricherListener() {
        return this.entityBaseEnricher.getParticipantEnricherListener();
    }

    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
        this.entityBaseEnricher.setInteractorEnricher(interactorEnricher);
        if (getModelledEntityEnricher() != null){
            getModelledEntityEnricher().setInteractorEnricher(interactorEnricher);
        }
        if (getExperimentalEntityEnricher() != null){
            getExperimentalEntityEnricher().setInteractorEnricher(interactorEnricher);
        }
    }

    public void setFeatureEnricher(FeatureEnricher<Feature> enricher) {
        this.entityBaseEnricher.setFeatureEnricher(enricher);
    }

    public void setParticipantEnricherListener(EntityEnricherListener listener) {
        this.entityBaseEnricher.setParticipantEnricherListener(listener);
    }
}
