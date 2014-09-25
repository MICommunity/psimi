package psidev.psi.mi.jami.enricher.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.full.FullInteractionEnricher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalCvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalInteractionEnricher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalParticipantEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.InteractionEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.impl.log.InteractionEnricherLogger;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;

import java.util.Collections;
import java.util.Date;

import static junit.framework.Assert.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 26/07/13
 */
public class MinimalInteractionEnricherTest {

    private MinimalInteractionEnricher interactionEnricher;
    MockCvTermFetcher mockCvTermFetcher;
    private  Interaction persistentInteraction;
    private int persistentInt = 0;

    @Before
    public void setup(){
        persistentInt = 0;
        interactionEnricher = new FullInteractionEnricher();
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

                    public void onEnrichmentError(Interaction object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onUpdatedRigid(Interaction interaction, String oldRigid) {
                        Assert.fail();                    }

                    public void onEnrichmentComplete(Object object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }

                    public void onEnrichmentError(Object object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onShortNameUpdate(Interaction interaction, String oldName) {
                        Assert.fail();
                    }

                    public void onUpdatedDateUpdate(Interaction interaction, Date oldUpdate) {
                        Assert.fail();
                    }

                    public void onCreatedDateUpdate(Interaction interaction, Date oldCreated) {
                        Assert.fail();
                    }

                    public void onInteractionTypeUpdate(Interaction interaction, CvTerm oldType) {
                        Assert.fail();
                    }

                    public void onAddedParticipant(Interaction interaction, Participant addedParticipant) {
                        Assert.fail();
                    }

                    public void onRemovedParticipant(Interaction interaction, Participant removedParticipant) {
                        Assert.fail();
                    }

                    public void onAddedAnnotation(Object o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Object o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onAddedChecksum(Object interactor, Checksum added) {
                        Assert.fail();
                    }

                    public void onRemovedChecksum(Object interactor, Checksum removed) {
                        Assert.fail();
                    }

                    public void onAddedIdentifier(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedIdentifier(Object o, Xref removed) {
                        Assert.fail();
                    }

                    public void onAddedXref(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedXref(Object o, Xref removed) {
                        Assert.fail();
                    }
                }
        ));
        interactionEnricher.enrich(persistentInteraction);
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_cvTermEnricher_but_no_cvTerms() throws EnricherException {

        interactionEnricher.setCvTermEnricher(new MinimalCvTermEnricher(new MockCvTermFetcher()));

        assertNull(persistentInteraction.getInteractionType());

        interactionEnricher.setInteractionEnricherListener( new InteractionEnricherListenerManager(
                // new InteractionEnricherLogger() ,
                new InteractionEnricherListener() {

                    public void onEnrichmentError(Interaction object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onUpdatedRigid(Interaction interaction, String oldRigid) {
                        Assert.fail();
                    }

                    public void onEnrichmentComplete(Object object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }

                    public void onEnrichmentError(Object object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onShortNameUpdate(Interaction interaction, String oldName) {
                        Assert.fail();
                    }

                    public void onUpdatedDateUpdate(Interaction interaction, Date oldUpdate) {
                        Assert.fail();
                    }

                    public void onCreatedDateUpdate(Interaction interaction, Date oldCreated) {
                        Assert.fail();
                    }

                    public void onInteractionTypeUpdate(Interaction interaction, CvTerm oldType) {
                        Assert.fail();
                    }

                    public void onAddedParticipant(Interaction interaction, Participant addedParticipant) {
                        Assert.fail();
                    }

                    public void onRemovedParticipant(Interaction interaction, Participant removedParticipant) {
                        Assert.fail();
                    }

                    public void onAddedAnnotation(Object o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Object o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onAddedChecksum(Object interactor, Checksum added) {
                        Assert.fail();
                    }

                    public void onRemovedChecksum(Object interactor, Checksum removed) {
                        Assert.fail();
                    }

                    public void onAddedIdentifier(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedIdentifier(Object o, Xref removed) {
                        Assert.fail();
                    }

                    public void onAddedXref(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedXref(Object o, Xref removed) {
                        Assert.fail();
                    }
                }
        ));
        interactionEnricher.enrich(persistentInteraction);

        assertNull(persistentInteraction.getInteractionType());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_CvTermEnricher_with_CvTerm() throws EnricherException {
        mockCvTermFetcher = new MockCvTermFetcher();
        interactionEnricher.setCvTermEnricher(new MinimalCvTermEnricher(mockCvTermFetcher));

        mockCvTermFetcher.addEntry("MI:0001", new DefaultCvTerm("ShortName", "FullName", "MI:0001"));

        persistentInteraction.setInteractionType(new DefaultCvTerm("ShortName" , "MI:0001"));
        assertNotNull(persistentInteraction.getInteractionType());
        assertNull(persistentInteraction.getInteractionType().getFullName());

        interactionEnricher.setInteractionEnricherListener( new InteractionEnricherListenerManager(
                // new InteractionEnricherLogger() ,
                new InteractionEnricherListener() {

                    public void onEnrichmentError(Interaction object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onUpdatedRigid(Interaction interaction, String oldRigid) {
                        Assert.fail();
                    }

                    public void onEnrichmentComplete(Object object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }

                    public void onEnrichmentError(Object object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onShortNameUpdate(Interaction interaction, String oldName) {
                        Assert.fail();
                    }

                    public void onUpdatedDateUpdate(Interaction interaction, Date oldUpdate) {
                        Assert.fail();
                    }

                    public void onCreatedDateUpdate(Interaction interaction, Date oldCreated) {
                        Assert.fail();
                    }

                    public void onInteractionTypeUpdate(Interaction interaction, CvTerm oldType) {
                        Assert.fail();
                    }

                    public void onAddedParticipant(Interaction interaction, Participant addedParticipant) {
                        Assert.fail();
                    }

                    public void onRemovedParticipant(Interaction interaction, Participant removedParticipant) {
                        Assert.fail();
                    }

                    public void onAddedAnnotation(Object o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Object o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onAddedChecksum(Object interactor, Checksum added) {
                        Assert.fail();
                    }

                    public void onRemovedChecksum(Object interactor, Checksum removed) {
                        Assert.fail();
                    }

                    public void onAddedIdentifier(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedIdentifier(Object o, Xref removed) {
                        Assert.fail();
                    }

                    public void onAddedXref(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedXref(Object o, Xref removed) {
                        Assert.fail();
                    }
                } )
        );
        interactionEnricher.enrich(persistentInteraction);

        assertNotNull(persistentInteraction.getInteractionType());
        assertNotNull(persistentInteraction.getInteractionType().getFullName());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_participantEnricher_but_no_participant() throws EnricherException {

        interactionEnricher.setParticipantEnricher(new CompositeParticipantEnricher(new MinimalParticipantEnricher<Participant, Feature>()));

        assertEquals(Collections.EMPTY_LIST, persistentInteraction.getParticipants());

        interactionEnricher.setInteractionEnricherListener(new InteractionEnricherListenerManager(
                new InteractionEnricherLogger(),
                new InteractionEnricherListener() {

                    public void onEnrichmentError(Interaction object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onUpdatedRigid(Interaction interaction, String oldRigid) {
                        Assert.fail();
                    }

                    public void onEnrichmentComplete(Object object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }

                    public void onEnrichmentError(Object object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onShortNameUpdate(Interaction interaction, String oldName) {
                        Assert.fail();
                    }

                    public void onUpdatedDateUpdate(Interaction interaction, Date oldUpdate) {
                        Assert.fail();
                    }

                    public void onCreatedDateUpdate(Interaction interaction, Date oldCreated) {
                        Assert.fail();
                    }

                    public void onInteractionTypeUpdate(Interaction interaction, CvTerm oldType) {
                        Assert.fail();
                    }

                    public void onAddedParticipant(Interaction interaction, Participant addedParticipant) {
                        Assert.fail();
                    }

                    public void onRemovedParticipant(Interaction interaction, Participant removedParticipant) {
                        Assert.fail();
                    }

                    public void onAddedAnnotation(Object o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Object o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onAddedChecksum(Object interactor, Checksum added) {
                        Assert.fail();
                    }

                    public void onRemovedChecksum(Object interactor, Checksum removed) {
                        Assert.fail();
                    }

                    public void onAddedIdentifier(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedIdentifier(Object o, Xref removed) {
                        Assert.fail();
                    }

                    public void onAddedXref(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedXref(Object o, Xref removed) {
                        Assert.fail();
                    }
                }
        ));
        interactionEnricher.enrich(persistentInteraction);

        assertNull(persistentInteraction.getInteractionType());
        assertEquals(1 , persistentInt);
    }

    @Test
    public void test_enrichment_with_participantEnricher_with_participant() throws EnricherException {

        persistentInteraction.addParticipant(new DefaultParticipant(new DefaultInteractor("InteractorName")));

        interactionEnricher.setParticipantEnricher(new CompositeParticipantEnricher(new MinimalParticipantEnricher()));

        //TODO assertEquals(Collections.EMPTY_LIST, persistentInteraction.getParticipants());

        interactionEnricher.setInteractionEnricherListener(new InteractionEnricherListenerManager(
                new InteractionEnricherLogger(),
                new InteractionEnricherListener() {

                    public void onEnrichmentError(Interaction object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onUpdatedRigid(Interaction interaction, String oldRigid) {
                        Assert.fail();
                    }

                    public void onEnrichmentComplete(Object object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentInteraction);
                        assertEquals(EnrichmentStatus.SUCCESS, status);
                        persistentInt++;
                    }

                    public void onEnrichmentError(Object object, String message, Exception e) {
                        Assert.fail();
                    }

                    public void onShortNameUpdate(Interaction interaction, String oldName) {
                        Assert.fail();
                    }

                    public void onUpdatedDateUpdate(Interaction interaction, Date oldUpdate) {
                        Assert.fail();
                    }

                    public void onCreatedDateUpdate(Interaction interaction, Date oldCreated) {
                        Assert.fail();
                    }

                    public void onInteractionTypeUpdate(Interaction interaction, CvTerm oldType) {
                        Assert.fail();
                    }

                    public void onAddedParticipant(Interaction interaction, Participant addedParticipant) {
                        Assert.fail();
                    }

                    public void onRemovedParticipant(Interaction interaction, Participant removedParticipant) {
                        Assert.fail();
                    }

                    public void onAddedAnnotation(Object o, Annotation added) {
                        Assert.fail();
                    }

                    public void onRemovedAnnotation(Object o, Annotation removed) {
                        Assert.fail();
                    }

                    public void onAddedChecksum(Object interactor, Checksum added) {
                        Assert.fail();
                    }

                    public void onRemovedChecksum(Object interactor, Checksum removed) {
                        Assert.fail();
                    }

                    public void onAddedIdentifier(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedIdentifier(Object o, Xref removed) {
                        Assert.fail();
                    }

                    public void onAddedXref(Object o, Xref added) {
                        Assert.fail();
                    }

                    public void onRemovedXref(Object o, Xref removed) {
                        Assert.fail();
                    }
                }
        ));
        interactionEnricher.enrich(persistentInteraction);

        assertNull(persistentInteraction.getInteractionType());
        assertEquals(1 , persistentInt);
    }

}
