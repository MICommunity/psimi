package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.Complex;

/**
 * The enricher for Interactions which can enrich a single interaction or a collection.
 * The interaction enricher has no fetcher.
 * Sub enrichers: Participant, CvTerm.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface ComplexEnricher extends ModelledInteractionEnricher<Complex>{

}
