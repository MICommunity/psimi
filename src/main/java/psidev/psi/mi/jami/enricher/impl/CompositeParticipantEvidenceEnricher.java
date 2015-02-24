package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * General enricher for entities and participants that can use sub enrichers for enriching specific interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/14</pre>
 */

public class CompositeParticipantEvidenceEnricher implements ParticipantEnricher<ParticipantEvidence, FeatureEvidence>{

    private ParticipantEnricher<ParticipantEvidence,FeatureEvidence> entityBaseEnricher;
    private ExperimentalParticipantPoolEnricher poolEntityEnricher;

    public CompositeParticipantEvidenceEnricher(ParticipantEnricher<ParticipantEvidence, FeatureEvidence> entityBaseEnricher){
        super();
        if (entityBaseEnricher == null){
            throw new IllegalArgumentException("At least the modelled Participant base enricher is needed and cannot be null") ;
        }
        this.entityBaseEnricher = entityBaseEnricher;
    }

    public ExperimentalParticipantPoolEnricher getPoolEntityEnricher() {
        return poolEntityEnricher;
    }

    public void setPoolEntityEnricher(ExperimentalParticipantPoolEnricher poolEntityEnricher) {
        this.poolEntityEnricher = poolEntityEnricher;
    }

    public void enrich(ParticipantEvidence object) throws EnricherException {
        if(object == null)
            throw new IllegalArgumentException("Cannot enrich a null entity.");
        if (object instanceof ExperimentalParticipantPool){
            if (this.poolEntityEnricher != null){
               this.poolEntityEnricher.enrich((ExperimentalParticipantPool)object);
            }
            else{
                this.entityBaseEnricher.enrich(object);
            }
        }
        else{
            this.entityBaseEnricher.enrich(object);
        }
    }

    public void enrich(Collection<ParticipantEvidence> objects) throws EnricherException {
        if(objects == null)
            throw new IllegalArgumentException("Cannot enrich a null collection of interactors.");

        for (ParticipantEvidence object : objects){
            enrich(object);
        }
    }

    public void enrich(ParticipantEvidence object, ParticipantEvidence objectSource) throws EnricherException {
        if (object instanceof ExperimentalParticipantPool && objectSource instanceof ExperimentalParticipantPool){
            if (this.poolEntityEnricher != null){
                this.poolEntityEnricher.enrich((ExperimentalParticipantPool)object, (ExperimentalParticipantPool)objectSource);
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

    public EntityEnricherListener getParticipantEnricherListener() {
        return this.entityBaseEnricher.getParticipantEnricherListener();
    }

    public void setCvTermEnricher(CvTermEnricher<CvTerm> enricher) {
        this.entityBaseEnricher.setCvTermEnricher(enricher);
        if (poolEntityEnricher != null){
            poolEntityEnricher.setCvTermEnricher(enricher);
        }
    }

    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
        this.entityBaseEnricher.setInteractorEnricher(interactorEnricher);
        if (poolEntityEnricher != null){
            poolEntityEnricher.setInteractorEnricher(interactorEnricher);
        }
    }

    public void setFeatureEnricher(FeatureEnricher<FeatureEvidence> enricher) {
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
