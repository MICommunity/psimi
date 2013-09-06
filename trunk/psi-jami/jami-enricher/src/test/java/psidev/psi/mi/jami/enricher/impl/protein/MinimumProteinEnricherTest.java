package psidev.psi.mi.jami.enricher.impl.protein;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.FailingProteinFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockProteinFetcher;
import psidev.psi.mi.jami.bridges.mapper.mock.MockProteinMapper;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherLogger;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Unit tests for MinimumProteinEnricher
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  23/05/13
 */

public class MinimumProteinEnricherTest {

    private MinimumProteinEnricher proteinEnricher;
    private MockProteinFetcher mockProteinFetcher;

    Protein persistentProtein;
    int persistentInt;

    private static final String TEST_OLD_SHORTNAME = "test old shortName";
    private static final String TEST_OLD_FULLNAME = "test old fullName";
    private static final String TEST_OLD_SEQUENCE = "CATCATCAT";
    private static final String TEST_ORGANISM_OLD_COMMON = "Old Common";
    private static final String TEST_ORGANISM_OLD_SCIENTIFIC = "Old Scientific";
    private static final String TEST_AC_DEAD_PROT = "X000000";

    private static final String TEST_AC_CUSTOM_PROT = "C098765";

    private static final String TEST_SHORTNAME = "test shortName";
    private static final String TEST_FULLNAME = "test fullName";
    private static final String TEST_AC_FULL_PROT = "P12345";
    private static final String TEST_AC_HALF_PROT = "P11111";
    private static final String TEST_SEQUENCE = "GATTACA";
    private static final int TEST_ORGANISM_ID = 1234;
    private static final String TEST_ORGANISM_COMMON = "Common";
    private static final String TEST_ORGANISM_SCIENTIFIC = "Scientific";

    @Before
    public void initialiseFetcherAndEnricher(){
        mockProteinFetcher = new MockProteinFetcher();
        proteinEnricher = new MinimumProteinEnricher(mockProteinFetcher);

        Protein fullProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        fullProtein.setUniprotkb(TEST_AC_FULL_PROT);
        fullProtein.setSequence(TEST_SEQUENCE);
        fullProtein.setOrganism(new DefaultOrganism(TEST_ORGANISM_ID, TEST_ORGANISM_COMMON, TEST_ORGANISM_SCIENTIFIC));
        Collection<Protein> fullProteinList = new ArrayList<Protein>();
        fullProteinList.add(fullProtein);
        mockProteinFetcher.addEntry(TEST_AC_FULL_PROT, fullProteinList);

        Protein halfProtein = new DefaultProtein(TEST_SHORTNAME);
        halfProtein.setUniprotkb(TEST_AC_HALF_PROT);
        Collection<Protein> halfProteinList = new ArrayList<Protein>();
        halfProteinList.add(halfProtein);
        mockProteinFetcher.addEntry(TEST_AC_HALF_PROT, halfProteinList);

        persistentProtein = null;
        persistentInt = 0;
    }


    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {

        FailingProteinFetcher fetcher = new FailingProteinFetcher(-1);
        Protein proteinToEnrich = new DefaultProtein(TEST_SHORTNAME);
        proteinToEnrich.setUniprotkb(TEST_AC_HALF_PROT);

        Protein proteinFetched = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        proteinFetched.setUniprotkb(TEST_AC_HALF_PROT);
        fetcher.addEntry(TEST_AC_HALF_PROT , proteinFetched);
        proteinEnricher.setProteinFetcher(fetcher);

        proteinEnricher.enrichProtein(proteinToEnrich);

        fail("Exception should be thrown before this point");
    }

