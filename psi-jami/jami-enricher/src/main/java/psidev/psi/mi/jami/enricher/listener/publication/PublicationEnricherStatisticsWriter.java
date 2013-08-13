package psidev.psi.mi.jami.enricher.listener.publication;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherStatisticsWriter
        extends StatisticsWriter<Publication>
        implements PublicationEnricherListener{

    private static final String jamiObject = "Publication";


    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter() throws IOException {
        super(jamiObject);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, jamiObject);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, jamiObject);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, jamiObject);
    }


    // ================================================================

    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message){
        onObjectEnriched(publication , status , message);
    }

    public void onPubmedIdUpdate(Publication publication, String oldPubmedId) {
        checkObject(publication);
        updateCount++;
    }

    public void onDoiUpdate(Publication publication, String oldDoi) {
        checkObject(publication);
        updateCount++;
    }

    public void onIdentifierAdded(Publication publication, Xref addedXref) {
        checkObject(publication);
        additionCount++;
    }

    public void onIdentifierRemoved(Publication publication, Xref removedXref) {
        checkObject(publication);
        removedCount++;
    }

    public void onImexIdentifierAdded(Publication publication, Xref addedXref) {
        checkObject(publication);
        additionCount++;
    }

    public void onTitleUpdated(Publication publication, String oldTitle) {
        checkObject(publication);
        updateCount++;
    }

    public void onJournalUpdated(Publication publication, String oldJournal) {
        checkObject(publication);
        updateCount++;
    }

    public void onPublicationDateUpdated(Publication publication, Date oldDate) {
        checkObject(publication);
        updateCount++;
    }

    public void onAuthorAdded(Publication publication, String addedAuthor) {
        checkObject(publication);
        additionCount++;
    }

    public void onAuthorRemoved(Publication publication, String removedAuthor) {
        checkObject(publication);
        removedCount++;
    }

    public void onXrefAdded(Publication publication, Xref addedXref) {
        checkObject(publication);
        additionCount++;
    }

    public void onXrefRemoved(Publication publication, Xref removedXref) {
        checkObject(publication);
        removedCount++;
    }

    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {
        checkObject(publication);
        additionCount++;
    }

    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved) {
        checkObject(publication);
        removedCount++;
    }

    public void onReleaseDateUpdated(Publication publication, Date oldDate) {
        checkObject(publication);
        updateCount++;
    }
}
