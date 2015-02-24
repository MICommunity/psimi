package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * General enricher for interactors that can use sub enrichers for enriching specific interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/14</pre>
 */

public class CompositeInteractorEnricher implements InteractorEnricher<Interactor>{

    private AbstractInteractorEnricher<Interactor> interactorBaseEnricher;
    private InteractorEnricher<Polymer> polymerBaseEnricher;
    private InteractorPoolEnricher interactorPoolEnricher;
    private ProteinEnricher proteinEnricher;
    private InteractorEnricher<BioactiveEntity> bioactiveEntityEnricher;
    private InteractorEnricher<Gene> geneEnricher;
    private ComplexEnricher complexEnricher;

    public CompositeInteractorEnricher(AbstractInteractorEnricher<Interactor> interactorBaseEnricher){
        super();
        if (interactorBaseEnricher == null){
            throw new IllegalArgumentException("At least the interactor base enricher is needed and cannot be null") ;
        }
        this.interactorBaseEnricher = interactorBaseEnricher;
    }

    public InteractorEnricher<Polymer> getPolymerBaseEnricher() {
        return polymerBaseEnricher;
    }

    public void setPolymerBaseEnricher(InteractorEnricher<Polymer> polymerBaseEnricher) {
        this.polymerBaseEnricher = polymerBaseEnricher;
    }

    public InteractorPoolEnricher getInteractorPoolEnricher() {
        return interactorPoolEnricher;
    }

    public void setInteractorPoolEnricher(InteractorPoolEnricher interactorPoolEnricher) {
        this.interactorPoolEnricher = interactorPoolEnricher;
    }

    public ProteinEnricher getProteinEnricher() {
        return proteinEnricher;
    }

    public void setProteinEnricher(ProteinEnricher proteinEnricher) {
        this.proteinEnricher = proteinEnricher;
    }

    public InteractorEnricher<BioactiveEntity> getBioactiveEntityEnricher() {
        return bioactiveEntityEnricher;
    }

    public void setBioactiveEntityEnricher(InteractorEnricher<BioactiveEntity> bioactiveEntityEnricher) {
        this.bioactiveEntityEnricher = bioactiveEntityEnricher;
    }

    public InteractorEnricher<Gene> getGeneEnricher() {
        return geneEnricher;
    }

    public void setGeneEnricher(InteractorEnricher<Gene> geneEnricher) {
        this.geneEnricher = geneEnricher;
    }

    public ComplexEnricher getComplexEnricher() {
        return complexEnricher;
    }

    public void setComplexEnricher(ComplexEnricher complexEnricher) {
        this.complexEnricher = complexEnricher;
    }

    public AbstractInteractorEnricher<Interactor> getInteractorBaseEnricher() {
        return interactorBaseEnricher;
    }

    public void enrich(Interactor object) throws EnricherException {
        if(object == null)
            throw new IllegalArgumentException("Cannot enrich a null interactor.");
        if (object instanceof Polymer){
            if (object instanceof Protein && this.proteinEnricher != null){
               this.proteinEnricher.enrich((Protein)object);
            }
            else if (this.polymerBaseEnricher != null){
               this.polymerBaseEnricher.enrich((Polymer)object);
            }
            else{
                this.interactorBaseEnricher.enrich(object);
            }
        }
        else if (object instanceof Gene && this.geneEnricher != null){
            this.geneEnricher.enrich((Gene)object);
        }
        else if (object instanceof BioactiveEntity && this.bioactiveEntityEnricher != null){
             this.bioactiveEntityEnricher.enrich((BioactiveEntity)object);
        }
        else if (object instanceof Complex && this.complexEnricher != null){
           this.complexEnricher.enrich((Complex)object);
        }
        else if (object instanceof InteractorPool && this.interactorPoolEnricher != null){
            this.interactorPoolEnricher.enrich((InteractorPool)object);
        }
        else{
            this.interactorBaseEnricher.enrich(object);
        }
    }

