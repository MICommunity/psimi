package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.ParticipantEvidencePoolChangeListener;
import psidev.psi.mi.jami.model.ParticipantEvidencePool;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface ParticipantEvidencePoolEnricherListener extends ParticipantEvidenceEnricherListener<ParticipantEvidencePool>, ParticipantEvidencePoolChangeListener, ParticipantPoolEnricherListener<ParticipantEvidencePool> {

}
