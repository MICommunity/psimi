package psidev.psi.mi.jami.bridges.chebi;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.AbstractCachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

import java.util.Collection;

/**
 * A Cached version of the chebi fetcher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/09/13</pre>
 */

public class CachedChebiFetcher extends AbstractCachedFetcher implements BioactiveEntityFetcher{

    public static final String CHEBI_CACHE_NAME = "chebi-cache";

    private ChebiFetcher chebiFetcher;

    public CachedChebiFetcher() throws BridgeFailedException {
        super(CHEBI_CACHE_NAME);
        this.chebiFetcher = new ChebiFetcher();
    }

    public BioactiveEntity fetchBioactiveEntityByIdentifier(String identifier) throws BridgeFailedException {
        return chebiFetcher.fetchBioactiveEntityByIdentifier(identifier);
    }

    public Collection<BioactiveEntity> fetchBioactiveEntitiesByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        return chebiFetcher.fetchBioactiveEntitiesByIdentifiers(identifiers);
    }
}
