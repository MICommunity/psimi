package psidev.psi.mi.jami.enricher.impl.cvterm;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.ExceptionThrowingMockCvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AliasUtils;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class CvTermEnricherMaximumTest {

    private CvTermEnricher cvTermEnricher;
    private MockCvTermFetcher mockCvTermFetcher;

    private String SHORT_NAME = "ShortName";
    private String FULL_NAME = "FullName";
    private String MI_ID = "MI:1234";
    private String SYNONYM_NAME = "SynonymName";
    private CvTerm cvTermFull;

    @Before
    public void setup() throws BridgeFailedException {
        mockCvTermFetcher = new MockCvTermFetcher();
        cvTermEnricher = new CvTermEnricherMaximum(mockCvTermFetcher);


        cvTermFull = new DefaultCvTerm( SHORT_NAME, FULL_NAME, MI_ID);
        cvTermFull.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", SYNONYM_NAME));
        mockCvTermFetcher.addCvTerm(MI_ID , cvTermFull);
    }


    /**
     * Creates a scenario where the fetcher always throws a bridge failure exception.
     * Shows that the query does not repeat infinitely.
     * @throws EnricherException
     */
    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {
        ExceptionThrowingMockCvTermFetcher fetcher = new ExceptionThrowingMockCvTermFetcher(-1);
        CvTerm cvTermToEnrich = new DefaultCvTerm(SHORT_NAME);
        cvTermToEnrich.setMIIdentifier(MI_ID);
        fetcher.addCvTerm(MI_ID , cvTermFull);

        cvTermEnricher.setCvTermFetcher(fetcher);
        cvTermEnricher.enrichCvTerm(cvTermToEnrich);

    }

    /**
     * Creates a scenario where the fetcher does not retrieve an entry on its first attempt.
     * If the enricher re-queries the fetcher, it will eventually receive the entry.
     *
     * @throws EnricherException
     */
    @Test
    public void test_bridgeFailure_does_not_throw_exception_when_not_persistent() throws EnricherException {
        int timesToTry = 3;

        assertTrue("The test can not be applied as the conditions do not invoke the requires response. " +
                "Change the timesToTry." ,
                timesToTry < CvTermEnricherMinimum.RETRY_COUNT);

        ExceptionThrowingMockCvTermFetcher fetcher = new ExceptionThrowingMockCvTermFetcher(timesToTry);
        CvTerm cvTermToEnrich = new DefaultCvTerm(SHORT_NAME);
        cvTermToEnrich.setMIIdentifier(MI_ID);
        fetcher.addCvTerm(MI_ID , cvTermFull);

        cvTermEnricher.setCvTermFetcher(fetcher);
        cvTermEnricher.enrichCvTerm(cvTermToEnrich);

        assertEquals(FULL_NAME, cvTermToEnrich.getFullName() );
    }

    /**
     * Attempts to enrich a null CvTerm.
     * This should always cause an illegal argument exception
     * @throws EnricherException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTerm() throws EnricherException {
        CvTerm nullCvTerm = null;
        cvTermEnricher.enrichCvTerm(nullCvTerm);
    }

    /**
     * Attempts to enrich a legal cvTerm but with a null fetcher.
     * This should throw an illegal state exception.
     * @throws EnricherException
     */
    @Test(expected = IllegalStateException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        CvTerm cvTerm = new DefaultCvTerm(SHORT_NAME , MI_ID);
        cvTermEnricher.setCvTermFetcher(null);
        assertNull(cvTermEnricher.getCvTermFetcher());
        cvTermEnricher.enrichCvTerm(cvTerm);
    }


    @Test
    public void test_enriching_CvTerm_by_MI_identifier() throws EnricherException {
        CvTerm cvTermToEnrich = new DefaultCvTerm("test" , MI_ID);

        assertEquals("test" , cvTermToEnrich.getShortName());
        assertNull(cvTermToEnrich.getFullName());
        assertEquals(1, cvTermToEnrich.getIdentifiers().size());
        assertEquals(0 , cvTermToEnrich.getSynonyms().size());

        cvTermEnricher.enrichCvTerm(cvTermToEnrich);

        assertEquals("test" , cvTermToEnrich.getShortName());
        assertNotNull(cvTermToEnrich.getFullName());
        assertEquals(FULL_NAME, cvTermToEnrich.getFullName());
        assertEquals(1 , cvTermToEnrich.getIdentifiers().size());
        assertEquals(1 , cvTermToEnrich.getSynonyms().size());
        assertEquals(SYNONYM_NAME , cvTermToEnrich.getSynonyms().iterator().next().getName() );
    }
}
