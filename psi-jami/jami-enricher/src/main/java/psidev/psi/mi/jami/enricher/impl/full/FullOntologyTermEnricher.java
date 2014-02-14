package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.enricher.impl.FullCvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalOntologyTermEnricher;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * Provides full enrichment of ontologYTerm.
 *
 * - enrich full properties of Cv Term. See description in FullCvTermEnricher
 * - enrich children of a term. It will use DefaultCvTermComparator to compare children and add missing children without
 * removing any existing children. It will enrich the children of the ontologyTerm but does not go deeper in the hierarchy
 *
 * It will ignore all other properties of an ontologyTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullOntologyTermEnricher extends MinimalOntologyTermEnricher {
    public FullOntologyTermEnricher(OntologyTermFetcher cvTermFetcher) {
        super(new FullCvTermEnricher<OntologyTerm>(cvTermFetcher));
    }
}
