package psidev.psi.mi.jami.bridges;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;

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
    public void test() throws BridgeFailedException {
        Organism organism = taxonomyFetcher.getOrganismByTaxID(9615);
        log.info("TaxID: "+organism.getTaxId());
        log.info("CommonName: "+organism.getCommonName());
        log.info("ScientificName: "+organism.getScientificName());
        for(Alias syno : organism.getAliases()){
            log.info("Synonym: "+syno.toString());
        }

    }
}
