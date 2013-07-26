package psidev.psi.mi.jami.enricher.impl.participant;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.feature.FeatureEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.protein.ProteinEnricherMinimum;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
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
public class ParticipantEnricherMinimumTest {

    private ParticipantEnricher participantEnricher;

    private Participant persistentParticipant;
    private int persistentInt = 0;

    @Before
    public void setup(){
        participantEnricher = new ParticipantEnricherMinimum();
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
                    public void onParticipantEnriched(Participant participant, EnrichmentStatus status, String message) {
                        assertTrue(participant == persistentParticipant);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++ ;
                    }
                }
        ));
        participantEnricher.enrichParticipant(persistentParticipant);
        assertEquals(1 , persistentInt);
    }


    @Test
    public void test_enrichment_with_cvTermEnricher_but_no_cvTerms() throws EnricherException {

        participantEnricher.setCvTermEnricher(new CvTermEnricherMinimum());
        participantEnricher.getCvTermEnricher().setCvTermFetcher(new MockCvTermFetcher());

        participantEnricher.setParticipantListener(new ParticipantEnricherListenerManager(
                new ParticipantEnricherLogger() ,
                new ParticipantEnricherListener() {
                    public void onParticipantEnriched(Participant participant, EnrichmentStatus status, String message) {
                        assertTrue(participant == persistentParticipant);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++ ;
                    }
                }
        ));

        participantEnricher.enrichParticipant(persistentParticipant);

        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_CvTermEnricher_enriches_CvTerms() throws EnricherException {

        participantEnricher.setCvTermEnricher(new CvTermEnricherMinimum());
        MockCvTermFetcher mockCvTermFetcher = new MockCvTermFetcher();
        mockCvTermFetcher.addCvTerm("MI:0001" , new DefaultCvTerm("ShortName" , "FullName" , "MI:0001"));
        participantEnricher.getCvTermEnricher().setCvTermFetcher(mockCvTermFetcher);


        persistentParticipant.setBiologicalRole(new DefaultCvTerm("ShortName", "MI:0001"));
        assertNotNull(persistentParticipant.getBiologicalRole());
        assertNull(persistentParticipant.getBiologicalRole().getFullName());

        participantEnricher.setParticipantListener(new ParticipantEnricherListenerManager(
                new ParticipantEnricherLogger() ,
                new ParticipantEnricherListener() {
                    public void onParticipantEnriched(Participant participant, EnrichmentStatus status, String message) {
                        assertTrue(participant == persistentParticipant);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++ ;
                    }
                }
        ));

        participantEnricher.enrichParticipant(persistentParticipant);

        assertNotNull(persistentParticipant.getBiologicalRole());
        assertNotNull(persistentParticipant.getBiologicalRole().getFullName());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_proteinEnricher_receives_featureEnricher_as_listener_when_added_first(){
        ProteinEnricher proteinEnricher = new ProteinEnricherMinimum();

        participantEnricher.setProteinEnricher(proteinEnricher);
        assertNull(proteinEnricher.getProteinEnricherListener());

        FeatureEnricher featureEnricher = new FeatureEnricherMinimum();

        participantEnricher.setFeatureEnricher(featureEnricher);
        assertNotNull(proteinEnricher.getProteinEnricherListener());
        assertTrue(proteinEnricher.getProteinEnricherListener() == featureEnricher);
    }

    @Test
    public void test_proteinEnricher_receives_featureEnricher_as_listener_when_added_second(){
        ProteinEnricher proteinEnricher = new ProteinEnricherMinimum();
        FeatureEnricher featureEnricher = new FeatureEnricherMinimum();

        assertNull(participantEnricher.getProteinEnricher());

        participantEnricher.setFeatureEnricher(featureEnricher);

        assertNull(participantEnricher.getProteinEnricher());
        assertNull(proteinEnricher.getProteinEnricherListener());

        participantEnricher.setProteinEnricher(proteinEnricher);

        assertNotNull(proteinEnricher.getProteinEnricherListener());
        assertTrue(proteinEnricher.getProteinEnricherListener() == featureEnricher);
    }
}
