package psidev.psi.mi.jami.bridges.uniprot.taxonomy;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Organism;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 29/07/13
 */
public class UniprotTaxonomyFetcherTest {

    protected static final Logger log = LoggerFactory.getLogger(UniprotTaxonomyFetcherTest.class.getName());

    UniprotTaxonomyFetcher taxonomyFetcher;


    @Before
    public void setup(){
        taxonomyFetcher = new UniprotTaxonomyFetcher();
    }

    @Test
    public void test_fetching_known_organism_A() throws BridgeFailedException {
        Organism organism = taxonomyFetcher.fetchByTaxID(9615);
        assertEquals(9615 , organism.getTaxId());
        assertEquals("Dog" , organism.getCommonName());
        assertEquals("Canis familiaris" , organism.getScientificName());
    }

    @Test
    public void test_fetching_known_organism_B() throws BridgeFailedException {
        Organism organism = taxonomyFetcher.fetchByTaxID(9258);
        assertEquals(9258 , organism.getTaxId());
        assertEquals("Duckbill platypus" , organism.getCommonName());
        assertEquals("Ornithorhynchus anatinus" , organism.getScientificName());
    }

    @Test
    public void test_fetching_known_organism_c() throws BridgeFailedException {
        Organism organism = taxonomyFetcher.fetchByTaxID(436495);
        assertEquals(436495 , organism.getTaxId());
        assertEquals("Tyrant lizard king" , organism.getCommonName());
        assertEquals("Tyrannosaurus rex" , organism.getScientificName());
    }

}
