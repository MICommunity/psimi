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

/**
 * Default implementation for gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class DefaultGene extends DefaultInteractor implements Gene {

    protected Xref ensembl;
    protected Xref ensemblGenome;
    protected Xref entrezGeneId;
    protected Xref refseq;

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
    protected void initializeIdentifiers() {
        this.identifiers = new GeneIdentifierList();
    }

    public String getEnsembl() {
        return this.ensembl != null ? this.ensembl.getId() : null;
    }

    public void setEnsembl(String ac) {
        // add new ensembl if not null
        if (ac != null){
            CvTerm ensemblDatabase = CvTermFactory.createEnsemblDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old ensembl if not null
            if (this.ensembl != null){
                identifiers.remove(this.ensembl);
            }
            this.ensembl = new DefaultXref(ensemblDatabase, ac, identityQualifier);
            this.identifiers.add(this.ensembl);
        }
        // remove all ensembl if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(this.identifiers, Xref.ENSEMBL_MI, Xref.ENSEMBL);
            this.ensembl = null;
        }
    }

    public String getEnsembleGenome() {
        return this.ensemblGenome != null ? this.ensemblGenome.getId() : null;
    }

    public void setEnsemblGenome(String ac) {
        // add new ensembl genomes if not null
        if (ac != null){
            CvTerm ensemblGenomesDatabase = CvTermFactory.createEnsemblGenomesDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old ensembl genome if not null
            if (this.ensemblGenome != null){
                identifiers.remove(this.ensemblGenome);
            }
            this.ensemblGenome = new DefaultXref(ensemblGenomesDatabase, ac, identityQualifier);
            this.identifiers.add(this.ensemblGenome);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(this.identifiers, Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES);
            this.ensemblGenome = null;
        }
    }

    public String getEntrezGeneId() {
        return this.entrezGeneId != null ? this.entrezGeneId.getId() : null;
    }

    public void setEntrezGeneId(String id) {
        // add new entrez gene id genomes if not null
        if (id != null){
            CvTerm entrezDatabase = CvTermFactory.createEntrezGeneIdDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old entrez gene id if not null
            if (this.entrezGeneId!= null){
                identifiers.remove(this.entrezGeneId);
            }
            this.entrezGeneId = new DefaultXref(entrezDatabase, id, identityQualifier);
            this.identifiers.add(this.entrezGeneId);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(this.identifiers, Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE);
            this.entrezGeneId = null;
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String ac) {
        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermFactory.createRefseqDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove refseq if not null
            if (this.refseq!= null){
                identifiers.remove(this.refseq);
            }
            this.refseq = new DefaultXref(refseqDatabase, ac, identityQualifier);
            this.identifiers.add(this.refseq);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(this.identifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    @Override
    public void setType(CvTerm type) {
        if (type == null){
            throw new IllegalArgumentException("The interactor type cannot be null.");
        }
        else if (!DefaultCvTermComparator.areEquals(type, CvTermUtils.getGene())){
            throw new IllegalArgumentException("This interactor is a Gene and the only available interactor type is gene (MI:0301)");
        }
        this.type = type;
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

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (ensembl != null && ensemblGenome.equals(removed)){
                ensembl = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.ENSEMBL_MI, Xref.ENSEMBL);
            }
            else if (ensemblGenome != null && ensemblGenome.equals(removed)){
                ensemblGenome = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES);
            }
            else if (entrezGeneId != null && entrezGeneId.equals(removed)){
                entrezGeneId = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE);
            }
            else if (refseq != null &&refseq.equals(removed)){
                refseq = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.REFSEQ_MI, Xref.REFSEQ);
            }
        }

        @Override
        protected void clearProperties() {
            ensembl = null;
            ensemblGenome = null;
            entrezGeneId = null;
            refseq = null;
        }
    }
}
