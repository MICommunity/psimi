package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * The minimum participant updater has default MinimumUpdaters for Proteins, CvTerms, and Features.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MinimumParticipantUpdater<P extends Participant , F extends Feature>
        extends AbstractParticipantEnricher <P , F>{

    @Override
    protected void processParticipant(P participantToEnrich) throws EnricherException {

    }
}
