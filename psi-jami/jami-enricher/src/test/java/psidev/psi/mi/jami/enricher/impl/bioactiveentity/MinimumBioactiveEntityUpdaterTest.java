package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.bioactiveentity.ExceptionThrowingMockBioactiveEntityFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.bioactiveentity.MockBioactiveEntityFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.ExceptionThrowingMockCvTermFetcher;
import psidev.psi.mi.jami.enricher.BioactiveEntityEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.MinimumCvTermEnricher;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import static junit.framework.Assert.*;
import static junit.framework.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class MinimumBioactiveEntityUpdaterTest {

    String CHEBI_ID = "TEST_ID";
    String TEST_FULLNAME = "fullName";
    String TEST_SHORTNAME = "shortName";

    BioactiveEntityEnricher enricher;
    MockBioactiveEntityFetcher fetcher;
    BioactiveEntity persistentBioactiveEntity;

    @Before
    public void setUp(){
        fetcher = new MockBioactiveEntityFetcher();
        enricher = new MinimumBioactiveEntityUpdater(fetcher);

        persistentBioactiveEntity = null;
    }



    // == RETRY ON FAILING FETCHER ============================================================

    /**
     * Creates a scenario where the fetcher always throws a bridge failure exception.
     * Shows that the query does not repeat infinitely.
     * @throws psidev.psi.mi.jami.enricher.exception.EnricherException
     */
    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {

        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        int timesToTry = -1;

        ExceptionThrowingMockBioactiveEntityFetcher fetcher = new ExceptionThrowingMockBioactiveEntityFetcher(timesToTry);
        fetcher.addEntry(CHEBI_ID , persistentBioactiveEntity);
        enricher.setBioactiveEntityFetcher(fetcher);

        enricher.enrichBioactiveEntity(persistentBioactiveEntity);

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

        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        int timesToTry = 3;
        assertTrue("The test can not be applied as the conditions do not invoke the required response. " +
                "Change the timesToTry." ,
                timesToTry < MinimumBioactiveEntityUpdater.RETRY_COUNT);

        ExceptionThrowingMockBioactiveEntityFetcher fetcher = new ExceptionThrowingMockBioactiveEntityFetcher(timesToTry);
        fetcher.addEntry(CHEBI_ID , persistentBioactiveEntity);
        enricher.setBioactiveEntityFetcher(fetcher);

        enricher.enrichBioactiveEntity(persistentBioactiveEntity);

        assertEquals(TEST_FULLNAME, persistentBioactiveEntity.getFullName() );
    }


    // == FAILURE ON NULL ======================================================================

    /**
     * Attempts to enrich a null CvTerm.
     * This should always cause an illegal argument exception
     * @throws EnricherException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTerm() throws EnricherException {
        BioactiveEntity nullBioactiveEntity = null;
        enricher.enrichBioactiveEntity(nullBioactiveEntity);
        fail("Exception should be thrown before this point");
    }

    /**
     * Attempts to enrich a legal cvTerm but with a null fetcher.
     * This should throw an illegal state exception.
     * @throws EnricherException
     */
    @Test(expected = IllegalStateException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        BioactiveEntity bioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME, TEST_FULLNAME);
        bioactiveEntity.setChebi(CHEBI_ID);

        enricher.setBioactiveEntityFetcher(null);
        assertNull(enricher.getBioactiveEntityFetcher());
        enricher.enrichBioactiveEntity(bioactiveEntity);
        fail("Exception should be thrown before this point");
    }
}
