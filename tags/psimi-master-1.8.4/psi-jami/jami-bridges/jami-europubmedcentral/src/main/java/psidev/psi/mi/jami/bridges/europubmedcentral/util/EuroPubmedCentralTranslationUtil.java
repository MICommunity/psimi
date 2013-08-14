package psidev.psi.mi.jami.bridges.europubmedcentral.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.cdb.webservice.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class EuroPubmedCentralTranslationUtil {

    protected static final Logger log = LoggerFactory.getLogger(EuroPubmedCentralTranslationUtil.class.getName());

    public static void convertDataResultToPublication(Publication publication , Result result){
        if(result == null) throw new IllegalArgumentException("Can not translate null publication result");

        // PubMed ID   //
        publication.setPubmedId(result.getPmid());
        log.info("publication.setPubmedId: "+result.getPmid());

        // DOI number
        publication.setDoi(result.getDOI());
        log.info("publication.setDoi: "+result.getDOI());

        //assignImexId

        // Publication Title
        publication.setTitle(result.getTitle());
        log.info("publication.setTitle: "+result.getTitle());

        // Journal Name
        if(result.getJournalInfo() != null ){
            if(result.getJournalInfo().getJournal() != null){
                publication.setJournal(result.getJournalInfo().getJournal().getTitle());
                log.info("publication.setJournal: "+result.getJournalInfo().getJournal().getTitle());
            }

            // Publication Date
            Calendar date = new GregorianCalendar(result.getJournalInfo().getYearOfPublication() ,
                    result.getJournalInfo().getMonthOfPublication()-1 , 1); // Dates begin from month 0; Days start from 1
            publication.setPublicationDate(date.getTime());
            log.info(" - month "+result.getJournalInfo().getMonthOfPublication().toString());
            log.info(" - year "+result.getJournalInfo().getYearOfPublication().toString());
        }

        // Publication Authors
        for(Authors authors : result.getAuthorList().getAuthor()){
            publication.getAuthors().add(authors.getFullName());
            log.info("publication.addAuthor: " + authors.getFullName());
        }

        // Xrefs
        // publication.getXrefs


        // Annotations
        // publication.getAnnotations()

        // setReleasedDate

        // Source Institution / Group

        if(result.getAffiliation() != null && result.getAffiliation().length() > 0)
            publication.setSource(new DefaultSource(result.getAffiliation())); // TODO
        log.info("publication.setSource: " + result.getAffiliation());
    }

    public static void convertXrefResultToPublication(Publication publication, ResponseWrapper result) {
        if(result == null || result.getDbCrossReferenceList() == null) return;

        for(DbCrossReference dbXref : result.getDbCrossReferenceList().getDbCrossReference()){
            for(DbCrossReferenceInfo dbXrefInfo : dbXref.getDbCrossReferenceInfo()){
                log.info("publication.addXref: "+dbXref.getDbName()+": "+dbXrefInfo.getInfo1());
                if( dbXref.getDbName().equals("UNIPROT") ){
                    publication.getXrefs().add(
                            XrefUtils.createUniprotIdentity(dbXrefInfo.getInfo1()));
                } else if( dbXref.getDbName().equals("EMBL")){
                    publication.getXrefs().add(
                            XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK , Xref.DDBJ_EMBL_GENBANK_MI , dbXrefInfo.getInfo1()));
                } else if( dbXref.getDbName().equals("PDB") ){
                } else if( dbXref.getDbName().equals("INTERPRO") ){
                    publication.getXrefs().add(
                            XrefUtils.createIdentityXref(Xref.INTERPRO , Xref.INTERPRO_MI , dbXrefInfo.getInfo1()));
                } else if( dbXref.getDbName().equals("OMIN") ){
                } else if( dbXref.getDbName().equals("CHEBI") ){
                    publication.getXrefs().add(
                            XrefUtils.createChebiIdentity(dbXrefInfo.getInfo1()));
                } else if( dbXref.getDbName().equals("CHEMBL") ){
                } else if( dbXref.getDbName().equals("INTACT")){
                } else if( dbXref.getDbName().equals("ARXPR")){
                }
            }
        }
    }
}
