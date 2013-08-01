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
 * Created with IntelliJ IDEA.
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

    public static final String EHCACHE_CONFIG_FILE = "/europubmedcentral-service.ehcache.xml";
    public static final String CACHE_NAME = "europubmedcentral-service-cache";

    public CachedEuroPubmedCentralFetcher() throws BridgeFailedException {
        super();
        initialiseCache();
    }


    public Publication getPublicationByPubmedID(String id) throws BridgeFailedException {
        final String key = "getPublicationByPubmedID#"+ id;
        Object data = getFromCache( key );
        if( data == null) {
            data = super.getPublicationByPubmedID(id);
            storeInCache(key , data);
        }
        return (Publication)data;
    }


    public Object getFromCache( String key ) {
        Object data = null;
        //if (cacheManager.)
        Element element = cache.get( key );
        if( element != null ){
            data = element.getValue();
        }
        return data;
    }

    public void storeInCache( String key, Object data ) {
        Element element = new Element( key, data );
        cache.put( element );
    }


    @Override
    public void initialiseCache() {
        URL url = getClass().getResource( EHCACHE_CONFIG_FILE );
        if( log.isDebugEnabled() ) log.debug( "Loading EHCACHE configuration: " + url );
        cacheManager = new CacheManager( url );
        this.cache = cacheManager.getCache( CACHE_NAME );
        if( cache == null ) throw new IllegalStateException( "Could not load cache: " + CACHE_NAME );
    }

    @Override
    public void initialiseCache(File settingsFile) {
        // TODO
        throw new IllegalStateException("Implementation has not been completed");
    }

    @Override
    public void initialiseCache(String settingsFile) {
        URL url = getClass().getResource( settingsFile );
        if( log.isDebugEnabled() ) log.debug( "Loading EHCACHE configuration: " + url );
        cacheManager = new CacheManager( url );
        if(cacheManager.getCacheNames().length>0){  //TODO see if there's a better way to validate this
            this.cache = cacheManager.getCache( cacheManager.getCacheNames()[0] );
        }
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
