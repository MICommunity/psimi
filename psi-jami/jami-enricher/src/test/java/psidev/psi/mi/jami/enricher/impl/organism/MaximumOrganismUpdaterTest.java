package psidev.psi.mi.jami.enricher.impl.organism;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 * Time: 14:52
 */
public class MaximumOrganismUpdaterTest {


    private MaximumOrganismUpdater maximumOrganismUpdater;
    private MockOrganismFetcher fetcher;
    //private EnricherEvent event;

    private static final String TEST_SCIENTIFICNAME = "test scientificName";
    private static final String TEST_COMMONNAME = "test commonName";
    private static final int TEST_AC_FULL_ORG = 11111;
    private static final int TEST_AC_HALF_ORG = 55555;

    @Before
    public void initialiseFetcherAndEnricher() {
        this.fetcher = new MockOrganismFetcher();
        this.maximumOrganismUpdater = new MaximumOrganismUpdater();
        maximumOrganismUpdater.setFetcher(fetcher);

        Organism fullOrganism = new DefaultOrganism(TEST_AC_FULL_ORG, TEST_COMMONNAME, TEST_SCIENTIFICNAME);
        fetcher.addNewOrganism("" + TEST_AC_FULL_ORG, fullOrganism);

        Organism halfOrganism = new DefaultOrganism(TEST_AC_HALF_ORG);
        fetcher.addNewOrganism("" + TEST_AC_HALF_ORG, halfOrganism);

        //event = null;
    }

    /**
     * Enrich a protein that has no full name.
     * Check the full name has been added
     */
    @Test
    public void test_full_overwrite()
            throws BridgeFailedException, MissingServiceException,
            BadEnrichedFormException, BadToEnrichFormException {

        Organism organism_with_all_fields = new DefaultOrganism(TEST_AC_FULL_ORG,"common","scientific");

        assertNotNull(organism_with_all_fields.getTaxId());
        assertNotNull(organism_with_all_fields.getCommonName());
        assertNotNull(organism_with_all_fields.getScientificName());

        this.maximumOrganismUpdater.enrichOrganism(organism_with_all_fields);

        assertEquals(TEST_AC_FULL_ORG, organism_with_all_fields.getTaxId());
        assertEquals(TEST_COMMONNAME, organism_with_all_fields.getCommonName());
        assertEquals(TEST_SCIENTIFICNAME, organism_with_all_fields.getScientificName());
    }

    /**
     * Enrich an already complete protein with one which is only half complete.
     * This should not have any additions, nor throw any exceptions.
     */
    @Test
    public void test_overwrite_does_not_change_fields_to_null_from_enrichedOrganism()
            throws BridgeFailedException, MissingServiceException,
            BadEnrichedFormException, BadToEnrichFormException {

        Organism organism_with_all_fields = new DefaultOrganism(TEST_AC_HALF_ORG,"common","scientific");

        assertEquals(TEST_AC_HALF_ORG, organism_with_all_fields.getTaxId());
        assertEquals("common", organism_with_all_fields.getCommonName());
        assertEquals("scientific", organism_with_all_fields.getScientificName());

        this.maximumOrganismUpdater.enrichOrganism(organism_with_all_fields);

        assertNotNull(organism_with_all_fields.getTaxId());
        assertNotNull(organism_with_all_fields.getCommonName());
        assertNotNull(organism_with_all_fields.getScientificName());

        assertEquals(TEST_AC_HALF_ORG, organism_with_all_fields.getTaxId());
        assertEquals("common", organism_with_all_fields.getCommonName());
        assertEquals("scientific", organism_with_all_fields.getScientificName());
    }

    /**
     * Enrich an organism which will have additions
     * Enrich an organism which will have mismatches
     * Enrich an organism with a different id and no additions or mismatches.
     * Check that after the final enrichment, the additions and mismatches were reset and the the ID was updated
     * Check the mismatches
     */
   /* @Test
    public void test_enricher_event_is_cleared()
            throws BridgeFailedException, MissingServiceException,
            BadEnrichedFormException, BadToEnrichFormException {

        Organism organism_test_one = new DefaultOrganism(TEST_AC_FULL_ORG);

        Organism organism_test_two = new DefaultOrganism(TEST_AC_FULL_ORG, "testpart2 commonName", "testpart2 scientificName");

        Organism organism_test_three = new DefaultOrganism(TEST_AC_HALF_ORG);

        maximumOrganismUpdater.enrichOrganism(organism_test_one);

        assertEquals(event.getQueryID(), ""+TEST_AC_FULL_ORG);

        assertTrue(event.getAdditions().size() > 0);
        assertTrue(event.getMismatches().size() == 0);
        assertTrue(event.getOverwrites().size() == 0);

        maximumOrganismUpdater.enrichOrganism(organism_test_two);

        assertEquals(event.getQueryID(), ""+TEST_AC_FULL_ORG);
        assertTrue(event.getAdditions().size() == 0);
        assertTrue(event.getMismatches().size() == 0);
        assertTrue(event.getOverwrites().size() > 0);

        maximumOrganismUpdater.enrichOrganism(organism_test_three);

        assertEquals(event.getQueryID(), ""+TEST_AC_HALF_ORG);
        assertTrue(event.getAdditions().size() == 0);
        assertTrue(event.getMismatches().size() == 0);
        assertTrue(event.getOverwrites().size() == 0);

    }  */

    /**
     * Enrich a half completed protein.
     * Check that this fires an enrichmentEvent.
     * Check this event has recorded some additions
     * check this event has recorded some mismatches
     * check this event has not recorded some overwrites
     */
    /*@Test
    public void test_enricher_event_is_fired_and_has_correct_content() throws EnrichmentException {
        Organism organism_to_enrich = new DefaultOrganism(TEST_AC_FULL_ORG, "testpart2 commonName", "testpart2 scientificName");

        this.maximumOrganismUpdater.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                event = e;
            }
        });

        //this.maximumOrganismUpdater.addEnricherListener(new LoggingEnricherListener());

        maximumOrganismUpdater.enrichOrganism(organism_to_enrich);

        assertNotNull(event);
        assertEquals(event.getObjectType(), "Organism");
        assertEquals(event.getQueryID(), ""+TEST_AC_FULL_ORG);
        assertEquals(event.getQueryIDType(), "TaxID");
        assertTrue(event.getAdditions().size() == 0);
        assertTrue(event.getMismatches().size() == 0);
        assertTrue(event.getOverwrites().size() == 2);
    }  */
}
