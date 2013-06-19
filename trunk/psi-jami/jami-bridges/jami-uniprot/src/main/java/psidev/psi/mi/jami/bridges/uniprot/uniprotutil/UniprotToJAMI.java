package psidev.psi.mi.jami.bridges.uniprot.uniprotutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.kraken.interfaces.uniprot.*;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsIsoform;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSynonym;
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
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 */
public class UniprotToJAMI {

    private final static Logger log = LoggerFactory.getLogger(UniprotToJAMI.class.getName());

    public Protein getProteinFromEntry(UniProtEntry e) throws BadResultException {

        if(e == null) throw new BadResultException("The Uniprot entry was null");

        Protein p;
        String shortName = null;
        String fullName = null;

        //THIS ID HAS BEEN TAKEN FROM THE 'ID' name
        List<Field> fields =  e.getProteinDescription().getRecommendedName().getFields();
        for(Field f: fields){
            if(f.getType() == FieldType.SHORT){
                if(shortName == null){
                    shortName = f.getValue();
                }
                else{log.debug("Uniprot entry has multiple rec. shortName: "+shortName+", "+f.getValue());}
            }
            else if(f.getType() == FieldType.FULL){
                if(fullName == null){
                    fullName = f.getValue();
                }
                else{log.debug("Uniprot entry has multiple rec. fullName: "+fullName+", "+f.getValue());}
            }
        }

        //SHORT NAME - ShortName/FullName/UniprotID/UniprotAC
        if(shortName != null){
            p = new DefaultProtein(shortName);
        }else if(fullName != null){
            p = new DefaultProtein(fullName);
        }else if(e.getUniProtId() != null){
            p = new DefaultProtein(e.getUniProtId().getValue());
        }else if(e.getPrimaryUniProtAccession() != null){
            p = new DefaultProtein(e.getPrimaryUniProtAccession().getValue());
        } else {
            throw new BadResultException(
                    "The Uniprot entry has no names, accessions, or identifiers.");
        }

        //FULLNAME
        if(fullName != null){
            p.setFullName(fullName);
        }

        //PRIMARY ACCESSION
        if(e.getPrimaryUniProtAccession() != null){
            p.setUniprotkb(e.getPrimaryUniProtAccession().getValue());
        } else {
            throw new BadResultException(
                    "The Uniprot entry ["+p.getShortName()+"] has no primary Accession.");
        }

        //UNIPROT ID AS SECONDARY AC
        if(e.getUniProtId() != null){
            p.getIdentifiers().add(
                    XrefUtils.createUniprotSecondary(e.getUniProtId().getValue()));
        }
        //SECONDARY ACs
        if(e.getSecondaryUniProtAccessions() != null
                && e.getSecondaryUniProtAccessions().size() > 0) {
            for(SecondaryUniProtAccession ac : e.getSecondaryUniProtAccessions()){
                if(ac.getValue() != null){
                    p.getIdentifiers().add(
                            XrefUtils.createUniprotSecondary(ac.getValue()));
                }
            }
        }

        //TODO review the aliases
        //Aliases
        if(e.getGenes() != null && e.getGenes().size() > 0){
            for(Gene g : e.getGenes()){
                //Gene Name
                if(g.hasGeneName()){
                    p.getAliases().add(
                            AliasUtils.createGeneName(g.getGeneName().getValue()));
                }
                //Gene Name Synonym
                if(g.getGeneNameSynonyms() != null
                        && g.getGeneNameSynonyms().size() > 0){
                    for(GeneNameSynonym gns : g.getGeneNameSynonyms()){
                        p.getAliases().add(
                                AliasUtils.createGeneNameSynonym(gns.getValue()));
                    }
                }
                //ORF names
                if(g.getORFNames() != null
                        && g.getORFNames().size() > 0){
                    for(ORFName orf : g.getORFNames()){
                        p.getAliases().add(
                                AliasUtils.createOrfName(orf.getValue()));
                    }
                }
                //Locus Names
                //TODO check these are equivalent
                if(g.getOrderedLocusNames() != null
                        && g.getOrderedLocusNames().size() > 0){
                    for(OrderedLocusName oln : g.getOrderedLocusNames()){
                        p.getAliases().add(
                                AliasUtils.createLocusName(oln.getValue()));
                    }
                }
            }
        }

        //Database Xrefs
        for(DatabaseCrossReference dbxref : e.getDatabaseCrossReferences()){
            Xref dbxrefStandardised = getDatabaseXref(dbxref);
            if(dbxrefStandardised != null){
                p.getXrefs().add(dbxrefStandardised);
            }
        }


        //SEQUENCE // CHECKSUMS
        p.setSequence(e.getSequence().getValue());

        //CHECKSUMS
        //todo add MI term for crc64 checksums
        p.getChecksums().add(
                ChecksumUtils.createChecksum("CRC64", null, e.getSequence().getCRC64()));

        //Rogid is not stored in the entry

        p.setOrganism(getOrganismFromEntry(e));

        return p;
    }



