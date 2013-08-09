package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MinimumParticipantEnricher<P extends Participant , F extends Feature>
        extends AbstractParticipantEnricher<P , F>  {

    @Override
    protected void processParticipant(P participantToEnrich) throws EnricherException {
    }
}
