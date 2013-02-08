package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Factory for creating CvTerms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class CvTermFactory {

    public static CvTerm createPsiMiDatabaseNameOnly(){
        return new DefaultCvTerm(CvTerm.PSI_MI);
    }

    public static CvTerm createIdentityQualifierNameOnly(){
        return new DefaultCvTerm(Xref.IDENTITY);
    }

    public static CvTerm createMICvTerm(String name, String MI){
        if (MI != null){
            return new DefaultCvTerm(name, new DefaultXref(CvTermUtils.getPsimi(), MI, CvTermUtils.getIdentity()));
        }
        else {
            return new DefaultCvTerm(name);
        }
    }

    public static CvTerm createMODCvTerm(String name, String MOD){
        if (MOD != null){
            return new DefaultCvTerm(name, new DefaultXref(CvTermUtils.getPsimod(), MOD, CvTermUtils.getIdentity()));
        }
        else {
            return new DefaultCvTerm(name);
        }
    }

    public static CvTerm createPsiMiDatabase(){
        return createMICvTerm(CvTerm.PSI_MI, CvTerm.PSI_MI_MI);
    }

    public static CvTerm createPsiModDatabase(){
        return createMICvTerm(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI);
    }

    public static CvTerm createIdentityQualifier(){
        return createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI);
    }

    public static CvTerm createChebiDatabase(){
        return createMICvTerm(Xref.CHEBI, Xref.CHEBI_MI);
    }

    public static CvTerm createEnsemblDatabase(){
        return createMICvTerm(Xref.ENSEMBL, Xref.ENSEMBL_MI);
    }

    public static CvTerm createEnsemblGenomesDatabase(){
        return createMICvTerm(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI);
    }

    public static CvTerm createEntrezGeneIdDatabase(){
        return createMICvTerm(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI);
    }

    public static CvTerm createRefseqDatabase(){
        return createMICvTerm(Xref.REFSEQ, Xref.REFSEQ_MI);
    }

    public static CvTerm createDdbjEmblGenbankDatabase(){
        return createMICvTerm(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI);
    }

    public static CvTerm createUniprotkbDatabase(){
        return createMICvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI);
    }

    public static CvTerm createImexDatabase(){
        return createMICvTerm(Xref.IMEX, Xref.IMEX_MI);
    }

    public static CvTerm createPubmedDatabase(){
        return createMICvTerm(Xref.PUBMED_MI, Xref.PUBMED);
    }

    public static CvTerm createDoiDatabase(){
        return createMICvTerm(Xref.DOI_MI, Xref.DOI);
    }

    public static CvTerm createInterproDatabase(){
        return createMICvTerm(Xref.INTERPRO_MI, Xref.INTERPRO);
    }

    public static CvTerm createSmile(){
        return createMICvTerm(Checksum.SMILE, Checksum.SMILE_MI);
    }

    public static CvTerm createStandardInchi(){
        return createMICvTerm(Checksum.INCHI, Checksum.INCHI_MI);
    }

    public static CvTerm createStandardInchiKey(){
        return createMICvTerm(Checksum.INCHI_KEY, Checksum.INCHI_KEY_MI);
    }

    public static CvTerm createRogid(){
        return createMICvTerm(Checksum.ROGID, Checksum.ROGID_MI);
    }

    public static CvTerm createCertainStatus(){
        return createMICvTerm(Position.CERTAIN, Position.CERTAIN_MI);
    }

    public static CvTerm createRangeStatus(){
        return createMICvTerm(Position.RANGE, Position.RANGE_MI);
    }

    public static CvTerm createUndeterminedStatus(){
        return createMICvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
    }

    public static CvTerm createNTerminalRangeStatus(){
        return createMICvTerm(Position.N_TERMINAL_RANGE, Position.N_TERMINAL_RANGE_MI);
    }

    public static CvTerm createCTerminalRangeStatus(){
        return createMICvTerm(Position.C_TERMINAL_RANGE, Position.C_TERMINAL_RANGE_MI);
    }

    public static CvTerm createNTerminalStatus(){
        return createMICvTerm(Position.N_TERMINAL, Position.N_TERMINAL_MI);
    }

    public static CvTerm createCTerminalStatus(){
        return createMICvTerm(Position.C_TERMINAL, Position.C_TERMINAL_MI);
    }

    public static CvTerm createRaggedNTerminalStatus(){
        return createMICvTerm(Position.RAGGED_N_TERMINAL, Position.RAGGED_N_TERMINAL_MI);
    }

    public static CvTerm createGreaterThanRangeStatus(){
        return createMICvTerm(Position.GREATER_THAN, Position.GREATER_THAN_MI);
    }

    public static CvTerm createLessThanRangeStatus(){
        return createMICvTerm(Position.LESS_THAN, Position.LESS_THAN_MI);
    }

    public static CvTerm createGeneInteractorType(){
        return createMICvTerm(Gene.GENE, Gene.GENE_MI);
    }

    public static CvTerm createGeneNameAliasType(){
        return createMICvTerm(Alias.GENE_NAME, Alias.GENE_NAME_MI);
    }

    public static CvTerm createUnspecifiedRole(){
        return createMICvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
    }

    public static CvTerm createComplexPhysicalProperties(){
        return createMICvTerm(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI);
    }

    public static CvTerm createImexPrimaryQualifier(){
        return createMICvTerm(Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI);
    }

    public static CvTerm createAllosteryCooperativeMechanism(){
        return createMICvTerm(CooperativeInteraction.ALLOSTERY, CooperativeInteraction.ALLOSTERY_ID);
    }

    public static CvTerm createIdentityXrefQualifier(){
        return createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI);
    }
}
