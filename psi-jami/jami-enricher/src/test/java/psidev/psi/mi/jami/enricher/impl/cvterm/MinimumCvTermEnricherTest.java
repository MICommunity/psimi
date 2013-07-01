package psidev.psi.mi.jami.enricher.impl.cvterm;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.bridges.ols.OlsFetcher;
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
public class MinimumCvTermEnricherTest {

    MinimumCvTermEnricher minimumCvTermEnricher;
    MockCvTermFetcher mockCvTermFetcher = new MockCvTermFetcher();

    private String SHORT_NAME = "ShortName";
    private String FULL_NAME = "FullName";
    private String MI_ID = "MI:1234";
    private String SYNONYM_NAME = "SynonymName";

    @Before
    public void setup() throws BridgeFailedException {
        minimumCvTermEnricher = new MinimumCvTermEnricher();
        minimumCvTermEnricher.setCvTermFetcher(mockCvTermFetcher);

        CvTerm cvTermFull = new DefaultCvTerm( SHORT_NAME, FULL_NAME, MI_ID);
        cvTermFull.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", SYNONYM_NAME));
        mockCvTermFetcher.addCvTerm(MI_ID , cvTermFull);

    }


    @Test
    public void test_enriching_CvTerm_by_MI_identifier() throws EnricherException {
        CvTerm cvTermToEnrich = new DefaultCvTerm("test" , MI_ID);

        assertEquals("test" , cvTermToEnrich.getShortName());
        assertNull(cvTermToEnrich.getFullName());
        assertEquals(1, cvTermToEnrich.getIdentifiers().size());
        assertEquals(0 , cvTermToEnrich.getSynonyms().size());

        minimumCvTermEnricher.enrichCvTerm(cvTermToEnrich);

        assertEquals("test" , cvTermToEnrich.getShortName());
        assertNotNull(cvTermToEnrich.getFullName());
        assertEquals(FULL_NAME, cvTermToEnrich.getFullName());
        assertEquals(1 , cvTermToEnrich.getIdentifiers().size());
        assertEquals(0 , cvTermToEnrich.getSynonyms().size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTerm() throws EnricherException {
        CvTerm nullCvTerm = null;
        minimumCvTermEnricher.enrichCvTerm(nullCvTerm);
    }

    @Test(expected = IllegalStateException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        CvTerm cvTerm = new DefaultCvTerm(SHORT_NAME , MI_ID);
        minimumCvTermEnricher.setCvTermFetcher(null);
        minimumCvTermEnricher.enrichCvTerm(cvTerm);
    }

}
