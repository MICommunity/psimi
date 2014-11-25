package psidev.psi.mi.jami.bridges.ols;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;


/**
 *
 *
 * Code for the cache based on the CachedOntologyService at uk.ac.ebi.intact.bridges.olslight;
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class CachedOlsSourceFetcher extends CachedOlsFetcher<psidev.psi.mi.jami.model.Source> implements SourceFetcher{

    public static final String CACHE_NAME = "source-cache";


    public CachedOlsSourceFetcher() throws BridgeFailedException {
        super(CACHE_NAME, new OlsSourceFetcher());
    }
}