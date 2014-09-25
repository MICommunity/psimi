package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalSourceUpdater;
import psidev.psi.mi.jami.model.Source;

/**
 * Provides full update of a Source.
 *
 * - update all properties of CvTerm (see FullCvTermUpdater for more details)
 * - update publication properties using publication updater. If the publication in the source to enrich is different from the
 * one from the fetched source (see DefaultPublicationComparator for more details), it will override the publication with the one from the fetched source before enriching it with the publication
 * enricher,
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullSourceUpdater extends MinimalSourceUpdater {
    public FullSourceUpdater(SourceFetcher cvTermFetcher) {
        super(new FullCvTermUpdater<Source>(cvTermFetcher));
    }
}