    public void enrich(Collection<Interactor> objects) throws EnricherException {
        if(objects == null)
            throw new IllegalArgumentException("Cannot enrich a null collection of interactors.");

        for (Interactor object : objects){
            enrich(object);
        }
    }

    public void enrich(Interactor object, Interactor objectSource) throws EnricherException {
        if (object instanceof Polymer && objectSource instanceof Polymer){
            if (object instanceof Protein && objectSource instanceof Protein && this.proteinEnricher != null){
                this.proteinEnricher.enrich((Protein)object, (Protein)objectSource);
            }
            else if (this.polymerBaseEnricher != null){
                this.polymerBaseEnricher.enrich((Polymer)object, (Polymer)objectSource);
            }
            else{
                this.interactorBaseEnricher.enrich(object, objectSource);
            }
        }
        else if (object instanceof Gene && objectSource instanceof Gene && this.geneEnricher != null){
            this.geneEnricher.enrich((Gene)object, (Gene)objectSource);
        }
        else if (object instanceof BioactiveEntity && objectSource instanceof BioactiveEntity && this.bioactiveEntityEnricher != null){
            this.bioactiveEntityEnricher.enrich((BioactiveEntity)object, (BioactiveEntity)objectSource);
        }
        else if (object instanceof Complex && objectSource instanceof Complex && this.complexEnricher != null){
            this.complexEnricher.enrich((Complex)object, (Complex)objectSource);
        }
        else if (object instanceof InteractorPool && objectSource instanceof InteractorPool && this.interactorPoolEnricher != null){
            this.interactorPoolEnricher.enrich((InteractorPool)object, (InteractorPool)objectSource);
        }
        else{
            this.interactorBaseEnricher.enrich(object, objectSource);
        }
    }

    public InteractorFetcher<Interactor> getInteractorFetcher() {
        return this.interactorBaseEnricher.getInteractorFetcher();
    }

    public InteractorEnricherListener<Interactor> getListener() {
        return this.interactorBaseEnricher.getListener();
    }

    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return this.interactorBaseEnricher.getCvTermEnricher();
    }

    public OrganismEnricher getOrganismEnricher() {
        return this.interactorBaseEnricher.getOrganismEnricher();
    }

    public void setListener(InteractorEnricherListener<Interactor> listener) {
        this.interactorBaseEnricher.setListener(listener);
    }

    public void setCvTermEnricher(CvTermEnricher<CvTerm> enricher) {
        this.interactorBaseEnricher.setCvTermEnricher(enricher);
        if (getProteinEnricher() != null){
            getProteinEnricher().setCvTermEnricher(enricher);
        }
        if (getPolymerBaseEnricher() != null){
            getPolymerBaseEnricher().setCvTermEnricher(enricher);
        }
        if (getGeneEnricher() != null){
            getGeneEnricher().setCvTermEnricher(enricher);
        }
        if (getBioactiveEntityEnricher() != null){
            getBioactiveEntityEnricher().setCvTermEnricher(enricher);
        }
        if (getComplexEnricher() != null){
            getComplexEnricher().setCvTermEnricher(enricher);
        }
        if (getInteractorPoolEnricher() != null){
            getInteractorPoolEnricher().setCvTermEnricher(enricher);
        }
    }

    public void setOrganismEnricher(OrganismEnricher enricher) {
        this.interactorBaseEnricher.setOrganismEnricher(enricher);
        if (getProteinEnricher() != null){
            getProteinEnricher().setOrganismEnricher(enricher);
        }
        if (getPolymerBaseEnricher() != null){
            getPolymerBaseEnricher().setOrganismEnricher(enricher);
        }
        if (getGeneEnricher() != null){
            getGeneEnricher().setOrganismEnricher(enricher);
        }
        if (getBioactiveEntityEnricher() != null){
            getBioactiveEntityEnricher().setOrganismEnricher(enricher);
        }
        if (getComplexEnricher() != null){
            getComplexEnricher().setOrganismEnricher(enricher);
        }
        if (getInteractorPoolEnricher() != null){
            getInteractorPoolEnricher().setOrganismEnricher(enricher);
        }
    }
}
