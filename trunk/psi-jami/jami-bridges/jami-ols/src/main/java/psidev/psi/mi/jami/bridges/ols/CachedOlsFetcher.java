package psidev.psi.mi.jami.bridges.ols;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

import java.io.File;
import java.net.URL;
/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 29/07/13
 */
public class CachedOlsFetcher
        extends OlsFetcher
        implements CachedFetcher , CvTermFetcher<CvTerm> {

    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/service.ehcache.xml";
    //public static final String CACHE_NAME = "service-cache";


    public CachedOlsFetcher() throws BridgeFailedException {
        super();
        initialiseCache();
    }


    public CvTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        final String key = "getCvTermByIdentifier#"+termIdentifier+"#DBNAME:"+ontologyDatabaseName;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByIdentifier(termIdentifier, ontologyDatabaseName);
            storeInCache( key, data );
        }
        return (CvTerm) data;
    }

    public CvTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        final String key = "getCvTermByIdentifier#"+termIdentifier+"#DBNAME:"+ontologyDatabase.toString();
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByIdentifier(termIdentifier, ontologyDatabase);
            storeInCache( key, data );
        }
        return (CvTerm) data;
    }

    public CvTerm getCvTermByExactName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        final String key = "getCvTermByExactName#"+searchName+"#DBNAME:"+ontologyDatabaseName;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByExactName(searchName, ontologyDatabaseName);
            storeInCache( key, data );
        }
        return (CvTerm) data;
    }

    public CvTerm getCvTermByExactName(String searchName) throws BridgeFailedException {
        final String key = "getCvTermByExactName#"+searchName;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByExactName(searchName, searchName);
            storeInCache( key, data );
        }
        return (CvTerm) data;
    }

    /////////////////////////
    // EH CACHE utilities

    public void initialiseCache() {
        initialiseCache( EHCACHE_CONFIG_FILE );
    }

    public void initialiseCache(File settingsFile) {
        throw new IllegalStateException("File is not yet implemented") ;
    }

    public void initialiseCache(String settingsFile) {
        URL url = getClass().getResource( settingsFile );
        //if( log.isDebugEnabled() ) log.debug( "Loading EHCACHE configuration: " + url );
        cacheManager = new CacheManager( url );
        if(cacheManager.getCacheNames().length>0){  //TODO see if there's a better way to validate this
            this.cache = cacheManager.getCache( cacheManager.getCacheNames()[0] );
        }
        if( cache == null ) throw new IllegalStateException( "Could not load cache" );
    }


    public Object getFromCache( String key ) {
        Object data = null;
        Element element = cache.get( key );
        if( element != null ){
            //if( log.isDebugEnabled() ) log.debug("getting key: "+key);
            data = element.getValue();
        }
        return data;
    }

    public void storeInCache( String key, Object data ) {
        //if( log.isDebugEnabled() ) log.debug("storing key: "+key);

        Element element = new Element( key, data );
        cache.put( element );
    }

    public void clearCache() {
        cacheManager.clearAll();
    }

    public void shutDownCache() {
        cacheManager.shutdown();
    }
}
