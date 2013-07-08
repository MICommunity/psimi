package psidev.psi.mi.jami.bridges.ols;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import psidev.psi.mi.jami.model.Xref;


/**
 *
 *
 * Code for the cache based on the CachedOntologyService at uk.ac.ebi.intact.bridges.olslight;
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
        if( log.isDebugEnabled() ) log.debug( "Loading EHCACHE configuration: " + url );
        if( cacheManager == null ) cacheManager = new CacheManager( url );
        this.cache = cacheManager.getCache( CACHE_NAME );
        if( cache == null ) throw new IllegalStateException( "Could not load cache: " + CACHE_NAME );
    }

    //=======================================
    // Find Relations


    /**
     * Finds all the leaf children and then their parents using the first identifier of the provided term as a starting point.
     * <p>
     * Finds the identifiers of all leaf children.
     * Then finds the OntologyTerm for these identifiers and all parents.
     *
     * @param ontologyTerm  A complete ontologyTerm to find children for
     * @return      A collection of all the deepest children
     * @throws BridgeFailedException
     */
    public Collection<OntologyTerm> findAllParentsOfDeepestChildren(OntologyTerm ontologyTerm , Xref identity)
            throws BridgeFailedException {

        if(ontologyTerm == null) throw new IllegalArgumentException("Provided OntologyTerm is null.");
        if(identity == null) throw new IllegalArgumentException("Provided OntologyTerm has no identifier.");

        final String key = "findAllParentsOfDeepestChildren#"+ontologyTerm+"#"+identity.getDatabase();

        Object data = getFromCache( key );
        if( data == null) {
            data = super.findAllParentsOfDeepestChildren(ontologyTerm);
            storeInCache(key, data);
        }
        return (Collection<OntologyTerm>)data;
    }

    /**
     * Finds the children of the provided term, checking the cache first.
     * If the entry has not been cached it uses the inherited form of this method to query OLS
     * @param termIdentifier
     * @return
     * @throws BridgeFailedException
     */
    public Map<String , String> getChildrenIDs(String termIdentifier) throws BridgeFailedException {
        final String key = "getChildrenIDs#"+termIdentifier;
        Object data = getFromCache( key );
        if( data == null) {
            data = super.getChildrenIDs(termIdentifier);
            storeInCache(key , data);
        }
        return (Map<String , String>)data;
    }

    //=======================================
    // Find with Relations

    /**
    * Finds an ontologyTerm using a termIdentifier and an ontology database name.
    * If children or parents are selected it will recursively find them.
    * If both are selected, all parents of the deepest children will be found.
    *
    * @param termIdentifier        The identifier for the CvTerm to fetch.
    * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
    * @param childrenDepth         Flag to note the depth of children that should be found.
    *                              0 if no children should be found, -1 if the depth should be infinite
    * @param parentDepth           Flag to note the depth of parents that should be found.
    *                              0 if no parents should be found, -1 if the depth should be infinite
    * @return          A completed term or null if no term could be found.
    * @throws BridgeFailedException
    */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName,
                                              int childrenDepth, int parentDepth)
            throws BridgeFailedException {

        final String key = "getCvTermByIdentifier#"+termIdentifier+"#DBNAME:"+ontologyDatabaseName+
                "#"+childrenDepth+"#"+parentDepth;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByIdentifier(termIdentifier, ontologyDatabaseName,childrenDepth, parentDepth);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase,
                                              int childrenDepth, int parentDepth)
            throws BridgeFailedException{

        final String key = "getCvTermByIdentifier#"+termIdentifier+"#CVTERM:"+ontologyDatabase+
                "#"+childrenDepth+"#"+parentDepth;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByIdentifier(termIdentifier, ontologyDatabase,childrenDepth, parentDepth);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;

    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName,
                                             int childrenDepth, int parentDepth)
            throws BridgeFailedException{

        final String key = "getCvTermByExactName#"+searchName+"#DBNAME:"+ontologyDatabaseName+"#"+childrenDepth+"#"+parentDepth;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByExactName(searchName, ontologyDatabaseName, childrenDepth , parentDepth);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;
    }

    public OntologyTerm getCvTermByExactName(String searchName , int childrenDepth, int parentDepth)
            throws BridgeFailedException{

        final String key = "getCvTermByExactName#"+searchName+"#"+childrenDepth+"#"+parentDepth;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByExactName(searchName ,childrenDepth, parentDepth);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;
    }

    //===============================
    // Find without Relations
/*
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabaseName, 0, false);
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabase , false, false);
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByExactName(searchName, ontologyDatabaseName, false, false);
    }

    public OntologyTerm getCvTermByExactName(String searchName)
            throws BridgeFailedException {
        return getCvTermByExactName(searchName, false, false);
    }   */


    /////////////////////////
    // EH CACHE utilities

    private Object getFromCache( String key ) {
        Object data = null;
        Element element = cache.get( key );
        if( element != null ){
            data = element.getValue();
            //log.info("Found key in cache "+key);
        }
        return data;
    }

    private void storeInCache( String key, Object data ) {
        Element element = new Element( key, data );
        cache.put( element );
        //log.info("added to cache key "+key);
    }
}