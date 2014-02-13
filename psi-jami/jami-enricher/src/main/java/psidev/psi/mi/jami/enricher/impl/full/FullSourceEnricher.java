package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.enricher.impl.FullCvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalSourceEnricher;
import psidev.psi.mi.jami.model.Source;

/**
 * Provides full enrichment of a Source.
 *
 * - enrich all properties of CvTerm (see FullCvTermEnricher for more details)
 * - enrich publication using publication enricher. If the publication is not null in the source to enrich,
 * it will ignore the publication loaded from the fetched source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class FullSourceEnricher extends MinimalSourceEnricher {
    public FullSourceEnricher(SourceFetcher cvTermFetcher) {
        super(new FullCvTermEnricher<Source>(cvTermFetcher));
    }
}
