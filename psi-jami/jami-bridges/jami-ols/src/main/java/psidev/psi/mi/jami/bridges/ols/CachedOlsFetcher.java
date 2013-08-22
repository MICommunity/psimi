package psidev.psi.mi.jami.bridges.ols;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 29/07/13
 */
public class CachedOlsFetcher
        extends OlsFetcher
        implements CachedFetcher {

    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/service.ehcache.xml";
    public static final String CACHE_NAME = "cvTerm-Cache";


    public CachedOlsFetcher() throws BridgeFailedException {
        super();
        initialiseCache();
    }

    /**
     * Uses the identifier and the name of the database to search for a complete form of the cvTerm.
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param miOntologyName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return  A full cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByIdentifier(String termIdentifier, String miOntologyName)
            throws BridgeFailedException{
        if(termIdentifier == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        final String key = "getCvTermByIdentifier#"+termIdentifier+"#"+miOntologyName;

        Object data = getFromCache( key );
        if( data == null) {
            data = super.getCvTermByIdentifier(termIdentifier, miOntologyName);
            storeInCache(key, data);
        }
        return (CvTerm)data;
    }

    /**
     * Uses the identifier and a cvTerm denoting the database to search to fetch a complete from of the term.
     * @param termIdentifier     The identifier for the CvTerm to fetch
     * @param ontologyDatabase  The cvTerm of the ontology to search for.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException{
        if(termIdentifier == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(ontologyDatabase == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        final String key = "getCvTermByIdentifier#"+termIdentifier+"#"+ontologyDatabase.getShortName();

        Object data = getFromCache( key );
        if( data == null) {
            data = super.getCvTermByIdentifier(termIdentifier , ontologyDatabase);
            storeInCache(key, data);
        }
        return (CvTerm)data;
    }

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * @param searchName    A full or short name for the term to be searched for.
     * @param miOntologyName  The ontology to search for the term in.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByExactName(String searchName, String miOntologyName)
            throws BridgeFailedException{
        if(searchName == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(miOntologyName == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        final String key = "getCvTermByExactName#"+searchName+"#"+miOntologyName;

        Object data = getFromCache( key );
        if( data == null) {
            data = super.getCvTermByExactName(searchName, miOntologyName);
            storeInCache(key, data);
        }
        return (CvTerm)data;
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
    public CvTerm getCvTermByExactName(String searchName)
            throws BridgeFailedException{
        if(searchName == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");

        final String key = "getCvTermByExactName#"+searchName;

        Object data = getFromCache( key );
        if( data == null) {
            data = super.getCvTermByExactName(searchName);
            storeInCache(key, data);
        }
        return (CvTerm)data;
    }

    /**
     * Identifies and initiates a CvTerm using its name.
     * A fuzzy search can also be used by setting @link{useFuzzySearch} to true.
     * This will extend to search possibilities to partial matches if no exact matches can be found.
     * @param searchName
     * @param databaseName
     * @return
     * @throws BridgeFailedException
     */
    public Collection<CvTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException{
        return Collections.EMPTY_LIST;
    }

    public Collection<CvTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException{
        return Collections.EMPTY_LIST;
    }



    /**
     * Uses the identifier and the name of the database to search for a complete form of the cvTerm.
     * @param termIdentifiers       The identifier for the CvTerm to fetch and the corresponding ontology database name.
     * @param miOntologyName  The name of the ontology to search for the names in.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers , String miOntologyName)
            throws BridgeFailedException{
        return Collections.EMPTY_LIST;
    }

    /**
     * Uses the identifier and a cvTerm denoting the database to search to fetch a complete from of the term.
     * @param termIdentifiers       The identifier for the CvTerms to fetch.
     * @param ontologyDatabase      The name of the ontology to search for the terms in.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers , CvTerm ontologyDatabase)
            throws BridgeFailedException{
        return Collections.EMPTY_LIST;
    }

    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     * @param searchNames   A full or short name for the term to be searched for.
     * @param miOntologyName  The name of the database to search for the names in.
     * @return              A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames , String miOntologyName )
            throws BridgeFailedException{
        return Collections.EMPTY_LIST;
    }

    /**
     * Finds the CvTerms which match the exact names provided.
     * <p>
     * If the a term found by the search can not be resolved to a database, this method may return null.
     *
     * @param searchNames   A collection full or short names for the term to be searched for.
     * @return              A collection of cvTerms which matched a search term.
     * @throws BridgeFailedException
     */
    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames)
            throws BridgeFailedException{
        return Collections.EMPTY_LIST;
    }





    /////////////////////////
    // EH CACHE utilities

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
