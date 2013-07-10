package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMaximum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEvidenceEnricherMaximum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantUpdaterMaximum;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionUpdaterMaximum<I extends Interaction, P extends Participant, F extends Feature>
        extends InteractionUpdaterMinimum<I , P , F> {



    public ParticipantEnricher<P, F> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ParticipantUpdaterMaximum<P,F>();
        return participantEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new CvTermUpdaterMaximum();
        return cvTermEnricher;
    }
}
