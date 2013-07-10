package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantUpdaterMinimum;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionUpdaterMinimum<I extends Interaction, P extends Participant, F extends Feature>
        extends AbstractInteractionEnricher <I , P , F>{



    public ParticipantEnricher<P, F> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ParticipantUpdaterMinimum<P,F>();
        return participantEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new CvTermUpdaterMinimum();
        return cvTermEnricher;
    }
}
