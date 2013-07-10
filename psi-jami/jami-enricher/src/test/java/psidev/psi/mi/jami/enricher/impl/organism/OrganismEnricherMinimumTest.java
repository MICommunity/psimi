package psidev.psi.mi.jami.enricher.impl.organism;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 * Time: 14:04
 */
public class OrganismEnricherMinimumTest {

    private OrganismEnricherMinimum organismEnricherMinimum;
    private MockOrganismFetcher fetcher;
    //private EnricherEvent event;

    private static final String TEST_SCIENTIFICNAME = "test scientificName";
    private static final String TEST_COMMONNAME = "test commonName";
    private static final int TEST_AC_FULL_ORG = 11111;
    private static final int TEST_AC_HALF_ORG = 55555;

    @Before
    public void initialiseFetcherAndEnricher() {
        this.fetcher = new MockOrganismFetcher();
        this.organismEnricherMinimum = new OrganismEnricherMinimum();
        organismEnricherMinimum.setFetcher(fetcher);

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
    public void test_set_scientificName_if_null() throws EnricherException {

        Organism organism_without_scientificName = new DefaultOrganism(TEST_AC_FULL_ORG);
        organism_without_scientificName.setCommonName(TEST_COMMONNAME);

        assertNull(organism_without_scientificName.getScientificName());

        this.organismEnricherMinimum.enrichOrganism(organism_without_scientificName);

        assertNotNull(organism_without_scientificName.getScientificName());
        assertEquals(TEST_SCIENTIFICNAME, organism_without_scientificName.getScientificName());
    }

    /**
     * Enrich a organism with commonName.
     * Check that the commonName has been added
     */
    @Test
    public void test_set_commonName_if_null() throws EnricherException {
        Organism organism_without_commonName = new DefaultOrganism(TEST_AC_FULL_ORG);
        organism_without_commonName.setScientificName(TEST_SCIENTIFICNAME);

        assertNull(organism_without_commonName.getCommonName());

        this.organismEnricherMinimum.enrichOrganism(organism_without_commonName);

        assertNotNull(organism_without_commonName.getCommonName());
        assertEquals(TEST_COMMONNAME, organism_without_commonName.getCommonName());
    }

    /**
     * Enrich an already complete protein with a different but complete protein.
     * This should not change any fields of the original protein.
     */
    @Test
    public void test_no_overwrite_on_not_null_fields() throws EnricherException {

        Organism organism_with_all_fields = new DefaultOrganism(TEST_AC_FULL_ORG, "commonName", "scientificName");

        assertNotNull(organism_with_all_fields.getTaxId());
        assertNotNull(organism_with_all_fields.getCommonName());
        assertNotNull(organism_with_all_fields.getScientificName());

        this.organismEnricherMinimum.enrichOrganism(organism_with_all_fields);

        assertEquals( organism_with_all_fields.getTaxId(), TEST_AC_FULL_ORG );
        assertEquals( organism_with_all_fields.getCommonName() , "commonName" );
        assertEquals( organism_with_all_fields.getScientificName(), "scientificName" );
    }

    /**
     * Enrich an already complete protein with one which is only half complete.
     * This should not have any additions, nor throw any exceptions.
     */
    @Test
    public void test_mismatch_does_not_happen_on_enrichedOrganism_with_null_fields() throws EnricherException {

        Organism organism_with_all_fields = new DefaultOrganism(TEST_AC_HALF_ORG, "commonName", "scientificName");

        this.organismEnricherMinimum.enrichOrganism(organism_with_all_fields);

        //assertTrue(event.getMismatches().size() == 0);
    }


}