    @Test
    public void test_bridgeFailure_does_not_throw_exception_when_not_persistent() throws EnricherException {
        int timesToTry = 3;
        assertTrue(timesToTry < MinimumProteinEnricher.RETRY_COUNT);

        FailingProteinFetcher fetcher = new FailingProteinFetcher(timesToTry);

        Protein proteinToEnrich = new DefaultProtein(TEST_SHORTNAME);
        proteinToEnrich.setUniprotkb(TEST_AC_HALF_PROT);

        Protein proteinFetched = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        proteinFetched.setUniprotkb(TEST_AC_HALF_PROT);
        fetcher.addEntry(TEST_AC_HALF_PROT, proteinFetched);
        proteinEnricher.setProteinFetcher(fetcher);
        proteinEnricher.enrichProtein(proteinToEnrich);

        assertEquals(TEST_FULLNAME , proteinToEnrich.getFullName() );
    }


    // == FAILURE ON NULL ======================================================================

    /**
     * Attempts to enrich a legal cvTerm but with a null fetcher.
     * This should throw an illegal state exception.
     * @throws EnricherException
     */
    @Test(expected = IllegalStateException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        persistentProtein = new DefaultProtein("name" , "namename");
        proteinEnricher.setProteinFetcher(null);
        assertNull(proteinEnricher.getProteinFetcher());
        proteinEnricher.enrichProtein(persistentProtein);
        fail("Exception should be thrown before this point");
    }


    /**
     * Assert that when a null protein is provided, an exception is thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_exception_when_fetching_on_null_protein() throws EnricherException {
        Protein null_protein = null;
        this.proteinEnricher.enrichProtein(null_protein);
    }

    // =====================================================
    // REMAPPER CASES BEGIN

    @Test
    public void test_default_has_no_remapper(){
        assertNull(proteinEnricher.getProteinMapper());
    }

    @Test
    public void test_set_remapper_is_returned(){
        MockProteinMapper mockProteinMapper = new MockProteinMapper();
        assertNull(proteinEnricher.getProteinMapper());
        proteinEnricher.setProteinMapper(mockProteinMapper);
        assertTrue(mockProteinMapper == proteinEnricher.getProteinMapper());
    }

    // == NULL REMAPPER CASES =======================================================================================

    /**
     * Assert that when a protein has no identifier, and the remapper is not provided,
     * the protein is not enriched and that a "failed" status is sent to the listener.
     */
    @Test
    public void test_no_fetching_on_protein_with_null_identifier_when_remapper_is_null()
            throws EnricherException {

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherLogger());

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentInt = 0;

        assertNotNull(persistentProtein);
        assertNull(persistentProtein.getUniprotkb());
        assertNull(proteinEnricher.getProteinMapper());



        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
               // new ProteinEnricherLogger() ,   //Comment this line to silence logging
                new ProteinEnricherListener() {
            public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                assertTrue(protein == persistentProtein);
                assertEquals(EnrichmentStatus.FAILED , status);
                persistentInt++;
            }

