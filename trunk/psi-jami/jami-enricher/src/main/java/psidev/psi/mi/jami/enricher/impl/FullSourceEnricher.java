package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;

/**
 * Full enricher for sources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullSourceEnricher extends MinimalSourceEnricher{
    public FullSourceEnricher(SourceFetcher cvTermFetcher) {
        super(new FullCvTermEnricher(cvTermFetcher));
    }
}
