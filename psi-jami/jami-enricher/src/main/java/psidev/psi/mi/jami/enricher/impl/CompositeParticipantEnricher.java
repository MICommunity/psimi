package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ParticipantPoolEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;
import java.util.Collections;

/**
 * General enricher for entities and participants that can use sub enrichers for enriching specific interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/14</pre>
 */

public class CompositeParticipantEnricher implements ParticipantEnricher<Participant, Feature>{

    private ParticipantEnricher<Participant,Feature> entityBaseEnricher;
    private CompositeModelledParticipantEnricher modelledEntityEnricher;
    private CompositeParticipantEvidenceEnricher experimentalEntityEnricher;
    private ParticipantPoolEnricher<ParticipantPool,Feature> poolEnricher;

    public CompositeParticipantEnricher(ParticipantEnricher<Participant, Feature> entityBaseEnricher){
        super();
        if (entityBaseEnricher == null){
            throw new IllegalArgumentException("At least the Participant base enricher is needed and cannot be null") ;
        }
        this.entityBaseEnricher = entityBaseEnricher;
    }

    public ParticipantEnricher<Participant, Feature> getEntityBaseEnricher() {
        return entityBaseEnricher;
    }

    public CompositeModelledParticipantEnricher getModelledEntityEnricher() {
        return modelledEntityEnricher;
    }

    public void setModelledEntityEnricher(CompositeModelledParticipantEnricher modelledEntityEnricher) {
        this.modelledEntityEnricher = modelledEntityEnricher;
    }

    public CompositeParticipantEvidenceEnricher getExperimentalEntityEnricher() {
        return experimentalEntityEnricher;
    }

    public void setExperimentalEntityEnricher(CompositeParticipantEvidenceEnricher experimentalEntityEnricher) {
        this.experimentalEntityEnricher = experimentalEntityEnricher;
    }

    public ParticipantPoolEnricher<ParticipantPool, Feature> getPoolEnricher() {
        return poolEnricher;
    }

    public void setPoolEnricher(ParticipantPoolEnricher<ParticipantPool, Feature> poolEnricher) {
        this.poolEnricher = poolEnricher;
    }

    public void enrich(Participant object) throws EnricherException {
        if(object == null)
            throw new IllegalArgumentException("Cannot enrich a null entity.");
        if (object instanceof ParticipantEvidence){
            if (this.experimentalEntityEnricher != null){
               this.experimentalEntityEnricher.enrich((ParticipantEvidence)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else if (object instanceof ModelledParticipant){
            if (this.modelledEntityEnricher != null){
                this.modelledEntityEnricher.enrich((ModelledParticipant)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else if (object instanceof ParticipantPool){
            if (this.poolEnricher != null){
                this.poolEnricher.enrich(Collections.singleton((ParticipantPool)object));
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
            if (this.experimentalEntityEnricher != null){
                this.experimentalEntityEnricher.enrich((ParticipantEvidence)object, (ParticipantEvidence)objectSource);
            }
            else{
                this.entityBaseEnricher.enrich(object, objectSource);
            }
        }
        else if (object instanceof ModelledParticipant && objectSource instanceof ModelledParticipant){
            if (this.modelledEntityEnricher != null){
                this.modelledEntityEnricher.enrich((ModelledParticipant)object, (ModelledParticipant)objectSource);
            }
            else{
                this.entityBaseEnricher.enrich(object, objectSource);
            }
        }
        else if (object instanceof ParticipantPool && objectSource instanceof ParticipantPool){
            if (this.poolEnricher != null){
                this.poolEnricher.enrich((ParticipantPool)object, (ParticipantPool)objectSource);
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

    public void setCvTermEnricher(CvTermEnricher<CvTerm> enricher) {
        this.entityBaseEnricher.setCvTermEnricher(enricher);

        if (this.experimentalEntityEnricher != null){
            this.experimentalEntityEnricher.setCvTermEnricher(enricher);
        }
        if (this.modelledEntityEnricher != null){
            this.modelledEntityEnricher.setCvTermEnricher(enricher);
        }
        if (this.poolEnricher != null){
            this.poolEnricher.setCvTermEnricher(enricher);
        }
    }

    public FeatureEnricher getFeatureEnricher() {
        return this.entityBaseEnricher.getFeatureEnricher();
    }

    public EntityEnricherListener getParticipantEnricherListener() {
        return this.entityBaseEnricher.getParticipantEnricherListener();
    }

    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
        this.entityBaseEnricher.setInteractorEnricher(interactorEnricher);

        if (this.experimentalEntityEnricher != null){
            this.experimentalEntityEnricher.setInteractorEnricher(interactorEnricher);
        }
        if (this.modelledEntityEnricher != null){
            this.modelledEntityEnricher.setInteractorEnricher(interactorEnricher);
        }
        if (this.poolEnricher != null){
            this.poolEnricher.setInteractorEnricher(interactorEnricher);
        }
    }

    public void setFeatureEnricher(FeatureEnricher<Feature> enricher) {
        this.entityBaseEnricher.setFeatureEnricher(enricher);

        if (this.poolEnricher != null){
            this.poolEnricher.setFeatureEnricher(enricher);
        }
    }

    public void setParticipantEnricherListener(EntityEnricherListener listener) {
        this.entityBaseEnricher.setParticipantEnricherListener(listener);

        if (this.poolEnricher != null){
            this.poolEnricher.setParticipantEnricherListener(listener);
        }
    }
}
