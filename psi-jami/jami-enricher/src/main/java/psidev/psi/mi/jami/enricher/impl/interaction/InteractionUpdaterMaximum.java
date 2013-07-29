package psidev.psi.mi.jami.enricher.impl.interaction;


import psidev.psi.mi.jami.model.*;

/**
 * An implementation of the InteractionEnricher which updates to the maximum level
 * It provides default maximum updaters.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionUpdaterMaximum<I extends Interaction, P extends Participant, F extends Feature>
        extends InteractionUpdaterMinimum<I , P , F> {
    /*
    @Override
    public void processInteraction(I interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);
    }  */

    /*
    @Override
    public ParticipantEnricher<P, F> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ParticipantUpdaterMaximum<P,F>();
        return participantEnricher;
    }

    @Override
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermUpdater();
        return cvTermEnricher;
    } */
}
