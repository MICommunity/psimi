package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * The ontologyTerm enricher is an enricher which can enrich either single ontologyTerm or a collection.
 * A ontology term enricher must be initiated with a fetcher.
 * Sub enrichers: none.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public interface OntologyTermEnricher extends CvTermEnricher<OntologyTerm>{
}
