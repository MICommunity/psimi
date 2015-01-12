package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;

/**
 * The enricher for Interactions which can enrich a single interaction or a collection.
 * The interaction enricher has no fetcher.
 * Sub enrichers: Participant, CvTerm.
 *
 * @param <I>   The type of interaction to be enriched
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEnricher <I extends Interaction> extends MIEnricher<I>{

    /**
     * The current sub enricher for participants.
     * @return  The enricher for participants. Can be null.
     */
    public ParticipantEnricher getParticipantEnricher();

    /**
     * Sets the sub enricher for CvTerms.
     * @return  The enricher for CvTerms. Can be null.
     */
    public CvTermEnricher<CvTerm> getCvTermEnricher();

    /**
     * The listener for changes made to interactions.
     * @return  The listener for interaction changes. Can be null.
     */
    public InteractionEnricherListener<I> getInteractionEnricherListener();

    public void setParticipantEnricher(ParticipantEnricher enricher);

    public void setCvTermEnricher(CvTermEnricher<CvTerm> enricher);

    public void setInteractionEnricherListener(InteractionEnricherListener<I> listener);
}
