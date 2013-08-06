package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;

/**
 * The enricher for Interactions which can enrich a single interaction or a collection.
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
public interface InteractionEnricher
        <I extends Interaction ,  P extends Participant , F extends Feature> {

    /**
     * Enrichment of a single interaction.
     * @param interactionToEnrich   The interaction which is to be enriched
     * @throws EnricherException    Thrown if a fetcher encounters problems.
     */
    public void enrichInteraction(I interactionToEnrich) throws EnricherException;

    /**
     * Enrichment of a collection of interactions
     * @param interactionsToEnrich  The interactions to be enriched
     * @throws EnricherException    Thrown if a fetcher encounters problems.
     */
    public void enrichInteractions(Collection<I> interactionsToEnrich) throws EnricherException;

    /**
     * The current sub enricher for participants.
     * @return  The enricher for participants. Can be null.
     */
    public ParticipantEnricher<P , F> getParticipantEnricher();

    /**
     * Sets the sub enricher for participants.
     * @param participantEnricher   The enricher for participants. Can be null.
     */
    public void setParticipantEnricher(ParticipantEnricher<P , F> participantEnricher);

    /**
     * The current sub enricher for CvTerms.
     * @param cvTermEnricher The enricher for cvTerms. Can be null.
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);

    /**
     * Sets the sub enricher for CvTerms.
     * @return  The enricher for CvTerms. Can be null.
     */
    public CvTermEnricher getCvTermEnricher();

    /**
     * The listener for changes made to interactions.
     * @return  The listener for interaction changes. Can be null.
     */
    public InteractionEnricherListener getInteractionEnricherListener();

    /**
     * Sets the listener to be used when interactions are changed.
     * @param listener  The listener for interaction changes. Can be null.
     */
    public void setInteractionEnricherListener(InteractionEnricherListener listener);

}
