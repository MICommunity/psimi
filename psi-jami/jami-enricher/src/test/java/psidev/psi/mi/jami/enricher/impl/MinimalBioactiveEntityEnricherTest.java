package psidev.psi.mi.jami.enricher.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.FailingBioactiveEntityFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockBioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.BioactiveEntityEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.FullBioactiveEntityUpdater;
import psidev.psi.mi.jami.enricher.impl.MinimalBioactiveEntityEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.BioactiveEntityEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.impl.BioactiveEntityEnricherLogger;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;

import static junit.framework.Assert.*;
import static junit.framework.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class MinimalBioactiveEntityEnricherTest {

    String CHEBI_ID = "TEST_ID";
    String TEST_FULLNAME = "fullName";
    String TEST_SHORTNAME = "shortName";
    String TEST_STRING = "A test term";
    String OTHER_TEST_STRING = "Something else";

    BioactiveEntityEnricher enricher;
    MockBioactiveEntityFetcher fetcher;

    BioactiveEntity persistentBioactiveEntity;
    int persistentInt = 0;

    @Before
    public void setUp(){
        fetcher = new MockBioactiveEntityFetcher();
        enricher = new MinimalBioactiveEntityEnricher(fetcher);

        persistentBioactiveEntity = null;
        persistentInt = 0;
    }



    // == RETRY ON FAILING FETCHER ============================================================

    /**
     * Creates a scenario where the fetcher always throws a bridge failure exception.
     * Shows that the query does not repeat infinitely.
     * @throws EnricherException
     */
    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {

        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        int timesToTry = -1;

        FailingBioactiveEntityFetcher fetcher = new FailingBioactiveEntityFetcher(timesToTry);
        fetcher.addEntry(CHEBI_ID , persistentBioactiveEntity);
        enricher = new MinimalBioactiveEntityEnricher(fetcher);

        enricher.enrich(persistentBioactiveEntity);

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

        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        int timesToTry = 3;
        assertTrue("The test can not be applied as the conditions do not invoke the required response. " +
                "Change the timesToTry." ,
                timesToTry < 5);

        FailingBioactiveEntityFetcher fetcher = new FailingBioactiveEntityFetcher(timesToTry);
        fetcher.addEntry(CHEBI_ID , persistentBioactiveEntity);
        enricher = new MinimalBioactiveEntityEnricher(fetcher);

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(TEST_FULLNAME, persistentBioactiveEntity.getFullName() );
    }


    // == FAILURE ON NULL ======================================================================

    /**
     * Attempts to enrich a null CvTerm.
     * This should always cause an illegal argument exception
     * @throws EnricherException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTerm() throws EnricherException {
        BioactiveEntity nullBioactiveEntity = null;
        enricher.enrich(nullBioactiveEntity);
        fail("Exception should be thrown before this point");
    }

    /**
     * Attempts to enrich a legal cvTerm but with a null fetcher.
     * This should throw an illegal state exception.
     * @throws EnricherException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        BioactiveEntity bioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME, TEST_FULLNAME);
        bioactiveEntity.setChebi(CHEBI_ID);

        enricher = new MinimalBioactiveEntityEnricher(null);
    }


    @Test
    public void test_enrichment_completes_as_failed_when_no_entry_fetched() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        fetcher.clearEntries();
        enricher.setBioactiveEntityEnricherListener( new BioactiveEntityEnricherListenerManager(
                new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));
        enricher.enrich(persistentBioactiveEntity);
        assertEquals(1, persistentInt);
    }



    // == FULL NAME ==============================================================================================

    @Test
    public void test_enriching_fullName_from_null_with_entry() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setFullName(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {
                        assertTrue(interactor == persistentBioactiveEntity);
                        assertNull(oldFullName);
                        persistentInt ++;
                    }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(TEST_STRING, persistentBioactiveEntity.getFullName());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_no_enriching_fullName_from_null_with_null() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setFullName(null);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertNull(persistentBioactiveEntity.getFullName());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_no_enriching_of_not_null_fullName() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);
        persistentBioactiveEntity.setFullName(OTHER_TEST_STRING);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setFullName(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(OTHER_TEST_STRING , persistentBioactiveEntity.getFullName());
        assertEquals(1 , persistentInt);
    }


    // == INCHI Key ================================================================================================
    @Test
    public void test_enriching_inchiKey_from_null_with_entry() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setStandardInchiKey(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey){
                        assertTrue(bioactiveEntity == persistentBioactiveEntity);
                        assertNull(oldKey);
                        persistentInt ++;
                    }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {fail("failed");}
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(TEST_STRING, persistentBioactiveEntity.getStandardInchiKey());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_no_enriching_inchiKey_from_null_with_null() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setStandardInchiKey(null);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertNull(persistentBioactiveEntity.getStandardInchiKey());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_no_enriching_of_not_null_inchiKey() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);
        persistentBioactiveEntity.setStandardInchiKey(OTHER_TEST_STRING);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setStandardInchiKey(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(OTHER_TEST_STRING , persistentBioactiveEntity.getStandardInchiKey());
        assertEquals(1 , persistentInt);
    }


    // == INCHI ================================================================================================
    @Test
    public void test_enriching_inchi_from_null_with_entry() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setStandardInchi(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){
                        assertTrue(bioactiveEntity == persistentBioactiveEntity);
                        assertNull(oldInchi);
                        persistentInt ++;
                    }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {fail("failed");}
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(TEST_STRING, persistentBioactiveEntity.getStandardInchi());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_no_enriching_inchi_from_null_with_null() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setStandardInchi(null);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertNull(persistentBioactiveEntity.getStandardInchi());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_no_enriching_of_not_null_inchi() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);
        persistentBioactiveEntity.setStandardInchi(OTHER_TEST_STRING);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setStandardInchi(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(OTHER_TEST_STRING , persistentBioactiveEntity.getStandardInchi());
        assertEquals(1 , persistentInt);
    }


    // == SMILE ===================================================================================================

    @Test
    public void test_enriching_smile_from_null_with_entry() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setSmile(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) {
                        assertTrue(bioactiveEntity == persistentBioactiveEntity);
                        assertNull(oldSmile);
                        persistentInt ++;
                    }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed");}
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {fail("failed");}
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(TEST_STRING, persistentBioactiveEntity.getSmile());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_no_enriching_smile_from_null_with_null() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setSmile(null);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertNull(persistentBioactiveEntity.getSmile());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_no_enriching_of_not_null_smile() throws EnricherException {
        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);
        persistentBioactiveEntity.setSmile(OTHER_TEST_STRING);

        BioactiveEntity mockBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME);
        mockBioactiveEntity.setChebi(CHEBI_ID);
        mockBioactiveEntity.setSmile(TEST_STRING);

        fetcher.addEntry(CHEBI_ID , mockBioactiveEntity);

        enricher.setBioactiveEntityEnricherListener(new BioactiveEntityEnricherListenerManager(
                // new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        enricher.enrich(persistentBioactiveEntity);

        assertEquals(OTHER_TEST_STRING , persistentBioactiveEntity.getSmile());
        assertEquals(1 , persistentInt);
    }

}
