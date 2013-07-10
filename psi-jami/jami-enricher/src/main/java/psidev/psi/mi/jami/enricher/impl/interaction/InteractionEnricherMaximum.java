package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMaximum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEnricherMaximum;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionEnricherMaximum<I extends Interaction, P extends Participant, F extends Feature>
    extends InteractionEnricherMinimum<I , P , F> {


    public ParticipantEnricher<P , F> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ParticipantEnricherMaximum<P , F>();
        return participantEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new CvTermEnricherMaximum();
        return cvTermEnricher;
    }
}
