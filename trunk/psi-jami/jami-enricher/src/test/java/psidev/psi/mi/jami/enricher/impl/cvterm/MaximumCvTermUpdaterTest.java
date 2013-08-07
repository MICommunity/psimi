package psidev.psi.mi.jami.enricher.impl.cvterm;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.ExceptionThrowingMockCvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.cvterm.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AliasUtils;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class MaximumCvTermUpdaterTest {


    private MaximumCvTermUpdater cvTermEnricher;
    private MockCvTermFetcher mockCvTermFetcher ;

    private CvTerm mockCvTerm;
    private String short_name = "ShortName";
    private String full_name = "FullName";
    private String MI_ID = "MI:1234";

    private String other_short_name = "other short name";
    private String other_full_name = "other full name";

    private Collection<String> reportForEnrichment= new ArrayList<String>();
    private String fullNameUpdateKey = "FullNameUpdate";
    private String shortNameUpdateKey = "ShortNameUpdate";
    private String synonymAddedKey = "SynonymAdded";
    private String synonymRemovedKey = "SynonymRemoved";
    private String identifierAddedKey = "IdentifierAdded";
    private String identifierRemovedKey = "IdentifierRemoved";

    private CvTerm persistentCvTerm;


    @Before
    public void setup() throws BridgeFailedException {
        mockCvTermFetcher = new MockCvTermFetcher();

        cvTermEnricher = new MaximumCvTermUpdater(mockCvTermFetcher);

        mockCvTerm = new DefaultCvTerm(short_name, full_name, MI_ID);
        mockCvTerm.getIdentifiers().add(new DefaultXref(
                new DefaultCvTerm(short_name) , short_name));
        mockCvTerm.getXrefs().add(new DefaultXref(
                new DefaultCvTerm(short_name) , short_name));
        mockCvTerm.getAnnotations().add(new DefaultAnnotation(
                new DefaultCvTerm(short_name) , short_name ));
        mockCvTerm.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", short_name));

        mockCvTermFetcher.addEntry(MI_ID , mockCvTerm);

        reportForEnrichment.clear();
        persistentCvTerm = null;
    }

    // == RETRY ON FAILING FETCHER ============================================================

    /**
     * Creates a scenario where the fetcher always throws a bridge failure exception.
     * Shows that the query does not repeat infinitely.
     * @throws psidev.psi.mi.jami.enricher.exception.EnricherException
     */
    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {

        persistentCvTerm = new DefaultCvTerm(short_name , MI_ID);

        int timesToTry = -1;

        ExceptionThrowingMockCvTermFetcher fetcher = new ExceptionThrowingMockCvTermFetcher(timesToTry);
        fetcher.addEntry(MI_ID , mockCvTerm);
        cvTermEnricher.setCvTermFetcher(fetcher);

        cvTermEnricher.enrichCvTerm(persistentCvTerm);

        fail("Exception should be thrown before this point");
    }

    /**
     * Creates a scenario where the fetcher does not retrieve an entry on its first attempt.
     * If the enricher re-queries the fetcher, it will eventually receive the entry.
     *
     * @throws EnricherException
     */
    @Test
    public void test_bridgeFailure_does_not_throw_exception_when_not_persistent() throws EnricherException {
        persistentCvTerm = new DefaultCvTerm(short_name , MI_ID);

        int timesToTry = 3;

        assertTrue("The test can not be applied as the conditions do not invoke the required response. " +
                "Change the timesToTry." ,
                timesToTry < MinimumCvTermEnricher.RETRY_COUNT);

        ExceptionThrowingMockCvTermFetcher fetcher = new ExceptionThrowingMockCvTermFetcher(timesToTry);
        fetcher.addEntry(MI_ID , mockCvTerm);
        cvTermEnricher.setCvTermFetcher(fetcher);

        cvTermEnricher.enrichCvTerm(persistentCvTerm);

        assertEquals(full_name, persistentCvTerm.getFullName() );
    }



    // == FAILURE ON NULL ======================================================================

    /**
     * Attempts to enrich a null CvTerm.
     * This should always cause an illegal argument exception
     * @throws EnricherException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTerm() throws EnricherException {
        CvTerm nullCvTerm = null;
        cvTermEnricher.enrichCvTerm(nullCvTerm);
        fail("Exception should be thrown before this point");
    }

    /**
     * Attempts to enrich a legal cvTerm but with a null fetcher.
     * This should throw an illegal state exception.
     * @throws EnricherException
     */
    @Test(expected = IllegalStateException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        CvTerm cvTerm = new DefaultCvTerm(short_name, MI_ID);
        cvTermEnricher.setCvTermFetcher(null);
        assertNull(cvTermEnricher.getCvTermFetcher());
        cvTermEnricher.enrichCvTerm(cvTerm);
        fail("Exception should be thrown before this point");
    }

    // == TEST ALL FIELDS ==========================
    /**
     * Show that when the fields are empty, the updater fills them in.
     * @throws EnricherException
     */
    @Test
    public void test_enriching_CvTerm_with_empty_fields() throws EnricherException {
        persistentCvTerm = new DefaultCvTerm(other_short_name , MI_ID);
        reportForEnrichment.clear();


        cvTermEnricher.setCvTermEnricherListener(new CvTermEnricherListener() {
            public void onEnrichmentComplete(CvTerm cvTerm, EnrichmentStatus status, String message) {
                assertTrue(cvTerm == persistentCvTerm);
                assertEquals(EnrichmentStatus.SUCCESS , status);
            }

            public void onShortNameUpdate(CvTerm cv, String oldShortName) {
                assertTrue(cv == persistentCvTerm);
                assertEquals(other_short_name , oldShortName);
                assertEquals(short_name, cv.getShortName());
                reportForEnrichment.add(shortNameUpdateKey);
            }

            public void onFullNameUpdate(CvTerm cv, String oldFullName) {
                assertTrue(cv == persistentCvTerm);
                assertNull(oldFullName);
                assertEquals(full_name, cv.getFullName());
                reportForEnrichment.add(fullNameUpdateKey);
            }

            public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {fail();}
            public void onMODIdentifierUpdate(CvTerm cv, String oldMOD)  {fail();}
            public void onPARIdentifierUpdate(CvTerm cv, String oldPAR)  {fail();}
            public void onAddedIdentifier(CvTerm cv, Xref added)   {
                assertTrue(cv == persistentCvTerm) ;
                assertEquals(short_name , added.getId());
                reportForEnrichment.add(identifierAddedKey);
            }
            public void onRemovedIdentifier(CvTerm cv, Xref removed)  {fail();}
            public void onAddedXref(CvTerm cv, Xref added)  {fail();}
            public void onRemovedXref(CvTerm cv, Xref removed)  {fail();}
            public void onAddedSynonym(CvTerm cv, Alias added)  {
                assertTrue(cv == persistentCvTerm) ;
                assertEquals(short_name , added.getName());
                reportForEnrichment.add(synonymAddedKey);
            }
            public void onRemovedSynonym(CvTerm cv, Alias removed)  {fail();}
        });

        cvTermEnricher.enrichCvTerm(persistentCvTerm);

        assertEquals(short_name, persistentCvTerm.getShortName());
        assertEquals(full_name, persistentCvTerm.getFullName());
        assertEquals(2 , persistentCvTerm.getIdentifiers().size());
        assertEquals(1 , persistentCvTerm.getSynonyms().size());

        // Show no change on unused fields
        assertEquals(0, persistentCvTerm.getXrefs().size());
        assertEquals(0 , persistentCvTerm.getAnnotations().size());

        // Show events were fired
        assertTrue(reportForEnrichment.contains(shortNameUpdateKey));
        assertTrue(reportForEnrichment.contains(fullNameUpdateKey));
        assertTrue(reportForEnrichment.contains(identifierAddedKey));
        assertTrue(reportForEnrichment.contains(synonymAddedKey));
    }


    /**
     * Show that if the term to enrich has fields completed, they are replaced with the updated form
     * @throws EnricherException
     */
    @Test
    public void test_updating_CvTerm_by_MI_identifier_with_different_fields() throws EnricherException {
        persistentCvTerm = new DefaultCvTerm(other_short_name, other_full_name, MI_ID);
        persistentCvTerm.getXrefs().add(new DefaultXref(
                new DefaultCvTerm(other_short_name) , other_short_name));
        persistentCvTerm.getAnnotations().add(new DefaultAnnotation(
                new DefaultCvTerm(other_short_name) , other_short_name ));
        persistentCvTerm.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", other_short_name));

        cvTermEnricher.setCvTermEnricherListener(new CvTermEnricherListener() {
            public void onEnrichmentComplete(CvTerm cvTerm, EnrichmentStatus status, String message) {
                assertTrue(cvTerm == persistentCvTerm);
                assertEquals(EnrichmentStatus.SUCCESS , status);
            }

            public void onShortNameUpdate(CvTerm cv, String oldShortName) {
                assertTrue(cv == persistentCvTerm);
                assertEquals(other_short_name , oldShortName);
                assertEquals(short_name, cv.getShortName());
                reportForEnrichment.add(shortNameUpdateKey);
            }

            public void onFullNameUpdate(CvTerm cv, String oldFullName) {
                assertTrue(cv == persistentCvTerm);
                assertEquals(other_full_name , oldFullName);
                assertEquals(full_name, cv.getFullName());
                reportForEnrichment.add(fullNameUpdateKey);
            }

            public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {fail();}
            public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {fail();}
            public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {fail();}
            public void onAddedIdentifier(CvTerm cv, Xref added) {
                assertTrue(cv == persistentCvTerm) ;
                assertEquals(short_name , added.getId());
                reportForEnrichment.add(identifierAddedKey);
            }
            public void onRemovedIdentifier(CvTerm cv, Xref removed) {fail();}
            public void onAddedXref(CvTerm cv, Xref added)  {fail();}
            public void onRemovedXref(CvTerm cv, Xref removed)  {fail();}
            public void onAddedSynonym(CvTerm cv, Alias added) {
                assertTrue(cv == persistentCvTerm) ;
                assertEquals(short_name , added.getName());
                reportForEnrichment.add(synonymAddedKey);
            }
            public void onRemovedSynonym(CvTerm cv, Alias removed)  {
                assertTrue(cv == persistentCvTerm) ;
                assertEquals(other_short_name , removed.getName());
                reportForEnrichment.add(synonymRemovedKey);
            }

        });

        cvTermEnricher.enrichCvTerm(persistentCvTerm);

        assertEquals(short_name, persistentCvTerm.getShortName());
        assertEquals(full_name, persistentCvTerm.getFullName());
        assertEquals(2 , persistentCvTerm.getIdentifiers().size());
        assertEquals(1 , persistentCvTerm.getSynonyms().size());
        assertEquals(short_name, persistentCvTerm.getSynonyms().iterator().next().getName() );

        // Show no change on maximum fields
        assertEquals(1 , persistentCvTerm.getXrefs().size());
        assertEquals(1 , persistentCvTerm.getAnnotations().size());
        assertEquals(other_short_name, persistentCvTerm.getXrefs().iterator().next().getId());
        assertEquals(other_short_name, persistentCvTerm.getAnnotations().iterator().next().getValue() );

        // Show events were fired
        assertTrue(reportForEnrichment.contains(shortNameUpdateKey));
        assertTrue(reportForEnrichment.contains(fullNameUpdateKey));
        assertTrue(reportForEnrichment.contains(identifierAddedKey));
        assertTrue(reportForEnrichment.contains(synonymAddedKey));
        assertTrue(reportForEnrichment.contains(synonymRemovedKey));
    }


    /**
     * Show that if the fetched fields and fields to enrich are the same, no changes are made
     */
    @Test
    public void test_no_updating_cvTerm_by_MI_identifier_with_same_fields() throws EnricherException {
        persistentCvTerm = new DefaultCvTerm(short_name, full_name, MI_ID);
        persistentCvTerm.getXrefs().add(new DefaultXref(
                new DefaultCvTerm(short_name) , short_name));
        persistentCvTerm.getAnnotations().add(new DefaultAnnotation(
                new DefaultCvTerm(short_name) , short_name ));
        persistentCvTerm.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", short_name));

        cvTermEnricher.setCvTermEnricherListener(new CvTermEnricherListener() {
            public void onEnrichmentComplete(CvTerm cvTerm, EnrichmentStatus status, String message) {
                assertTrue(cvTerm == persistentCvTerm);
                assertEquals(EnrichmentStatus.SUCCESS , status);
            }

            public void onShortNameUpdate(CvTerm cv, String oldShortName) {fail();}

            public void onFullNameUpdate(CvTerm cv, String oldFullName) {fail();}

            public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {fail();}
            public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {fail();}
            public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {fail();}
            public void onAddedIdentifier(CvTerm cv, Xref added)   {
                assertTrue(cv == persistentCvTerm) ;
                assertEquals(short_name , added.getId());
                reportForEnrichment.add(identifierAddedKey);
            }
            public void onRemovedIdentifier(CvTerm cv, Xref removed)  {fail();}
            public void onAddedXref(CvTerm cv, Xref added)  {fail();}
            public void onRemovedXref(CvTerm cv, Xref removed)  {fail();}
            public void onAddedSynonym(CvTerm cv, Alias added) {fail();}
            public void onRemovedSynonym(CvTerm cv, Alias removed)  {fail();}

        });

        cvTermEnricher.enrichCvTerm(persistentCvTerm);

        assertEquals(short_name, persistentCvTerm.getShortName());
        assertEquals(full_name, persistentCvTerm.getFullName());
        assertEquals(2 , persistentCvTerm.getIdentifiers().size());

        // Show no change on maximum fields
        assertEquals(1 , persistentCvTerm.getXrefs().size());
        assertEquals(1 , persistentCvTerm.getAnnotations().size());
        assertEquals(1 , persistentCvTerm.getSynonyms().size());
        assertEquals(short_name, persistentCvTerm.getXrefs().iterator().next().getId() );
        assertEquals(short_name, persistentCvTerm.getAnnotations().iterator().next().getValue() );
        assertEquals(short_name, persistentCvTerm.getSynonyms().iterator().next().getName() );

        // Show events were fired
        assertTrue(reportForEnrichment.contains(identifierAddedKey));
    }

    /**
     * Show that fields are not changed to null if the fetcher does not find fields
     */
    @Test
    public void test_no_updating_cvTerm_by_MI_identifier_when_fetched_fields_are_null() throws EnricherException {
        mockCvTerm.setShortName(other_short_name);
        mockCvTerm.setFullName(null);
        mockCvTerm.getAnnotations().clear();
        mockCvTerm.getSynonyms().clear();
        mockCvTerm.getXrefs().clear();


        persistentCvTerm = new DefaultCvTerm(other_short_name, other_full_name, MI_ID);
        persistentCvTerm.getXrefs().add(new DefaultXref(
                new DefaultCvTerm(other_short_name) , other_short_name));
        persistentCvTerm.getAnnotations().add(new DefaultAnnotation(
                new DefaultCvTerm(other_short_name) , other_short_name ));
        persistentCvTerm.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", other_short_name));

        cvTermEnricher.setCvTermEnricherListener(new CvTermEnricherListener() {
            public void onEnrichmentComplete(CvTerm cvTerm, EnrichmentStatus status, String message) {
                assertTrue(cvTerm == persistentCvTerm);
                assertEquals(EnrichmentStatus.SUCCESS , status);
            }

            public void onShortNameUpdate(CvTerm cv, String oldShortName) {fail();}
            public void onFullNameUpdate(CvTerm cv, String oldFullName) {fail();}
            public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {fail();}
            public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {fail();}
            public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {fail();}
            public void onAddedIdentifier(CvTerm cv, Xref added)  {
                assertTrue(cv == persistentCvTerm) ;
                assertEquals(short_name , added.getId());
                reportForEnrichment.add(identifierAddedKey);
            }
            public void onRemovedIdentifier(CvTerm cv, Xref removed)  {fail();}
            public void onAddedXref(CvTerm cv, Xref added)  {fail();}
            public void onRemovedXref(CvTerm cv, Xref removed)  {fail();}
            public void onAddedSynonym(CvTerm cv, Alias added) {fail();}
            public void onRemovedSynonym(CvTerm cv, Alias removed)  {fail();}

        });

        cvTermEnricher.enrichCvTerm(persistentCvTerm);

        assertEquals(other_short_name, persistentCvTerm.getShortName());
        assertEquals(other_full_name, persistentCvTerm.getFullName());
        assertEquals(2 , persistentCvTerm.getIdentifiers().size());
        assertEquals(1 , persistentCvTerm.getSynonyms().size());

        // Show no change on maximum fields
        assertEquals(1 , persistentCvTerm.getXrefs().size());
        assertEquals(1 , persistentCvTerm.getAnnotations().size());
        assertEquals(other_short_name, persistentCvTerm.getSynonyms().iterator().next().getName() );
        assertEquals(other_short_name, persistentCvTerm.getXrefs().iterator().next().getId());
        assertEquals(other_short_name, persistentCvTerm.getAnnotations().iterator().next().getValue() );

        // Show events were fired
        assertTrue(reportForEnrichment.contains(identifierAddedKey));
    }
}