            public void onProteinRemapped(Protein protein, String oldUniprot)  {fail();}
            public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail();}
            public void onRefseqUpdate(Protein protein, String oldRefseq) {fail();}
            public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail();}
            public void onRogidUpdate(Protein protein, String oldRogid)  {fail();}
            public void onSequenceUpdate(Protein protein, String oldSequence) {fail();}
            public void onShortNameUpdate(Protein protein, String oldShortName)  {fail();}
            public void onFullNameUpdate(Protein protein, String oldFullName) {fail();}
            public void onAddedInteractorType(Protein protein)  {fail();}
            public void onAddedOrganism(Protein protein)  {fail();}
            public void onAddedIdentifier(Protein protein, Xref added)  {fail();}
            public void onRemovedIdentifier(Protein protein, Xref removed)  {fail();}
            public void onAddedXref(Protein protein, Xref added)  {fail();}
            public void onRemovedXref(Protein protein, Xref removed) {fail();}
            public void onAddedAlias(Protein protein, Alias added)  {fail();}
            public void onRemovedAlias(Protein protein, Alias removed)  {fail();}
            public void onAddedChecksum(Protein protein, Checksum added)  {fail();}
            public void onRemovedChecksum(Protein protein, Checksum removed)  {fail();}
        }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_OLD_SHORTNAME, persistentProtein.getShortName());
        assertNull(persistentProtein.getUniprotkb());
        assertEquals(1, persistentInt);
    }

    /**
     * Assert that when a protein has a dead identifier, and the remapper is not provided,
     * the protein is not enriched and that a "failed" status is sent to the listener.
     */
    @Test
    public void test_no_fetching_on_protein_with_dead_identifier_when_remapper_is_null()
            throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentInt = 0;

        assertNotNull(persistentProtein);
        persistentProtein.setUniprotkb(TEST_AC_DEAD_PROT);
        assertNotNull(persistentProtein.getUniprotkb());
        assertNull(proteinEnricher.getProteinMapper());



        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
               // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail();}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail();}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail();}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail();}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail();}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail();}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Protein protein, String oldFullName) {fail();}
                    public void onAddedInteractorType(Protein protein)  {fail();}
                    public void onAddedOrganism(Protein protein)  {fail();}
                    public void onAddedIdentifier(Protein protein, Xref added)  {fail();}
                    public void onRemovedIdentifier(Protein protein, Xref removed)  {fail();}
                    public void onAddedXref(Protein protein, Xref added)  {fail();}
                    public void onRemovedXref(Protein protein, Xref removed) {fail();}
                    public void onAddedAlias(Protein protein, Alias added)  {fail();}
                    public void onRemovedAlias(Protein protein, Alias removed)  {fail();}
                    public void onAddedChecksum(Protein protein, Checksum added)  {fail();}
                    public void onRemovedChecksum(Protein protein, Checksum removed)  {fail();}
                }
        ));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_OLD_SHORTNAME, persistentProtein.getShortName());
        assertNull(persistentProtein.getUniprotkb());
        assertEquals(1, persistentInt);
    }

    // == PROVIDED REMAPPER CASES WHERE ENTRY IS NOT REMAPPED =======================================================

    /**
     * Check that when a protein has no identifier, and the remapper is provided but finds no entry,
     * the protein is not enriched and that a "failed" note is sent to the listener.
     */
    @Test
    public void test_fetching_on_protein_with_null_identifier_when_remapper_is_not_null_and_no_remap_is_found()
            throws EnricherException {


        MockProteinMapper mockProteinMapper = new MockProteinMapper();
        proteinEnricher.setProteinMapper(mockProteinMapper);

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentInt = 0;

        assertNotNull(persistentProtein);
        assertNull(persistentProtein.getUniprotkb());
        assertNotNull(proteinEnricher.getProteinMapper());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
              //  new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail();}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail();}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail();}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail();}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail();}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail();}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Protein protein, String oldFullName) {fail();}
                    public void onAddedInteractorType(Protein protein)  {fail();}
                    public void onAddedOrganism(Protein protein)  {fail();}
                    public void onAddedIdentifier(Protein protein, Xref added)  {fail();}
                    public void onRemovedIdentifier(Protein protein, Xref removed)  {fail();}
                    public void onAddedXref(Protein protein, Xref added)  {fail();}
                    public void onRemovedXref(Protein protein, Xref removed) {fail();}
                    public void onAddedAlias(Protein protein, Alias added)  {fail();}
                    public void onRemovedAlias(Protein protein, Alias removed)  {fail();}
                    public void onAddedChecksum(Protein protein, Checksum added)  {fail();}
                    public void onRemovedChecksum(Protein protein, Checksum removed)  {fail();}
                }
        ));

        this.proteinEnricher.enrichProtein(persistentProtein);


        assertEquals(TEST_OLD_SHORTNAME , persistentProtein.getShortName());
        assertNull(persistentProtein.getUniprotkb());
        assertEquals(1, persistentInt);
    }


    /**
     * Check that when a protein has a dead identifier, and the remapper is provided but finds no entry,
     * the protein is not enriched and that a "failed" note is sent to the listener.
     */
    @Test
    public void test_fetching_on_protein_with_dead_identifier_when_remapper_is_not_null_and_no_remap_is_found()
            throws EnricherException {

        MockProteinMapper mockProteinMapper = new MockProteinMapper();
        proteinEnricher.setProteinMapper(mockProteinMapper);

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentInt = 0;

        assertNotNull(persistentProtein);
        persistentProtein.setUniprotkb(TEST_AC_DEAD_PROT);
        assertNotNull(persistentProtein.getUniprotkb());
        assertNotNull(proteinEnricher.getProteinMapper());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
               // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail();}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail();}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail();}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail();}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail();}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail();}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Protein protein, String oldFullName) {fail();}
                    public void onAddedInteractorType(Protein protein)  {fail();}
                    public void onAddedOrganism(Protein protein)  {fail();}
                    public void onAddedIdentifier(Protein protein, Xref added)  {fail();}
                    public void onRemovedIdentifier(Protein protein, Xref removed)  {fail();}
                    public void onAddedXref(Protein protein, Xref added)  {fail();}
                    public void onRemovedXref(Protein protein, Xref removed) {fail();}
                    public void onAddedAlias(Protein protein, Alias added)  {fail();}
                    public void onRemovedAlias(Protein protein, Alias removed)  {fail();}
                    public void onAddedChecksum(Protein protein, Checksum added)  {fail();}
                    public void onRemovedChecksum(Protein protein, Checksum removed)  {fail();}
                }
        ));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_OLD_SHORTNAME, persistentProtein.getShortName());
        assertNull(persistentProtein.getUniprotkb());
        assertEquals(1, persistentInt);
    }

    // == PROVIDED REMAPPER CASES WHERE AN ENTRY IS REMAPPED AND FETCHED =============================================

    /**
     * Check that when a protein has no identifier, and the remapper is provided and finds an entry,
     * the protein is enriched and a "success" status is sent to the listener.
     */
    @Test
    public void test_fetching_on_protein_with_null_identifier_when_remapper_is_not_null_and_remap_is_found()
            throws EnricherException {


        MockProteinMapper mockProteinMapper = new MockProteinMapper();
        mockProteinMapper.addMappingResult(TEST_SEQUENCE, TEST_AC_HALF_PROT);
        proteinEnricher.setProteinMapper(mockProteinMapper);

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setSequence(TEST_SEQUENCE);

        assertNotNull(persistentProtein);
        assertNull(persistentProtein.getUniprotkb());
        assertNotNull(proteinEnricher.getProteinMapper());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
               // new ProteinEnricherLogger() , //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot) {
                        assertTrue(protein == persistentProtein);
                        assertNull(oldUniprot);
                        assertEquals(TEST_AC_HALF_PROT , protein.getUniprotkb());
                    }

                    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
                        assertTrue(protein == persistentProtein);
                        assertNull(oldUniprot);
                        assertEquals(TEST_AC_HALF_PROT , protein.getUniprotkb());
                    }

                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail();}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail();}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail();}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail();}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Protein protein, String oldFullName) {fail();}
                    public void onAddedInteractorType(Protein protein)  {fail();}
                    public void onAddedOrganism(Protein protein)  {fail();}
                    public void onAddedIdentifier(Protein protein, Xref added)  {fail();}
                    public void onRemovedIdentifier(Protein protein, Xref removed)  {fail();}
                    public void onAddedXref(Protein protein, Xref added)  {fail();}
                    public void onRemovedXref(Protein protein, Xref removed) {fail();}
                    public void onAddedAlias(Protein protein, Alias added)  {fail();}
                    public void onRemovedAlias(Protein protein, Alias removed)  {fail();}
                    public void onAddedChecksum(Protein protein, Checksum added)  {fail();}
                    public void onRemovedChecksum(Protein protein, Checksum removed)  {fail();}
                }
        ));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_OLD_SHORTNAME , persistentProtein.getShortName());
        assertNotNull(persistentProtein.getUniprotkb());
        assertEquals(TEST_OLD_SHORTNAME , persistentProtein.getShortName());
        assertEquals(TEST_AC_HALF_PROT , persistentProtein.getUniprotkb());
        assertEquals(1, persistentInt);
    }

    /**
     * Check that when a protein has a dead identifier, and the remapper is provided and finds an entry,
     * the protein is enriched and a "success" status is sent to the listener.
     */
    @Test
    public void test_fetching_on_protein_with_dead_identifier_when_remapper_is_not_null_and_remap_is_found()
            throws EnricherException {

        MockProteinMapper mockProteinMapper = new MockProteinMapper();
        mockProteinMapper.addMappingResult(TEST_SEQUENCE, TEST_AC_HALF_PROT);
        proteinEnricher.setProteinMapper(mockProteinMapper);

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_DEAD_PROT);
        persistentProtein.setSequence(TEST_SEQUENCE);

        assertNotNull(persistentProtein);
        assertNotNull(persistentProtein.getUniprotkb());
        assertNotNull(proteinEnricher.getProteinMapper());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
               // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot) {
                        assertTrue(protein == persistentProtein);
                        assertNotNull(oldUniprot);
                        assertEquals(TEST_AC_DEAD_PROT , oldUniprot);
                        assertEquals(TEST_AC_HALF_PROT , protein.getUniprotkb());
                    }

                    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
                        assertTrue(protein == persistentProtein);
                        assertNotNull(oldUniprot);
                        assertEquals(TEST_AC_DEAD_PROT, oldUniprot);
                        assertEquals(TEST_AC_HALF_PROT, protein.getUniprotkb());
                    }

                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail();}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail();}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail();}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail();}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Protein protein, String oldFullName) {fail();}
                    public void onAddedInteractorType(Protein protein)  {fail();}
                    public void onAddedOrganism(Protein protein)  {fail();}
                    public void onAddedIdentifier(Protein protein, Xref added)  {fail();}
                    public void onRemovedIdentifier(Protein protein, Xref removed)  {fail();}
                    public void onAddedXref(Protein protein, Xref added)  {fail();}
                    public void onRemovedXref(Protein protein, Xref removed) {fail();}
                    public void onAddedAlias(Protein protein, Alias added)  {fail();}
                    public void onRemovedAlias(Protein protein, Alias removed)  {fail();}
                    public void onAddedChecksum(Protein protein, Checksum added)  {fail();}
                    public void onRemovedChecksum(Protein protein, Checksum removed)  {fail();}
                }
        ));

        this.proteinEnricher.enrichProtein(persistentProtein);
        assertEquals(TEST_OLD_SHORTNAME , persistentProtein.getShortName());
        assertNotNull(persistentProtein.getUniprotkb());
        assertEquals(TEST_AC_HALF_PROT , persistentProtein.getUniprotkb());
        assertEquals(1, persistentInt);
    }

    // =====================================================
    // ENRICHING CASES BEGIN

    /**
     * Assert that when a protein has a known interactor type other than protein,
     * the enrichment fails and no changes are made.
     */
    @Test
    public void test_interactorType_conflict_stops_enrichment() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME, TEST_OLD_FULLNAME );
        persistentProtein.setUniprotkb(TEST_AC_HALF_PROT);
        persistentProtein.setInteractorType(CvTermUtils.createGeneInteractorType());

        assertEquals(Gene.GENE,
                persistentProtein.getInteractorType().getShortName());
        assertEquals(Gene.GENE_MI,
                persistentProtein.getInteractorType().getMIIdentifier());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED, status);
                        assertTrue(message.toUpperCase().contains("INTERACTOR"));
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein) {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        proteinEnricher.enrichProtein(persistentProtein);
        assertEquals(Gene.GENE,
                persistentProtein.getInteractorType().getShortName());
        assertEquals(Gene.GENE_MI,
                persistentProtein.getInteractorType().getMIIdentifier());
        assertEquals(1 , persistentInt);
    }

    /**
     * Assert that when a protein has a known interactor type other than protein,
     * the enrichment fails and no changes are made.
     */
    @Test
    public void test_organism_conflict_between_organism_TAXIDs_stops_enrichment() throws EnricherException {
        Protein customProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.setOrganism(new DefaultOrganism(9898));
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME, TEST_OLD_FULLNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.setInteractorType(CvTermUtils.createGeneInteractorType());
        persistentProtein.setOrganism(new DefaultOrganism(1010));
        persistentProtein.setInteractorType(CvTermUtils.createGeneInteractorType());
        assertEquals(Gene.GENE,
                persistentProtein.getInteractorType().getShortName());
        assertEquals(Gene.GENE_MI,
                persistentProtein.getInteractorType().getMIIdentifier());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED, status);
                        assertTrue(message.toUpperCase().contains("ORGANISM"));
                        assertTrue(message.toUpperCase().contains("INTERACTOR"));
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein) {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(1 , persistentInt);
    }

    /**
     * Assert that when a protein has a known interactor type other than protein,
     * the enrichment fails and no changes are made.
     */
    @Test
    public void test_organism_and_interactorType_conflicts_both_reported() throws EnricherException {
        Protein customProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.setOrganism(new DefaultOrganism(9898));
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME, TEST_OLD_FULLNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.setInteractorType(CvTermUtils.createGeneInteractorType());
        persistentProtein.setOrganism(new DefaultOrganism(1010));


        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        assertTrue(message.toUpperCase().contains("ORGANISM"));
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein) {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(1 , persistentInt);
    }

    // == INTERACTOR TYPE =======================================================================

    /**
     * Assert that when a protein already has a protein interactor type,
     * no changes are made and enrichment is successful.
     */
    @Test
    public void test_interactorType_ignored_if_is_already_protein() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME, TEST_OLD_FULLNAME );
        persistentProtein.setUniprotkb(TEST_AC_HALF_PROT);

        CvTerm value = CvTermUtils.createProteinInteractorType();
        persistentProtein.setInteractorType(value);
        assertEquals(Protein.PROTEIN,
                persistentProtein.getInteractorType().getShortName());
        assertEquals(Protein.PROTEIN_MI,
                persistentProtein.getInteractorType().getMIIdentifier());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
               // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));


        proteinEnricher.enrichProtein(persistentProtein);

        assertTrue(persistentProtein.getInteractorType() == value); //Show they are the same instance
        assertEquals(Protein.PROTEIN,
                persistentProtein.getInteractorType().getShortName());
        assertEquals(Protein.PROTEIN_MI,
                persistentProtein.getInteractorType().getMIIdentifier());

        assertEquals(1 , persistentInt);
    }


    /**
     * Assert that when a protein has an unknown interactor type,
     * the type is updated to protein and the enrichment is successful.
     */
    @Test
    public void test_interactorType_updated_if_unknown() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME, TEST_OLD_FULLNAME );
        persistentProtein.setUniprotkb(TEST_AC_HALF_PROT);
        persistentProtein.setInteractorType(CvTermUtils.createUnknownInteractorType());

        assertEquals(Protein.UNKNOWN_INTERACTOR_MI,
                persistentProtein.getInteractorType().getMIIdentifier());
        assertEquals(Protein.UNKNOWN_INTERACTOR,
                persistentProtein.getInteractorType().getShortName());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}

                    public void onAddedInteractorType(Protein protein)  {
                        assertTrue(protein == persistentProtein);
                    }

                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(Protein.PROTEIN,
                persistentProtein.getInteractorType().getShortName());
        assertEquals(Protein.PROTEIN_MI,
                persistentProtein.getInteractorType().getMIIdentifier());
        assertEquals(1 , persistentInt);
    }

    // == SHORT NAME ===================================================================================

    /**
     * Enrich a protein that has a full name.
     * Check the full name has not been added
     */
    @Test
    public void test_shortName_not_enriched_if_not_null() throws EnricherException {
        persistentInt = 0;
        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertNotNull(persistentProtein.getShortName());
        assertEquals(TEST_OLD_SHORTNAME , persistentProtein.getShortName());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_OLD_SHORTNAME , persistentProtein.getShortName());
        assertEquals(1 , persistentInt);
    }

    // == FULL NAME ===================================================================================

    /**
     * Enrich a protein that has no full name.
     * Check the full name has been added
     */
    @Test
    public void test_fullName_enriched_if_null() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertNull(persistentProtein.getFullName());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
               // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}

                    public void onFullNameUpdate(Protein protein, String oldFullName)  {
                        assertTrue(protein == persistentProtein);
                        assertNull(oldFullName);
                        assertEquals(TEST_FULLNAME , protein.getFullName());
                    }

                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_FULLNAME , persistentProtein.getFullName());
        assertEquals(1 , persistentInt);
    }

    /**
     * Enrich a protein that has a full name.
     * Check the full name has not been added
     */
    @Test
    public void test_fullName_not_enriched_if_not_null() throws EnricherException {
        persistentInt = 0;

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME , TEST_OLD_FULLNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertNotNull(persistentProtein.getFullName());
        assertEquals(TEST_OLD_FULLNAME , persistentProtein.getFullName());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}

                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}

                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_OLD_FULLNAME , persistentProtein.getFullName());
        assertEquals(1 , persistentInt);
    }

    // == SEQUENCE ===================================================================================

    /**
     * Enrich a protein that has no sequence.
     * Check the sequence has been added
     */
    @Test
    public void test_sequence_enriched_if_null() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setSequence(TEST_SEQUENCE);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(TEST_SEQUENCE , customProtein.getSequence());
        assertNull(persistentProtein.getSequence());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}

                    public void onSequenceUpdate(Protein protein, String oldSequence) {
                        assertTrue(protein == persistentProtein);
                        assertNull(oldSequence);
                        assertEquals(TEST_SEQUENCE , protein.getSequence());
                    }

                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_SEQUENCE , persistentProtein.getSequence());
        assertEquals(1 , persistentInt);
    }

    /**
     * Enrich a protein that has a sequence
     * Check the sequence has not been added
     */
    @Test
    public void test_sequence_not_enriched_if_not_null() throws EnricherException {
        persistentInt = 0;

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.setSequence(TEST_OLD_SEQUENCE);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setSequence(TEST_SEQUENCE);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(TEST_SEQUENCE , customProtein.getSequence());
        assertNotNull(persistentProtein.getSequence());
        assertEquals(TEST_OLD_SEQUENCE , persistentProtein.getSequence());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid)  {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence) {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(TEST_OLD_SEQUENCE , persistentProtein.getSequence());
        assertEquals(1 , persistentInt);
    }

    // == IDENTIFIERS ===================================================================================

    /**
     * Enrich a protein that has no sequence.
     * Check the sequence has been added
     */
    @Test
    public void test_identifiers_enriched() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.getIdentifiers().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN000"));

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.getIdentifiers().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN999"));

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);


        assertEquals(2 , persistentProtein.getIdentifiers().size());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }
                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid) {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)  {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}

                    public void onAddedIdentifier(Protein protein, Xref added) {
                        assertTrue(protein == persistentProtein);
                        assertNotNull(added);
                        assertNotNull(added.getId());
                        assertEquals( "EN999" , added.getId());
                    }

                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added){fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(3 , persistentProtein.getIdentifiers().size());

        boolean originalXref = false;
        boolean newXref = false;
        boolean idXref = false;
        for(Xref xref : persistentProtein.getIdentifiers()){
            if(xref.getId().equals("EN000"))
                if(originalXref) fail("multiples of the original id");
                else originalXref=true;
            else if(xref.getId().equals("EN999"))
                if(newXref) fail("multiples of the new id");
                else newXref=true;
            else if(xref.getId().equals(TEST_AC_CUSTOM_PROT))
                if(idXref) fail("multiples of the uniprot id");
                else idXref=true;
            else
                fail(xref+"unrecognised alias");
        }

        assertTrue(originalXref);
        assertTrue(newXref);
        assertTrue(idXref);

        assertEquals(1 , persistentInt);
    }

    // == ALIASES ===================================================================================

    /**
     * Enrich a protein that has no sequence.
     * Check the sequence has been added
     */
    @Test
    public void test_aliases_enriched() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.getAliases().add(new DefaultAlias(CvTermUtils.createEnsemblDatabase() , "EN000"));

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.getAliases().add(new DefaultAlias(CvTermUtils.createEnsemblDatabase() , "EN999"));

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(1 , persistentProtein.getAliases().size());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }
                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid) {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)  {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}

                    public void onAddedAlias(Protein protein, Alias added){
                        assertTrue(protein == persistentProtein);
                        assertNotNull(added);
                        assertNotNull(added.getName());
                        assertEquals( "EN999" , added.getName());
                    }

                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(2 , persistentProtein.getAliases().size());

        boolean originalAlias = false;
        boolean newAlias = false;
        for(Alias alias : persistentProtein.getAliases()){
            if(alias.getName().equals("EN000"))
                if(originalAlias) fail("multiples of the original alias");
                else originalAlias=true;
            else if(alias.getName().equals("EN999"))
                if(newAlias) fail("multiples of the new alias");
                else newAlias=true;
            else
                fail("unrecognised alias");
        }

        assertTrue(originalAlias);
        assertTrue(newAlias);

        assertEquals(1 , persistentInt);
    }

    // == CHECKSUMS ===================================================================================
    //TODO - checksums logic is still lacking, check logic carefully before testing!!


    // == XREFS ===================================================================================

    /**
     * Enrich a protein that has no sequence.
     * Check the sequence has been added
     */
    @Test
    public void test_xrefs_enriched() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.getXrefs().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN000"));

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.getXrefs().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN999"));

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(1 , persistentProtein.getXrefs().size());

        proteinEnricher.setProteinEnricherListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt++;
                    }
                    public void onProteinRemapped(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {fail("Should not reach this point");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq) {fail("Should not reach this point");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName) {fail("Should not reach this point");}
                    public void onRogidUpdate(Protein protein, String oldRogid) {fail("Should not reach this point");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)  {fail("Should not reach this point");}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("Should not reach this point");}
                    public void onAddedInteractorType(Protein protein)  {fail("Should not reach this point");}
                    public void onAddedOrganism(Protein protein) {fail("Should not reach this point");}
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added){fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                }));

        this.proteinEnricher.enrichProtein(persistentProtein);

        assertEquals(1 , persistentProtein.getXrefs().size());

        boolean originalXref = false;
        for(Xref xref : persistentProtein.getXrefs()){
            if(xref.getId().equals("EN000"))
                if(originalXref) fail("multiples of the original xref");
                else originalXref=true;
            else
                fail(xref+"unrecognised alias");
        }

        assertTrue(originalXref);

        assertEquals(1 , persistentInt);
    }




    // == ORGANISM ===================================================================================

    @Test
    public void test_set_organism_if_null() throws EnricherException {
        Protein protein_without_organism = new DefaultProtein(TEST_SHORTNAME);
        protein_without_organism.setUniprotkb(TEST_AC_HALF_PROT);
        assertNull(protein_without_organism.getOrganism());

        this.proteinEnricher.enrichProtein(protein_without_organism);

        assertNotNull(protein_without_organism.getOrganism());
        assertEquals(-3 , protein_without_organism.getOrganism().getTaxId());
    }

    // TODO  onRefseqUpdate, onGeneNameUpdate


}
