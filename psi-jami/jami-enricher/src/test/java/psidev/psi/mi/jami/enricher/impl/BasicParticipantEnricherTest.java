package psidev.psi.mi.jami.enricher.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.ParticipantEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.impl.ParticipantEnricherLogger;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 26/07/13
 */
public class BasicParticipantEnricherTest {

    private ParticipantEnricher participantEnricher;

    private Participant persistentParticipant;
    private int persistentInt = 0;

    @Before
    public void setup(){
        participantEnricher = new BasicParticipantEnricher();
        Protein protein = new DefaultProtein("ShortName");
        protein.setUniprotkb("P1234");
        persistentParticipant = new DefaultParticipant(protein);
        persistentInt = 0;
    }

    @Test
    public void test_enrichment_without_enrichers_succeeds() throws EnricherException {
        assertNull(participantEnricher.getFeatureEnricher());
        assertNull(participantEnricher.getCvTermEnricher());

        participantEnricher.setParticipantListener(new ParticipantEnricherListenerManager(
                new ParticipantEnricherLogger() ,
                new ParticipantEnricherListener() {
                    public void onEnrichmentComplete(Participant participant, EnrichmentStatus status, String message) {
                        assertTrue(participant == persistentParticipant);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++ ;
                    }

                    public void onEnrichmentError(Participant object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));
        participantEnricher.enrich(persistentParticipant);
        assertEquals(1 , persistentInt);
    }


    // == CVTERM ENRICHER TESTS =========================================================================

    /**
     * Assert that if a CvTerm enricher is included,
     * but the cvTerms to enrich are null, that no problems are encountered.
     * @throws EnricherException
     */
    @Test
    public void test_enrichment_with_cvTermEnricher_but_no_cvTerms() throws EnricherException {

        participantEnricher.setCvTermEnricher(new MinimalCvTermEnricher(new MockCvTermFetcher()));

        participantEnricher.setParticipantListener(new ParticipantEnricherListenerManager(
                new ParticipantEnricherLogger() ,
                new ParticipantEnricherListener() {
                    public void onEnrichmentComplete(Participant participant, EnrichmentStatus status, String message) {
                        assertTrue(participant == persistentParticipant);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++ ;
                    }
                    public void onEnrichmentError(Participant object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        participantEnricher.enrich(persistentParticipant);

        assertEquals(1 , persistentInt);
    }

    /**
     * Assert that if a CvTerm enricher is included,
     * and the cvTerms are present, the CvTerms are enriched.
     * @throws EnricherException
     */
    @Test
    public void test_enrichment_with_CvTermEnricher_enriches_CvTerms() throws EnricherException {
        MockCvTermFetcher mockCvTermFetcher = new MockCvTermFetcher();
        participantEnricher.setCvTermEnricher(new MinimalCvTermEnricher(mockCvTermFetcher));
        mockCvTermFetcher.addEntry("MI:0001" , new DefaultCvTerm("ShortName" , "FullName" , "MI:0001"));

        persistentParticipant.setBiologicalRole(new DefaultCvTerm("ShortName", "MI:0001"));
        assertNotNull(persistentParticipant.getBiologicalRole());
        assertNull(persistentParticipant.getBiologicalRole().getFullName());

        participantEnricher.setParticipantListener(new ParticipantEnricherListenerManager(
                new ParticipantEnricherLogger() ,
                new ParticipantEnricherListener() {
                    public void onEnrichmentComplete(Participant participant, EnrichmentStatus status, String message) {
                        assertTrue(participant == persistentParticipant);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++ ;
                    }

                    public void onEnrichmentError(Participant object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        participantEnricher.enrich(persistentParticipant);

        assertNotNull(persistentParticipant.getBiologicalRole());
        assertNotNull(persistentParticipant.getBiologicalRole().getFullName());
        assertEquals(1 , persistentInt);
    }
}