    /**
     *
     *
     *
     * The mapping of fields for isoforms is as follows:
     * SHORT NAME - isoform identifier
     * ORGANISM - masterProtein organism
     * SEQUENCE - isoform sequence
     * ALIASES - gene name; gene name synonym; orf; locus name
     * ALIASES - isoform synonyms
     * XREF - ONLY: MasterProtein (db = uniprotkb, id = uniprotMaster, qualifier = isoform-parent (MI:0243))
     *
     * Other notes:
     * CHECKSUMS - rogid will be generated at enrichment, CRC64 will be ignored
     *
     * **FULL NAME - protein description, primary id, secondary ids
     *
     * @param entry
     * @param isoform
     * @param identifier
     * @return
     * @throws BadResultException
     */
    public static Protein getProteinIsoformFromEntry(
            UniProtEntry entry, AlternativeProductsIsoform isoform, String identifier)
            throws BadResultException {

        if(entry == null) throw new BadResultException("Uniprot entry was null.");
        if(isoform == null) throw new BadResultException("Isoform entry was null.");

        // SHORT NAME - identifier
        Protein p = new DefaultProtein(identifier);

        //todo
        // FULL NAME - protein description
        if(entry.getProteinDescription().hasRecommendedName()) {
            for(Field f: entry.getProteinDescription().getRecommendedName().getFields()){
                if(f.getType() == FieldType.FULL){
                    p.setFullName(f.getValue()); //Use the full name if there is one
                    break;
                }else if(f.getType() == FieldType.SHORT){
                    p.setFullName(f.getValue()); //Use the short name if nothing else
                }
            }
        }

        // ORGANISM
        p.setOrganism(getOrganismFromEntry(entry));


        // SEQUENCE
        switch (isoform.getIsoformSequenceStatus()) {
            case NOT_DESCRIBED:
                log.error("According to uniprot the splice variant has no sequence (status = NOT_DESCRIBED)");
                break;
            case DESCRIBED:
                p.setSequence(entry.getSplicedSequence(isoform.getName().getValue()));
                break;
            case DISPLAYED:
                p.setSequence(entry.getSplicedSequence(isoform.getName().getValue()));
                break;
            case EXTERNAL:
                // As it currently stands, when an isoform is retrieved,
                // it will be retrieved with the entry which matches its identifier.
                // This means that it should never have an external sequence.
                // This would change if there was an attempt to withdraw all other isoforms from the entry
                // as well as the one which matches.
                log.warn("An isoform ["+isoform.getName().getValue()+"] has an unexpected external sequence.");
                //getExternalSequence(isoform);
                break;
        }

        //CHECKSUMS
        //CRC64 checksum will be recalculated
        //Rogid checksum will be recalculated

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

        // ALIASES - isoform synonyms
        for ( IsoformSynonym syn : isoform.getSynonyms() ) {
            p.getAliases().add( AliasUtils.createIsoformSynonym( syn.getValue() ));
        }

        // XREF - uniprotMaster
        // TODO confirm this is the correct xref
        // add a xref with db = uniprotkb, id = uniprotMaster and qualifier = isoform-parent (MI:0243)
        p.getXrefs().add(XrefUtils.createSecondaryXref("uniprotkb","MI:0243","uniprotMaster"));

        return p;
    }


