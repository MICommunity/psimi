package psidev.psi.mi.jami.bridges.uniprot;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.uniprot.util.UniprotTranslationUtil;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsIsoform;
import uk.ac.ebi.kraken.interfaces.uniprot.features.Feature;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

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

    public static final String EHCACHE_CONFIG_FILE = "/uniprot-service.ehcache.xml";
    public static final String CACHE_NAME = "uniprot-service-cache";



    public CachedUniprotFetcher() {
        super();
        startCache();
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

    public void startCache(){
        URL url = getClass().getResource( EHCACHE_CONFIG_FILE );
        if( log.isDebugEnabled() ) log.debug( "Loading EHCACHE configuration: " + url );
        cacheManager = new CacheManager( url );
        this.cache = cacheManager.getCache( CACHE_NAME );
        if( cache == null ) throw new IllegalStateException( "Could not load cache: " + CACHE_NAME );
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

    public void closeCache(){
        cacheManager.shutdown();
    }
}
