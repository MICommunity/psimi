package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;

/**
 * The enricher for Interactions which can enrich a single interaction or a collection.
 * The interaction enricher has subEnrichers for participants and cvTerms.
 * It has no fetcher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimalInteractionEnricher<I extends Interaction, P extends Participant, F extends Feature>
        implements InteractionEnricher<I , P , F> {

    private InteractionEnricherListener listener;
    private ParticipantEnricher<P , F> participantEnricher;
    private CvTermEnricher cvTermEnricher;

    /**
     * Enrichment of a single interaction.
     * @param interactionToEnrich   The interaction which is to be enriched
     * @throws EnricherException    Thrown if a fetcher encounters problems.
     */
    public void enrich(I interactionToEnrich) throws EnricherException {
        if ( interactionToEnrich == null )
            throw new IllegalArgumentException("Attempted to enrich null interaction.") ;

        // Enrich interaction type
        processInteractionType(interactionToEnrich);

        // Enrich all participants
        processParticipants(interactionToEnrich);

        // Enrich remaining properties
        processInteraction(interactionToEnrich);

        if(listener != null)
            listener.onEnrichmentComplete(interactionToEnrich , EnrichmentStatus.SUCCESS , "Interaction successfully enriched.");
    }

    public void enrich(Collection<I> objects) throws EnricherException {
        if( objects == null )
            throw new IllegalArgumentException("Cannot enrich a null collection of interactions.");
        for(I i : objects){
            enrich(i);
        }
    }

    /**
     * The current sub enricher for CvTerms.
     * @param cvTermEnricher The enricher for cvTerms. Can be null.
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    /**
     * Sets the sub enricher for CvTerms.
     * @return  The enricher for CvTerms. Can be null.
     */
    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    /**
     * Sets the sub enricher for participants.
     * @param participantEnricher   The enricher for participants. Can be null.
     */
    public void setParticipantEnricher(ParticipantEnricher<P , F> participantEnricher){
        this.participantEnricher = participantEnricher;
    }
    /**
     * The current sub enricher for participants.
     * @return  The enricher for participants. Can be null.
     */
    public ParticipantEnricher<P , F> getParticipantEnricher(){
        return this.participantEnricher;
    }

    /**
     * The listener for changes made to interactions.
     * @return  The listener for interaction changes. Can be null.
     */
    public InteractionEnricherListener getInteractionEnricherListener() {
        return listener;
    }

    /**
     * Sets the listener to be used when interactions are changed.
     * @param listener  The listener for interaction changes. Can be null.
     */
    public void setInteractionEnricherListener(InteractionEnricherListener listener) {
        this.listener = listener;
    }

    protected void processInteractionType(I interactionToEnrich) throws EnricherException {
        if( getCvTermEnricher() != null &&
                interactionToEnrich.getInteractionType() != null)
            getCvTermEnricher().enrich(interactionToEnrich.getInteractionType());
    }

    protected void processParticipants(I interactionToEnrich) throws EnricherException {
        if( getParticipantEnricher() != null )
            getParticipantEnricher().enrich(interactionToEnrich.getParticipants());
    }

    /**
     * The strategy used for enriching the interaction.
     * Can be overwritten to change the behaviour.
     * @param interactionToEnrich   The interaction being enriched.
     * @throws EnricherException    Thrown if a fetcher encounters a problem.
     */
    protected void processInteraction(I interactionToEnrich) throws EnricherException {
        // do nothing by default
    }
}
