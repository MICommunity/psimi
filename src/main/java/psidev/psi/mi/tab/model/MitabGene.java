package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Collection;
import java.util.List;

/**
 * Default MITAB interactor implementation for genes which is a patch for backward compatibility.
 * It only contains molecule information (not any participant information such as experimental role, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/02/13</pre>
 */

public class MitabGene extends MitabInteractor implements Gene {
    private Xref ensembl;
    private Xref ensemblGenome;
    private Xref entrezGeneId;
    private Xref refseq;

    public MitabGene() {
        super(CvTermFactory.createGeneInteractorType());
    }

    public MitabGene(List<CrossReference> identifiers) {
        super(identifiers, CvTermFactory.createGeneInteractorType());
    }

    public String getEnsembl() {
        return this.ensembl != null ? this.ensembl.getId() : null;
    }

    public void setEnsembl(String ac) {
        Collection<Xref> geneIdentifiers = getIdentifiers();
        // add new ensembl if not null
        if (ac != null){
            // first remove old ensembl if not null
            if (this.ensembl != null){
                geneIdentifiers.remove(this.ensembl);
            }
            this.ensembl = new CrossReferenceImpl(Xref.ENSEMBL, ac);
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
            // first remove old ensembl genome if not null
            if (this.ensemblGenome != null){
                geneIdentifiers.remove(this.ensemblGenome);
            }
            this.ensemblGenome = new CrossReferenceImpl(Xref.ENSEMBL_GENOMES, ac);
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
            // first remove old entrez gene id if not null
            if (this.entrezGeneId!= null){
                geneIdentifiers.remove(this.entrezGeneId);
            }
            this.entrezGeneId = new CrossReferenceImpl(Xref.ENTREZ_GENE, id);
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
            // first remove refseq if not null
            if (this.refseq!= null){
                geneIdentifiers.remove(this.refseq);
            }
            this.refseq = new CrossReferenceImpl(Xref.REFSEQ, ac);
            geneIdentifiers.add(this.refseq);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!geneIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(geneIdentifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    @Override
    protected void processAddedIdentifierEvent(Xref added) {
        // the added identifier is ensembl and it is not the current ensembl identifier
        if (ensembl == null && XrefUtils.isXrefFromDatabase(added, Xref.ENSEMBL_MI, Xref.ENSEMBL)){
            // the current ensembl identifier is not identity, we may want to set ensembl Identifier
            this.ensembl = added;
        }
        // the added identifier is ensembl genomes and it is not the current ensembl genomes identifier
        else if (ensemblGenome == added && XrefUtils.isXrefFromDatabase(added, Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES)){
            // the current ensembl genomes identifier is not identity, we may want to set ensembl genomes Identifier
            this.ensemblGenome = added;
        }
        // the added identifier is entrez gene id and it is not the current entrez gene id
        else if (entrezGeneId != added && XrefUtils.isXrefFromDatabase(added, Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE)){
            // the current entrez gene id is not identity, we may want to set entrez gene id
            this.entrezGeneId = added;
        }
        // the added identifier is refseq id and it is not the current refseq id
        else if (refseq != added && XrefUtils.isXrefFromDatabase(added, Xref.REFSEQ_MI, Xref.REFSEQ)){
            // the current refseq id is not identity, we may want to set refseq id
            this.refseq = added;
        }
    }

    @Override
    protected void processRemovedIdentifierEvent(Xref removed) {
        if (ensembl != null && XrefUtils.isXrefFromDatabase(removed, Xref.ENSEMBL_MI, Xref.ENSEMBL)
                && removed.getId().equals(ensembl.getId())){
            ensembl = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.ENSEMBL_MI, Xref.ENSEMBL);
        }
        else if (ensemblGenome != null && XrefUtils.isXrefFromDatabase(removed, Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES)
                && removed.getId().equals(ensemblGenome.getId())){
            ensemblGenome = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES);
        }
        else if (entrezGeneId != null && XrefUtils.isXrefFromDatabase(removed, Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE)
                && removed.getId().equals(entrezGeneId.getId())){
            entrezGeneId = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE);
        }
        else if (refseq != null && XrefUtils.isXrefFromDatabase(removed, Xref.REFSEQ_MI, Xref.REFSEQ)
                && removed.getId().equals(refseq.getId())){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    @Override
    protected void clearPropertiesLinkedToIdentifiers() {
        ensembl = null;
        ensemblGenome = null;
        entrezGeneId = null;
        refseq = null;
    }
}
