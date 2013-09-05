package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * Ontology fetcher that delegates to different ontologyTermFetchers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */

public class OntologyTermCompositeFetcher extends CvTermCompositeFetcherTemplate<OntologyTerm, OntologyTermFetcher> implements OntologyTermFetcher{
}
