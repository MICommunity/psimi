package psidev.psi.mi.jami.bridges.uniprot.mapping;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapper;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.protein.mapping.model.contexts.IdentificationContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.net.URL;

/**
 * A cached uniprot protein mapper
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/09/13</pre>
 */

public class CachedUniprotProteinMapper extends UniprotProteinMapper implements ProteinMapper{

    public static final String CACHE_NAME = "uniprot-mapping-cache";
    private Cache cache;
    private static CacheManager cacheManager;

    public static final String EHCACHE_CONFIG_FILE = "/service.ehcache.xml";

    public CachedUniprotProteinMapper() {
        initialiseCache();
    }

    @Override
    protected IdentificationResults identifyProtein(
            Xref xrefToMap, IdentificationContext context)
            throws BridgeFailedException {

        String value =  xrefToMap.getId();
        String database = null;
        String databaseName = null;

        //Find a way to identify the database
        if(xrefToMap.getDatabase() != null){
            database = xrefToMap.getDatabase().getMIIdentifier();
            databaseName = xrefToMap.getDatabase().getShortName();
        }
        //If there's an identity do a search - else return an empty result.
        if((database != null || databaseName != null) && value != null){

            String key = "IDENTIFY_XREF_DB_"+(database != null ? database : databaseName)+"_"+value+(context.getOrganism() != null ? context.getOrganism().getTaxId() : "");
            Object o = getFromCache(key);
            if (o != null){
                return (IdentificationResults)o;
            }
            IdentificationResults res = super.identifyProtein(xrefToMap, context);
            storeInCache(key, res);
            return res;
        }

        return null;
    }

    /**
     * Finds a Mapping for the proteins sequence using the method which is implemented.
     * @param sequence the protein sequence to find a Mapping for
     * @return  The results of the query. Can be null.
     */
    @Override
    protected IdentificationResults identifyProtein(String sequence, IdentificationContext context)
            throws BridgeFailedException {

        if(sequence != null && sequence.length() > 0) {
            String key = "IDENTIFY_SEQUENCE_"+sequence+"_"+(context.getOrganism() != null ? context.getOrganism().getTaxId() : "");
            Object o = getFromCache(key);
            if (o != null){
                return (IdentificationResults)o;
            }
            IdentificationResults res = super.identifyProtein(sequence, context);
            storeInCache(key, res);
            return res;
        }
       return super.identifyProtein(sequence, context);
    }

    /////////////////////////
    // EH CACHE utilities

    public void initialiseCache() {
        initialiseCache( EHCACHE_CONFIG_FILE );
    }

    public void initialiseCache(String settingsFile) {
        URL url = getClass().getResource( settingsFile );
        cacheManager =  CacheManager.create(url);
        if(! cacheManager.cacheExists(CACHE_NAME))
            cacheManager.addCache(CACHE_NAME);
        this.cache = cacheManager.getCache(CACHE_NAME);
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
