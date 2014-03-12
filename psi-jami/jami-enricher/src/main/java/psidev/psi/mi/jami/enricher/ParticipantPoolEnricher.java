package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * The Participant pool enricher is an enricher which can enrich either single Participant pool or a collection.
 * Sub enrichers: CvTerm, Organism.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface ParticipantPoolEnricher<T extends Participant, F extends Feature> extends ParticipantEnricher<T,F>{

    public ParticipantEnricher getParticipantEnricher();

    public void setParticipantComparator(Comparator<Participant> interactorComparator);

    public Comparator<Participant> getParticipantComparator();
}
