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

public class CompositeEntityEnricher implements ParticipantEnricher<Participant, Feature>{

    private ParticipantEnricher<Participant,Feature> entityBaseEnricher;
    private ParticipantEnricher<ModelledParticipant,ModelledFeature> modelledEntityEnricher;
    private ParticipantEnricher<ParticipantEvidence,FeatureEvidence> experimentalEntityEnricher;
    private ParticipantEnricher<ModelledParticipantPool,ModelledFeature> modelledEntityPoolEnricher;
    private ParticipantEnricher<ParticipantEvidencePool,FeatureEvidence> experimentalEntityPoolEnricher;

    public CompositeEntityEnricher(ParticipantEnricher<Participant,Feature> entityBaseEnricher){
        super();
        if (entityBaseEnricher == null){
            throw new IllegalArgumentException("At least the Participant base enricher is needed and cannot be null") ;
        }
        this.entityBaseEnricher = entityBaseEnricher;
    }

    public ParticipantEnricher<Participant, Feature> getEntityBaseEnricher() {
        return entityBaseEnricher;
    }

    public ParticipantEnricher<ModelledParticipant, ModelledFeature> getModelledEntityEnricher() {
        return modelledEntityEnricher;
    }

    public void setModelledEntityEnricher(ParticipantEnricher<ModelledParticipant, ModelledFeature> modelledEntityEnricher) {
        this.modelledEntityEnricher = modelledEntityEnricher;
    }

    public ParticipantEnricher<ParticipantEvidence, FeatureEvidence> getExperimentalEntityEnricher() {
        return experimentalEntityEnricher;
    }

    public void setExperimentalEntityEnricher(ParticipantEnricher<ParticipantEvidence, FeatureEvidence> experimentalEntityEnricher) {
        this.experimentalEntityEnricher = experimentalEntityEnricher;
    }

    public ParticipantEnricher<ModelledParticipantPool, ModelledFeature> getModelledEntityPoolEnricher() {
        return modelledEntityPoolEnricher;
    }

    public void setModelledEntityPoolEnricher(ParticipantEnricher<ModelledParticipantPool, ModelledFeature> modelledEntityPoolEnricher) {
        this.modelledEntityPoolEnricher = modelledEntityPoolEnricher;
    }

    public ParticipantEnricher<ParticipantEvidencePool, FeatureEvidence> getExperimentalEntityPoolEnricher() {
        return experimentalEntityPoolEnricher;
    }

    public void setExperimentalEntityPoolEnricher(ParticipantEnricher<ParticipantEvidencePool, FeatureEvidence> experimentalEntityPoolEnricher) {
        this.experimentalEntityPoolEnricher = experimentalEntityPoolEnricher;
    }

    public void enrich(Participant object) throws EnricherException {
        if(object == null)
            throw new IllegalArgumentException("Cannot enrich a null entity.");
        if (object instanceof ParticipantEvidence){
            if (object instanceof ParticipantEvidencePool && this.experimentalEntityPoolEnricher != null){
               this.experimentalEntityPoolEnricher.enrich((ParticipantEvidencePool)object);
            }
            else if (this.experimentalEntityEnricher != null){
               this.experimentalEntityEnricher.enrich((ParticipantEvidence)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else if (object instanceof ModelledParticipant){
            if (object instanceof ModelledParticipantPool && this.modelledEntityPoolEnricher != null){
                this.modelledEntityPoolEnricher.enrich((ModelledParticipantPool)object);
            }
            else if (this.modelledEntityEnricher != null){
                this.modelledEntityEnricher.enrich((ModelledParticipant)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else{
            this.entityBaseEnricher.enrich(object);
        }
    }

    public void enrich(Collection<Participant> objects) throws EnricherException {
        if(objects == null)
            throw new IllegalArgumentException("Cannot enrich a null collection of interactors.");

        for (Participant object : objects){
            enrich(object);
        }
    }

    public void enrich(Participant object, Participant objectSource) throws EnricherException {
        if (object instanceof ParticipantEvidence && objectSource instanceof ParticipantEvidence){
            if (object instanceof ParticipantEvidencePool && objectSource instanceof  ParticipantEvidence && this.experimentalEntityPoolEnricher != null){
                this.experimentalEntityPoolEnricher.enrich((ParticipantEvidencePool)object, (ParticipantEvidencePool)objectSource);
            }
            else if (this.experimentalEntityEnricher != null){
                this.experimentalEntityEnricher.enrich((ParticipantEvidence)object, (ParticipantEvidence)objectSource);
            }
            else{
                this.entityBaseEnricher.enrich(object, objectSource);
            }
        }
        else if (object instanceof ModelledParticipant && objectSource instanceof ModelledParticipant){
            if (object instanceof ModelledParticipantPool && objectSource instanceof ModelledParticipantPool && this.modelledEntityPoolEnricher != null){
                this.modelledEntityPoolEnricher.enrich((ModelledParticipantPool)object, (ModelledParticipantPool)objectSource);
            }
            else if (this.modelledEntityEnricher != null){
                this.modelledEntityEnricher.enrich((ModelledParticipant)object, (ModelledParticipant)objectSource);
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
