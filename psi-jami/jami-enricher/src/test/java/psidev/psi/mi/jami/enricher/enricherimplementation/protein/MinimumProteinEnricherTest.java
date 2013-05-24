package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.exception.ConflictException;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.protein.MockProteinFetcher;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

import java.util.Collection;

/**
 * Unit tester for MinimumProteinEnricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class MinimumProteinEnricherTest {

    private MinimumProteinEnricher minimumProteinEnricher;
    private MockProteinFetcher fetcher;
    private EnricherEvent event;

    private static final String TEST_SHORTNAME = "test shortName";
    private static final String TEST_FULLNAME = "test fullName";
    private static final String TEST_AC_FULL_PROT = "P12345";
    private static final String TEST_AC_HALF_PROT = "P11111";
    private static final String TEST_SEQUENCE = "GATTACA";

    @Before
    public void initialiseFetcherAndEnricher() throws EnrichmentException {
        this.fetcher = new MockProteinFetcher();
        this.minimumProteinEnricher = new MinimumProteinEnricher(fetcher);

        Protein fullProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        fullProtein.setUniprotkb(TEST_AC_FULL_PROT);
        fullProtein.setSequence(TEST_SEQUENCE);
        fetcher.addNewProtein(TEST_AC_FULL_PROT, fullProtein);

        Protein halfProtein = new DefaultProtein(TEST_SHORTNAME);
        halfProtein.setUniprotkb(TEST_AC_HALF_PROT);
        fetcher.addNewProtein(TEST_AC_HALF_PROT, halfProtein);
        event = null;
    }

    /**
     * Enrich a protein that has no full name.
     * Check the full name has been added
     * @throws EnrichmentException
     */
    @Test
    public void test_set_fullName_if_null() throws EnrichmentException {

        Protein protein_without_fullName = new DefaultProtein("test2 shortName");
        protein_without_fullName.setUniprotkb(TEST_AC_FULL_PROT);

        this.minimumProteinEnricher.enrichProtein(protein_without_fullName);

        assertNotNull(protein_without_fullName.getFullName());
        assertEquals(TEST_FULLNAME, protein_without_fullName.getFullName());
    }

    //TEST CAN NOT BE IMPLEMENTED WITH CURRENT MOCK.
    //@Test
    //public void test_set_primaryAC_if_null() throws EnrichmentException {
    //
    //    Protein protein_without_AC = new DefaultProtein("test2 shortName");
    //
    //    this.minimumProteinEnricher.enrichProtein(protein_without_AC);
    //
    //    Assert.assertNotNull(protein_without_fullName.getFullName());
    //    Assert.assertEquals("test1 fullName", protein_without_fullName.getFullName());
    //    Assert.assertEquals("test2 shortName", protein_without_fullName.getShortName());
    //}

    /**
     * Enrich a protein with no sequence.
     * Check that the sequence has been added
     * @throws EnrichmentException
     */
    @Test
    public void test_set_sequence_if_null() throws EnrichmentException {

        Protein protein_without_sequence = new DefaultProtein("test2 shortName");
        protein_without_sequence.setUniprotkb(TEST_AC_FULL_PROT);

        this.minimumProteinEnricher.enrichProtein(protein_without_sequence);

        assertNotNull(protein_without_sequence.getSequence());
        assertEquals(TEST_SEQUENCE, protein_without_sequence.getSequence());
    }

    @Test
    public void test_set_organism_if_null() throws EnrichmentException{
        Protein protein_without_organism = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        protein_without_organism.setUniprotkb(TEST_AC_FULL_PROT);
        protein_without_organism.setSequence(TEST_SEQUENCE);

        try {
            fetcher.getProteinByID(TEST_AC_FULL_PROT).setOrganism(new DefaultOrganism(11111,"Common","Scientific"));
        } catch (FetcherException e) {
            e.printStackTrace();
        }

        assertNull(protein_without_organism.getOrganism());

        this.minimumProteinEnricher.enrichProtein(protein_without_organism);

        assertNotNull(protein_without_organism.getOrganism());
        assertEquals(11111 , protein_without_organism.getOrganism().getTaxId());
        assertEquals("Common" , protein_without_organism.getOrganism().getCommonName());
        assertEquals("Scientific" , protein_without_organism.getOrganism().getScientificName());
    }

    @Test(expected = ConflictException.class)
    public void test_organism_conflict() throws EnrichmentException{
        Protein protein_with_wrong_organism = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        protein_with_wrong_organism.setUniprotkb(TEST_AC_FULL_PROT);
        protein_with_wrong_organism.setSequence(TEST_SEQUENCE);
        protein_with_wrong_organism.setOrganism(new DefaultOrganism(55555));

        try {
            fetcher.getProteinByID(TEST_AC_FULL_PROT).setOrganism(new DefaultOrganism(11111,"Common","Scientific"));
        } catch (FetcherException e) {
            e.printStackTrace();
        }

        assertNotNull(protein_with_wrong_organism.getOrganism());

        this.minimumProteinEnricher.enrichProtein(protein_with_wrong_organism);
    }

    /**
     * Enrich an already complete protein with a different but complete protein.
     * This should not change any fields of the original protein.
     * @throws EnrichmentException
     */
    @Test
    public void test_no_overwrite_on_not_null_fields() throws EnrichmentException {
        Protein protein_with_all_fields = new DefaultProtein("test2 shortName", "test2 fullName");
        protein_with_all_fields.setUniprotkb(TEST_AC_FULL_PROT);
        protein_with_all_fields.setSequence("TAGTAG");

        assertNotNull(protein_with_all_fields.getShortName());
        assertNotNull(protein_with_all_fields.getFullName());
        assertNotNull(protein_with_all_fields.getUniprotkb());
        assertNotNull(protein_with_all_fields.getSequence());

        this.minimumProteinEnricher.enrichProtein(protein_with_all_fields);

        assertEquals( protein_with_all_fields.getShortName(), "test2 shortName");
        assertEquals( protein_with_all_fields.getFullName(), "test2 fullName");
        assertEquals( protein_with_all_fields.getSequence(), "TAGTAG");
    }

    /**
     * Enrich an already complete protein with one which is only half complete.
     * This should not have any additions, nor throw any exceptions.
     * @throws EnrichmentException
     */
    @Test
    public void test_mismatch_does_not_happen_on_a_null_enrichedProtein() throws EnrichmentException{


        Protein protein_with_all_fields = new DefaultProtein("test2 shortName", "test2 fullName");
        protein_with_all_fields.setUniprotkb(TEST_AC_HALF_PROT);
        protein_with_all_fields.setSequence("TAGTAG");

        this.minimumProteinEnricher.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                event = e;
            }
        });

        //If this is failing, you may wish to use a logging listener to read the log.
        //this.minimumProteinEnricher.addEnricherListener(new LoggingEnricherListener());

        this.minimumProteinEnricher.enrichProtein(protein_with_all_fields);

        assertTrue(event.getMismatches().size() == 1);
    }

    //TODO Consider further tests for the passing of an organism to the enricher

    //TODO Add a test for the rogid

    //todo add test for crc64 checksum:
    //todo check that crc64 already existing but the same is passed over and not added
    //todo check that crc64 that is different throws conflict

    /**
     * Enrich an already completed protein with a less complete protein
     * Count additions, should be 0
     * Count mismatches, should be at least 1 (shortname)
     * Check the id, should be the same as the enriched protein.
     * Run enrichment on another protein of different id
     * Check the id, should be the same as new protein
     * Check the mismatches
     * @throws EnrichmentException
     */
    @Test
    public void test_enricher_event_is_cleared() throws EnrichmentException {
        Protein protein_test_one = new DefaultProtein("testpart1 shortName", "testpart1 fullName");
        protein_test_one.setUniprotkb(TEST_AC_FULL_PROT);
        protein_test_one.setSequence("TAGTAG");

        Protein protein_test_two = new DefaultProtein("testpart2 shortName", "testpart2 fullName");
        protein_test_two.setUniprotkb(TEST_AC_HALF_PROT);
        protein_test_two.setSequence("TAGTAG");

        this.minimumProteinEnricher.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                event = e;
            }
        });

        //If this is failing, you may wish to use a logging listener to read the log.
        //this.minimumProteinEnricher.addEnricherListener(new LoggingEnricherListener());

        minimumProteinEnricher.enrichProtein(protein_test_one);

        assertEquals(event.getQueryID(), TEST_AC_FULL_PROT);

        assertTrue(event.getAdditions().size() == 0);
        assertTrue(event.getMismatches().size() > 0);
        assertTrue(event.getOverwrites().size() == 0);

        minimumProteinEnricher.enrichProtein(protein_test_two);

        assertEquals(event.getQueryID(), TEST_AC_HALF_PROT);
        assertTrue(event.getAdditions().size() == 0);
        assertTrue(event.getMismatches().size() == 1);
        assertTrue(event.getOverwrites().size() == 0);

    }

    /**
     * Enrich a half completed protein.
     * Check that this fires an enrichmentEvent.
     * Check this event has recorded some additions
     * check this event has recorded some mismatches
     * check this event has not recorded some overwrites
     * @throws EnrichmentException
     */
    @Test
    public void test_enricher_event_is_fired_and_has_correct_content() throws EnrichmentException {
        Protein protein_to_enrich = new DefaultProtein("test2 shortName", "test2 fullName");
        protein_to_enrich.setUniprotkb(TEST_AC_FULL_PROT);

        event = null;
        this.minimumProteinEnricher.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                event = e;
            }
        });

        minimumProteinEnricher.enrichProtein(protein_to_enrich);

        assertNotNull(event);
        assertEquals(event.getObjectType(), "Protein");
        assertEquals(event.getQueryID(), TEST_AC_FULL_PROT);
        assertEquals(event.getQueryIDType(), "UniprotKB AC");
        assertTrue(event.getAdditions().size() > 0);
        assertTrue(event.getMismatches().size() > 0);
        assertTrue(event.getOverwrites().size() == 0);
    }
}
