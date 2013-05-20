package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;

/**
 * Factory for Xref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class XrefFactory {

    public Xref createXref(String databaseName, String databaseMi, String id){
        return new DefaultXref(CvTermFactory.createMICvTerm(databaseName, databaseMi), id);
    }

    public Xref createXref(String databaseName, String id){
        return new DefaultXref(CvTermFactory.createMICvTerm(databaseName, null), id);
    }

    public Xref createXrefWithQualifier(String databaseName, String databaseMi, String id, String qualifierName, String qualifierMi){
        return new DefaultXref(CvTermFactory.createMICvTerm(databaseName, databaseMi), id, CvTermFactory.createMICvTerm(qualifierName, qualifierMi));
    }

    public Xref createXrefWithQualifier(String databaseName, String id, String qualifierName){
        return new DefaultXref(CvTermFactory.createMICvTerm(databaseName, null), id, CvTermFactory.createMICvTerm(qualifierName, null));
    }

    public Xref createIdentityXref(String databaseName, String databaseMi, String id){
        return createXrefWithQualifier(databaseName, databaseMi, id, Xref.IDENTITY, Xref.IDENTITY_MI);
    }

    public Xref createIdentityXref(String databaseName, String id){
        return createXrefWithQualifier(databaseName, null, id, Xref.IDENTITY, Xref.IDENTITY_MI);
    }

    public Xref createSecondaryXref(String databaseName, String databaseMi, String id){
        return createXrefWithQualifier(databaseName, databaseMi, id, Xref.SECONDARY, Xref.SECONDARY_MI);
    }

    public Xref createSecondaryXref(String databaseName, String id){
        return createXrefWithQualifier(databaseName, null, id, Xref.SECONDARY, Xref.SECONDARY_MI);
    }

    public Xref createUniprotIdentity(String uniprot){
        return createIdentityXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, uniprot);
    }

    public Xref createRefseqIdentity(String refseq){
        return createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, refseq);
    }

    public Xref createEnsemblIdentity(String ensembl){
        return createIdentityXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, ensembl);
    }

    public Xref createEnsemblGenomesIdentity(String ensembl){
        return createIdentityXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, ensembl);
    }

    public Xref createEntrezGeneIdIdentity(String geneId){
        return createIdentityXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, geneId);
    }

    public Xref createDdbjEmblGenbankIdentity(String id){
        return createIdentityXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, id);
    }

    public Xref createChebiIdentity(String id){
        return createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, id);
    }

    public Xref createPubmedIdentity(String id){
        return createIdentityXref(Xref.PUBMED, Xref.PUBMED_MI, id);
    }

    public Xref createDoiIdentity(String id){
        return createIdentityXref(Xref.DOI, Xref.DOI_MI, id);
    }

    public Xref createPsiMiIdentity(String id){
        return createIdentityXref(CvTerm.PSI_MI, CvTerm.PSI_MI_MI, id);
    }

    public Xref createPsiModIdentity(String id){
        return createSecondaryXref(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI, id);
    }

    public Xref createUniprotSecondary(String uniprot){
        return createSecondaryXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, uniprot);
    }

    public Xref createRefseqSecondary(String refseq){
        return createSecondaryXref(Xref.REFSEQ, Xref.REFSEQ_MI, refseq);
    }

    public Xref createEnsemblSecondary(String ensembl){
        return createSecondaryXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, ensembl);
    }

    public Xref createEnsemblGenomesSecondary(String ensembl){
        return createSecondaryXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, ensembl);
    }

    public Xref createEntrezGeneIdSecondary(String geneId){
        return createSecondaryXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, geneId);
    }

    public Xref createDdbjEmblGenbankSecondary(String id){
        return createSecondaryXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, id);
    }

    public Xref createChebiSecondary(String id){
        return createSecondaryXref(Xref.CHEBI, Xref.CHEBI_MI, id);
    }

    public Xref createPsiMiSecondary(String id){
        return createSecondaryXref(CvTerm.PSI_MI, CvTerm.PSI_MI_MI, id);
    }

    public Xref createPsiModSecondary(String id){
        return createSecondaryXref(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI, id);
    }
}
