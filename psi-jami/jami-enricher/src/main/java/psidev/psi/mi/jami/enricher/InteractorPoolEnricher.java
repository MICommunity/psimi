package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;

import java.util.Comparator;

/**
 * The Interactor pool enricher is an enricher which can enrich either single interactor pool or a collection.
 * Sub enrichers: CvTerm, Organism.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface InteractorPoolEnricher extends InteractorEnricher<InteractorPool>{
    public void setInteractorEnricher(InteractorEnricher<Interactor> interactorEnricher);

    public InteractorEnricher<Interactor> getInteractorEnricher();

    public void setInteractorComparator(Comparator<Interactor> interactorComparator);

    public Comparator<Interactor> getInteractorComparator();
}
