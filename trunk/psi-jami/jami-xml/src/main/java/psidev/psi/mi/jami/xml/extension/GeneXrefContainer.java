package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Xref container for Gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "geneXrefContainer")
public class GeneXrefContainer extends InteractorXrefContainer{

    private Xref ensembl;
    private Xref ensemblGenome;
    private Xref entrezGeneId;
    private Xref refseq;

    @Override
    /**
     * Return the first ensembl identifier if provided, otherwise the first ensemblGenomes if provided, otherwise
     * the first entrez/gene id if provided, otherwise the first refseq id if provided
     * otherwise the first identifier in the list of identifiers
     */
    public Xref getPreferredIdentifier() {
        return ensembl != null ? ensembl : (ensemblGenome != null ? ensemblGenome : (entrezGeneId != null ? entrezGeneId : (refseq != null ? refseq : super.getPreferredIdentifier())));
    }

    public String getEnsembl() {
        return this.ensembl != null ? this.ensembl.getId() : null;
    }

    public void setEnsembl(String ac) {
        FullIdentifierList geneIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new ensembl if not null
        if (ac != null){
            CvTerm ensemblDatabase = CvTermUtils.createEnsemblDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old ensembl if not null
            if (this.ensembl != null){
                geneIdentifiers.removeOnly(this.ensembl);
                if (this.ensembl instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.ensembl);
                }
            }
            this.ensembl = new XmlXref(ensemblDatabase, ac, identityQualifier);
            geneIdentifiers.addOnly(this.ensembl);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.ensembl);
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
        FullIdentifierList geneIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new ensembl genomes if not null
        if (ac != null){
            CvTerm ensemblGenomesDatabase = CvTermUtils.createEnsemblGenomesDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old ensembl genome if not null
            if (this.ensemblGenome != null){
                geneIdentifiers.removeOnly(this.ensemblGenome);
                if (this.ensemblGenome instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.ensemblGenome);
                }
            }
            this.ensemblGenome = new XmlXref(ensemblGenomesDatabase, ac, identityQualifier);
            geneIdentifiers.addOnly(this.ensemblGenome);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.ensemblGenome);
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
        FullIdentifierList geneIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new entrez gene id genomes if not null
        if (id != null){
            CvTerm entrezDatabase = CvTermUtils.createEntrezGeneIdDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old entrez gene id if not null
            if (this.entrezGeneId!= null){
                geneIdentifiers.removeOnly(this.entrezGeneId);
                if (this.entrezGeneId instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.entrezGeneId);
                }
            }
            this.entrezGeneId = new XmlXref(entrezDatabase, id, identityQualifier);
            geneIdentifiers.addOnly(this.entrezGeneId);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.ensemblGenome);
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
        FullIdentifierList geneIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermUtils.createRefseqDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove refseq if not null
            if (this.refseq!= null){
                geneIdentifiers.removeOnly(this.refseq);
                if (this.refseq instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.refseq);
                }
            }
            this.refseq = new XmlXref(refseqDatabase, ac, identityQualifier);
            geneIdentifiers.addOnly(this.refseq);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.refseq);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!geneIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(geneIdentifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    protected void processAddedBasicIdentifier(Xref added) {
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

    protected void processRemovedBasicIdentifier(Xref removed) {
        if (ensembl != null && ensembl.equals(removed)){
            ensembl = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.ENSEMBL_MI, Xref.ENSEMBL);
        }
        else if (ensemblGenome != null && ensemblGenome.equals(removed)){
            ensemblGenome = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES);
        }
        else if (entrezGeneId != null && entrezGeneId.equals(removed)){
            entrezGeneId = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE);
        }
        else if (refseq != null &&refseq.equals(removed)){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    protected void clearPropertiesLinkedToBasicIdentifiers() {
        ensembl = null;
        ensemblGenome = null;
        entrezGeneId = null;
        refseq = null;
    }

    @Override
    protected void processRemovedPrimaryRef(Xref removed) {
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef);
        }
        else {
            processRemovedBasicIdentifier(this.primaryRef);
        }
    }

    @Override
    protected void processAddedPrimaryRef() {
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
            processAddedBasicIdentifier(this.primaryRef);
        }
        else {
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
        }
    }

    @Override
    protected void processAddedIdentifier(Xref added) {
        super.processAddedIdentifier(added);
        processAddedBasicIdentifier(added);
    }

    @Override
    protected void processRemovedIdentifier(Xref removed) {
        super.processRemovedIdentifier(removed);
        processRemovedBasicIdentifier(removed);
    }

    @Override
    protected void clearFullIdentifiers() {
        super.clearFullIdentifiers();
        clearPropertiesLinkedToBasicIdentifiers();
    }
}