    /**
     *
     * The mapping of fields for features is as follows:
     * SHORT NAME - pro_xx identifier
     * PRIMARY AC - uniprotIdMaster-chainId
     * ORGANISM - masterProtein organism
     * SEQUENCE - feature sequence
     * ALIASES - gene name; gene name synonym; orf; locus name
     * ALIASES - isoform synonyms
     * XREF - ONLY: MasterProtein (db = uniprotkb, id = uniprotMaster and qualifier = chain-parent (MI:0951))
     *
     * Other notes:
     * CHECKSUMS - rogid will be generated at enrichment, CRC64 will be ignored
     * fullname is feature chain description.
     *
     * @param entry
     * @param feature
     * @param identifier
     * @return
     * @throws BadResultException
     */
    public static Protein getProteinFeatureFromEntry(
            UniProtEntry entry, Feature feature, String identifier)
            throws BadResultException {


        if(entry == null) throw new BadResultException("Uniprot entry was null.");
        if(feature == null) throw new BadResultException("Feature entry was null.");

        // SHORT NAME - identifier
        Protein p = new DefaultProtein(identifier);

        // FULL NAME - feature description
        // SEQUENCE - feature sequence
        FeatureLocation location = null;
        if(feature.getType() == FeatureType.CHAIN) {
            ChainFeature chainFeature = (ChainFeature)feature;
            location = chainFeature.getFeatureLocation();
            //FULL NAME
            p.setFullName(chainFeature.getFeatureDescription().getValue());
        }else if(feature.getType() == FeatureType.PEPTIDE) {
            PeptideFeature peptideFeature = (PeptideFeature)feature;
            location = peptideFeature.getFeatureLocation();
            //FULL NAME
            p.setFullName(peptideFeature.getFeatureDescription().getValue());
        }else if(feature.getType() == FeatureType.PROPEP)  {
            ProPepFeature proPepFeature = (ProPepFeature)feature;
            location = proPepFeature.getFeatureLocation();
            //FULL NAME
            p.setFullName(proPepFeature.getFeatureDescription().getValue());
        }

        if (location != null){
            int begin = location.getStart()-1;
            int end = location.getEnd();

            if(location.getStart() == -1) begin = 0;
            if(location.getEnd() == -1) end = entry.getSequence().getValue().length() -1;

            if(begin > end) throw new BadResultException(
                    "Sequence has beginning ("+begin+") larger than end ("+end+").");

            if(end > entry.getSequence().getValue().length()) throw new BadResultException(
                    "Sequence has end ("+end+") larger than " +
                    "length ("+entry.getSequence().getValue().length()+").");

            if(begin < 0 || end < 0) throw new BadResultException(
                    "Sequence a beginning ("+begin+") or end ("+end+") lower than 0.");

            p.setSequence(entry.getSequence().subSequence(begin,end).getValue());
        }



        //todo
        // FULL NAME - protein description
        //entry.getProteinDescription().);

        //PRIMARY AC - uniprotIdMaster-chainId
        if(entry.getPrimaryUniProtAccession() != null){
            String primaryAc = entry.getPrimaryUniProtAccession().getValue()+"-"+identifier;
            p.setUniprotkb(primaryAc);
        }

        // ORGANISM
        p.setOrganism(getOrganismFromEntry(entry));

        //CHECKSUMS
        //CRC64 checksum will be recalculated
        //Rogid checksum will be recalculated

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
        // TODO confirm this is the correct xref
        //db = uniprotkb, id = uniprotMaster and qualifier = chain-parent (MI:0951))
        p.getXrefs().add(XrefUtils.createSecondaryXref("uniprotkb","MI:0951","uniprotMaster"));

        return p;
    }



    private Map<DatabaseType,CvTerm> uniprotDatabases = null;


    private void initiateDatabaseMap(){
        uniprotDatabases = new HashMap<DatabaseType, CvTerm>();
        uniprotDatabases.put( DatabaseType.GO,       new DefaultCvTerm("go" , "MI:0448"));
        uniprotDatabases.put( DatabaseType.INTERPRO, new DefaultCvTerm("interpro" , "MI:0449"));
        uniprotDatabases.put( DatabaseType.PDB,      new DefaultCvTerm("pdb" , "MI:0460"));
        uniprotDatabases.put( DatabaseType.REACTOME, new DefaultCvTerm("reactome" , "MI:0467"));
        uniprotDatabases.put( DatabaseType.ENSEMBL,  new DefaultCvTerm("ensembl" , "MI:0476"));
        uniprotDatabases.put( DatabaseType.WORMBASE, new DefaultCvTerm("wormbase" , "MI:0487" ));
        uniprotDatabases.put( DatabaseType.FLYBASE,  new DefaultCvTerm("flybase" , "MI:0478" ));
        uniprotDatabases.put( DatabaseType.REFSEQ,   new DefaultCvTerm("refseq" , "MI:0481" ));
        uniprotDatabases.put( DatabaseType.IPI,      new DefaultCvTerm("ipi" , "MI:0675" ));
    }

