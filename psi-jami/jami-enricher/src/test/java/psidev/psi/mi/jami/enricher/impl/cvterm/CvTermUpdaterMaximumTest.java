package psidev.psi.mi.jami.enricher.impl.cvterm;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.ExceptionThrowingMockCvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AliasUtils;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class CvTermUpdaterMaximumTest {


    private CvTermUpdaterMaximum cvTermEnricher;
    private MockCvTermFetcher mockCvTermFetcher;

    private String SHORT_NAME = "ShortName";
    private String FULL_NAME = "FullName";
    private String MI_ID = "MI:1234";
    private String SYNONYM_NAME = "SynonymName";
    private CvTerm cvTermFull;

    private String other_short_name = "other short name";
    private String other_full_name = "other full name";
    private String other_synonym = "other synonym";

    private Collection<String> reportForEnrichment= new ArrayList<String>();
    private String fullNameUpdateKey = "FullNameUpdate";
    private String shortNameUpdateKey = "ShortNameUpdate";
    private String synonymAddedKey = "SynonymAdded";
    private String synonymRemovedKey = "SynonymRemoved";


    @Before
    public void setup() throws BridgeFailedException {
        mockCvTermFetcher = new MockCvTermFetcher();
        cvTermEnricher = new CvTermUpdaterMaximum(mockCvTermFetcher);

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
        int timesToTry = 3;

        assertTrue("The test can not be applied as the conditions do not invoke the required response. " +
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
        fail("Exception should be thrown before this point");
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
        fail("Exception should be thrown before this point");
    }


    /**
     * Enrich a CvTerm
     * Show that the full name is set and the synonyms are not.
     * @throws EnricherException
     */
    @Test
    public void test_enriching_CvTerm_by_MI_identifier_with_completed_fields() throws EnricherException {

        CvTerm cvTermToEnrichWithCompleteFields = new DefaultCvTerm(other_short_name, MI_ID);
        cvTermToEnrichWithCompleteFields.setFullName(other_full_name);
        cvTermToEnrichWithCompleteFields.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", other_synonym));

        assertEquals(other_short_name, cvTermToEnrichWithCompleteFields.getShortName());
        assertEquals(other_full_name, cvTermToEnrichWithCompleteFields.getFullName());
        assertEquals(1, cvTermToEnrichWithCompleteFields.getIdentifiers().size());
        assertEquals(1 , cvTermToEnrichWithCompleteFields.getSynonyms().size());

        cvTermEnricher.enrichCvTerm(cvTermToEnrichWithCompleteFields);

        assertEquals(SHORT_NAME , cvTermToEnrichWithCompleteFields.getShortName());
        assertNotNull(cvTermToEnrichWithCompleteFields.getFullName());
        assertEquals(FULL_NAME, cvTermToEnrichWithCompleteFields.getFullName());
        assertEquals(1 , cvTermToEnrichWithCompleteFields.getIdentifiers().size());
        assertEquals(1 , cvTermToEnrichWithCompleteFields.getSynonyms().size());
        assertEquals(SYNONYM_NAME , cvTermToEnrichWithCompleteFields.getSynonyms().iterator().next().getName());

        /*
        int otherSynonymCount = 0;
        int fetchedSynonymCount = 0;
        for(Alias alias : cvTermToEnrichWithCompleteFields.getSynonyms()){
            if(alias.getName().equals(other_synonym))
                otherSynonymCount++;
            else if(alias.getName().equals(SYNONYM_NAME))
                fetchedSynonymCount++;
            else
                fail("Synonym with unknown name was found: "+alias.getName());
        }
        assertEquals(1 , otherSynonymCount);
        assertEquals(1 , fetchedSynonymCount); */

    }

    private CvTerm cvTermToEnrichWithCompleteFields = new DefaultCvTerm(other_short_name, MI_ID);

    @Test
    public void test_enriching_CvTerm_by_MI_identifier_with_completed_fields_and_listener() throws EnricherException {
        reportForEnrichment.clear();
        cvTermToEnrichWithCompleteFields.setFullName(other_full_name);
        cvTermToEnrichWithCompleteFields.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", other_synonym));

        cvTermEnricher.setCvTermEnricherListener(new CvTermEnricherListener() {
            public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message) {
                assertTrue(cvTerm == cvTermToEnrichWithCompleteFields);
                assertEquals(EnrichmentStatus.SUCCESS , status);
            }

            public void onShortNameUpdate(CvTerm cv, String oldShortName) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                assertEquals(other_short_name , oldShortName);
                assertEquals(SHORT_NAME , cv.getShortName());
                reportForEnrichment.add(shortNameUpdateKey);
            }

            public void onFullNameUpdate(CvTerm cv, String oldFullName) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                assertEquals(other_full_name , oldFullName);
                assertEquals(FULL_NAME , cv.getFullName());
                reportForEnrichment.add(fullNameUpdateKey);
            }

            public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                fail("No additional MI was supplied");
            }

            public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                fail("No MOD was supplied");
            }

            public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                fail("No PAR was supplied");
            }

            public void onAddedIdentifier(CvTerm cv, Xref added) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                fail("No ID was supplied");
            }

            public void onRemovedIdentifier(CvTerm cv, Xref removed) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                fail("No ID should be removed for enrichment");
            }

            public void onAddedXref(CvTerm cv, Xref added) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                fail("No xref should be added for enrichment");
            }

            public void onRemovedXref(CvTerm cv, Xref removed) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                fail("No xref should be removed for enrichment");
            }

            public void onAddedSynonym(CvTerm cv, Alias added) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                assertEquals(SYNONYM_NAME, added.getName() );
                reportForEnrichment.add(synonymAddedKey);
            }

            public void onRemovedSynonym(CvTerm cv, Alias removed) {
                assertTrue(cv == cvTermToEnrichWithCompleteFields);
                assertEquals(other_synonym, removed.getName() );
                reportForEnrichment.add(synonymRemovedKey);
            }
        });

        cvTermEnricher.enrichCvTerm(cvTermToEnrichWithCompleteFields);

        assertTrue(reportForEnrichment.contains(shortNameUpdateKey));
        assertTrue(reportForEnrichment.contains(fullNameUpdateKey));
        assertTrue(reportForEnrichment.contains(synonymAddedKey));
        assertTrue(reportForEnrichment.contains(synonymRemovedKey));
    }


    /**
     * Enrich a CvTerm
     * Show that the full name is set and the synonyms are not.
     * @throws EnricherException
     */
    @Test
    public void test_enriching_CvTerm_by_MI_identifier_with_incomplete_fields() throws EnricherException {
        String other_short_name = "other short name";


        CvTerm cvTermToEnrich = new DefaultCvTerm(other_short_name , MI_ID);

        assertEquals(other_short_name , cvTermToEnrich.getShortName());
        assertNull(cvTermToEnrich.getFullName());
        assertEquals(1, cvTermToEnrich.getIdentifiers().size());
        assertEquals(0 , cvTermToEnrich.getSynonyms().size());

        cvTermEnricher.enrichCvTerm(cvTermToEnrich);

        assertEquals(SHORT_NAME , cvTermToEnrich.getShortName());
        assertNotNull(cvTermToEnrich.getFullName());
        assertEquals(FULL_NAME, cvTermToEnrich.getFullName());
        assertEquals(1 , cvTermToEnrich.getIdentifiers().size());
        assertEquals(1 , cvTermToEnrich.getSynonyms().size());
        assertEquals(SYNONYM_NAME , cvTermToEnrich.getSynonyms().iterator().next().getName());
    }


    CvTerm cvTermToEnrichWithoutCompleteFields = new DefaultCvTerm(other_short_name , MI_ID);

    @Test
    public void test_enriching_CvTerm_by_MI_identifier_with_incomplete_fields_and_listener() throws EnricherException {
        reportForEnrichment.clear();


        cvTermEnricher.setCvTermEnricherListener(new CvTermEnricherListener() {
            public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message) {
                assertTrue(cvTerm == cvTermToEnrichWithoutCompleteFields);
                assertEquals(EnrichmentStatus.SUCCESS , status);
            }

            public void onShortNameUpdate(CvTerm cv, String oldShortName) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                assertEquals(other_short_name , oldShortName);
                assertEquals(SHORT_NAME , cv.getShortName());
                reportForEnrichment.add(shortNameUpdateKey);
            }

            public void onFullNameUpdate(CvTerm cv, String oldFullName) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                assertNull(oldFullName);
                assertEquals(FULL_NAME , cv.getFullName());
                reportForEnrichment.add(fullNameUpdateKey);
            }

            public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No additional MI was supplied");
            }

            public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No MOD was supplied");
            }

            public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No PAR was supplied");
            }

            public void onAddedIdentifier(CvTerm cv, Xref added) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No ID was supplied");
            }

            public void onRemovedIdentifier(CvTerm cv, Xref removed) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No ID should be removed for enrichment");
            }

            public void onAddedXref(CvTerm cv, Xref added) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No xref should be added for enrichment");
            }

            public void onRemovedXref(CvTerm cv, Xref removed) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No xref should be removed for enrichment");
            }

            public void onAddedSynonym(CvTerm cv, Alias added) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                assertEquals(SYNONYM_NAME, added.getName() );
                reportForEnrichment.add(synonymAddedKey);
            }

            public void onRemovedSynonym(CvTerm cv, Alias removed) {
                assertTrue(cv == cvTermToEnrichWithoutCompleteFields);
                fail("No synonyms should be present to remove");
            }
        });

        cvTermEnricher.enrichCvTerm(cvTermToEnrichWithoutCompleteFields);

        assertTrue(reportForEnrichment.contains(shortNameUpdateKey));
        assertTrue(reportForEnrichment.contains(fullNameUpdateKey));
        assertTrue(reportForEnrichment.contains(synonymAddedKey));
    }
}
