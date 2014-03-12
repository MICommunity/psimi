package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.ParticipantEvidenceChangeListener;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface ParticipantEvidenceEnricherListener<P extends ParticipantEvidence> extends ParticipantEnricherListener<P>, ParticipantEvidenceChangeListener<P> {

}
