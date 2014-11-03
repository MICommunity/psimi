package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalOntologyTermUpdater;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * Provides full update of ontologYTerm.
 *
 * - update full properties of Cv Term. See description in FullCvTermUpdater
 * - update children of a term. It will use DefaultCvTermComparator to compare children and add missing children and
 * remove children that are not in fetched ontology term. It will enrich the children of the ontologyTerm
 *
 * It will ignore all other properties of an ontologyTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullOntologyTermUpdater extends MinimalOntologyTermUpdater {
    public FullOntologyTermUpdater(OntologyTermFetcher cvTermFetcher) {
        super(new FullCvTermUpdater<OntologyTerm>(cvTermFetcher));
    }

    protected FullOntologyTermUpdater(FullCvTermUpdater<OntologyTerm> cvTermEnricher) {
        super(cvTermEnricher);
    }
}
