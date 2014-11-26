package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.ParticipantPoolChangeListener;
import psidev.psi.mi.jami.model.ExperimentalParticipantPool;

/**
 * Experimental Participant pool enricher listener
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface ExperimentalParticipantPoolEnricherListener extends ParticipantEvidenceEnricherListener<ExperimentalParticipantPool>,
        ParticipantPoolChangeListener<ExperimentalParticipantPool>{

}
