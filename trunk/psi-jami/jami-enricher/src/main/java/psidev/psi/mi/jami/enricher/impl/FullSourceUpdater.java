package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;

/**
 * Full source updater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullSourceUpdater extends MinimalSourceUpdater{
    public FullSourceUpdater(SourceFetcher cvTermFetcher) {
        super(new FullCvTermUpdater<psidev.psi.mi.jami.model.Source>(cvTermFetcher));
    }
}
