package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.model.Source;

/**
 * Source fetcher that delegates to different sourceFetchers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */

public class SourceCompositeFetcher extends CvTermCompositeFetcherTemplate<Source, SourceFetcher> implements SourceFetcher{
}
