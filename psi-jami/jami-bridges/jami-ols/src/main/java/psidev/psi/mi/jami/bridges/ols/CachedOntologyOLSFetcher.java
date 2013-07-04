package psidev.psi.mi.jami.bridges.ols;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //=======================================
    // Find Relations

    public OntologyTerm findDirectChildren(String termIdentifier , CvTerm ontologyDatabase, OntologyTerm ontologyTermFetched)
            throws BridgeFailedException {

        final String key = "getOntologyTermByIdentifier#"+termIdentifier+"#"+ontologyDatabase.getShortName()+
                "#"+true+"#"+false;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.findDirectChildren(termIdentifier, ontologyDatabase, ontologyTermFetched);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;
    }

    public OntologyTerm findDirectParents(String termIdentifier , CvTerm ontologyDatabase, OntologyTerm ontologyTermFetched)
            throws BridgeFailedException {

        final String key = "getOntologyTermByIdentifier#"+termIdentifier+"#"+ontologyDatabase.getShortName()+
                "#"+false+"#"+true;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.findDirectParents(termIdentifier, ontologyDatabase, ontologyTermFetched);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;
    }


    //=======================================
    // Find with Relations

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName,
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

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase,
                                              boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException{

        final String key = "getOntologyTermByIdentifier#"+termIdentifier+"#"+ontologyDatabase+
                "#"+fetchChildren+"#"+fetchParents;
        Object data = getFromCache( key );
        if( data == null ) {
            data = getCvTermByIdentifier(termIdentifier, ontologyDatabase, fetchChildren, fetchParents);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;

    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName,
                                             boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException{

        final String key = "getOntologyTermByIdentifier#"+searchName+"#"+ontologyDatabaseName+
                "#"+fetchChildren+"#"+fetchParents;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByExactName(searchName, ontologyDatabaseName, fetchChildren, fetchParents);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;


    }

    public OntologyTerm getCvTermByExactName(String searchName , boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException{

        final String key = "getOntologyTermByIdentifier#"+searchName+"#"+fetchChildren+"#"+fetchParents;
        Object data = getFromCache( key );
        if( data == null ) {
            data = super.getCvTermByExactName(searchName, fetchChildren, fetchParents);
            storeInCache( key, data );
        }
        return (OntologyTerm) data;
    }

    //===============================
    // Find without Relations

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database name.
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return
     * @throws BridgeFailedException
     */
    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabaseName, false, false);
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
