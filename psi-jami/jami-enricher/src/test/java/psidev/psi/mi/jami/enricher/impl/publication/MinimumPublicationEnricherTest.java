package psidev.psi.mi.jami.enricher.impl.publication;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.publication.MockPublicationFetcher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.publication.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.impl.publication.listener.PublicationEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.publication.listener.PublicationEnricherLogger;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MinimumPublicationEnricherTest {

    private PublicationEnricher publicationEnricher;
    private MockPublicationFetcher fetcher;

    private Publication persistentPublication = null;
    private  Publication testPub = null;

    public String TEST_PUBMED_ID = "010101010";


    @Before
    public void setup(){
        fetcher = new MockPublicationFetcher();
        publicationEnricher = new MinimumPublicationEnricher(fetcher);

        persistentPublication = new DefaultPublication();

        testPub = new DefaultPublication(TEST_PUBMED_ID);

        fetcher.addNewPublication(TEST_PUBMED_ID , testPub);
    }


    @Test
    public void test_failure_when_ID_is_missing() throws EnricherException {
        //persistentPublication.setPubmedId(TEST_PUBMED_ID);
        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger() ,
                new PublicationEnricherListener() {
                    public void onPublicationEnriched(Publication publication, EnrichmentStatus status, String message) {
                        assertTrue(publication == persistentPublication);
                        assertEquals(EnrichmentStatus.FAILED , status);
                    }
                    public void onPubmedIdUpdate(Publication publication, String oldPubmedId)       {fail("fail");}
                    public void onDoiUpdate(Publication publication, String oldDoi)                 {fail("fail");}
                    public void onIdentifierAdded(Publication publication, Xref addedXref)          {fail("fail");}
                    public void onIdentifierRemoved(Publication publication, Xref removedXref)      {fail("fail");}
                    public void onImexIdentifierAdded(Publication publication, Xref addedXref)      {fail("fail");}
                    public void onTitleUpdated(Publication publication, String oldTitle)            {fail("fail");}
                    public void onJournalUpdated(Publication publication, String oldJournal)        {fail("fail");}
                    public void onPublicationDateUpdated(Publication publication, Date oldDate)     {fail("fail");}
                    public void onAuthorAdded(Publication publication, String addedAuthor)          {fail("fail");}
                    public void onAuthorRemoved(Publication publication, String removedAuthor)      {fail("fail");}
                    public void onXrefAdded(Publication publication, Xref addedXref)                {fail("fail");}
                    public void onXrefRemoved(Publication publication, Xref removedXref)            {fail("fail");}
                    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {fail("fail");}
                    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved) {fail("fail");}
                    public void onReleaseDateUpdated(Publication publication, Date oldDate)         {fail("fail");}
                }
        ));
        publicationEnricher.enrichPublication(persistentPublication);
    }


    @Test
    public void test() throws EnricherException {

        persistentPublication.setPubmedId(TEST_PUBMED_ID);

        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger() ,
                new PublicationEnricherListener() {
                    public void onPublicationEnriched(Publication publication, EnrichmentStatus status, String message) {
                        assertTrue(publication == persistentPublication);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onPubmedIdUpdate(Publication publication, String oldPubmedId)       {fail("fail");}
                    public void onDoiUpdate(Publication publication, String oldDoi)                 {fail("fail");}
                    public void onIdentifierAdded(Publication publication, Xref addedXref)          {fail("fail");}
                    public void onIdentifierRemoved(Publication publication, Xref removedXref)      {fail("fail");}
                    public void onImexIdentifierAdded(Publication publication, Xref addedXref)      {fail("fail");}
                    public void onTitleUpdated(Publication publication, String oldTitle)            {fail("fail");}
                    public void onJournalUpdated(Publication publication, String oldJournal)        {fail("fail");}
                    public void onPublicationDateUpdated(Publication publication, Date oldDate)     {fail("fail");}
                    public void onAuthorAdded(Publication publication, String addedAuthor)          {fail("fail");}
                    public void onAuthorRemoved(Publication publication, String removedAuthor)      {fail("fail");}
                    public void onXrefAdded(Publication publication, Xref addedXref)                {fail("fail");}
                    public void onXrefRemoved(Publication publication, Xref removedXref)            {fail("fail");}
                    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {fail("fail");}
                    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved) {fail("fail");}
                    public void onReleaseDateUpdated(Publication publication, Date oldDate)         {fail("fail");}
                }
        ));

        publicationEnricher.enrichPublication(persistentPublication);
    }

}