    public Map<DatabaseType,CvTerm> getUniprotDatabases(){
        if (uniprotDatabases == null) initiateDatabaseMap();
        return uniprotDatabases;
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
    protected Xref getDatabaseXref(DatabaseCrossReference dbxref){
        if(uniprotDatabases == null) initiateDatabaseMap();

        if (uniprotDatabases.containsKey(dbxref.getDatabase())){
            CvTerm database = uniprotDatabases.get(dbxref.getDatabase());
            String id = null;

            if(dbxref.getDatabase() == DatabaseType.GO){
                Go db = (Go)dbxref;
                if(db.hasGoId()) id = db.getGoId().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.INTERPRO){
                InterPro db = (InterPro)dbxref;
                if(db.hasInterProId()) id = db.getInterProId().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.PDB){
                Pdb db = (Pdb)dbxref;
                if(db.hasPdbAccessionNumber()) id = db.getPdbAccessionNumber().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.REACTOME){
                Reactome db = (Reactome)dbxref;
                if(db.hasReactomeAccessionNumber()) id = db.getReactomeAccessionNumber().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.ENSEMBL){
                //Todo check that the order of these is correct
                Ensembl db = (Ensembl)dbxref;
                if(db.hasEnsemblProteinIdentifier()) id = db.getEnsemblProteinIdentifier().getValue();
                else if(db.hasEnsemblTranscriptIdentifier()) id = db.getEnsemblTranscriptIdentifier().getValue();
                else if(db.hasEnsemblGeneIdentifier()) id = db.getEnsemblGeneIdentifier().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.WORMBASE){
                WormBase db = (WormBase)dbxref;
                if(db.hasWormBaseAccessionNumber()) id = db.getWormBaseAccessionNumber().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.FLYBASE){
                FlyBase db = (FlyBase)dbxref;
                if(db.hasFlyBaseAccessionNumber()) id = db.getFlyBaseAccessionNumber().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.REFSEQ){
                RefSeq db = (RefSeq)dbxref;
                if(db.hasRefSeqAccessionNumber()) id = db.getRefSeqAccessionNumber().getValue();
            }
            else if(dbxref.getDatabase() == DatabaseType.IPI){
                Ipi db = (Ipi)dbxref;
                if(db.hasIpiAcNumber()) id = db.getIpiAcNumber().getValue();
            }

            if(id != null) return new DefaultXref(database, id);
        }
        return null;
    }


    public static Organism getOrganismFromEntry(UniProtEntry e)
            throws BadResultException{

        Organism o = null;

        if(e.getNcbiTaxonomyIds() == null
                || e.getNcbiTaxonomyIds().isEmpty()){
            o = new DefaultOrganism(-3); //Unknown
        } else if(e.getNcbiTaxonomyIds().size() > 1){
            throw new BadResultException(
                    "Uniprot entry ["+e.getPrimaryUniProtAccession().getValue()+"] "
                            +"has multiple organisms.");
        } else {
            String id = e.getNcbiTaxonomyIds().get(0).getValue();
            try{
                o = new DefaultOrganism( Integer.parseInt( id ) );
                if(e.getOrganism().hasCommonName())
                    o.setCommonName(e.getOrganism().getCommonName().getValue());
                if(e.getOrganism().getScientificName() != null)
                    o.setScientificName(e.getOrganism().getScientificName().getValue());
                if(e.getOrganism().hasSynonym())    //TODO check the type on an organism alias
                    o.getAliases().add(AliasUtils.createAlias(null, e.getOrganism().getSynonym().getValue()));
            }catch(NumberFormatException n){
                throw new BadResultException("Uniprot entry ["+e.getPrimaryUniProtAccession().getValue()+"] " +
                        "has a TaxonomyID which could not be cast to an integer: ("+id+").",n);
            }
        }
        return o;
    }
}
