package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

/**
 * Utility class for CvTerms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class CvTermUtils {

    private static CvTerm gene;
    private static CvTerm allosteryMechanism;
    private static CvTerm psimi;
    private static CvTerm psimod;
    private static CvTerm psipar;
    private static CvTerm identity;
    private static CvTerm secondary;
    private static CvTerm imexPrimary;
    private static CvTerm undetermined;
    private static CvTerm nTerminalRange;
    private static CvTerm cTerminalRange;
    private static CvTerm nTerminal;
    private static CvTerm cTerminal;
    private static CvTerm nTerminalRagged;
    private static CvTerm greaterThan;
    private static CvTerm lessThan;
    private static CvTerm certain;
    private static CvTerm range;
    private static CvTerm putative_self;
    private static CvTerm self;
    private static CvTerm unknownInteractorType;

    /**
     * Gets the unique identifier of a CvObject. If it has PSI MI Identifier (miIdentifier) return it, otherwise returns the MOD identifier,
     * If no MOD identifier, returns the PAR identifier.
     * if no PAR identifier, returns the first identifier in the list of identifiers.
     *
     * @param cvObject The object to get the identifier from.
     * @return The identifier. Will be null if no miIdentifier or identity xref is found.
     * @since 1.8.0
     */
    public static String getBestIdentifier(CvTerm cvObject) {
        if (cvObject == null) return null;

        // try the PSI MI first
        if (cvObject.getMIIdentifier() != null) {
            return cvObject.getMIIdentifier();
        }
        // try the PSI MOD second
        else if (cvObject.getMODIdentifier() != null) {
            return cvObject.getMODIdentifier();
        }
        // try the PSI PAR third
        else if (cvObject.getPARIdentifier() != null) {
            return cvObject.getPARIdentifier();
        }
        else if (!cvObject.getIdentifiers().isEmpty()) {
           return cvObject.getIdentifiers().iterator().next().getId();
        }
        else{
            return null;
        }
    }

    public static CvTerm getGene() {
        if (gene == null){
            gene = createGeneNameAliasType();
        }
        return gene;
    }

    public static CvTerm getAllosteryMechanism() {
        if (allosteryMechanism == null){
            allosteryMechanism = createAllosteryCooperativeMechanism();
        }
        return allosteryMechanism;
    }

    public static CvTerm getPsimi() {
        if (psimi == null){
            psimi = createPsiMiDatabaseNameOnly();
        }
        return psimi;
    }

    public static CvTerm getPsimod() {
        if (psimod == null){
            psimod = createPsiModDatabase();
        }
        return psimod;
    }

    public static CvTerm getPsipar() {
        if (psipar == null){
            psipar = createPsiParDatabase();
        }
        return psipar;
    }

    public static CvTerm getIdentity() {
        if (identity == null){
            identity = createIdentityQualifierNameOnly();
        }
        return identity;
    }

    public static CvTerm getSecondary() {
        if (secondary == null){
            secondary = createSecondaryXrefQualifier();
        }
        return secondary;
    }

    public static CvTerm getImexPrimary() {
        if (imexPrimary == null){
            imexPrimary = createImexPrimaryQualifier();
        }
        return imexPrimary;
    }

    public static CvTerm getUndetermined() {
        if (undetermined == null){
            undetermined = createUndeterminedStatus();
        }
        return undetermined;
    }

    public static CvTerm getNTerminalRange() {
        if (nTerminalRange == null){
            nTerminalRange = createNTerminalRangeStatus();
        }
        return nTerminalRange;
    }

    public static CvTerm getCTerminalRange() {
        if (cTerminalRange == null){
            cTerminalRange = createCTerminalRangeStatus();
        }
        return cTerminalRange;
    }

    public static CvTerm getFuzzyRange() {
        if (range == null){
            range = createRangeStatus();
        }
        return range;
    }

    public static CvTerm getNTerminal() {
        if (nTerminal == null){
            nTerminal = createNTerminalStatus();
        }
        return nTerminal;
    }

    public static CvTerm getCTerminal() {
        if (cTerminal == null){
            cTerminal = createCTerminalStatus();
        }
        return cTerminal;
    }

    public static CvTerm getNTerminalRagged() {
        if (nTerminalRagged == null){
            nTerminalRagged = createRaggedNTerminalStatus();
        }
        return nTerminalRagged;
    }

    public static CvTerm getGreaterThan() {
        if (greaterThan == null){
            greaterThan = createGreaterThanRangeStatus();
        }
        return greaterThan;
    }

    public static CvTerm getLessThan() {
        if (lessThan == null){
            lessThan = createLessThanRangeStatus();
        }
        return lessThan;
    }

    public static CvTerm getCertain() {
        if (certain == null){
            certain = createCertainStatus();
        }
        return certain;
    }

    public static CvTerm getPutativeSelf() {
        if (putative_self == null){
            putative_self = createPutativeSelf();
        }
        return putative_self;
    }

    public static CvTerm getSelf() {
        if (self == null){
            self = createSelf();
        }
        return self;
    }

    public static CvTerm getUnknownInteractorType() {
        if (unknownInteractorType == null){
            unknownInteractorType = createUnknownInteractorType();
        }
        return unknownInteractorType;
    }

    public static CvTerm createPsiMiDatabaseNameOnly(){
        return new DefaultCvTerm(CvTerm.PSI_MI);
    }

    public static CvTerm createIdentityQualifierNameOnly(){
        return new DefaultCvTerm(Xref.IDENTITY);
    }

    public static CvTerm createMICvTerm(String name, String MI){
        if (MI != null){
            return new DefaultCvTerm(name, new DefaultXref(new DefaultCvTerm(CvTerm.PSI_MI, CvTerm.PSI_MI_MI), MI, new DefaultCvTerm(Xref.IDENTITY, Xref.IDENTITY_MI)));
        }
        else {
            return new DefaultCvTerm(name);
        }
    }

    public static CvTerm createMODCvTerm(String name, String MOD){
        if (MOD != null){
            return new DefaultCvTerm(name, new DefaultXref(new DefaultCvTerm(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI), MOD, CvTermUtils.getIdentity()));
        }
        else {
            return new DefaultCvTerm(name);
        }
    }

    public static CvTerm createPARCvTerm(String name, String PAR){
        if (PAR != null){
            return new DefaultCvTerm(name, new DefaultXref(new DefaultCvTerm(CvTerm.PSI_PAR), PAR, CvTermUtils.getIdentity()));
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

    public static CvTerm createPsiParDatabase(){
        return createMICvTerm(CvTerm.PSI_PAR, null);
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
        return createMICvTerm(Xref.PUBMED, Xref.PUBMED_MI);
    }

    public static CvTerm createDoiDatabase(){
        return createMICvTerm(Xref.DOI, Xref.DOI_MI);
    }

    public static CvTerm createInterproDatabase(){
        return createMICvTerm(Xref.INTERPRO, Xref.INTERPRO_MI);
    }

    public static CvTerm createSmile(){
        return createMICvTerm(Checksum.SMILE, Checksum.SMILE_MI);
    }

    public static CvTerm createStandardInchi(){
        return createMICvTerm(Checksum.INCHI, Checksum.INCHI_MI);
    }

    public static CvTerm createStandardInchiKey(){
        return createMICvTerm(Checksum.STANDARD_INCHI_KEY, Checksum.STANDARD_INCHI_KEY_MI);
    }

    public static CvTerm createRogid(){
        return createMICvTerm(Checksum.ROGID, null);
    }

    public static CvTerm createRigid(){
        return createMICvTerm(Checksum.RIGID, null);
    }

    public static CvTerm createCertainStatus(){
        return createMICvTerm(Position.CERTAIN, Position.CERTAIN_MI);
    }

    public static CvTerm createPutativeSelf(){
        return createMICvTerm(Participant.PUTATIVE_SELF_ROLE, Participant.PUTATIVE_SELF_ROLE_MI);
    }

    public static CvTerm createSelf(){
        return createMICvTerm(Participant.SELF_ROLE, Participant.SELF_ROLE_MI);
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

    public static CvTerm createPolymerInteractorType(){
        return createMICvTerm(Polymer.POLYMER, Polymer.POLYMER_MI);
    }

    public static CvTerm createProteinInteractorType(){
        return createMICvTerm(Protein.PROTEIN, Protein.PROTEIN_MI);
    }

    public static CvTerm createNucleicAcidInteractorType(){
        return createMICvTerm(NucleicAcid.NULCEIC_ACID, NucleicAcid.NULCEIC_ACID_MI);
    }

    public static CvTerm createBioactiveEntityType(){
        return createMICvTerm(BioactiveEntity.BIOACTIVE_ENTITY, BioactiveEntity.BIOACTIVE_ENTITY_MI);
    }

    public static CvTerm createMoleculeSetType(){
        return createMICvTerm(EntitySet.ENTITY_SET, EntitySet.ENTITY_SET_MI);
    }

    public static CvTerm createGeneNameAliasType(){
        return createMICvTerm(Alias.GENE_NAME, Alias.GENE_NAME_MI);
    }

    public static CvTerm createComplexInteractorType(){
        return createMICvTerm(Complex.COMPLEX, Complex.COMPLEX_MI);
    }

    public static CvTerm createComplexSynonym(){
        return createMICvTerm(Alias.COMPLEX_SYNONYM, Alias.COMPLEX_SYNONYM_MI);
    }

    public static CvTerm createAuthorAssignedName(){
        return createMICvTerm(Alias.AUTHOR_ASSIGNED_NAME, Alias.AUTHOR_ASSIGNED_NAME_MI);
    }

    public static CvTerm createGeneNameSynonym(){
        return createMICvTerm(Alias.GENE_NAME_SYNONYM, Alias.GENE_NAME_SYNONYM_MI);
    }

    public static CvTerm createIsoformSynonym(){
        return createMICvTerm(Alias.ISOFORM_SYNONYM, Alias.ISOFORM_SYNONYM_MI);
    }

    public static CvTerm createOrfName(){
        return createMICvTerm(Alias.ORF_NAME, Alias.ORF_NAME_MI);
    }

    public static CvTerm createLocusName(){
        return createMICvTerm(Alias.LOCUS_NAME, Alias.LOCUS_NAME_MI);
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
        return createMICvTerm(CooperativeEffect.ALLOSTERY, CooperativeEffect.ALLOSTERY_ID);
    }

    public static CvTerm createIdentityXrefQualifier(){
        return createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI);
    }

    public static CvTerm createSecondaryXrefQualifier(){
        return createMICvTerm(Xref.SECONDARY, Xref.SECONDARY_MI);
    }

    public static CvTerm createBiologicalFeatureType(){
        return createMICvTerm(Feature.BIOLOGICAL_FEATURE, Feature.BIOLOGICAL_FEATURE_MI);
    }

    public static CvTerm createUnspecifiedMethod(){
        return createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI);
    }

    public static CvTerm createUnknownInteractorType(){
        return createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI);
    }

    /**
     * Check if the CvTerm natches the MI term and/or the name if no MI term exist
     * @param term : the term to check
     * @param mi : the mi id to compare
     * @param name : the name to compare
     * @return true if the CvTerm natches the MI term and/or the name
     */
    public static boolean isCvTerm(CvTerm term, String mi, String name){

        if (term.getMIIdentifier() != null && mi != null){
            return term.getMIIdentifier().equals(mi);
        }
        else{
            if (term.getShortName().equalsIgnoreCase(name) || (term.getFullName() != null && term.getFullName().equalsIgnoreCase(name))){
                return true;
            }
            else {
                return false;
            }
        }
    }
}
