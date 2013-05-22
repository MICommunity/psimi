package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactGeneComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Collection;

/**
 * Default implementation for gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class DefaultGene extends DefaultInteractor implements Gene {

    private Xref ensembl;
    private Xref ensemblGenome;
    private Xref entrezGeneId;
    private Xref refseq;

    public DefaultGene(String name) {
        super(name, CvTermFactory.createGeneInteractorType());
    }

    public DefaultGene(String name, String fullName) {
        super(name, fullName, CvTermFactory.createGeneInteractorType());
    }

    public DefaultGene(String name, Organism organism) {
        super(name, CvTermFactory.createGeneInteractorType(), organism);
    }

    public DefaultGene(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), organism);
    }

    public DefaultGene(String name, Xref uniqueId) {
        super(name, CvTermFactory.createGeneInteractorType(), uniqueId);
    }

    public DefaultGene(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), uniqueId);
    }

    public DefaultGene(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermFactory.createGeneInteractorType(), organism, uniqueId);
    }

    public DefaultGene(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), organism, uniqueId);
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new GeneIdentifierList());
    }

    public String getEnsembl() {
        return this.ensembl != null ? this.ensembl.getId() : null;
    }

    public void setEnsembl(String ac) {
        Collection<Xref> geneIdentifiers = getIdentifiers();

        // add new ensembl if not null
        if (ac != null){
            CvTerm ensemblDatabase = CvTermFactory.createEnsemblDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old ensembl if not null
            if (this.ensembl != null){
                geneIdentifiers.remove(this.ensembl);
            }
            this.ensembl = new DefaultXref(ensemblDatabase, ac, identityQualifier);
            geneIdentifiers.add(this.ensembl);
        }
        // remove all ensembl if the collection is not empty
        else if (!geneIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(geneIdentifiers, Xref.ENSEMBL_MI, Xref.ENSEMBL);
            this.ensembl = null;
        }
    }

    public String getEnsembleGenome() {
        return this.ensemblGenome != null ? this.ensemblGenome.getId() : null;
    }

    public void setEnsemblGenome(String ac) {
        Collection<Xref> geneIdentifiers = getIdentifiers();

        // add new ensembl genomes if not null
        if (ac != null){
            CvTerm ensemblGenomesDatabase = CvTermFactory.createEnsemblGenomesDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old ensembl genome if not null
            if (this.ensemblGenome != null){
                geneIdentifiers.remove(this.ensemblGenome);
            }
            this.ensemblGenome = new DefaultXref(ensemblGenomesDatabase, ac, identityQualifier);
            geneIdentifiers.add(this.ensemblGenome);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!geneIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(geneIdentifiers, Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES);
            this.ensemblGenome = null;
        }
    }

    public String getEntrezGeneId() {
        return this.entrezGeneId != null ? this.entrezGeneId.getId() : null;
    }

    public void setEntrezGeneId(String id) {
        Collection<Xref> geneIdentifiers = getIdentifiers();

        // add new entrez gene id genomes if not null
        if (id != null){
            CvTerm entrezDatabase = CvTermFactory.createEntrezGeneIdDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old entrez gene id if not null
            if (this.entrezGeneId!= null){
                geneIdentifiers.remove(this.entrezGeneId);
            }
            this.entrezGeneId = new DefaultXref(entrezDatabase, id, identityQualifier);
            geneIdentifiers.add(this.entrezGeneId);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!geneIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(geneIdentifiers, Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE);
            this.entrezGeneId = null;
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String ac) {
        Collection<Xref> geneIdentifiers = getIdentifiers();

        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermFactory.createRefseqDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove refseq if not null
            if (this.refseq!= null){
                geneIdentifiers.remove(this.refseq);
            }
            this.refseq = new DefaultXref(refseqDatabase, ac, identityQualifier);
            geneIdentifiers.add(this.refseq);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!geneIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(geneIdentifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    protected void processAddedIdentifierEvent(Xref added) {
        // the added identifier is ensembl and it is not the current ensembl identifier
        if (ensembl != added && XrefUtils.isXrefFromDatabase(added, Xref.ENSEMBL_MI, Xref.ENSEMBL)){
            // the current ensembl identifier is not identity, we may want to set ensembl Identifier
            if (!XrefUtils.doesXrefHaveQualifier(ensembl, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the ensembl identifier is not set, we can set the ensembl identifier
                if (ensembl == null){
                    ensembl = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    ensembl = added;
                }
                // the added xref is secondary object and the current ensembl identifier is not a secondary object, we reset ensembl identifier
                else if (!XrefUtils.doesXrefHaveQualifier(ensembl, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    ensembl = added;
                }
            }
        }
        // the added identifier is ensembl genomes and it is not the current ensembl genomes identifier
        else if (ensemblGenome != added && XrefUtils.isXrefFromDatabase(added, Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES)){
            // the current ensembl genomes identifier is not identity, we may want to set ensembl genomes Identifier
            if (!XrefUtils.doesXrefHaveQualifier(ensemblGenome, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the ensembl genomes Identifier is not set, we can set the ensembl genomes Identifier
                if (ensemblGenome == null){
                    ensemblGenome = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    ensemblGenome = added;
                }
                // the added xref is secondary object and the current ensembl genomes Identifier is not a secondary object, we reset ensembl genomes Identifier
                else if (!XrefUtils.doesXrefHaveQualifier(ensemblGenome, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    ensemblGenome = added;
                }
            }
        }
        // the added identifier is entrez gene id and it is not the current entrez gene id
        else if (entrezGeneId != added && XrefUtils.isXrefFromDatabase(added, Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE)){
            // the current entrez gene id is not identity, we may want to set entrez gene id
            if (!XrefUtils.doesXrefHaveQualifier(entrezGeneId, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the entrez gene id is not set, we can set the entrez gene idr
                if (entrezGeneId == null){
                    entrezGeneId = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    entrezGeneId = added;
                }
                // the added xref is secondary object and the current entrez gene id is not a secondary object, we reset entrez gene id
                else if (!XrefUtils.doesXrefHaveQualifier(entrezGeneId, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    entrezGeneId = added;
                }
            }
        }
        // the added identifier is refseq id and it is not the current refseq id
        else if (refseq != added && XrefUtils.isXrefFromDatabase(added, Xref.REFSEQ_MI, Xref.REFSEQ)){
            // the current refseq id is not identity, we may want to set refseq id
            if (!XrefUtils.doesXrefHaveQualifier(refseq, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the refseq id is not set, we can set the refseq id
                if (refseq == null){
                    refseq = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    refseq = added;
                }
                // the added xref is secondary object and the current refseq id is not a secondary object, we reset refseq id
                else if (!XrefUtils.doesXrefHaveQualifier(entrezGeneId, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    refseq = added;
                }
            }
        }
    }

    protected void processRemovedIdentifierEvent(Xref removed) {
        if (ensembl != null && ensemblGenome.equals(removed)){
            ensembl = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.ENSEMBL_MI, Xref.ENSEMBL);
        }
        else if (ensemblGenome != null && ensemblGenome.equals(removed)){
            ensemblGenome = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES);
        }
        else if (entrezGeneId != null && entrezGeneId.equals(removed)){
            entrezGeneId = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE);
        }
        else if (refseq != null &&refseq.equals(removed)){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        ensembl = null;
        ensemblGenome = null;
        entrezGeneId = null;
        refseq = null;
    }

    @Override
    public void setInteractorType(CvTerm type) {
        if (!DefaultCvTermComparator.areEquals(type, CvTermUtils.getGene())){
            throw new IllegalArgumentException("This interactor is a Gene and the only available interactor type is gene (MI:0301)");
        }
        super.setInteractorType(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Gene)){
            return false;
        }

        // use UnambiguousExactGeneEntity comparator for equals
        return UnambiguousExactGeneComparator.areEquals(this, (Gene) o);
    }

    @Override
    public String toString() {
        return ensembl != null ? ensembl.getId() : (ensemblGenome != null ? ensemblGenome.getId() : (entrezGeneId != null ? entrezGeneId.getId() : (refseq != null ? refseq.getId() : super.toString())));
    }

    private class GeneIdentifierList extends AbstractListHavingPoperties<Xref> {
        public GeneIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();
        }
    }
}
