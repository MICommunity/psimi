package psidev.psi.mi.jami.enricher.impl.organism;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.ExceptionThrowingMockOrganismFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.listener.organism.OrganismEnricherListener;
import psidev.psi.mi.jami.enricher.listener.organism.OrganismEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  24/05/13
 */
public class MinimumOrganismEnricherTest {

    private MinimumOrganismEnricher organismEnricher;
    private MockOrganismFetcher fetcher;
    private Organism mockOrganism;
    public Organism persistentOrganism;

    private static final String TEST_SCIENTIFICNAME = "test scientificName";
    private static final String TEST_COMMONNAME = "test commonName";
    private static final String TEST_OLD_SCIENTIFICNAME = "test old scientificName";
    private static final String TEST_OLD_COMMONNAME = "test old commonName";
    private static final int TEST_AC_FULL_ORG = 11111;
    private static final int TEST_AC_HALF_ORG = 55555;
    private static final int TEST_AC_CUSTOM_ORG = 99999;

    @Before
    public void initialiseFetcherAndEnricher() {
        persistentOrganism = null;
        this.fetcher = new MockOrganismFetcher();
        this.organismEnricher = new MinimumOrganismEnricher();
        organismEnricher.setOrganismFetcher(fetcher);

        Organism fullOrganism = new DefaultOrganism(TEST_AC_FULL_ORG, TEST_COMMONNAME, TEST_SCIENTIFICNAME);
        fullOrganism.getAliases().add(new DefaultAlias("TestAlias"));
        fetcher.addNewOrganism("" + TEST_AC_FULL_ORG, fullOrganism);

        Organism halfOrganism = new DefaultOrganism(TEST_AC_HALF_ORG);
        fetcher.addNewOrganism("" + TEST_AC_HALF_ORG, halfOrganism);

        mockOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG , "mock" , "mockus mockus");
    }

    // == RETRY ON FAILING FETCHER ============================================================

    /**
     * Creates a scenario where the fetcher always throws a bridge failure exception.
     * Shows that the query does not repeat infinitely.
     * @throws psidev.psi.mi.jami.enricher.exception.EnricherException
     */
    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {
        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);

        int timesToTry = -1;

        ExceptionThrowingMockOrganismFetcher fetcher = new ExceptionThrowingMockOrganismFetcher(timesToTry);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG), mockOrganism);
        organismEnricher.setOrganismFetcher(fetcher);

        organismEnricher.enrichOrganism(persistentOrganism);

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
        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);

        int timesToTry = 3;


        assertTrue("The test can not be applied as the conditions do not invoke the required response. " +
                "Change the timesToTry." ,
                timesToTry < AbstractOrganismEnricher.RETRY_COUNT);

        ExceptionThrowingMockOrganismFetcher fetcher = new ExceptionThrowingMockOrganismFetcher(timesToTry);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG), mockOrganism);
        organismEnricher.setOrganismFetcher(fetcher);

        organismEnricher.enrichOrganism(persistentOrganism);

        assertEquals("mockus mockus", persistentOrganism.getScientificName() );
    }


    // == FAILURE ON NULL ======================================================================

    /**
     * Attempts to enrich a null CvTerm.
     * This should always cause an illegal argument exception
     * @throws EnricherException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTerm() throws EnricherException {
        Organism organism = null;
        organismEnricher.enrichOrganism(organism);
        fail("Exception should be thrown before this point");
    }

    /**
     * Attempts to enrich a legal cvTerm but with a null fetcher.
     * This should throw an illegal state exception.
     * @throws EnricherException
     */
    @Test(expected = IllegalStateException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        persistentOrganism = new DefaultOrganism(1234);
        organismEnricher.setOrganismFetcher(null);
        assertNull(organismEnricher.getOrganismFetcher());
        organismEnricher.enrichOrganism(persistentOrganism);
        fail("Exception should be thrown before this point");
    }


    // == SCIENTIFIC NAME =====================================================================================

    @Test
    public void test_set_scientificName_if_null() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setScientificName(TEST_SCIENTIFICNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);

        assertNull(persistentOrganism.getScientificName());

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}

                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
                        assertTrue(persistentOrganism == organism);
                        assertNull(oldScientificName);
                        assertEquals(TEST_SCIENTIFICNAME , organism.getScientificName());
                    }

                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}
                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getScientificName());
        assertEquals(TEST_SCIENTIFICNAME, persistentOrganism.getScientificName());
    }

    @Test
    public void test_do_not_update_scientificName_if_different() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setScientificName(TEST_SCIENTIFICNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setScientificName(TEST_OLD_SCIENTIFICNAME);

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}

                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {fail();}
                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}
                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getScientificName());
        assertEquals(TEST_OLD_SCIENTIFICNAME, persistentOrganism.getScientificName());
    }

    @Test
    public void test_do_not_update_scientificName_if_same() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setScientificName(TEST_SCIENTIFICNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setScientificName(TEST_SCIENTIFICNAME);

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }
                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}
                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {fail();}
                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getScientificName());
        assertEquals(TEST_SCIENTIFICNAME, persistentOrganism.getScientificName());
    }


    // == COMMON NAME ========================================================================================

    @Test
    public void test_set_commonName_if_null() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setCommonName(TEST_COMMONNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);

        assertNull(persistentOrganism.getCommonName());

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){
                        assertTrue(persistentOrganism == organism);
                        assertNull(oldCommonName);
                        assertEquals(TEST_COMMONNAME , organism.getCommonName());
                    }

                    public void onScientificNameUpdate(Organism organism, String oldScientificName){fail("Should not reach this point");}

                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);


        assertEquals(TEST_COMMONNAME, persistentOrganism.getCommonName());
    }

    @Test
    public void test_do_not_update_commonName_if_different() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setCommonName(TEST_COMMONNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setCommonName(TEST_OLD_COMMONNAME);

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail();}
                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {fail("Should not reach this point");}
                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getCommonName());
        assertEquals(TEST_OLD_COMMONNAME, persistentOrganism.getCommonName());
    }

    @Test
    public void test_do_not_update_commonName_if_same() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setCommonName(TEST_COMMONNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setCommonName(TEST_COMMONNAME);

        assertNotNull(persistentOrganism.getCommonName());

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }
                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}
                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {fail();}
                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getCommonName());
        assertEquals(TEST_COMMONNAME, persistentOrganism.getCommonName());
    }


}
