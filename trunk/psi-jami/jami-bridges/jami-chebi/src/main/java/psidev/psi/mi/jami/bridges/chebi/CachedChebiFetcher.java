package psidev.psi.mi.jami.bridges.chebi;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.AbstractCachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    public CachedChebiFetcher() {
        super(CHEBI_CACHE_NAME);
        this.chebiFetcher = new ChebiFetcher();
    }

    public BioactiveEntity fetchByIdentifier(String identifier) throws BridgeFailedException {
        if (identifier != null){
            final String key = "GET_ENTITY_BY_IDENTIFIER_"+identifier;
            Object object = getFromCache(key);
            if (object != null){
                return (BioactiveEntity)object;
            }
            else{
                BioactiveEntity entity = chebiFetcher.fetchByIdentifier(identifier);
                storeInCache(key, entity);
                return entity;
            }
        }
        return chebiFetcher.fetchByIdentifier(identifier);
    }

    public Collection<BioactiveEntity> fetchByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        if (identifiers != null){
            List<String> ids = new ArrayList<String>(identifiers);
            Collections.sort(ids);
            String key = "GET_ENTITIES_BY_IDENTIFIERS";
            for (String id : ids){
                key= key+"_"+id;
            }
            Object object = getFromCache(key);
            if (object != null){
                return (Collection<BioactiveEntity>)object;
            }
            else{
                Collection<BioactiveEntity> entity = chebiFetcher.fetchByIdentifiers(identifiers);
                storeInCache(key, entity);
                return entity;
            }
        }
        return chebiFetcher.fetchByIdentifiers(identifiers);
    }
}
