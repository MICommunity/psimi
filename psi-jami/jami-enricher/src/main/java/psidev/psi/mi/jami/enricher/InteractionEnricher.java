package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;

/**
 * The enricher for Interactions. which can enrich a single interaction or a collection.
 * The interaction enricher has subEnrichers for participants and cvTerms.
 * It has no fetcher.
 *
 * @param <I>   The type of interaction to be enriched
 * @param <P>   The type of participants in the interaction
 * @param <F>   The type of features in the participants.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEnricher <I extends Interaction ,  P extends Participant , F extends Feature> {

    /**
     * Enrichment of a single interaction.
     * @param interactionToEnrich   The interaction which is to be enriched
     * @throws EnricherException
     */
    public void enrichInteraction(I interactionToEnrich) throws EnricherException;

    /**
     * Enrichment of a collection of interactions
     * @param interactionsToEnrich  The interactions to be enriched
     * @throws EnricherException
     */
    public void enrichInteractions(Collection<I> interactionsToEnrich) throws EnricherException;

    /**
     * The participant enricher
     * @return
     */
    public ParticipantEnricher<P , F> getParticipantEnricher();
    public void setParticipantEnricher(ParticipantEnricher<P , F> participantEnricher);

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    public CvTermEnricher getCvTermEnricher();


    public InteractionEnricherListener getInteractionEnricherListener();
    public void setInteractionEnricherListener(InteractionEnricherListener listener);

}
