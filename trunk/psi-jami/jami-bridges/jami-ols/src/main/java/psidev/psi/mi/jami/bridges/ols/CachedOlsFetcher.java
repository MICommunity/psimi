package psidev.psi.mi.jami.bridges.ols;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

import java.net.URL;
import java.util.Collection;

/**
 * Generic implementation for CachedOlsService
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/09/13</pre>
 */

public class CachedOlsFetcher<T extends CvTerm> implements CvTermFetcher<T>, CachedFetcher {

    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/service.ehcache.xml";
    private String cacheName;

    private CvTermFetcher<T> delegateFetcher;


    public CachedOlsFetcher(String cacheName, CvTermFetcher<T> delegateFetcher) throws BridgeFailedException {
        if (cacheName == null){
            throw new IllegalArgumentException("The name of the cache is mandatory");
        }
        this.cacheName = cacheName;
        if (delegateFetcher == null){
            throw new IllegalArgumentException("The delegating fetcher is mandatory");
        }
        this.delegateFetcher = delegateFetcher;
        initialiseCache();
    }

    /**
     * Uses the identifier and the name of the database to search for a complete form of the cvTerm.
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param miOntologyName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return  A full cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public T fetchCvTermByIdentifier(String termIdentifier, String miOntologyName)
            throws BridgeFailedException{
        if(termIdentifier == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        final String key = "getCvTermByIdentifier#"+termIdentifier+"#"+miOntologyName;

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchCvTermByIdentifier(termIdentifier, miOntologyName);
            storeInCache(key, data);
        }
        return (T)data;
    }

    /**
     * Uses the identifier and a cvTerm denoting the database to search to fetch a complete from of the term.
     * @param termIdentifier     The identifier for the CvTerm to fetch
     * @param ontologyDatabase  The cvTerm of the ontology to search for.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public T fetchCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException{
        if(termIdentifier == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(ontologyDatabase == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        final String key = "getCvTermByIdentifier#"+termIdentifier+"#"+ontologyDatabase.getShortName();

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchCvTermByIdentifier(termIdentifier, ontologyDatabase);
            storeInCache(key, data);
        }
        return (T)data;
    }

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * @param searchName    A full or short name for the term to be searched for.
     * @param miOntologyName  The ontology to search for the term in.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public T fetchCvTermByName(String searchName, String miOntologyName)
            throws BridgeFailedException{
        if(searchName == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        final String key = "getCvTermByName#"+searchName+"#"+miOntologyName;

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchCvTermByName(searchName, miOntologyName);
            storeInCache(key, data);
        }
        return (T)data;
    }

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * <p>
     * If the term can not be resolved to a database, then this method may return null.
     *
     * @param searchName    A full or short name for the term to be searched for.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<T> fetchCvTermByName(String searchName)
            throws BridgeFailedException{
        if(searchName == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");

        final String key = "getCvTermByName#"+searchName;

        Object data = getFromCache( key );
        if( data == null) {
            data = delegateFetcher.fetchCvTermByName(searchName);
            storeInCache(key, data);
        }
        return (Collection<T>)data;
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, String miOntologyName) throws BridgeFailedException {
        return delegateFetcher.fetchCvTermsByIdentifiers(termIdentifiers, miOntologyName);
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames, String miOntologyName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /////////////////////////
    // EH CACHE utilities

    public void initialiseCache() {
        initialiseCache( EHCACHE_CONFIG_FILE );
    }

    public void initialiseCache(String settingsFile) {
        URL url = getClass().getResource( settingsFile );
        cacheManager =  CacheManager.create( url );
        if(! cacheManager.cacheExists(cacheName))
            cacheManager.addCache(cacheName);
        this.cache = cacheManager.getCache(cacheName);
        if( cache == null ) throw new IllegalStateException( "Could not load cache" );
    }


    public Object getFromCache( String key ) {
        Object data = null;
        Element element = cache.get( key );
        if( element != null ){
            //if( log.isTraceEnabled() ) log.trace("getting key: "+key);
            data = element.getObjectValue();
        }
        return data;
    }

    public void storeInCache( String key, Object data ) {
        //if( log.isTraceEnabled() ) log.trace("storing key: "+key);
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
