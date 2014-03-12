package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.ParticipantEvidencePool;

/**
 * Listener for changes in an experimental entity pool
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface ParticipantEvidencePoolChangeListener extends ParticipantPoolChangeListener<ParticipantEvidencePool>, ParticipantEvidenceChangeListener<ParticipantEvidencePool> {

}
