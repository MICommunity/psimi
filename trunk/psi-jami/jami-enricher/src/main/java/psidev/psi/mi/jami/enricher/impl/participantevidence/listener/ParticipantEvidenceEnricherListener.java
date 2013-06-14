package psidev.psi.mi.jami.enricher.impl.participantevidence.listener;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:58
 */
public interface ParticipantEvidenceEnricherListener {

    public void onParticipantEvidenceEnriched(ParticipantEvidence participantEvidence, String status);

}
