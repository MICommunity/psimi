package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.ExperimentalParticipantPool;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * The Participant pool enricher is an enricher which can enrich either single Participant pool or a collection.
 * Sub enrichers: CvTerm, Organism.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface ExperimentalParticipantPoolEnricher extends ParticipantEvidenceEnricher<ExperimentalParticipantPool>,
        ParticipantPoolEnricher<ExperimentalParticipantPool, FeatureEvidence>{

}
