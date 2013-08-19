package psidev.psi.mi.jami.bridges.uniprot;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 14/05/13
 */
public class CachedUniprotFetcher
        extends UniprotFetcher
        implements CachedFetcher {

    private final Logger log = LoggerFactory.getLogger(CachedUniprotFetcher.class.getName());

    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/service.ehcache.xml";
    public static final String CACHE_NAME = "uniprot-service-cache";



    public CachedUniprotFetcher() {
        super();
        initialiseCache();
    }

    public Collection<Protein> getProteinsByIdentifier(String identifier) throws BridgeFailedException {

        final String key = "getProteinsByIdentifier#"+identifier;
        Object data = getFromCache( key );
        if( data == null) {
            data = super.getProteinsByIdentifier(identifier);
            storeInCache(key , data);
        }
        return (Collection<Protein> )data;
    }

    public Collection<Protein> getProteinsByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        Collection<Protein> results = new ArrayList<Protein>();
        for(String identifier : identifiers){
            results.addAll(getProteinsByIdentifier(identifier));
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
        cacheManager.addCache( CACHE_NAME );
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
