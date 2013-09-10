package psidev.psi.mi.jami.bridges.uniprot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.uniprot.util.UniprotUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.intact.commons.util.Crc64;
import uk.ac.ebi.intact.irefindex.seguid.RogidGenerator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;
import uk.ac.ebi.kraken.interfaces.uniprot.*;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.*;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensembl.Ensembl;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.flybase.FlyBase;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.go.Go;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.interpro.InterPro;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ipi.Ipi;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.pdb.Pdb;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.reactome.Reactome;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.refseq.RefSeq;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.wormbase.WormBase;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Field;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FieldType;
import uk.ac.ebi.kraken.interfaces.uniprot.features.*;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.GeneNameSynonym;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.ORFName;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.OrderedLocusName;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  14/05/13
 */
public class UniprotProteinFetcher
        implements ProteinFetcher {

    private final Logger log = LoggerFactory.getLogger(UniprotProteinFetcher.class.getName());

    private UniProtQueryService uniProtQueryService;

    private Map<DatabaseType,CvTerm> selectedDatabases = null;
    private RogidGenerator rogidGenerator;

    public UniprotProteinFetcher() {
        uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();
        rogidGenerator = new RogidGenerator();
    }

    private void initiateDatabaseMap(){
        selectedDatabases = new HashMap<DatabaseType, CvTerm>();
        selectedDatabases.put(DatabaseType.GO, new DefaultCvTerm(Xref.GO, Xref.GO_MI));
        selectedDatabases.put(DatabaseType.INTERPRO, new DefaultCvTerm(Xref.INTERPRO, Xref.INTERPRO_MI));
        selectedDatabases.put(DatabaseType.PDB, new DefaultCvTerm("pdb", "MI:0460"));
        selectedDatabases.put(DatabaseType.REACTOME, new DefaultCvTerm("reactome", "MI:0467"));
        selectedDatabases.put(DatabaseType.ENSEMBL, new DefaultCvTerm(Xref.ENSEMBL, Xref.ENSEMBL_MI));
        selectedDatabases.put(DatabaseType.WORMBASE, new DefaultCvTerm("wormbase", "MI:0487"));
        selectedDatabases.put(DatabaseType.FLYBASE, new DefaultCvTerm("flybase", "MI:0478"));
        selectedDatabases.put(DatabaseType.REFSEQ, new DefaultCvTerm(Xref.REFSEQ, Xref.REFSEQ_MI));
        selectedDatabases.put(DatabaseType.IPI, new DefaultCvTerm("ipi", "MI:0675"));
    }

    public Map<DatabaseType, CvTerm> getSelectedDatabases() {
        return selectedDatabases;
    }

    public void setSelectedDatabases(Map<DatabaseType, CvTerm> selectedDatabases) {
        this.selectedDatabases = selectedDatabases;
    }

    /**
     * Takes the various type of uniprot protein identifier and uses the uniprotJAPI to retrieve the matching proteins.
     * @param identifier    A Uniprot protein identifier, a Uniprot protein isoform identifier or a PRO identifier.
     * @return              The proteins which match the given identifier.
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchProteinsByIdentifier(String identifier)
            throws BridgeFailedException {

        if(identifier == null) throw new IllegalArgumentException("Could not perform search on null identifier.");

        if(UniprotUtils.UNIPROT_PRO_REGEX.matcher(identifier).find()){
            // Truncate the pro identifier to allow the search to take place
            String proIdentifier = identifier.substring(identifier.indexOf("PRO")).replace("-","_");
            return fetchFeaturesByIdentifier(proIdentifier);
        } else if (UniprotUtils.UNIPROT_ISOFORM_REGEX.matcher(identifier).find()){
            return fetchIsoformsByIdentifier(identifier);
        } else {
            return fetchMasterProteinsByIdentifier(identifier);
        }
    }

    /**
     * Takes the various type of uniprot protein identifier
     * and uses the uniprotJAPI to retrieve the matching proteins.
     * @param identifiers   The identifiers to search for.
     * @return              The proteins which match an identifier in the query.
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchProteinsByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        if(identifiers == null) throw new IllegalArgumentException("Could not perform search on null collection of identifiers.");
        if(identifiers.isEmpty()){
           return Collections.EMPTY_LIST;
        }

        List<String> masterIdentifiers = new ArrayList<String>(identifiers.size());
        List<String> featureIdentifiers = new ArrayList<String>(identifiers.size());
        List<String> isoformIdentifiers = new ArrayList<String>(identifiers.size());

        for(String identifier : identifiers){
            if(UniprotUtils.UNIPROT_PRO_REGEX.matcher(identifier).find()){
                // Truncate the pro identifier to allow the search to take place
                String proIdentifier = identifier.substring(identifier.indexOf("PRO")).replace("-","_");
                featureIdentifiers.add(proIdentifier);
            } else if (UniprotUtils.UNIPROT_ISOFORM_REGEX.matcher(identifier).find()){
                isoformIdentifiers.add(identifier);
            } else {
                masterIdentifiers.add(identifier);
            }
        }

        Collection<Protein> proteinResults = new ArrayList<Protein>(identifiers.size());

        // == Masters ===========
        proteinResults.addAll(fetchMasterProteinsByIdentifiers(masterIdentifiers));

        // == Isoforms ==========
        proteinResults.addAll(fetchIsoformsByIdentifiers(isoformIdentifiers));

        // == Features ===========
        for (String featureId : featureIdentifiers){
            proteinResults.addAll(fetchFeaturesByIdentifier(featureId));
        }
        return proteinResults;
    }

    /**
     *
     * @param identifier    The identifier for a master protein
     * @return              The master proteins which match the identifier.
     * @throws BridgeFailedException
     */
    private Collection<Protein> fetchMasterProteinsByIdentifier(String identifier) throws BridgeFailedException {
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                proteins.add(createMasterProteinFromEntry(entries.next()));
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }

        // Examples:
        // - one single entry : P12345
        // - uniprot demerge (different uniprot entries with different organisms) : P77681
        // - uniprot demerge (different uniprot entries with same organisms) : P11163

        return proteins;
    }

    /**
     *
     * @param identifiers    The identifier for a master protein
     * @return              The master proteins which match the identifier.
     * @throws BridgeFailedException
     */
    private Collection<Protein> fetchMasterProteinsByIdentifiers(List<String> identifiers) throws BridgeFailedException {
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildIDListQuery(identifiers);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                proteins.add(createMasterProteinFromEntry(entries.next()));
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }

        // Examples:
        // - one single entry : P12345
        // - uniprot demerge (different uniprot entries with different organisms) : P77681
        // - uniprot demerge (different uniprot entries with same organisms) : P11163

        return proteins;
    }

    /**
     * Queries uniprot for the isoform identifier and returns the results in a list of Proteins
     *
     * @param identifier    the isoform identifier in the form of [MasterID]-[IsoformNumber]
     * @return      the collection of proteins which match the search term
     * @throws BridgeFailedException
     */
    private Collection<Protein> fetchIsoformsByIdentifier(String identifier) throws BridgeFailedException {
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry = entries.next();
                AlternativeProductsIsoform isoform = findIsoformInEntry(entry, identifier);
                if(isoform == null) log.warn("No isoform in entry "+entry.getUniProtId());
                else{
                    proteins.add(createIsoformFrom(entry, isoform));
                }
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem encountered whilst querying Uniprot service for isoforms.",e);
        }
        return proteins;
    }

    /**
     * Queries uniprot for the isoform identifier and returns the results in a list of Proteins
     *
     * @param identifiers    the isoform identifier in the form of [MasterID]-[IsoformNumber]
     * @return      the collection of proteins which match the search term
     * @throws BridgeFailedException
     */
    private Collection<Protein> fetchIsoformsByIdentifiers(List<String> identifiers) throws BridgeFailedException {
        Collection<Protein> proteins = new ArrayList<Protein>(identifiers.size());

        try{
            Query query = UniProtQueryBuilder.buildIDListQuery(identifiers);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry = entries.next();
                Collection<AlternativeProductsIsoform> matchingIsoform = findIsoformsInEntry(entry, identifiers);
                if(matchingIsoform.isEmpty()) log.warn("No isoform in entry "+entry.getUniProtId());
                else{
                    for (AlternativeProductsIsoform isoform : matchingIsoform){
                        proteins.add(createIsoformFrom(entry, isoform));
                    }
                }
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem encountered whilst querying Uniprot service for isoforms.",e);
        }
        return proteins;
    }

    /**
     * Queries uniprot for the feature identifier  and returns the results in a list of protein.
     *
     * The search term is a PRO identifier. These may be encountered in the form [MasterID]-PRO_[number]
     * or in the form PRO-[number]. Only the number should be supplied for the search.
     * The "Pro_" marker and all preceding identifiers must be removed.
     *
     * @param identifier    the identifier for the feature in the form of a 10 digit number.
     * @return      the collection of proteins found in the search
     * @throws BridgeFailedException
     */
    private Collection<Protein> fetchFeaturesByIdentifier(String identifier)
            throws  BridgeFailedException {
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildFullTextSearch(
                    UniprotUtils.FEATURE_CHAIN_FIELD + identifier + " OR " +
                            UniprotUtils.FEATURE_PEPTIDE_FIELD + identifier + " OR " +
                            UniprotUtils.FEATURE_PRO_PEPTIDE_FIELD + identifier );

            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry = entries.next();
                Feature feature = findFeatureInEntry(entry, identifier);
                if(feature == null) log.warn("No feature in entry "+entry.getUniProtId());
                else{
                    proteins.add(createProteinFeatureFrom(entry, feature));
                }
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }
        return proteins;
    }

    /**
     * SHORT NAME = recommended short name // full name // identifier
     * FULL NAME = recommended  full name
     * UNIPROT AC = primary UniprotAC
     * IDENTIFIERS + UniprotID
     * IDENTIFIERS + Secondary UniprotACs
     * ALIASES + genes, ORF, Locus
     * XREFS + database cross references
     * SEQUENCE = sequence
     * ORGANISM = organism
     * CHECKSUMS + generated ROGID, supplied CRC64
     * @param entity    A uniprot protein entity
     * @return          The protein object from the entity
     * @throws BridgeFailedException
     */
    private Protein createMasterProteinFromEntry(UniProtEntry entity) throws BridgeFailedException {

        Protein p;
        String shortName = null;
        String fullName = null;

        //THIS ID HAS BEEN TAKEN FROM THE 'ID' name
        List<Field> fields =  entity.getProteinDescription().getRecommendedName().getFields();
        for(Field f: fields){
            if(f.getType() == FieldType.SHORT){
                if(shortName == null){
                    shortName = f.getValue();
                }
            }
            else if(f.getType() == FieldType.FULL){
                if(fullName == null){
                    fullName = f.getValue();
                }
            }
        }

        //SHORT NAME - ShortName/FullName/UniprotID/UniprotAC
        if(shortName != null){
            p = new DefaultProtein(shortName);
        }else if(fullName != null){
            p = new DefaultProtein(fullName);
        }else if(entity.getUniProtId() != null){
            p = new DefaultProtein(entity.getUniProtId().getValue().toLowerCase());
        }else {
            p = new DefaultProtein(entity.getPrimaryUniProtAccession().getValue().toLowerCase());

        }

        //FULL NAME
        p.setFullName(fullName);

        //PRIMARY ACCESSION
        p.setUniprotkb(entity.getPrimaryUniProtAccession().getValue());

        //SECONDARY ACs
        if(entity.getSecondaryUniProtAccessions() != null
                && ! entity.getSecondaryUniProtAccessions().isEmpty()) {
            for(SecondaryUniProtAccession ac : entity.getSecondaryUniProtAccessions()){
                p.getIdentifiers().add(
                        XrefUtils.createUniprotSecondary(ac.getValue()));
            }
        }

        //Aliases
        if(entity.getGenes() != null && entity.getGenes().size() > 0){
            for(Gene g : entity.getGenes()){
                //Gene Name
                if(g.hasGeneName()){
                    p.getAliases().add(AliasUtils.createGeneName(g.getGeneName().getValue()));
                }
                //Gene Name Synonym
                if(g.getGeneNameSynonyms() != null
                        && !g.getGeneNameSynonyms().isEmpty()){
                    for(GeneNameSynonym gns : g.getGeneNameSynonyms()){
                        p.getAliases().add(AliasUtils.createGeneNameSynonym(gns.getValue()));
                    }
                }
                //ORF names
                if(g.getORFNames() != null
                        && !g.getORFNames().isEmpty()){
                    for(ORFName orf : g.getORFNames()){
                        p.getAliases().add(AliasUtils.createOrfName(orf.getValue()));
                    }
                }
                //Locus Names
                if(g.getOrderedLocusNames() != null
                        && !g.getOrderedLocusNames().isEmpty()){
                    for(OrderedLocusName oln : g.getOrderedLocusNames()){
                        p.getAliases().add(AliasUtils.createLocusName(oln.getValue()));
                    }
                }
            }
        }

        // Database Xrefs
        for(DatabaseCrossReference dbxref : entity.getDatabaseCrossReferences()){
            Xref dbxrefStandardised = createXrefFrom(dbxref);
            if(dbxrefStandardised != null){
                p.getXrefs().add(dbxrefStandardised);
            }
        }


        // SEQUENCE
        p.setSequence(entity.getSequence().getValue());

        // ORGANISM
        p.setOrganism(UniprotUtils.createOrganismFromEntry(entity));

        generateChecksums(p);

        return p;
    }

    /**
     * For each UniprotEntry DatabaseCrossReference,
     * find the matching CvTerm and return it in an Xref with the identifier.
     *
     * For each type of DatabaseCrossReference in Uniprot there is a different method of access.
     * Each of these which are considered relevant have been implemented here.
     *
     * @param dbxref
     * @return
     */
    private Xref createXrefFrom(DatabaseCrossReference dbxref){
        if(selectedDatabases == null) initiateDatabaseMap();

        if (selectedDatabases.containsKey(dbxref.getDatabase())){
            CvTerm database = selectedDatabases.get(dbxref.getDatabase());
            String id = null;

            switch(dbxref.getDatabase()){
                case GO :
                    Go goDB = (Go)dbxref;
                    if(goDB.hasGoId()) id = goDB.getGoId().getValue(); break;
                case INTERPRO :
                    InterPro interProDB = (InterPro)dbxref;
                    if(interProDB.hasInterProId()) id = interProDB.getInterProId().getValue();  break;
                case PDB :
                    Pdb pdbDB = (Pdb)dbxref;
                    if(pdbDB.hasPdbAccessionNumber()) id = pdbDB.getPdbAccessionNumber().getValue(); break;
                case REACTOME:
                    Reactome reactomeDB = (Reactome)dbxref;
                    if(reactomeDB.hasReactomeAccessionNumber()) id = reactomeDB.getReactomeAccessionNumber().getValue();
                    break;
                case ENSEMBL :
                    Ensembl ensemblDB = (Ensembl)dbxref;
                    if(ensemblDB.hasEnsemblProteinIdentifier()) id = ensemblDB.getEnsemblProteinIdentifier().getValue();
                    if(ensemblDB.hasEnsemblTranscriptIdentifier()) id = ensemblDB.getEnsemblTranscriptIdentifier().getValue();
                    if(ensemblDB.hasEnsemblGeneIdentifier()) id = ensemblDB.getEnsemblGeneIdentifier().getValue();
                    break;
                case WORMBASE :
                    WormBase wormBaseDB = (WormBase)dbxref;
                    if(wormBaseDB.hasWormBaseAccessionNumber()) id = wormBaseDB.getWormBaseAccessionNumber().getValue();
                    break;
                case FLYBASE :
                    FlyBase flyBaseDB = (FlyBase)dbxref;
                    if(flyBaseDB.hasFlyBaseAccessionNumber()) id = flyBaseDB.getFlyBaseAccessionNumber().getValue();
                    break;
                case REFSEQ :
                    RefSeq refSeqDB = (RefSeq)dbxref;
                    if(refSeqDB.hasRefSeqAccessionNumber()) id = refSeqDB.getRefSeqAccessionNumber().getValue();
                    break;
                case IPI :
                    Ipi ipiDB = (Ipi)dbxref;
                    if(ipiDB.hasIpiAcNumber()) id = ipiDB.getIpiAcNumber().getValue();
                    break;
            }

            if(id != null) return new DefaultXref(database, id);
        }
        return null;
    }

    private void generateChecksums(Protein p) throws BridgeFailedException {
        // CHECKSUMS
        if(p.getSequence() != null){
            //TODO add an MI term if one is created
            p.getChecksums().add(ChecksumUtils.createChecksum("crc64", Crc64.getCrc64(p.getSequence())));

            if(p.getOrganism() != null){
                try {
                    String rogidValue = rogidGenerator.calculateRogid(
                            p.getSequence(),Integer.toString(p.getOrganism().getTaxId()));
                    p.setRogid(rogidValue);

                } catch (SeguidException e) {
                    throw new BridgeFailedException(
                            "Error was encountered whilst generating RogID in protein fetcher.",e);
                }
            }
        }
    }

    /**
     *
     * Entry => comments => Isoforms => ids
     * There will be one ID matching the search identifier for each entry.
     *
     * @param entry
     * @param identifier
     */
    private AlternativeProductsIsoform findIsoformInEntry(UniProtEntry entry, String identifier){

        List<AlternativeProductsComment> comments = entry.getComments(CommentType.ALTERNATIVE_PRODUCTS );

        for ( AlternativeProductsComment comment : comments ) {
            List<AlternativeProductsIsoform> isoforms = comment.getIsoforms();
            for ( AlternativeProductsIsoform isoform : isoforms ){
                for( IsoformId id :  isoform.getIds()){
                    if(identifier.equals(id.getValue())) return isoform;
                }
            }
        }
        return null;
    }

    /**
     * The mapping of fields for isoforms is as follows:
     * SHORTNAME = isoform identifier
     * FULLNAME = protein description
     * UNIPROTAC = ?
     * IDENTIFIERS + ?
     * IDENTIFIERS + ?
     * ALIASES = genes, ORF, Locus
     * ALIASES = isoform synonyms
     * SEQUENCE = spliced sequence
     * ORGANISM = organism
     * CHECKSUMS + generated ROGID, supplied CRC64
     * XREFS - ONLY: MasterProtein (db = uniprotkb, id = uniprotMaster, qualifier = isoform-parent (MI:0243))
     *
     *primary id, secondary ids
     *
     * @param entry
     * @param isoform
     * @return
     */
    private Protein createIsoformFrom(UniProtEntry entry, AlternativeProductsIsoform isoform)
            throws BridgeFailedException {

        Protein p;
        String fullName = null;

        //THIS ID HAS BEEN TAKEN FROM THE 'ID' name
        List<Field> fields =  entry.getProteinDescription().getRecommendedName().getFields();
        for(Field f: fields){
            if(f.getType() == FieldType.FULL){
                if(fullName == null){
                    fullName = f.getValue();
                }
            }
        }
        if (fullName == null && isoform.hasName()){
            fullName = isoform.getName().getValue();
        }
        Iterator<IsoformId> acIterator = isoform.getIds().iterator();
        IsoformId firstAc = acIterator.next();
        //SHORT NAME - ShortName/FullName/UniprotID/UniprotAC
        p = new DefaultProtein(firstAc.getValue().toLowerCase());
        p.setFullName(fullName);
        //PRIMARY ACCESSION
        p.setUniprotkb(firstAc.getValue());

        // SECONDARY ACS
        while(acIterator.hasNext()){
            IsoformId id = acIterator.next();
            p.getIdentifiers().add(XrefUtils.createUniprotSecondary(id.getValue()));
        }

        // ORGANISM
        p.setOrganism(UniprotUtils.createOrganismFromEntry(entry));

        // SEQUENCE
        switch (isoform.getIsoformSequenceStatus()) {
            case NOT_DESCRIBED:
                log.error("Splice variant has no sequence (status = NOT_DESCRIBED)");
                break;
            case DESCRIBED:
                p.setSequence(entry.getSplicedSequence(isoform.getName().getValue())); break;
            case DISPLAYED:
                p.setSequence(entry.getSplicedSequence(isoform.getName().getValue())); break;
            case EXTERNAL:
                // When an isoform is retrieved, it will be retrieved with the master which matches its identifier.
                log.warn("Isoform ["+isoform.getName().getValue()+"] has an unexpected external sequence.");
                break;
        }

        //CHECKSUMS
        generateChecksums(p);

        // ALIASES - gene name, gene name synonyms, orf, locus
        if(entry.getGenes() != null && !entry.getGenes().isEmpty()){
            for(Gene g : entry.getGenes()){
                //Gene Name
                if(g.hasGeneName()) p.getAliases().add(AliasUtils.createGeneName(g.getGeneName().getValue()));
                //Gene Name Synonym
                if(g.getGeneNameSynonyms() != null && !g.getGeneNameSynonyms().isEmpty()){
                    for(GeneNameSynonym gns : g.getGeneNameSynonyms()){
                        p.getAliases().add( AliasUtils.createGeneNameSynonym(gns.getValue()));
                    }
                }
                //ORF names
                if(g.getORFNames() != null && !g.getORFNames().isEmpty()){
                    for(ORFName orf : g.getORFNames()){
                        p.getAliases().add( AliasUtils.createOrfName(orf.getValue()));
                    }
                }
                //Locus Names
                if(g.getOrderedLocusNames() != null && !g.getOrderedLocusNames().isEmpty()){
                    for(OrderedLocusName oln : g.getOrderedLocusNames()){
                        p.getAliases().add( AliasUtils.createLocusName(oln.getValue()));
                    }
                }
            }
        }

        // ALIASES - isoform synonyms
        for ( IsoformSynonym syn : isoform.getSynonyms() ) {
            p.getAliases().add( AliasUtils.createIsoformSynonym( syn.getValue() ));
        }

        // XREF - uniprotMaster
        p.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB , Xref.UNIPROTKB_MI,
                entry.getPrimaryUniProtAccession().getValue() , Xref.ISOFORM_PARENT, Xref.ISOFORM_PARENT_MI));

        return p;
    }

    /**
     * Searches a uniprot entry to find the feature with a matching identifier.
     * @param entry
     * @param identifier
     * @return
     */
    private Feature findFeatureInEntry(UniProtEntry entry, String identifier){
        Collection<ChainFeature> chainFeatures = entry.getFeatures( FeatureType.CHAIN );
        for(ChainFeature f : chainFeatures){
            if(f.getFeatureId().getValue().equals(identifier)) return f;
        }

        Collection<PeptideFeature> peptideFeatures = entry.getFeatures( FeatureType.PEPTIDE );
        for(PeptideFeature f : peptideFeatures){
            if(f.getFeatureId().getValue().equals(identifier))return f;
        }

        Collection<ProPepFeature> proPepFeatures = entry.getFeatures( FeatureType.PROPEP );
        for(ProPepFeature f : proPepFeatures){
            if(f.getFeatureId().getValue().equals(identifier)) return f;
        }
        return null;
    }

    /**
     *
     * The mapping of fields for features is as follows:
     * SHORTNAME = pro identifier number
     * FULLNAME = feature chain description.
     * UNIPROTAC = uniprotIdMaster-chainId
     * IDENTIFIERS + ?
     * IDENTIFIERS + ?
     * ALIASES = genes, ORF, Locus
     * ALIASES = isoform synonyms
     * SEQUENCE = feature sequence
     * ORGANISM = organism
     * CHECKSUMS = generated ROGID, supplied CRC64
     * XREF = ONLY: MasterProtein (db = uniprotkb, id = masterId and qualifier = chain-parent (MI:0951))
     *
     * @param entry
     * @param feature
     * @return
     */
    private Protein createProteinFeatureFrom(
            UniProtEntry entry, Feature feature) throws BridgeFailedException {

        // SHORT NAME - identifier
        Protein p;
        String id;
        switch (feature.getType()){
            case CHAIN:
                ChainFeature chainFeature = (ChainFeature)feature;
                id = chainFeature.getFeatureId().getValue();
                p = new DefaultProtein(id.toLowerCase());
                p.setFullName(chainFeature.getFeatureDescription().getValue());
                break;
            case PEPTIDE:
                PeptideFeature peptideFeature = (PeptideFeature)feature;
                id = peptideFeature.getFeatureId().getValue();
                p = new DefaultProtein(id.toLowerCase());
                p.setFullName(peptideFeature.getFeatureDescription().getValue());
                break;
            case PROPEP:
                ProPepFeature proPepFeature = (ProPepFeature)feature;
                id = proPepFeature.getFeatureId().getValue();
                p = new DefaultProtein(id.toLowerCase());
                p.setFullName(proPepFeature.getFeatureDescription().getValue());
                break;
            default:
                throw new IllegalArgumentException("Only chains, peptides and propeptides are properly converted");
        }

        // PRIMARY AC - uniprotIdMaster-chainId
        String primaryAc = entry.getPrimaryUniProtAccession().getValue()+"-"+id;
        p.setUniprotkb(primaryAc);

        // SEQUENCE
        FeatureLocation location = feature.getFeatureLocation();
        if (location != null){
            int begin = location.getStart()-1;
            int end = location.getEnd();

            if (begin >= 0 && end >=0){
                if(begin > end) throw new IllegalArgumentException(
                        "Sequence has a start ("+begin+") larger than end ("+end+").");

                if(end > entry.getSequence().getValue().length()) throw new IllegalArgumentException(
                        "Sequence has end ("+end+") larger than " +
                                "length ("+entry.getSequence().getValue().length()+").");

                p.setSequence(entry.getSequence().subSequence(begin,end).getValue());
            }
        }

        // ORGANISM
        p.setOrganism(UniprotUtils.createOrganismFromEntry(entry));

        //CHECKSUMS
        generateChecksums(p);

        // ALIASES - gene name, gene name synonyms, orf, locus
        if(entry.getGenes() != null && entry.getGenes().size() > 0){
            for(Gene g : entry.getGenes()){
                //Gene Name
                if(g.hasGeneName()) p.getAliases().add(AliasUtils.createGeneName(g.getGeneName().getValue()));
                //Gene Name Synonym
                if(g.getGeneNameSynonyms() != null && g.getGeneNameSynonyms().size() > 0){
                    for(GeneNameSynonym gns : g.getGeneNameSynonyms()){
                        p.getAliases().add( AliasUtils.createGeneNameSynonym(gns.getValue()));
                    }
                }
                //ORF names
                if(g.getORFNames() != null && g.getORFNames().size() > 0){
                    for(ORFName orf : g.getORFNames()){
                        p.getAliases().add( AliasUtils.createOrfName(orf.getValue()));
                    }
                }
                //Locus Names
                if(g.getOrderedLocusNames() != null && g.getOrderedLocusNames().size() > 0){
                    for(OrderedLocusName oln : g.getOrderedLocusNames()){
                        p.getAliases().add( AliasUtils.createLocusName(oln.getValue()));
                    }
                }
            }
        }

        // XREF - uniprotMaster
        p.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB , Xref.UNIPROTKB_MI,
                entry.getPrimaryUniProtAccession().getValue() , Xref.CHAIN_PARENT, Xref.CHAIN_PARENT_MI));

        return p;
    }

    private Collection<AlternativeProductsIsoform> findIsoformsInEntry(UniProtEntry entry, Collection<String> identifiers){

        List<AlternativeProductsComment> comments = entry.getComments(CommentType.ALTERNATIVE_PRODUCTS );
        Collection<AlternativeProductsIsoform> results = new ArrayList<AlternativeProductsIsoform>(identifiers.size());

        for ( AlternativeProductsComment comment : comments ) {
            List<AlternativeProductsIsoform> isoforms = comment.getIsoforms();
            for ( AlternativeProductsIsoform isoform : isoforms ){
                for( IsoformId id :  isoform.getIds()){
                    if(identifiers.contains(id.getValue())){
                        results.add(isoform);
                        // the isoform has been fetched. No need to fetch it again
                        identifiers.remove(id.getValue());
                    }
                }
            }
        }
        return null;
    }
}