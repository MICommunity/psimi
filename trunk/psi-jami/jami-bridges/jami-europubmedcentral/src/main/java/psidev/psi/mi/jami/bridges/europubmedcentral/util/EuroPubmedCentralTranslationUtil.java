package psidev.psi.mi.jami.bridges.europubmedcentral.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import uk.ac.ebi.cdb.webservice.Authors;
import uk.ac.ebi.cdb.webservice.Result;

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

    public static Publication convertResultToPublication(Result result){
        if(result == null) throw new IllegalArgumentException("Can not translate null publication result");

        Publication publication = new DefaultPublication();

        // PubMed ID
        publication.setPubmedId(result.getPmid());
        log.info("publication.setPubmedId: "+result.getPmid());

        // DOI number
        publication.setDoi(result.getDOI());
        log.info("publication.setDoi: "+result.getDOI());



        log.info("DB xrefs "+result.getHasDbCrossReferences());
        if(result.getHasDbCrossReferences().equals("Y"))
            //publication.getIdentifiers
            if( result.getDbCrossReferenceList() != null
                    && result.getDbCrossReferenceList().getDbName()  != null )
                for( String ac : result.getDbCrossReferenceList().getDbName() ){
                    log.info("Xref types"+ac);
                }



        log.info("Ac Numbers "+result.getHasTMAccessionNumbers());
        if(result.getHasTMAccessionNumbers().equals("Y"))
            //publication.getIdentifiers
            if( result.getTmAccessionTypeList() != null
                    && result.getTmAccessionTypeList().getAccessionType()  != null )
                for( String ac : result.getTmAccessionTypeList().getAccessionType() ){
                    log.info("AC types"+ac);
                }





        //assignImexId

        // Publication Title
        publication.setTitle(result.getTitle());
        log.info("publication.setTitle: "+result.getTitle());

        // Journal Name
        publication.setJournal(result.getJournalInfo().getJournal().getTitle());
        log.info("publication.setJournal: "+result.getJournalInfo().getJournal().getTitle());

        // Publication Date
        Calendar date = new GregorianCalendar(result.getJournalInfo().getYearOfPublication() ,
                result.getJournalInfo().getMonthOfPublication()-1 , 1); // Dates begin from month 0; Days start from 1
        publication.setPublicationDate(date.getTime());
        log.info(" - month "+result.getJournalInfo().getMonthOfPublication().toString());
        log.info(" - year "+result.getJournalInfo().getYearOfPublication().toString());

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



        /*
        log.info(result.getJournalInfo().getDateOfPublication());
        // log.info(result.getJournalInfo().getIssue());
        // log.info(result.getJournalInfo().getVolume());
        log.info(result.getJournalInfo().getJournal().getTitle());
        log.info(result.getJournalInfo().getJournal().getISOAbbreviation());
        log.info(result.getJournalInfo().getJournal().getMedlineAbbreviation());
        log.info(result.getJournalInfo().getJournal().getNLMid());
        log.info(result.getJournalInfo().getJournal().getISSN());
        log.info(result.getJournalInfo().getJournalIssueId().toString());
         */

        return publication;
    }

}
