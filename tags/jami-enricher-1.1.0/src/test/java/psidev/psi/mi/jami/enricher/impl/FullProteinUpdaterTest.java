package psidev.psi.mi.jami.enricher.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.FailingProteinFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockProteinFetcher;
import psidev.psi.mi.jami.bridges.mapper.mock.MockProteinMapper;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.full.FullProteinUpdater;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.ProteinEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.impl.log.ProteinEnricherLogger;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  23/05/13
 */
public class FullProteinUpdaterTest {


    private FullProteinUpdater proteinEnricher;
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
        proteinEnricher = new FullProteinUpdater(mockProteinFetcher);

        Protein fullProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        fullProtein.setUniprotkb(TEST_AC_FULL_PROT);
        fullProtein.setSequence(TEST_SEQUENCE);
        fullProtein.setOrganism(new DefaultOrganism(TEST_ORGANISM_ID, TEST_ORGANISM_COMMON, TEST_ORGANISM_SCIENTIFIC));
        Collection<Protein> fullProteinList = new ArrayList<Protein>();
        fullProteinList.add(fullProtein);
        mockProteinFetcher.addEntry(TEST_AC_FULL_PROT, fullProteinList);

        
        Protein halfProtein = new DefaultProtein(TEST_SHORTNAME);
        halfProtein.setUniprotkb(TEST_AC_HALF_PROT);
        // halfProtein.setOrganism(new DefaultOrganism(-3));
        Collection<Protein> halfProteinList = new ArrayList<Protein>();
        halfProteinList.add(halfProtein);
        mockProteinFetcher.addEntry(TEST_AC_HALF_PROT, halfProteinList);

