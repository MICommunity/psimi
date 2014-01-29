package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;

/**
 * Full ontology term updater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullOntologyTermUpdater extends MinimalOntologyTermUpdater{
    public FullOntologyTermUpdater(OntologyTermFetcher cvTermFetcher) {
        super(new FullCvTermUpdater(cvTermFetcher));
    }
}
