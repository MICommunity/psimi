package psidev.psi.mi.jami.bridges.ols;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some code based on the CachedOntologyService at uk.ac.ebi.intact.bridges.olslight;
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class CachedOntologyOLSFetcher
        extends OntologyOLSFetcher {



    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/ols-service.ehcache.xml";
    public static final String CACHE_NAME = "ols-service-cache";

    public CachedOntologyOLSFetcher() throws BridgeFailedException {
        super();

        URL url = getClass().getResource( EHCACHE_CONFIG_FILE );

        if( log.isDebugEnabled() ){
            log.debug( "Loading EHCACHE configuration: " + url );
        }

        if( cacheManager == null ) {
            // we should only have a single instance of CacheManager for that configuration file.
            cacheManager = new CacheManager( url );
        }

        this.cache = cacheManager.getCache( CACHE_NAME );

        if( cache == null ){
            throw new IllegalStateException( "Could not load cache: " + CACHE_NAME );
        }
    }

    public OntologyTerm getCvTermByIdentifier(
            String termIdentifier, String ontologyDatabaseName,
            boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException {

        final String key = "getOntologyTermByIdentifier#"+termIdentifier+"#"+ontologyDatabaseName+
                "#"+fetchChildren+"#"+fetchParents;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByIdentifier(
                    termIdentifier, ontologyDatabaseName, fetchChildren, fetchParents);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;
    }




    /////////////////////////
    // EH CACHE utilities

    private Object getFromCache( String key ) {
        Object data = null;
        Element element = cache.get( key );
        if( element != null ){
            data = element.getValue();
        }
        return data;
    }

    private void storeInCache( String key, Object data ) {
        Element element = new Element( key, data );
        cache.put( element );
    }
}
