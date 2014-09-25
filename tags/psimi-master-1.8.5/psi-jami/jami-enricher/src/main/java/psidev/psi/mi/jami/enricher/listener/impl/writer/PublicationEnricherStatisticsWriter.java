package psidev.psi.mi.jami.enricher.listener.impl.writer;

import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.model.*;

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
        extends EnricherStatisticsWriter<Publication>
        implements PublicationEnricherListener {

    private static final String FILE_NAME = "Publication";


    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }


    // ================================================================

    public void onPubmedIdUpdate(Publication publication, String oldPubmedId) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onDoiUpdate(Publication publication, String oldDoi) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onAddedIdentifier(Publication publication, Xref addedXref) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onRemovedIdentifier(Publication publication, Xref removedXref) {
        checkObject(publication);
        incrementRemovedCount();
    }

    public void onImexIdentifierUpdate(Publication publication, Xref addedXref) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onTitleUpdated(Publication publication, String oldTitle) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onJournalUpdated(Publication publication, String oldJournal) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onCurationDepthUpdate(Publication publication, CurationDepth oldDepth) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onPublicationDateUpdated(Publication publication, Date oldDate) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onAuthorAdded(Publication publication, String addedAuthor) {
        checkObject(publication);
        incrementAdditionCount();
    }

    public void onAuthorRemoved(Publication publication, String removedAuthor) {
        checkObject(publication);
        incrementRemovedCount();
    }

    public void onAddedXref(Publication publication, Xref addedXref) {
        checkObject(publication);
        incrementAdditionCount();
    }

    public void onRemovedXref(Publication publication, Xref removedXref) {
        checkObject(publication);
        incrementRemovedCount();
    }

    public void onAddedAnnotation(Publication publication, Annotation annotationAdded) {
        checkObject(publication);
        incrementAdditionCount();
    }

    public void onRemovedAnnotation(Publication publication, Annotation annotationRemoved) {
        checkObject(publication);
        incrementRemovedCount();
    }

    public void onReleaseDateUpdated(Publication publication, Date oldDate) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onSourceUpdated(Publication publication, Source oldSource) {
        checkObject(publication);
        incrementUpdateCount();
    }
}