        persistentProtein = null;
        persistentInt = 0;
    }


    @Test
    public void test_broken_isoform() throws EnricherException {
        Protein broken = new DefaultProtein("MUC1");
        broken.setUniprotkb("p15941-7");
        Collection<Protein> halfProteinList = new ArrayList<Protein>();
        halfProteinList.add(broken);
        mockProteinFetcher.addEntry("p15941-7", halfProteinList);

        proteinEnricher.setListener(new ProteinEnricherLogger());

        Protein testProt = new DefaultProtein("p15941-7");
        testProt.setUniprotkb("p15941-7");
        proteinEnricher.enrich(testProt);


    }

    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {

        FailingProteinFetcher fetcher = new FailingProteinFetcher(-1);
        Protein proteinToEnrich = new DefaultProtein(TEST_SHORTNAME);
        proteinToEnrich.setUniprotkb(TEST_AC_HALF_PROT);

        Protein proteinFetched = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        proteinFetched.setUniprotkb(TEST_AC_HALF_PROT);
        fetcher.addEntry(TEST_AC_HALF_PROT , proteinFetched);
        proteinEnricher = new FullProteinUpdater(fetcher);
        proteinEnricher.enrich(proteinToEnrich);
        fail("Exception should be thrown before this point");
    }

    @Test
    public void test_bridgeFailure_does_not_throw_exception_when_not_persistent() throws EnricherException {
        int timesToTry = 3;
        assertTrue(timesToTry < proteinEnricher.getRetryCount());

        FailingProteinFetcher fetcher = new FailingProteinFetcher(timesToTry);

        Protein proteinToEnrich = new DefaultProtein(TEST_SHORTNAME);
        proteinToEnrich.setUniprotkb(TEST_AC_HALF_PROT);

        Protein proteinFetched = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        proteinFetched.setUniprotkb(TEST_AC_HALF_PROT);
        fetcher.addEntry(TEST_AC_HALF_PROT, proteinFetched);
        proteinEnricher = new FullProteinUpdater(fetcher);
        proteinEnricher.enrich(proteinToEnrich);

        assertEquals(TEST_FULLNAME , proteinToEnrich.getFullName() );
    }


    /**
     * Assert that when a null protein is provided, an exception is thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_exception_when_fetching_on_null_protein() throws EnricherException {
        Protein null_protein = null;
        this.proteinEnricher.enrich(null_protein);
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

        proteinEnricher.setListener(new ProteinEnricherLogger());

        persistentProtein = new DefaultProtein(TEST_OLD_SHORTNAME);
        persistentInt = 0;

        assertNotNull(persistentProtein);
        assertNull(persistentProtein.getUniprotkb());
        assertNull(proteinEnricher.getProteinMapper());



        proteinEnricher.setListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,   //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt++;
                    }
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onProteinRemapped(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq)       {fail("failed");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName)   {fail("failed");}
                    public void onRogidUpdate(Protein protein, String oldRogid)         {fail("failed");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)   {fail("failed");}
                    public void onShortNameUpdate(Protein protein, String oldShortName) {fail("failed");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("failed");}
                    public void onAddedIdentifier(Protein protein, Xref added)          {fail("failed");}
                    public void onRemovedIdentifier(Protein protein, Xref removed)      {fail("failed");}
                    public void onAddedXref(Protein protein, Xref added)                {fail("failed");}
                    public void onRemovedXref(Protein protein, Xref removed)            {fail("failed");}
                    public void onAddedAlias(Protein protein, Alias added)              {fail("failed");}
                    public void onRemovedAlias(Protein protein, Alias removed)          {fail("failed");}
                    public void onAddedChecksum(Protein protein, Checksum added)        {fail("failed");}
                    public void onRemovedChecksum(Protein protein, Checksum removed)    {fail("failed");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

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



        proteinEnricher.setListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq)       {fail("failed");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName)   {fail("failed");}
                    public void onRogidUpdate(Protein protein, String oldRogid)         {fail("failed");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)   {fail("failed");}
                    public void onShortNameUpdate(Protein protein, String oldShortName) {fail("failed");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("failed");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added)          {fail("failed");}
                    public void onRemovedIdentifier(Protein protein, Xref removed)      {}
                    public void onAddedXref(Protein protein, Xref added)                {}
                    public void onRemovedXref(Protein protein, Xref removed)            {}
                    public void onAddedAlias(Protein protein, Alias added)              {fail("failed");}
                    public void onRemovedAlias(Protein protein, Alias removed)          {fail("failed");}
                    public void onAddedChecksum(Protein protein, Checksum added)        {fail("failed");}
                    public void onRemovedChecksum(Protein protein, Checksum removed)    {fail("failed");}
                    public void onAddedAnnotation(Protein o, Annotation added) {

                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

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

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
                //  new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq)       {fail("failed");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName)   {fail("failed");}
                    public void onRogidUpdate(Protein protein, String oldRogid)         {fail("failed");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)   {fail("failed");}
                    public void onShortNameUpdate(Protein protein, String oldShortName) {fail("failed");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("failed");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added)          {fail("failed");}
                    public void onRemovedIdentifier(Protein protein, Xref removed)      {fail("failed");}
                    public void onAddedXref(Protein protein, Xref added)                {fail("failed");}
                    public void onRemovedXref(Protein protein, Xref removed)            {fail("failed");}
                    public void onAddedAlias(Protein protein, Alias added)              {fail("failed");}
                    public void onRemovedAlias(Protein protein, Alias removed)          {fail("failed");}
                    public void onAddedChecksum(Protein protein, Checksum added)        {fail("failed");}
                    public void onRemovedChecksum(Protein protein, Checksum removed)    {fail("failed");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);


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

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
                // new ProteinEnricherLogger() ,  //Comment this line to silence logging
                new ProteinEnricherListener() {
                    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
                        assertTrue(protein == persistentProtein);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt++;
                    }

                    public void onProteinRemapped(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onUniprotKbUpdate(Protein protein, String oldUniprot)   {fail("failed");}
                    public void onRefseqUpdate(Protein protein, String oldRefseq)       {fail("failed");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName)   {fail("failed");}
                    public void onRogidUpdate(Protein protein, String oldRogid)         {fail("failed");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)   {fail("failed");}
                    public void onShortNameUpdate(Protein protein, String oldShortName) {fail("failed");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("failed");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("Should not reach this point");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("Should not reach this point");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added)          {fail("failed");}
                    public void onRemovedIdentifier(Protein protein, Xref removed)      {}
                    public void onAddedXref(Protein protein, Xref added)                {}
                    public void onRemovedXref(Protein protein, Xref removed)            {}
                    public void onAddedAlias(Protein protein, Alias added)              {fail("failed");}
                    public void onRemovedAlias(Protein protein, Alias removed)          {fail("failed");}
                    public void onAddedChecksum(Protein protein, Checksum added)        {fail("failed");}
                    public void onRemovedChecksum(Protein protein, Checksum removed)    {fail("failed");}
                    public void onAddedAnnotation(Protein o, Annotation added) {

                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

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
        mockProteinMapper.addMappingResult(TEST_SEQUENCE , TEST_AC_HALF_PROT);
        proteinEnricher.setProteinMapper(mockProteinMapper);

        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setSequence(TEST_SEQUENCE);

        assertNotNull(persistentProtein);
        assertNull(persistentProtein.getUniprotkb());
        assertNotNull(proteinEnricher.getProteinMapper());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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

                    public void onRefseqUpdate(Protein protein, String oldRefseq)       {fail("failed");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName)   {fail("failed");}
                    public void onRogidUpdate(Protein protein, String oldRogid)         {fail("failed");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)   {}
                    public void onShortNameUpdate(Protein protein, String oldShortName) {fail("failed");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("failed");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("Should not reach this point");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("Should not reach this point");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added)          {fail("failed");}
                    public void onRemovedIdentifier(Protein protein, Xref removed)      {fail("failed");}
                    public void onAddedXref(Protein protein, Xref added)                {fail("failed");}
                    public void onRemovedXref(Protein protein, Xref removed)            {fail("failed");}
                    public void onAddedAlias(Protein protein, Alias added)              {fail("failed");}
                    public void onRemovedAlias(Protein protein, Alias removed)          {fail("failed");}
                    public void onAddedChecksum(Protein protein, Checksum added)        {fail("failed");}
                    public void onRemovedChecksum(Protein protein, Checksum removed)    {fail("failed");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_SHORTNAME , persistentProtein.getShortName());
        assertNotNull(persistentProtein.getUniprotkb());
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
        mockProteinMapper.addMappingResult(TEST_SEQUENCE , TEST_AC_HALF_PROT);
        proteinEnricher.setProteinMapper(mockProteinMapper);

        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_DEAD_PROT);
        persistentProtein.setSequence(TEST_SEQUENCE);

        assertNotNull(persistentProtein);
        assertNotNull(persistentProtein.getUniprotkb());
        assertNotNull(proteinEnricher.getProteinMapper());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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

                    public void onRefseqUpdate(Protein protein, String oldRefseq)       {fail("failed");}
                    public void onGeneNameUpdate(Protein protein, String oldGeneName)   {fail("failed");}
                    public void onRogidUpdate(Protein protein, String oldRogid)         {fail("failed");}
                    public void onSequenceUpdate(Protein protein, String oldSequence)   {}
                    public void onShortNameUpdate(Protein protein, String oldShortName) {fail("failed");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("failed");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("Should not reach this point");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("Should not reach this point");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added)          {fail("failed");}
                    public void onRemovedIdentifier(Protein protein, Xref removed)      {}
                    public void onAddedXref(Protein protein, Xref added)                {}
                    public void onRemovedXref(Protein protein, Xref removed)            {}
                    public void onAddedAlias(Protein protein, Alias added)              {fail("failed");}
                    public void onRemovedAlias(Protein protein, Alias removed)          {fail("failed");}
                    public void onAddedChecksum(Protein protein, Checksum added)        {fail("failed");}
                    public void onRemovedChecksum(Protein protein, Checksum removed)    {fail("failed");}
                    public void onAddedAnnotation(Protein o, Annotation added) {

                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);
        assertEquals(TEST_SHORTNAME , persistentProtein.getShortName());
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

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("Should not reach this point");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("Should not reach this point");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        proteinEnricher.enrich(persistentProtein);
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

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("Should not reach this point");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("Should not reach this point");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        proteinEnricher.enrich(persistentProtein);

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


        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("Should not reach this point");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("Should not reach this point");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        proteinEnricher.enrich(persistentProtein);

        assertEquals(1 , persistentInt);
    }

    // == INTERACTOR TYPE =======================================================================

    /**
     * Assert that when a protein already has a protein interactor type,
     * no changes are made and enrichment is successful.
     */
    @Test
    public void test_interactorType_ignored_if_is_already_protein() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        persistentProtein.setUniprotkb(TEST_AC_HALF_PROT);

        CvTerm value = CvTermUtils.createProteinInteractorType();
        persistentProtein.setInteractorType(value);
        assertEquals(Protein.PROTEIN,
                persistentProtein.getInteractorType().getShortName());
        assertEquals(Protein.PROTEIN_MI,
                persistentProtein.getInteractorType().getMIIdentifier());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {}

                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("Should not reach this point");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("Should not reach this point");
                    }

                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));


        proteinEnricher.enrich(persistentProtein);

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

        persistentProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        persistentProtein.setUniprotkb(TEST_AC_HALF_PROT);
        persistentProtein.setInteractorType(CvTermUtils.createUnknownInteractorType());

        assertEquals(Protein.UNKNOWN_INTERACTOR_MI,
                persistentProtein.getInteractorType().getMIIdentifier());
        assertEquals(Protein.UNKNOWN_INTERACTOR,
                persistentProtein.getInteractorType().getShortName());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {}

                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        assertTrue(interactor == persistentProtein);
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        proteinEnricher.enrich(persistentProtein);

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
    public void test_shortName_enriched_if_fetched_field_not_null() throws EnricherException {
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

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {
                        assertTrue(protein == persistentProtein);
                        assertEquals( TEST_OLD_SHORTNAME , oldShortName );
                        persistentInt++;
                    }
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_SHORTNAME , persistentProtein.getShortName());
        assertEquals(2 , persistentInt);
    }

    /**
     * Enrich a protein that has a full name.
     * Check the full name has not been added
     */
    @Test
    public void test_shortName_not_enriched_if_fetched_field_same() throws EnricherException {
        persistentInt = 0;
        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertNotNull(persistentProtein.getShortName());
        assertEquals(TEST_SHORTNAME , persistentProtein.getShortName());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_SHORTNAME , persistentProtein.getShortName());
        assertEquals(1 , persistentInt);
    }


    // == FULL NAME ===================================================================================

    @Test
    public void test_null_fullName_enriched_if_field_is_fetched() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertNull(persistentProtein.getFullName());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                        persistentInt++;
                    }
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_FULLNAME , persistentProtein.getFullName());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_fullName_not_enriched_if_fetched_null() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME , TEST_OLD_FULLNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME );
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertNull( customProtein.getFullName() );
        assertEquals( TEST_OLD_FULLNAME , persistentProtein.getFullName() );

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertNull(persistentProtein.getFullName());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_fullName_enriched_if_fetched_is_different() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME , TEST_OLD_FULLNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals( TEST_FULLNAME , customProtein.getFullName() );
        assertEquals( TEST_OLD_FULLNAME , persistentProtein.getFullName() );

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                        assertTrue(persistentProtein == protein);
                        assertEquals( oldFullName , TEST_OLD_FULLNAME );
                        persistentInt++;
                    }
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_FULLNAME , persistentProtein.getFullName());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_fullName_not_enriched_if_fetched_is_same() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME , TEST_FULLNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(customProtein.getFullName() , persistentProtein.getFullName());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_FULLNAME , persistentProtein.getFullName());
        assertEquals(1 , persistentInt);
    }

    // == SEQUENCE ===================================================================================

    @Test
    public void test_null_sequence_enriched_if_field_is_fetched() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.setSequence(TEST_SEQUENCE);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(TEST_SEQUENCE , customProtein.getSequence());
        assertNull(persistentProtein.getSequence());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                        persistentInt++;
                    }

                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)   {fail("Should not reach this point");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_SEQUENCE , persistentProtein.getSequence());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_sequence_not_enriched_if_fetched_null() throws EnricherException {
        persistentInt = 0;

        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.setSequence(TEST_OLD_SEQUENCE);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.setSequence(TEST_OLD_SEQUENCE);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(TEST_OLD_SEQUENCE , customProtein.getSequence());
        assertEquals(TEST_OLD_SEQUENCE , persistentProtein.getSequence());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onSequenceUpdate(Protein protein, String oldSequence) {}
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_OLD_SEQUENCE , persistentProtein.getSequence());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_sequence_enriched_if_fetched_is_different() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.setSequence(TEST_OLD_SEQUENCE);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.setSequence(TEST_SEQUENCE);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(TEST_SEQUENCE , customProtein.getSequence());
        assertEquals(TEST_OLD_SEQUENCE , persistentProtein.getSequence());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                        assertTrue( protein == persistentProtein );
                        assertEquals(TEST_OLD_SEQUENCE , oldSequence);
                        persistentInt++;
                    }
                    public void onShortNameUpdate(Protein protein, String oldShortName)  {fail("Should not reach this point");}
                    public void onFullNameUpdate(Protein protein, String oldFullName)  {fail("Should not reach this point");}
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_SEQUENCE , persistentProtein.getSequence());
        assertEquals(2 , persistentInt);
    }

    @Test
    public void test_sequence_not_enriched_if_fetched_is_same() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.setSequence(TEST_SEQUENCE);

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.setSequence(TEST_SEQUENCE);
        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(TEST_SEQUENCE , customProtein.getSequence());
        assertEquals(TEST_SEQUENCE , persistentProtein.getSequence());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added) {fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(TEST_SEQUENCE , persistentProtein.getSequence());
        assertEquals(1 , persistentInt);
    }

    // == IDENTIFIERS ===================================================================================

    @Test
    public void test_identifiers_enriched() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.getIdentifiers().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN000"));

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.getIdentifiers().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN999"));

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(2 , persistentProtein.getIdentifiers().size());    // Has to include the uniprotkb

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {
                        assertTrue(protein == persistentProtein);
                        assertEquals( "EN999" , added.getId());
                        persistentInt++;
                    }
                    public void onRemovedIdentifier(Protein protein, Xref removed) {
                        assertTrue(protein == persistentProtein);
                        assertEquals( "EN000" , removed.getId());
                        persistentInt++;
                    }
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedAlias(Protein protein, Alias added){fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(2 , persistentProtein.getIdentifiers().size());

        boolean newXref = false;
        boolean idXref = false;
        for(Xref xref : persistentProtein.getIdentifiers()){
            if(xref.getId().equals("EN000"))
                fail("the original id should be removed");
            else if(xref.getId().equals("EN999"))
                if(newXref) fail("multiples of the new id");
                else newXref=true;
            else if(xref.getId().equals(TEST_AC_CUSTOM_PROT))
                if(idXref) fail("multiples of the uniprot id");
                else idXref=true;
            else
                fail(xref+" unrecognised alias");
        }

        assertTrue(newXref);
        assertTrue(idXref);

        assertEquals(3 , persistentInt);
    }

    // == ALIASES ===================================================================================

    /**
     * Enrich a protein that has no sequence.
     * Check the sequence has been added
     */
    @Test
    public void test_aliases_enriched() throws EnricherException {
        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.getAliases().add(new DefaultAlias(CvTermUtils.createEnsemblDatabase() , "EN000"));

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.getAliases().add(new DefaultAlias(CvTermUtils.createEnsemblDatabase() , "EN999"));

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(1 , persistentProtein.getAliases().size());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}
                    public void onAddedXref(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedXref(Protein protein, Xref removed) {fail("Should not reach this point");}

                    public void onAddedAlias(Protein protein, Alias added){
                        assertTrue(protein == persistentProtein);
                        assertEquals( "EN999" , added.getName());
                        persistentInt++;
                    }

                    public void onRemovedAlias(Protein protein, Alias removed) {
                        assertTrue(protein == persistentProtein);
                        assertEquals( "EN000" , removed.getName());
                        persistentInt++;
                    }
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(1 , persistentProtein.getAliases().size());
        boolean newAlias = false;
        for(Alias alias : persistentProtein.getAliases()){
            if(alias.getName().equals("EN000"))
                fail("the original id should be removed");
            else if(alias.getName().equals("EN999"))
                if(newAlias) fail("multiples of the new alias");
                else newAlias=true;
            else
                fail("unrecognised alias");
        }

        assertTrue(newAlias);

        assertEquals( 3 , persistentInt);
    }

    // == CHECKSUMS ===================================================================================
    //TODO - checksums logic is still lacking, check logic carefully before testing!!



    // == ORGANISM ===================================================================================

    @Test
    public void test_set_organism_if_null() throws EnricherException {
        Protein protein_without_organism = new DefaultProtein(TEST_SHORTNAME);
        protein_without_organism.setUniprotkb(TEST_AC_HALF_PROT);
        assertNull(protein_without_organism.getOrganism());

        this.proteinEnricher.enrich(protein_without_organism);

        assertNull(protein_without_organism.getOrganism());
    }


    // == XREFS ===================================================================================

    /**
     * Enrich a protein that has no sequence.
     * Check the sequence has been added
     */
    @Test
    public void test_xrefs_enriched() throws EnricherException {

        persistentProtein = new DefaultProtein(TEST_SHORTNAME);
        persistentProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        persistentProtein.getXrefs().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN000"));

        Protein customProtein = new DefaultProtein(TEST_SHORTNAME);
        customProtein.setUniprotkb(TEST_AC_CUSTOM_PROT);
        customProtein.getXrefs().add(new DefaultXref(CvTermUtils.createEnsemblDatabase() , "EN999"));

        Collection<Protein> customList = new ArrayList<Protein>();
        customList.add(customProtein);
        mockProteinFetcher.addEntry(TEST_AC_CUSTOM_PROT , customList);

        assertEquals(1 , persistentProtein.getXrefs().size());

        proteinEnricher.setListener(new ProteinEnricherListenerManager(
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
                    public void onOrganismUpdate(Protein interactor, Organism oldOrganism) {
                        fail("failed");
                    }

                    public void onInteractorTypeUpdate(Protein interactor, CvTerm oldType) {
                        fail("failed");
                    }
                    public void onAddedIdentifier(Protein protein, Xref added) {fail("Should not reach this point");}
                    public void onRemovedIdentifier(Protein protein, Xref removed) {fail("Should not reach this point");}

                    public void onAddedXref(Protein protein, Xref added) {
                        assertTrue(protein == persistentProtein);
                        assertEquals( "EN999" , added.getId());
                        persistentInt++;
                    }

                    public void onRemovedXref(Protein protein, Xref removed) {
                        assertTrue(protein == persistentProtein);
                        assertEquals( "EN000" , removed.getId());
                        persistentInt++;
                    }
                    public void onAddedAlias(Protein protein, Alias added){fail("Should not reach this point");}
                    public void onRemovedAlias(Protein protein, Alias removed) {fail("Should not reach this point");}
                    public void onAddedChecksum(Protein protein, Checksum added) {fail("Should not reach this point");}
                    public void onRemovedChecksum(Protein protein, Checksum removed) {fail("Should not reach this point");}
                    public void onAddedAnnotation(Protein o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Protein o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onEnrichmentError(Protein object, String message, Exception e) {
                        Assert.fail();
                    }
                }));

        this.proteinEnricher.enrich(persistentProtein);

        assertEquals(1 , persistentProtein.getXrefs().size());
        boolean newXref = false;
        for(Xref xref : persistentProtein.getXrefs()){
            if(xref.getId().equals("EN000"))
                fail("should have been removed");
            else if(xref.getId().equals("EN999"))
                if(newXref) fail("multiples of the new id");
                else newXref=true;
            else
                fail(xref+"unrecognised alias");
        }
        assertTrue(newXref);
        assertEquals(3 , persistentInt);
    }
}
