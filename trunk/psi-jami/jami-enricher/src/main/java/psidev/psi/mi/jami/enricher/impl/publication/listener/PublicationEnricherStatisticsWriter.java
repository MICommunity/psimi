package psidev.psi.mi.jami.enricher.impl.publication.listener;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherStatisticsWriter
        extends StatisticsWriter<Publication>
        implements PublicationEnricherListener{

    private static final String OBJECT = "Publication";

    public PublicationEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, OBJECT);
    }

    public PublicationEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, OBJECT);
    }

    public PublicationEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, OBJECT);
    }

    public void onPublicationEnriched(Publication publication, EnrichmentStatus status, String message){
        onObjectEnriched(publication , status , message);
    }


    //--------------


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
