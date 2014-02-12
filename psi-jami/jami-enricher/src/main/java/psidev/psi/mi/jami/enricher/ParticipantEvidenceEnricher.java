package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * The entity pool enricher is an enricher which can enrich either single entity pool or a collection.
 * Sub enrichers: CvTerm, Organism.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface ParticipantEvidenceEnricher<T extends ExperimentalEntity, F extends FeatureEvidence> extends ParticipantEnricher<T,F>{
    public void setOrganismEnricher(OrganismEnricher organismEnricher);

    public OrganismEnricher getOrganismEnricher();

}
