package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ParticipantPoolEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ModelledParticipantPool;

import java.util.Collection;

/**
 * General enricher for entities and participants that can use sub enrichers for enriching specific interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/14</pre>
 */

public class CompositeModelledParticipantEnricher implements ParticipantEnricher<ModelledParticipant, ModelledFeature>{

    private ParticipantEnricher<ModelledParticipant,ModelledFeature> entityBaseEnricher;
    private ParticipantPoolEnricher<ModelledParticipantPool,ModelledFeature> poolEntityEnricher;

    public CompositeModelledParticipantEnricher(ParticipantEnricher<ModelledParticipant, ModelledFeature> entityBaseEnricher){
        super();
        if (entityBaseEnricher == null){
            throw new IllegalArgumentException("At least the modelled Participant base enricher is needed and cannot be null") ;
        }
        this.entityBaseEnricher = entityBaseEnricher;
    }

    public ParticipantPoolEnricher<ModelledParticipantPool, ModelledFeature> getPoolEntityEnricher() {
        return poolEntityEnricher;
    }

    public void setPoolEntityEnricher(ParticipantPoolEnricher<ModelledParticipantPool, ModelledFeature> poolEntityEnricher) {
        this.poolEntityEnricher = poolEntityEnricher;
    }

    public void enrich(ModelledParticipant object) throws EnricherException {
        if(object == null)
            throw new IllegalArgumentException("Cannot enrich a null entity.");
        if (object instanceof ModelledParticipantPool){
            if (this.poolEntityEnricher != null){
               this.poolEntityEnricher.enrich((ModelledParticipantPool)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else{
            this.entityBaseEnricher.enrich(object);
        }
    }

    public void enrich(Collection<ModelledParticipant> objects) throws EnricherException {
        if(objects == null)
            throw new IllegalArgumentException("Cannot enrich a null collection of interactors.");

        for (ModelledParticipant object : objects){
            enrich(object);
        }
    }

    public void enrich(ModelledParticipant object, ModelledParticipant objectSource) throws EnricherException {
        if (object instanceof ModelledParticipantPool && objectSource instanceof ModelledParticipantPool){
            if (this.poolEntityEnricher != null){
                this.poolEntityEnricher.enrich((ModelledParticipantPool)object, (ModelledParticipantPool)objectSource);
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
        if (poolEntityEnricher != null){
            poolEntityEnricher.setCvTermEnricher(enricher);
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
        if (poolEntityEnricher != null){
            poolEntityEnricher.setInteractorEnricher(interactorEnricher);
        }
    }

    public void setFeatureEnricher(FeatureEnricher<ModelledFeature> enricher) {
        this.entityBaseEnricher.setFeatureEnricher(enricher);
        if (poolEntityEnricher != null){
            poolEntityEnricher.setFeatureEnricher(enricher);
        }
    }

    public void setParticipantEnricherListener(EntityEnricherListener listener) {
        this.entityBaseEnricher.setParticipantEnricherListener(listener);
        if (poolEntityEnricher != null){
            poolEntityEnricher.setParticipantEnricherListener(listener);
        }
    }
}
