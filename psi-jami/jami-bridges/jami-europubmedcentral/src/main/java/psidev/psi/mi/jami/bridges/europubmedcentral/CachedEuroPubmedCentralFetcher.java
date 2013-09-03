package psidev.psi.mi.jami.bridges.europubmedcentral;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.io.File;
import java.net.URL;

/**
 * A cached version of the EuroPubmedCentralFetcher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class CachedEuroPubmedCentralFetcher
        extends EuroPubmedCentralFetcher
        implements CachedFetcher {

    private final Logger log = LoggerFactory.getLogger(CachedEuroPubmedCentralFetcher.class.getName());

    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/service.ehcache.xml";
    public static final String CACHE_NAME = "europubmedcentral-service-cache";

    public CachedEuroPubmedCentralFetcher() throws BridgeFailedException {
        super();
        initialiseCache();
    }


    public Publication getPublicationByIdentifier(String id) throws BridgeFailedException {
        final String key = "getPublicationByPubmedID#"+ id;
        Object data = getFromCache( key );
        if( data == null) {
            data = super.getPublicationByIdentifier(id);
            storeInCache(key , data);
        }
        return (Publication)data;
    }

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

    @Override
    public void initialiseCache() {
        initialiseCache( EHCACHE_CONFIG_FILE );
    }

    @Override
    public void initialiseCache(String settingsFile) {
        URL url = getClass().getResource( settingsFile );
        cacheManager =  CacheManager.create( url );
        if(! cacheManager.cacheExists(CACHE_NAME))
            cacheManager.addCache(CACHE_NAME);
        this.cache = cacheManager.getCache( CACHE_NAME );
        if( cache == null ) throw new IllegalStateException( "Could not load cache" );
    }

    @Override
    public void clearCache() {
        cacheManager.clearAll();
    }

    @Override
    public void shutDownCache() {
        cacheManager.shutdown();
    }
}
