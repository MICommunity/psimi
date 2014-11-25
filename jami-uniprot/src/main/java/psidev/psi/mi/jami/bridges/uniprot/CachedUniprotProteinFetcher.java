package psidev.psi.mi.jami.bridges.uniprot;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.AbstractCachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 14/05/13
 */
public class CachedUniprotProteinFetcher
        extends AbstractCachedFetcher
        implements ProteinFetcher {

    public static final String CACHE_NAME = "uniprot-service-cache";
    private ProteinFetcher proteinFetcher;

    public CachedUniprotProteinFetcher() {
        super(CACHE_NAME);
        proteinFetcher = new UniprotProteinFetcher();
    }

    public Collection<Protein> fetchByIdentifier(String identifier) throws BridgeFailedException {
        final String key = "GET_PROTEINS_BY_ACCESSION_"+identifier;
        Object data = getFromCache( key );
        if( data == null) {
            data = proteinFetcher.fetchByIdentifier(identifier);
            storeInCache(key , data);
        }
        return (Collection<Protein>)data;
    }

    public Collection<Protein> fetchByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        if (identifiers != null){
            List<String> ids = new ArrayList<String>(identifiers);
            Collections.sort(ids);
            String key = "GET_PROTEINS_BY_IDENTIFIERS";
            for (String id : ids){
                key= key+"_"+id;
            }
            Object object = getFromCache(key);
            if (object != null){
                return (Collection<Protein>)object;
            }
            else{
                Collection<Protein> entity = proteinFetcher.fetchByIdentifiers(identifiers);
                storeInCache(key, entity);
                return entity;
            }
        }
        return proteinFetcher.fetchByIdentifiers(identifiers);
    }
}
