package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * The Participant pool enricher is an enricher which can enrich either single Participant pool or a collection.
 * Sub enrichers: CvTerm, Organism.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface ParticipantEvidenceEnricher<T extends ParticipantEvidence> extends ParticipantEnricher<T,FeatureEvidence>{

    public OrganismEnricher getOrganismEnricher();

    public void setOrganismEnricher(OrganismEnricher enricher);

}
