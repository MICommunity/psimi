package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * General enricher for interactors that can use sub enrichers for enriching specific interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/14</pre>
 */

public class CompositeInteractorEnricher implements MIEnricher<Interactor>{

    private AbstractInteractorEnricher<Interactor> interactorBaseEnricher;
    private PolymerEnricher<Polymer> polymerBaseEnricher;
    private InteractorPoolEnricher interactorPoolEnricher;
    private ProteinEnricher proteinEnricher;
    private BioactiveEntityEnricher bioactiveEntityEnricher;
    private GeneEnricher geneEnricher;
    private ComplexEnricher complexEnricher;

    public CompositeInteractorEnricher(AbstractInteractorEnricher<Interactor> interactorBaseEnricher, PolymerEnricher<Polymer> polymerBaseEnricher,
                                       InteractorPoolEnricher interactorPoolEnricher, ProteinEnricher proteinEnricher,
                                       BioactiveEntityEnricher bioactiveEntityEnricher, GeneEnricher geneEnricher,
                                       ComplexEnricher complexEnricher){
        super();
        if (interactorBaseEnricher == null){
            throw new IllegalArgumentException("At least the interactor base enricher is needed and cannot be null") ;
        }
        this.interactorBaseEnricher = interactorBaseEnricher;
        this.interactorPoolEnricher = interactorPoolEnricher;
        this.polymerBaseEnricher = polymerBaseEnricher;
        this.proteinEnricher = proteinEnricher;
        this.geneEnricher = geneEnricher;
        this.bioactiveEntityEnricher = bioactiveEntityEnricher;
        this.complexEnricher = complexEnricher;
    }

    public PolymerEnricher<Polymer> getPolymerBaseEnricher() {
        return polymerBaseEnricher;
    }

    public void setPolymerBaseEnricher(PolymerEnricher<Polymer> polymerBaseEnricher) {
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

    public BioactiveEntityEnricher getBioactiveEntityEnricher() {
        return bioactiveEntityEnricher;
    }

    public void setBioactiveEntityEnricher(BioactiveEntityEnricher bioactiveEntityEnricher) {
        this.bioactiveEntityEnricher = bioactiveEntityEnricher;
    }

    public GeneEnricher getGeneEnricher() {
        return geneEnricher;
    }

    public void setGeneEnricher(GeneEnricher geneEnricher) {
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
}
