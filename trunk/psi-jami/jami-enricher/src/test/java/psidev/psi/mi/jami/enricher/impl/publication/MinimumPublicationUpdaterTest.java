package psidev.psi.mi.jami.enricher.impl.publication;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockPublicationFetcher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.MinimalPublicationUpdater;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.PublicationEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.impl.PublicationEnricherLogger;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.*;

import java.util.Collections;
import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MinimumPublicationUpdaterTest {

    private PublicationEnricher publicationEnricher;
    private MockPublicationFetcher fetcher;

    private Publication persistentPublication = null;
    int persistentInt = 0;
    private Publication testPub = null;

    public String TEST_PUBMED_ID = "010101010";


    @Before
    public void setup(){
        fetcher = new MockPublicationFetcher();
        publicationEnricher = new MinimalPublicationUpdater(fetcher);

        testPub = new DefaultPublication(TEST_PUBMED_ID);
        fetcher.addEntry(TEST_PUBMED_ID, testPub);

        persistentPublication = new DefaultPublication();
        persistentInt = 0;

    }

    // == FAILURE ON NULL ======================================================================

    @Test (expected = IllegalArgumentException.class)
    public void test_failure_when_query_publication_is_null() throws EnricherException {
        persistentPublication = null;
        publicationEnricher.enrichPublication(persistentPublication);
        fail("Exception should be thrown before this point");
    }

    @Test  (expected = IllegalStateException.class)
    public void test_failure_when_fetcher_is_missing() throws EnricherException {
        persistentPublication = new DefaultPublication(TEST_PUBMED_ID);


        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger() ,
                new PublicationEnricherListener() {
                    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
                        fail();
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

        publicationEnricher.setPublicationFetcher(null);

        publicationEnricher.enrichPublication(persistentPublication);
    }

    @Test
    public void test_enrichment_completes_with_failed_when_ID_is_missing() throws EnricherException {
        persistentPublication.setPubmedId(null);

        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger(),
                new PublicationEnricherListener() {
                    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
                        assertTrue(publication == persistentPublication);
                        Assert.assertEquals(EnrichmentStatus.FAILED, status);
                        persistentInt ++;
                    }

                    public void onPubmedIdUpdate(Publication publication, String oldPubmedId) {fail("fail");}
                    public void onDoiUpdate(Publication publication, String oldDoi)  {fail("fail");}
                    public void onIdentifierAdded(Publication publication, Xref addedXref)  {fail("fail");}
                    public void onIdentifierRemoved(Publication publication, Xref removedXref)  {fail("fail");}
                    public void onImexIdentifierAdded(Publication publication, Xref addedXref)  {fail("fail");}
                    public void onTitleUpdated(Publication publication, String oldTitle)  {fail("fail");}
                    public void onJournalUpdated(Publication publication, String oldJournal)  {fail("fail");}
                    public void onPublicationDateUpdated(Publication publication, Date oldDate) {fail("fail");}
                    public void onAuthorAdded(Publication publication, String addedAuthor)  {fail("fail");}
                    public void onAuthorRemoved(Publication publication, String removedAuthor) {fail("fail");}
                    public void onXrefAdded(Publication publication, Xref addedXref)  {fail("fail");}
                    public void onXrefRemoved(Publication publication, Xref removedXref)  {fail("fail");}
                    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {fail("fail");}
                    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved)  {fail("fail");}
                    public void onReleaseDateUpdated(Publication publication, Date oldDate) {fail("fail");}
                }
        ));
        publicationEnricher.enrichPublication(persistentPublication);

        Assert.assertEquals(1, persistentInt);
    }


    // == PUBMED_ID ============================================================================================


    // == AUTHORS ==============================================================================================

    /**
     * Assert that where the authors are empty, they will be added
     * @throws EnricherException
     */
    @Test
    public void test_enrichment_of_empty_authors() throws EnricherException {
        Publication testPub = new DefaultPublication(TEST_PUBMED_ID);
        testPub.getAuthors().add("TEST_A");

        fetcher.addEntry(TEST_PUBMED_ID, testPub);

        persistentPublication.setPubmedId(TEST_PUBMED_ID);

        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger() ,
                new PublicationEnricherListener() {
                    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
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
                    public void onAuthorAdded(Publication publication, String addedAuthor)          {
                        assertTrue(publication == persistentPublication);
                        assertEquals("TEST_A" , addedAuthor);
                    }
                    public void onAuthorRemoved(Publication publication, String removedAuthor)      {fail("fail");}
                    public void onXrefAdded(Publication publication, Xref addedXref)                {fail("fail");}
                    public void onXrefRemoved(Publication publication, Xref removedXref)            {fail("fail");}
                    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {fail("fail");}
                    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved) {fail("fail");}
                    public void onReleaseDateUpdated(Publication publication, Date oldDate)         {fail("fail");}
                }
        ));

        publicationEnricher.enrichPublication(persistentPublication);


        assertEquals(1 , persistentPublication.getAuthors().size());

        assertEquals(TEST_PUBMED_ID , persistentPublication.getPubmedId());
        assertEquals(1 , persistentPublication.getIdentifiers().size());
        assertNull(persistentPublication.getTitle());
        assertNull(persistentPublication.getJournal());
        assertNull(persistentPublication.getDoi());
        assertNull(persistentPublication.getPublicationDate());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getXrefs());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getAnnotations());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getExperiments());
        assertEquals(CurationDepth.undefined , persistentPublication.getCurationDepth());
        assertNull(persistentPublication.getReleasedDate());
        assertNull(persistentPublication.getSource());
    }


    @Test
    public void test_enrichment_of_present_authors() throws EnricherException {
        Publication testPub = new DefaultPublication(TEST_PUBMED_ID);
        testPub.getAuthors().add("TEST_A");
        fetcher.addEntry(TEST_PUBMED_ID , testPub);

        persistentPublication.setPubmedId(TEST_PUBMED_ID);
        persistentPublication.getAuthors().add("TEST_B");

        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger() ,
                new PublicationEnricherListener() {
                    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
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
                    public void onAuthorAdded(Publication publication, String addedAuthor)          {
                        assertTrue(publication == persistentPublication);
                        assertEquals("TEST_A" , addedAuthor);
                    }
                    public void onAuthorRemoved(Publication publication, String removedAuthor)      {
                        assertTrue(publication == persistentPublication);
                        assertEquals("TEST_B" , removedAuthor);
                    }
                    public void onXrefAdded(Publication publication, Xref addedXref)                {fail("fail");}
                    public void onXrefRemoved(Publication publication, Xref removedXref)            {fail("fail");}
                    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {fail("fail");}
                    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved) {fail("fail");}
                    public void onReleaseDateUpdated(Publication publication, Date oldDate)         {fail("fail");}
                }
        ));

        publicationEnricher.enrichPublication(persistentPublication);

        assertEquals(1 , persistentPublication.getAuthors().size());
        assertEquals( "TEST_A" , persistentPublication.getAuthors().iterator().next() );

        assertEquals(TEST_PUBMED_ID , persistentPublication.getPubmedId());
        assertEquals(1 , persistentPublication.getIdentifiers().size());
        assertNull(persistentPublication.getTitle());
        assertNull(persistentPublication.getJournal());
        assertNull(persistentPublication.getDoi());
        assertNull(persistentPublication.getPublicationDate());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getXrefs());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getAnnotations());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getExperiments());
        assertEquals(CurationDepth.undefined , persistentPublication.getCurationDepth());
        assertNull(persistentPublication.getReleasedDate());
        assertNull(persistentPublication.getSource());
    }

    /**
     * Assert that where the authors are empty, they will be added
     * @throws EnricherException
     */
    @Test
    public void test_enrichment_of_authors_with_empty_fetched() throws EnricherException {
        Publication testPub = new DefaultPublication(TEST_PUBMED_ID);
        testPub.getAuthors().clear();
        fetcher.addEntry(TEST_PUBMED_ID, testPub);

        persistentPublication.setPubmedId(TEST_PUBMED_ID);
        persistentPublication.getAuthors().add("TEST_A");

        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger() ,
                new PublicationEnricherListener() {
                    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
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


        assertEquals(1 , persistentPublication.getAuthors().size());

        assertEquals(TEST_PUBMED_ID , persistentPublication.getPubmedId());
        assertEquals(1 , persistentPublication.getIdentifiers().size());
        assertNull(persistentPublication.getTitle());
        assertNull(persistentPublication.getJournal());
        assertNull(persistentPublication.getDoi());
        assertNull(persistentPublication.getPublicationDate());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getXrefs());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getAnnotations());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getExperiments());
        assertEquals(CurationDepth.undefined , persistentPublication.getCurationDepth());
        assertNull(persistentPublication.getReleasedDate());
        assertNull(persistentPublication.getSource());
    }


    // == OTHER ========================================================================================

    /**
     * Enrich a publication where the fetcher finds all fields.
     * Assert that the fields which are not to be included at the minimum level remain un-enriched
     * @throws EnricherException
     */
    @Test
    public void test_enrichment_does_not_apply_to_other_fields() throws EnricherException {

        Publication testPub = new DefaultPublication(TEST_PUBMED_ID);
        testPub.setTitle("TITLE");
        testPub.setJournal("JOURNAL");
        //testPub.setPublicationDate(new Date(99));
        testPub.setDoi("DOI");
        testPub.getXrefs().add(new DefaultXref(new DefaultCvTerm("Test CvTerm"),"Test xref"));
        testPub.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test Cvterm") , "value"));
        testPub.getExperiments().add(new DefaultExperiment(testPub));
        testPub.setCurationDepth(CurationDepth.IMEx);
        //testPub.setReleasedDate(new Date(99));
        testPub.setSource(new DefaultSource("SOURCE"));


        fetcher.addEntry(TEST_PUBMED_ID, testPub);

        persistentPublication.setPubmedId(TEST_PUBMED_ID);

        publicationEnricher.setPublicationEnricherListener(new PublicationEnricherListenerManager(
                new PublicationEnricherLogger() ,
                new PublicationEnricherListener() {
                    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
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

        assertEquals(TEST_PUBMED_ID , persistentPublication.getPubmedId());
        assertEquals(1 , persistentPublication.getIdentifiers().size());
        assertNull(persistentPublication.getTitle());
        assertNull(persistentPublication.getJournal());
        assertNull(persistentPublication.getDoi());
        assertNull(persistentPublication.getPublicationDate());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getAuthors());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getXrefs());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getAnnotations());
        assertEquals(Collections.EMPTY_LIST , persistentPublication.getExperiments());
        assertEquals(CurationDepth.undefined , persistentPublication.getCurationDepth());
        assertNull(persistentPublication.getReleasedDate());
        assertNull(persistentPublication.getSource());
    }
}
