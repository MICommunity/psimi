package psidev.psi.mi.jami.enricher.impl.protein;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.protein.MockProteinFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.organism.OrganismEnricherMaximum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 12/07/13
 */
public class ProteinEnricherMaximumTest {


    private ProteinEnricherMaximum proteinEnricher;
    private MockProteinFetcher fetcher;

    //private ProteinEnricherCounter counter;

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
        this.fetcher = new MockProteinFetcher();
        this.proteinEnricher = new ProteinEnricherMaximum();
        proteinEnricher.setFetcher(fetcher);

        Protein fullProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        fullProtein.setUniprotkb(TEST_AC_FULL_PROT);
        fullProtein.setSequence(TEST_SEQUENCE);
        fullProtein.setOrganism(new DefaultOrganism(TEST_ORGANISM_ID, TEST_ORGANISM_COMMON, TEST_ORGANISM_SCIENTIFIC));
        fetcher.addNewProtein(TEST_AC_FULL_PROT, fullProtein);

        Protein halfProtein = new DefaultProtein(TEST_SHORTNAME);
        halfProtein.setUniprotkb(TEST_AC_HALF_PROT);
        halfProtein.setOrganism(new DefaultOrganism(-3));
        fetcher.addNewProtein(TEST_AC_HALF_PROT, halfProtein);

        //ProteinEnricherListenerManager manager = new ProteinEnricherListenerManager();
        //counter = new ProteinEnricherCounter();

        //manager.addProteinEnricherListener(new ProteinEnricherLogger());
        //manager.addProteinEnricherListener(counter);
        //proteinEnricher.setProteinEnricherListener(manager);
        //proteinEnricher.getOrganismEnricher().setOrganismEnricherListener(new OrganismEnricherLogger());
    }


    /**
     * Confirm that when a null protein is provided, an exception is thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_exception_when_fetching_on_null_protein() throws EnricherException {
        Protein null_protein = null;
        this.proteinEnricher.enrichProtein(null_protein);
    }

    /**
     * Confirm that the default organism enricher matches the protein enricher.
     */
    @Test
    public void test_organism_enricher_is_of_matching_type(){
        assertTrue(proteinEnricher.getOrganismEnricher() instanceof OrganismEnricherMaximum);
    }



}
