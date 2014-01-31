package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.Polymer;

/**
 * The Polymer enricher is an enricher which can enrich either single polymer or a collection.
 * Sub enrichers: CvTerm, Organism.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface PolymerEnricher<P extends Polymer> extends InteractorEnricher<P>{

}
