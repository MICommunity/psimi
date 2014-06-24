package psidev.psi.mi.jami.xml.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.extension.AbstractAvailability;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cache system based on eh-cache
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/06/14</pre>
 */

public class PsiXmlIdEhCache implements PsiXmlIdCache{
    private Cache cache;
    private CacheManager cacheManager;
    private static final Logger logger = Logger.getLogger("PsiXmlIdEhCache");

    public static final String EHCACHE_CONFIG_FILE = "/ehcache/ehcache.xml";
    public static final String CACHE_NAME = "psixml-cache";

    public PsiXmlIdEhCache(){
        if (System.getProperty("ehcache.disk.store.dir") == null){
           System.setProperty("ehcache.disk.store.dir","java.io.tmpdir");
        }
        this.cacheManager = CacheManager.create(PsiXmlIdEhCache.class.getResource(EHCACHE_CONFIG_FILE));
        this.cache = this.cacheManager.getCache(CACHE_NAME);
    }

    public PsiXmlIdEhCache(URL cacheConfig, String cacheName){
        this.cacheManager = CacheManager.create(cacheConfig);
        this.cache = this.cacheManager.getCache(cacheName);
    }

    public PsiXmlIdEhCache(String diskStoreLocation){
        System.setProperty("ehcache.disk.store.dir",diskStoreLocation);

        this.cacheManager = CacheManager.create(PsiXmlIdEhCache.class.getResource(EHCACHE_CONFIG_FILE));
        this.cache = this.cacheManager.getCache(CACHE_NAME);
    }

    @Override
    public void registerAvailability(int id, AbstractAvailability object) {
         storeInCache("AVAILABILITY_ID_"+Integer.toString(id), object);
    }

    @Override
    public AbstractAvailability getAvailability(int id) {
        return getFromCache("AVAILABILITY_ID_"+Integer.toString(id));
    }

    @Override
    public void registerExperiment(int id, Experiment object) {
        storeInCache("EXPERIMENT_ID_"+Integer.toString(id), object);
    }

    @Override
    public Experiment getExperiment(int id) {
        return getFromCache("EXPERIMENT_ID_"+Integer.toString(id));
    }

    @Override
    public void registerInteraction(int id, Interaction object) {
        storeInCache("INTERACTION_ID_"+Integer.toString(id), object);
    }

    @Override
    public Interaction getInteraction(int id) {
        return getFromCache("INTERACTION_ID_"+Integer.toString(id));
    }

    @Override
    public void registerInteractor(int id, Interactor object) {
        storeInCache("INTERACTOR_ID_"+Integer.toString(id), object);
    }

    @Override
    public Interactor getInteractor(int id) {
        return getFromCache("INTERACTOR_ID_"+Integer.toString(id));
    }

    @Override
    public void registerParticipant(int id, Participant object) {
        storeInCache("PARTICIPANT_ID_"+Integer.toString(id), object);
    }

    @Override
    public Participant getParticipant(int id) {
        return getFromCache("PARTICIPANT_ID_"+Integer.toString(id));
    }

    @Override
    public void registerFeature(int id, Feature object) {
        storeInCache("FEATURE_ID_"+Integer.toString(id), object);
    }

    @Override
    public Feature getFeature(int id) {
        return getFromCache("FEATURE_ID_"+Integer.toString(id));
    }

    @Override
    public void registerComplex(int id, Complex object) {
        storeInCache("COMPLEX_ID_"+Integer.toString(id), object);
    }

    @Override
    public Complex getComplex(int id) {
        return getFromCache("COMPLEX_ID_"+Integer.toString(id));
    }

    @Override
    public void registerVariableParameterValue(int id, VariableParameterValue object) {
        storeInCache("VARIABLE_ID_"+Integer.toString(id), object);
    }

    @Override
    public VariableParameterValue getVariableParameterValue(int id) {
        return getFromCache("VARIABLE_ID_"+Integer.toString(id));
    }

    @Override
    public void clear() {
        try{
            this.cache.removeAll();
        }
        catch (IllegalStateException e){
            logger.log(Level.SEVERE, "Cannot clear psixml cache", e);
        }
        catch (CacheException e){
            logger.log(Level.SEVERE, "Cannot clear psixml cache", e);
        }
    }

    @Override
    public void close() {
        try{
            this.cache.removeAll();
        }
        catch (IllegalStateException e){
            logger.log(Level.SEVERE, "Cannot clear psixml cache", e);
        }
        catch (CacheException e){
            logger.log(Level.SEVERE, "Cannot clear psixml cache", e);
        }
        try{
            this.cache.dispose();
        }
        catch (IllegalStateException e){
            logger.log(Level.SEVERE, "Cannot close psixml cache", e);
        }
        this.cacheManager.shutdown();
    }

    @Override
    public boolean containsExperiment(int id) {
        return this.cache.isKeyInCache("EXPERIMENT_ID_"+Integer.toString(id));
    }

    @Override
    public boolean containsAvailability(int id) {
        return this.cache.isKeyInCache("AVAILABILITY_ID_"+Integer.toString(id));
    }

    @Override
    public boolean containsInteraction(int id) {
        return this.cache.isKeyInCache("INTERACTION_ID_"+Integer.toString(id));
    }

    @Override
    public boolean containsInteractor(int id) {
        return this.cache.isKeyInCache("INTERACTOR_ID_"+Integer.toString(id));
    }

    @Override
    public boolean containsParticipant(int id) {
        return this.cache.isKeyInCache("PARTICIPANT_ID_"+Integer.toString(id));
    }

    @Override
    public boolean containsFeature(int id) {
        return this.cache.isKeyInCache("FEATURE_ID_"+Integer.toString(id));
    }

    @Override
    public boolean containsVariableParameter(int id) {
        return this.cache.isKeyInCache("VARIABLE_ID_"+Integer.toString(id));
    }

    @Override
    public boolean containsComplex(int id) {
        return this.cache.isKeyInCache("COMPLEX_ID_"+Integer.toString(id));
    }

    private void storeInCache( String key, Object data ) {
        Element element = new Element( key, data );
        this.cache.put( element );
    }

    private <T extends Object> T getFromCache( String key ) {
        T data = null;
        Element element = cache.get( key );
        if( element != null ){
            data = (T)element.getObjectValue();
        }
        return data;
    }
}
