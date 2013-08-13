package psidev.psi.mi.jami.enricher.impl.interaction;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.MinimumCvTermEnricher;
import psidev.psi.mi.jami.enricher.listener.interaction.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.interaction.InteractionEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.interaction.InteractionEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.participant.MinimumParticipantEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;

import java.util.Collections;

import static junit.framework.Assert.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 26/07/13
 */
public class MinimumInteractionEnricherTest {

    private InteractionEnricher interactionEnricher;
    MockCvTermFetcher mockCvTermFetcher;
    private  Interaction persistentInteraction;
    private int persistentInt = 0;

    @Before
    public void setup(){
        persistentInt = 0;
        interactionEnricher = new MinimumInteractionEnricher();
        persistentInteraction = new DefaultInteraction("shortName");
    }

    @Test
    public void test_enrichers_begin_empty(){
        assertNull(interactionEnricher.getParticipantEnricher());
        assertNull(interactionEnricher.getCvTermEnricher());
        assertNull(interactionEnricher.getInteractionEnricherListener());
    }

    @Test
    public void test_enrichment_without_enrichers_succeeds() throws EnricherException {
        assertNull(interactionEnricher.getCvTermEnricher() );
        assertNull(interactionEnricher.getParticipantEnricher() );

        interactionEnricher.setInteractionEnricherListener(new InteractionEnricherListenerManager(
                //  new InteractionEnricherLogger(),
                new InteractionEnricherListener() {
                    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message) {
                        assertTrue(interaction == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }
                }
        ));
        interactionEnricher.enrichInteraction(persistentInteraction);
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_cvTermEnricher_but_no_cvTerms() throws EnricherException {

        interactionEnricher.setCvTermEnricher(new MinimumCvTermEnricher(new MockCvTermFetcher()));

        assertNull(persistentInteraction.getInteractionType());

        interactionEnricher.setInteractionEnricherListener( new InteractionEnricherListenerManager(
                // new InteractionEnricherLogger() ,
                new InteractionEnricherListener() {
                    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message) {
                        assertTrue(interaction == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                }
        ));
        interactionEnricher.enrichInteraction(persistentInteraction);

        assertNull(persistentInteraction.getInteractionType());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_CvTermEnricher_with_CvTerm() throws EnricherException {
        mockCvTermFetcher = new MockCvTermFetcher();
        interactionEnricher.setCvTermEnricher(new MinimumCvTermEnricher(mockCvTermFetcher));

        mockCvTermFetcher.addEntry("MI:0001" , new DefaultCvTerm("ShortName" , "FullName" , "MI:0001"));
        interactionEnricher.getCvTermEnricher().setCvTermFetcher(mockCvTermFetcher);


        persistentInteraction.setInteractionType(new DefaultCvTerm("ShortName" , "MI:0001"));
        assertNotNull(persistentInteraction.getInteractionType());
        assertNull(persistentInteraction.getInteractionType().getFullName());

        interactionEnricher.setInteractionEnricherListener( new InteractionEnricherListenerManager(
                // new InteractionEnricherLogger() ,
                new InteractionEnricherListener() {
                    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message) {
                        assertTrue(interaction == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                        persistentInt ++;
                    }
                } )
        );
        interactionEnricher.enrichInteraction(persistentInteraction);

        assertNotNull(persistentInteraction.getInteractionType());
        assertNotNull(persistentInteraction.getInteractionType().getFullName());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_participantEnricher_but_no_participant() throws EnricherException {

        interactionEnricher.setParticipantEnricher(new MinimumParticipantEnricher());

        assertEquals(Collections.EMPTY_LIST, persistentInteraction.getParticipants());

        interactionEnricher.setInteractionEnricherListener(new InteractionEnricherListenerManager(
                new InteractionEnricherLogger(),
                new InteractionEnricherListener() {
                    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message) {
                        assertTrue(interaction == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }
                }
        ));
        interactionEnricher.enrichInteraction(persistentInteraction);

        assertNull(persistentInteraction.getInteractionType());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_participantEnricher_with_participant() throws EnricherException {

        persistentInteraction.addParticipant(new DefaultParticipant(new DefaultInteractor("InteractorName")));

        interactionEnricher.setParticipantEnricher(new MinimumParticipantEnricher());

        //TODO assertEquals(Collections.EMPTY_LIST, persistentInteraction.getParticipants());

        interactionEnricher.setInteractionEnricherListener(new InteractionEnricherListenerManager(
                new InteractionEnricherLogger(),
                new InteractionEnricherListener() {
                    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message) {
                        assertTrue(interaction == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }
                }
        ));
        interactionEnricher.enrichInteraction(persistentInteraction);

        assertNull(persistentInteraction.getInteractionType());
        assertEquals(1 , persistentInt);
    }

}
