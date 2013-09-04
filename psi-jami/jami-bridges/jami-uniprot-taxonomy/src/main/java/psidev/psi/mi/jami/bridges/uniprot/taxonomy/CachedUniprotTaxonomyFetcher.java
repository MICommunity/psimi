package psidev.psi.mi.jami.bridges.uniprot.taxonomy;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class CachedUniprotTaxonomyFetcher
        extends UniprotTaxonomyFetcher
        implements CachedFetcher , OrganismFetcher{

    private final Logger log = LoggerFactory.getLogger(CachedUniprotTaxonomyFetcher.class.getName());

    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/service.ehcache.xml";
    public static final String CACHE_NAME = "uniprot-taxonomy-service-cache";


    public CachedUniprotTaxonomyFetcher() {
        super();
        initialiseCache();
    }


    public Organism getOrganismByTaxID(int taxID) throws BridgeFailedException {
        final String key = "getOrganismByTaxID#"+taxID;
        Object data = getFromCache( key );
        if( data == null) {
            data = super.getOrganismByTaxID(taxID);
            storeInCache(key , data);
        }
        return (Organism )data;
    }

    public Collection<Organism> getOrganismsByTaxIDs(Collection<Integer> taxIDs) throws BridgeFailedException {
        Collection<Organism> results = new ArrayList<Organism>();
        for(Integer taxID : taxIDs){
            results.add(getOrganismByTaxID(taxID));
        }
        return results;
    }

    /////////////////////////
    // EH CACHE utilities

    public Object getFromCache( String key ) {
        Object data = null;
        Element element = cache.get( key );
        if( element != null ){
            data = element.getObjectValue();
        }
        return data;
    }

    public void storeInCache( String key, Object data ) {
        Element element = new Element( key, data );
        cache.put( element );
    }

    public void initialiseCache() {
        initialiseCache( EHCACHE_CONFIG_FILE );
    }

    public void initialiseCache(String settingsFile) {
        URL url = getClass().getResource( settingsFile );
        cacheManager =  CacheManager.create( url );
        if(! cacheManager.cacheExists(CACHE_NAME))
            cacheManager.addCache(CACHE_NAME);
        this.cache = cacheManager.getCache( CACHE_NAME );
        if( cache == null ) throw new IllegalStateException( "Could not load cache" );
    }

    public void clearCache() {
        cacheManager.clearAll();
    }

    public void shutDownCache() {
        cacheManager.shutdown();
    }
}
