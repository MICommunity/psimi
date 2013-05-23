package psidev.psi.mi.jami.bridges.uniprot.uniprotutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.EntryNotFoundException;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.factory.AliasFactory;
import psidev.psi.mi.jami.utils.factory.ChecksumFactory;
import psidev.psi.mi.jami.utils.factory.XrefFactory;
import uk.ac.ebi.kraken.interfaces.uniprot.*;
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
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:41
 */
public class UniprotToJAMI {

    private final static Logger log = LoggerFactory.getLogger(UniprotToJAMI.class.getName());

    public static Protein getProteinFromEntry(UniProtEntry e)
            throws FetcherException {

        if(e == null){
            throw new EntryNotFoundException("Uniprot entry was null.");
        }

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

        Protein p;

        //SHORTNAME - ShortName/FullName/UniprotID/UniprotAC
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

        XrefFactory xrefFactory = new XrefFactory();
        //UNIPROT ID AS SECONDARY AC
        if(e.getUniProtId() != null){
            p.getIdentifiers().add(
                    xrefFactory.createUniprotSecondary(e.getUniProtId().getValue()));
        }
        //SECONDARY ACs
        if(e.getSecondaryUniProtAccessions() != null
                && e.getSecondaryUniProtAccessions().size() > 0) {
            for(SecondaryUniProtAccession ac : e.getSecondaryUniProtAccessions()){
                if(ac.getValue() != null){
                    p.getIdentifiers().add(
                            xrefFactory.createUniprotSecondary(ac.getValue()));
                }
            }
        }

        //TODO review the aliases
        //Aliases
        if(e.getGenes() != null && e.getGenes().size() > 0){
            for(Gene g : e.getGenes()){
                //Gene Name
                if(g.hasGeneName()){
                    p.getAliases().add(AliasFactory.createGeneName(
                            g.getGeneName().getValue()));
                }
                //Gene Name Synonym
                if(g.getGeneNameSynonyms() != null
                        && g.getGeneNameSynonyms().size() > 0){
                    for(GeneNameSynonym gns : g.getGeneNameSynonyms()){
                        p.getAliases().add(AliasFactory.createGeneNameSynonym(
                                gns.getValue()));
                    }
                }
                //ORF names
                if(g.getORFNames() != null
                        && g.getORFNames().size() > 0){
                    for(ORFName orf : g.getORFNames()){
                        p.getAliases().add(AliasFactory.createOrfName(
                                orf.getValue()));
                    }
                }
                //Locus Names
                if(g.getOrderedLocusNames() != null
                        && g.getOrderedLocusNames().size() > 0){
                    for(OrderedLocusName oln : g.getOrderedLocusNames()){
                        p.getAliases().add(AliasFactory.createLocusName(
                                oln.getValue()));
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
        ChecksumFactory cf = new ChecksumFactory();
        //todo add MI term for crc64 checksums
        p.getChecksums().add(cf.createAnnotation("CRC64", null, e.getSequence().getCRC64()));
        //Rogid will be calculated at enrichment - the equation need not be applied in an organism conflict

        p.setOrganism(getOrganismFromEntry(e));

        return p;
    }

    private static Map<DatabaseType,CvTerm> databaseMap = null;
    protected static void initiateDatabaseMap(){
        databaseMap = new HashMap<DatabaseType, CvTerm>();
        databaseMap.put(DatabaseType.GO,        new DefaultCvTerm("go" , "MI:0448"));
        databaseMap.put(DatabaseType.INTERPRO,  new DefaultCvTerm("interpro" , "MI:0449"));
        databaseMap.put(DatabaseType.PDB,       new DefaultCvTerm("pdb" , "MI:0460"));
        databaseMap.put(DatabaseType.REACTOME,  new DefaultCvTerm("reactome" , "MI:0467"));
        databaseMap.put(DatabaseType.ENSEMBL,   new DefaultCvTerm("ensembl" , "MI:0476"));
        databaseMap.put(DatabaseType.WORMBASE,  new DefaultCvTerm("wormbase" , "MI:0487" ));
        databaseMap.put(DatabaseType.FLYBASE,   new DefaultCvTerm("flybase" , "MI:0478" ));
        databaseMap.put(DatabaseType.REFSEQ,    new DefaultCvTerm("refseq" , "MI:0481" ));
        databaseMap.put(DatabaseType.IPI,       new DefaultCvTerm("ipi" , "MI:0675" ));
    }

    protected static Xref getDatabaseXref(DatabaseCrossReference dbxref){
        if(databaseMap == null) initiateDatabaseMap();

        if (databaseMap.containsKey(dbxref.getDatabase())){
            CvTerm database = databaseMap.get(dbxref.getDatabase());

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
                //Todo check that the order f these is correct
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
            throws FetcherException{

        //TODO Change where ogrnaisms come from
        Organism o;

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
            }catch(NumberFormatException n){
                throw new BadResultException("NbiTaxonomyID could not be cast to an integer",n);
            }
        }

        //Todo catch null pointer exception
        o.setCommonName(e.getOrganism().getCommonName().getValue());
        o.setScientificName(e.getOrganism().getScientificName().getValue());

        return o;
    }

}
