package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * The entity pool enricher is an enricher which can enrich either single entity pool or a collection.
 * Sub enrichers: CvTerm, Organism.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface EntityPoolEnricher<T extends EntityPool, F extends Feature> extends ParticipantEnricher<T,F>{
    public void setParticipantEnricher(ParticipantEnricher interactorEnricher);

    public ParticipantEnricher getParticipantEnricher();

    public void setParticipantComparator(Comparator<Entity> interactorComparator);

    public Comparator<Entity> getParticipantComparator();
}
