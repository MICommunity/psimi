package psidev.psi.mi.jami.enricher.impl.interaction;


import psidev.psi.mi.jami.model.*;

/**
 * An implementation of the InteractionEnricher which enriches to the maximum level
 * It provides default maximum enrichers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class MaximumInteractionEnricher<I extends Interaction, P extends Participant, F extends Feature>
        extends MinimumInteractionEnricher<I , P , F> {


    /*@Override
    public void processInteraction(I interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);
    } */

    /*
    @Override
    public ParticipantEnricher<P , F> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new MaximumParticipantEnricher<P , F>();
        return participantEnricher;
    }

    @Override
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermEnricher();
        return cvTermEnricher;
    }   */
}
