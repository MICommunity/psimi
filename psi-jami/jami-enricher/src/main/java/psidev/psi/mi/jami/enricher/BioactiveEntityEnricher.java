package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * An enricher for bioactive entities which can either enrich a single entity or a collection.
 * The enricher must be initiated with a fetcher.
 * Sub enrichers: None.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  07/08/13
 */
public interface BioactiveEntityEnricher extends InteractorEnricher<BioactiveEntity>{
}
