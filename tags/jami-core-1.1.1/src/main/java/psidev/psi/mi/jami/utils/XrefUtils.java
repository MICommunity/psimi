package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Utility class for Xrefs
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class XrefUtils {

    /**
     * Method to know if a Xref is a potential identifier.
     * It can be an identity xref or a secondary xref.
     * @param ref : the Xref
     * @return true if the Xref has a qualifier identity or secondary-ac
     */
    public static boolean isXrefAnIdentifier(Xref ref){
        if (ref == null || ref.getQualifier() == null){
            return false;
        }

        String qualifierMI = ref.getQualifier().getMIIdentifier();

        if (qualifierMI != null){
            return (Xref.IDENTITY_MI.equals(qualifierMI) || Xref.SECONDARY_MI.equals(qualifierMI));
        }
        else {
            String qualifier = ref.getQualifier().getShortName();

            return (Xref.IDENTITY.equalsIgnoreCase(qualifier) || Xref.SECONDARY.equalsIgnoreCase(qualifier));
        }
    }

    /**
     * Method to know if a Xref is from the same database (dbId is the MI identifier and dbName is the database shortname)
     * @param ref : the Xref
     * @param dbId : the database MI identifier
     * @param dbName : the database shortname
     * @return true if the Xref has a database MI which is dbId or database shortname which is dbName
     */
    public static boolean isXrefFromDatabase(Xref ref, String dbId, String dbName){

        if (ref == null || (dbName == null && dbId == null)){
            return false;
        }

        CvTerm database = ref.getDatabase();
        // we can compare identifiers
        if (dbId != null && database.getMIIdentifier() != null){
            // we have the same database id
            return database.getMIIdentifier().equals(dbId);
        }
        // we need to compare dbNames
        else if (dbName != null) {
            return dbName.equalsIgnoreCase(database.getShortName());
        }

        return false;
    }

    /**
     * Method to know if a Xref has the same qualifier (qualifierId is the MI identifier and qualifierName is the qualifier shortname)
     * @param ref : the Xref
     * @param qualifierId : the qualifier MI identifier
     * @param qualifierName : the qualifier shortname
     * @return true if the Xref has a qualifier MI which is qualifierId or qualifier shortname which is qualifierName
     */
    public static boolean doesXrefHaveQualifier(Xref ref, String qualifierId, String qualifierName){

        if (ref == null || (qualifierName == null && qualifierId == null)){
            return false;
        }

        CvTerm qualifier = ref.getQualifier();
        if (qualifier == null){
            return false;
        }
        // we can compare identifiers
        if (qualifierId != null && qualifier.getMIIdentifier() != null){
            // we have the same database id
            return qualifier.getMIIdentifier().equals(qualifierId);
        }
        // we need to compare dbNames
        else if (qualifierName != null) {
            return qualifierName.equalsIgnoreCase(qualifier.getShortName());
        }

        return false;
    }

    /**
     * Method to know if a Xref is from the same database (dbId is the MI identifier and dbName is the database shortname)
     * @param ref : the Xref
     * @param dbId : the database MI identifier
     * @param dbName : the database shortname
     * @param id: database id
     * @return true if the Xref has a database MI which is dbId or database shortname which is dbName
     */
    public static boolean doesXrefHaveDatabaseAndId(Xref ref, String dbId, String dbName, String id){

        if (ref == null || (dbName == null && dbId == null)){
            return false;
        }

        CvTerm database = ref.getDatabase();
        // we can compare identifiers
        if (dbId != null && database.getMIIdentifier() != null){
            // we have the same database id
            if (database.getMIIdentifier().equals(dbId)){
                return ref.getId().equals(id);
            }
            else{
                return false;
            }
        }
        // we need to compare dbNames
        else if (dbName != null) {
            if (dbName.equalsIgnoreCase(database.getShortName())){
                return ref.getId().equals(id);
            }
            else{
                return false;
            }
        }

        return false;
    }

    /**
     * Method to know if a Xref has the same qualifier (qualifierId is the MI identifier and qualifierName is the qualifier shortname)
     * @param ref : the Xref
     * @param qualifierId : the qualifier MI identifier
     * @param qualifierName : the qualifier shortname
     * @param id: database id
     * @return true if the Xref has a qualifier MI which is qualifierId or qualifier shortname which is qualifierName
     */
    public static boolean doesXrefHaveQualifierAndId(Xref ref, String qualifierId, String qualifierName, String id){

        if (ref == null || (qualifierName == null && qualifierId == null)){
            return false;
        }

        CvTerm qualifier = ref.getQualifier();
        if (qualifier == null){
            return false;
        }
        // we can compare identifiers
        if (qualifierId != null && qualifier.getMIIdentifier() != null){
            // we have the same database id
            if (qualifier.getMIIdentifier().equals(qualifierId)){
                return ref.getId().equals(id);
            }
            else{
                return false;
            }
        }
        // we need to compare dbNames
        else if (qualifierName != null) {
            if (qualifierName.equalsIgnoreCase(qualifier.getShortName())){
                return ref.getId().equals(id);
            }
            else{
                return false;
            }
        }

        return false;
    }

    /**
     * Method to return a sub collection of identifiers (qualifier identity or secondary) in a list of Xrefs
     * @param refs
     * @return the sublist of identifiers (qualifier identity or secondary) from the list of Xrefs
     */
    public static Collection<Xref> collectAllIdentifiersFrom(Collection<? extends Xref> refs){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (isXrefAnIdentifier(ref)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific qualifier
     * @param refs
     * @param qualifierId
     * @param qualifierName
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingQualifier(Collection<? extends Xref> refs, String qualifierId, String qualifierName){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (doesXrefHaveQualifier(ref, qualifierId, qualifierName)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific database
     * @param refs
     * @param dbId
     * @param dbName
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingDatabase(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (isXrefFromDatabase(ref, dbId, dbName)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific database and qualifier
     * @param refs
     * @param dbId
     * @param dbName
     * @param qualifierId
     * @param qualifierName
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingDatabaseAndQualifier(Collection<? extends Xref> refs, String dbId, String dbName, String qualifierId, String qualifierName){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (isXrefFromDatabase(ref, dbId, dbName) && doesXrefHaveQualifier(ref, qualifierId, qualifierName)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific qualifier
     * @param refs
     * @param qualifierId
     * @param qualifierName
     * @param id
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingQualifierAndId(Collection<? extends Xref> refs, String qualifierId, String qualifierName, String id){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (doesXrefHaveQualifierAndId(ref, qualifierId, qualifierName, id)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific database
     * @param refs
     * @param dbId
     * @param dbName
     * @param id
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingDatabaseAndId(Collection<? extends Xref> refs, String dbId, String dbName, String id){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (doesXrefHaveDatabaseAndId(ref, dbId, dbName, id)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific database and qualifier
     * @param refs
     * @param dbId
     * @param dbName
     * @param qualifierId
     * @param qualifierName
     * @param id
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingDatabaseQualifierAndId(Collection<? extends Xref> refs, String dbId, String dbName, String id, String qualifierId, String qualifierName){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (isXrefFromDatabase(ref, dbId, dbName) && doesXrefHaveQualifierAndId(ref, qualifierId, qualifierName, id)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having specific databases and qualifiers
     * @param xrefs
     * @param dbMiRefs
     * @param dbRefs
     * @param typeMiRefs
     * @param typeRefs
     * @return
     */
    public static Collection<Xref> searchAllXrefsHavingDatabaseAndQualifier( Collection<Xref> xrefs,
                                                                             Collection<String> dbMiRefs,
                                                                             Collection<String> dbRefs,
                                                                             Collection<String> typeMiRefs,
                                                                             Collection<String> typeRefs) {

        if (xrefs == null || xrefs.isEmpty() || (typeMiRefs.isEmpty() && typeRefs.isEmpty()) || (dbMiRefs.isEmpty() && dbRefs.isEmpty())){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> refs = new ArrayList<Xref>(xrefs.size());

        for ( Xref ref : xrefs ) {
            if (ref.getDatabase().getMIIdentifier() != null && !dbMiRefs.isEmpty()) {
                if (!dbMiRefs.contains( ref.getDatabase().getMIIdentifier() )){
                    continue;
                }
            }
            else if (!dbRefs.contains( ref.getDatabase().getShortName() )){
                continue;
            }

            if ( ref.getQualifier() == null ) {
                continue;
            }
            else if (ref.getQualifier().getMIIdentifier() != null && !typeMiRefs.isEmpty()){
                if ( !typeMiRefs.contains( ref.getQualifier().getMIIdentifier() )){
                    continue;
                }
            }
            else if (!typeRefs.contains( ref.getQualifier().getShortName() )){
                continue;
            }
            refs.add( ref );
        }

        return refs;
    }

    /**
     * Collect all Xrefs having specific databases
     * @param xrefs
     * @param dbMiRefs
     * @param dbRefs
     * @return
     */
    public static Collection<Xref> searchAllXrefsHavingDatabases(Collection<Xref> xrefs, Collection<String> dbMiRefs, Collection<String> dbRefs) {

        if (xrefs == null || xrefs.isEmpty() || (dbMiRefs.isEmpty() && dbRefs.isEmpty())){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> refs = new ArrayList<Xref>(xrefs.size());

        for ( Xref ref : xrefs ) {
            if (ref.getDatabase().getMIIdentifier() != null && !dbMiRefs.isEmpty()) {
                if (!dbMiRefs.contains( ref.getDatabase().getMIIdentifier() )){
                    continue;
                }
            }
            else if (!dbRefs.contains( ref.getDatabase().getShortName() )){
                continue;
            }
            refs.add( ref );
        }

        return refs;
    }

    /**
     * This method will return the first Xref from this database having :
     * - identity qualifier
     * - secondary identifier if no identity qualifier
     * - first Xref from this database if no identity or secondary qualifier
     * It will return null if there are no Xrefs with this database id/name
     * @param refs : the collection of Xrefs
     * @param dbId : the database id to look for
     * @param dbName : the database name to look for
     * @return the first identifier having this database name/id, null if no Xrefs with this database name/id
     */
    public static Xref collectFirstIdentifierWithDatabase(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs == null || (dbName == null && dbId == null)){
            return null;
        }

        for (Xref xref : refs){
            if (isXrefAnIdentifier(xref) && isXrefFromDatabase(xref, dbId, dbName)){
                return xref;
            }
        }

        return null;
    }

    /**
     * Remove all Xrefs having this database name/database id from the collection of xrefs
     * @param refs : the collection of Xrefs
     * @param dbId : the database id to look for
     * @param dbName : the database name to look for
     */
    public static void removeAllXrefsWithDatabase(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs != null){
            Iterator<? extends Xref> refIterator = refs.iterator();

            while (refIterator.hasNext()){
                if (isXrefFromDatabase(refIterator.next(), dbId, dbName)){
                    refIterator.remove();
                }
            }
        }
    }

    /**
     * This method will return the first Xref from this database having :
     * - identity qualifier
     * - secondary identifier if no identity qualifier
     * - first Xref from this database if no identity or secondary qualifier
     * It will return null if there are no Xrefs with this database id/name
     * @param refs : the collection of Xrefs
     * @param dbId : the database id to look for
     * @param dbName : the database name to look for
     * @param id
     * @return the first identifier having this database name/id, null if no Xrefs with this database name/id
     */
    public static Xref collectFirstIdentifierWithDatabaseAndId(Collection<? extends Xref> refs, String dbId, String dbName, String id){

        if (refs == null || (dbName == null && dbId == null) || id == null){
            return null;
        }

        for (Xref xref : refs){
            if (isXrefAnIdentifier(xref) && doesXrefHaveDatabaseAndId(xref, dbId, dbName, id)){
                return xref;
            }
        }

        return null;
    }

    /**
     * Remove all Xrefs having this database name/database id from the collection of xrefs
     * @param refs : the collection of Xrefs
     * @param dbId : the database id to look for
     * @param dbName : the database name to look for
     * @param id
     */
    public static void removeAllXrefsWithDatabaseAndId(Collection<? extends Xref> refs, String dbId, String dbName, String id){

        if (refs != null){
            Iterator<? extends Xref> refIterator = refs.iterator();

            while (refIterator.hasNext()){
                if (doesXrefHaveDatabaseAndId(refIterator.next(), dbId, dbName, id)){
                    refIterator.remove();
                }
            }
        }
    }

    public static Xref createXref(String databaseName, String databaseMi, String id){
        return new DefaultXref(CvTermUtils.createMICvTerm(databaseName, databaseMi), id);
    }

    public static Xref createXref(String databaseName, String id){
        return new DefaultXref(CvTermUtils.createMICvTerm(databaseName, null), id);
    }

    public static Xref createXrefWithQualifier(String databaseName, String databaseMi, String id, String qualifierName, String qualifierMi){
        return new DefaultXref(CvTermUtils.createMICvTerm(databaseName, databaseMi), id, CvTermUtils.createMICvTerm(qualifierName, qualifierMi));
    }

    public static Xref createXrefWithQualifier(String databaseName, String id, String qualifierName){
        return new DefaultXref(CvTermUtils.createMICvTerm(databaseName, null), id, CvTermUtils.createMICvTerm(qualifierName, null));
    }

    public static Xref createIdentityXref(String databaseName, String databaseMi, String id){
        return createXrefWithQualifier(databaseName, databaseMi, id, Xref.IDENTITY, Xref.IDENTITY_MI);
    }

    public static Xref createIdentityXref(String databaseName, String id){
        return createXrefWithQualifier(databaseName, null, id, Xref.IDENTITY, Xref.IDENTITY_MI);
    }

    public static Xref createSecondaryXref(String databaseName, String databaseMi, String id){
        return createXrefWithQualifier(databaseName, databaseMi, id, Xref.SECONDARY, Xref.SECONDARY_MI);
    }

    public static Xref createSecondaryXref(String databaseName, String id){
        return createXrefWithQualifier(databaseName, null, id, Xref.SECONDARY, Xref.SECONDARY_MI);
    }

    public static Xref createPrimaryXref(String databaseName, String databaseMi, String id){
        return createXrefWithQualifier(databaseName, databaseMi, id, Xref.PRIMARY, Xref.PRIMARY_MI);
    }

    public static Xref createPrimaryXref(String databaseName, String id){
        return createXrefWithQualifier(databaseName, null, id, Xref.PRIMARY, Xref.PRIMARY_MI);
    }

    public static Xref createUniprotIdentity(String uniprot){
        return createIdentityXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, uniprot);
    }

    public static Xref createRefseqIdentity(String refseq){
        return createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, refseq);
    }

    public static Xref createEnsemblIdentity(String ensembl){
        return createIdentityXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, ensembl);
    }

    public static Xref createEnsemblGenomesIdentity(String ensembl){
        return createIdentityXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, ensembl);
    }

    public static Xref createEntrezGeneIdIdentity(String geneId){
        return createIdentityXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, geneId);
    }

    public static Xref createDdbjEmblGenbankIdentity(String id){
        return createIdentityXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, id);
    }

    public static Xref createChebiIdentity(String id){
        return createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, id);
    }

    public static Xref createPubmedIdentity(String id){
        return createIdentityXref(Xref.PUBMED, Xref.PUBMED_MI, id);
    }

    public static Xref createDoiIdentity(String id){
        return createIdentityXref(Xref.DOI, Xref.DOI_MI, id);
    }

    public static Xref createPsiMiIdentity(String id){
        return createIdentityXref(CvTerm.PSI_MI, CvTerm.PSI_MI_MI, id);
    }

    public static Xref createPsiModIdentity(String id){
        return createSecondaryXref(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI, id);
    }

    public static Xref createUniprotSecondary(String uniprot){
        return createSecondaryXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, uniprot);
    }

    public static Xref createRefseqSecondary(String refseq){
        return createSecondaryXref(Xref.REFSEQ, Xref.REFSEQ_MI, refseq);
    }

    public static Xref createEnsemblSecondary(String ensembl){
        return createSecondaryXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, ensembl);
    }

    public static Xref createEnsemblGenomesSecondary(String ensembl){
        return createSecondaryXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, ensembl);
    }

    public static Xref createEntrezGeneIdSecondary(String geneId){
        return createSecondaryXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, geneId);
    }

    public static Xref createDdbjEmblGenbankSecondary(String id){
        return createSecondaryXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, id);
    }

    public static Xref createChebiSecondary(String id){
        return createSecondaryXref(Xref.CHEBI, Xref.CHEBI_MI, id);
    }

    public static Xref createPsiMiSecondary(String id){
        return createSecondaryXref(CvTerm.PSI_MI, CvTerm.PSI_MI_MI, id);
    }

    public static Xref createPsiModSecondary(String id){
        return createSecondaryXref(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI, id);
    }
}
