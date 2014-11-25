package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * Finds ontology terms in the Ontology Lookup Service
 * as well as having options to recursively find parents and or children.
 *
 * Extends CvTermFetcher with OntologyTerms.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public interface OntologyTermFetcher extends CvTermFetcher<OntologyTerm>{
}
