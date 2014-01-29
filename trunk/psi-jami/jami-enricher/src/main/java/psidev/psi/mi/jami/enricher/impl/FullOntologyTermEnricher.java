package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;

/**
 * Full Enricher for OntologyTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullOntologyTermEnricher extends MinimalOntologyTermEnricher{
    public FullOntologyTermEnricher(OntologyTermFetcher cvTermFetcher) {
        super(new FullCvTermEnricher(cvTermFetcher));
    }
}
