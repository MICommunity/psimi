package psidev.psi.mi.jami.xml.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import psidev.psi.mi.jami.model.*;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * It keeps a in memory cache of objects having an id.
 * The cache is based on a in memory Identity map.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class PsiXmlObjectEhCache implements PsiXmlObjectCache {
    private int current;
    private Cache cache;
    private CacheManager cacheManager;
    private static final Logger logger = Logger.getLogger("PsiXmlIdEhCache");
    private Set<ModelledInteraction> complexes;

    public static final String EHCACHE_CONFIG_FILE = "/ehcache/ehcache.xml";
    public static final String CACHE_NAME = "psixml-cache";

    public PsiXmlObjectEhCache(){
        if (System.getProperty("ehcache.disk.store.dir") == null){
            System.setProperty("ehcache.disk.store.dir","java.io.tmpdir");
        }
        this.cacheManager = CacheManager.create(PsiXmlIdEhCache.class.getResource(EHCACHE_CONFIG_FILE));
        this.cache = this.cacheManager.getCache(CACHE_NAME);
        this.complexes = new HashSet<ModelledInteraction>();
    }

    public PsiXmlObjectEhCache(URL cacheConfig, String cacheName){
        this.cacheManager = CacheManager.create(cacheConfig);
        this.cache = this.cacheManager.getCache(cacheName);
        this.complexes = new HashSet<ModelledInteraction>();
    }

    public PsiXmlObjectEhCache(String diskStoreLocation){
        System.setProperty("ehcache.disk.store.dir",diskStoreLocation);

        this.cacheManager = CacheManager.create(PsiXmlIdEhCache.class.getResource(EHCACHE_CONFIG_FILE));
        this.cache = this.cacheManager.getCache(CACHE_NAME);
        this.complexes = new HashSet<ModelledInteraction>();
    }

    @Override
    public int extractIdForAvailability(String av) {
        return extractIdFor("AVAILABILITY_O_"+Integer.toString(av.hashCode()));
    }

    @Override
    public int extractIdForExperiment(Experiment o) {
        return extractIdFor("EXPERIMENT_O_"+Integer.toString(o.hashCode()));
    }

    @Override
    public int extractIdForInteractor(Interactor o) {
        return extractIdFor("INTERACTOR_O_"+Integer.toString(o.hashCode()));
    }

    @Override
    public int extractIdForInteraction(Interaction o) {
        return extractIdFor("INTERACTION_O_"+Integer.toString(o.hashCode()));
    }

    @Override
    public int extractIdForParticipant(Participant o) {
        return extractIdFor("PARTICIPANT_O_"+Integer.toString(o.hashCode()));
    }

    @Override
    public int extractIdForVariableParameterValue(VariableParameterValue o) {
        return extractIdFor("VARIABLE_O_"+Integer.toString(o.hashCode()));
    }

    @Override
    public int extractIdForFeature(Feature o) {
        return extractIdFor("FEATURE_O_"+Integer.toString(o.hashCode()));
    }

    @Override
    public int extractIdForComplex(Complex o) {
        return extractIdFor("COMPLEX_O_"+Integer.toString(o.hashCode()));
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
    public boolean contains(Object o) {
        if (o instanceof String){
            this.cache.isKeyInCache("AVAILABILITY_O_"+Integer.toString(o.hashCode()));
        }
        else if (o instanceof Experiment){
            this.cache.isKeyInCache("EXPERIMENT_O_"+Integer.toString(o.hashCode()));
        }
        else if (o instanceof Interactor){
            this.cache.isKeyInCache("INTERACTOR_O_"+Integer.toString(o.hashCode()));
        }
        else if (o instanceof Interaction){
            this.cache.isKeyInCache("INTERACTION_O_"+Integer.toString(o.hashCode()));
        }
        else if (o instanceof Participant){
            this.cache.isKeyInCache("PARTICIPANT_O_"+Integer.toString(o.hashCode()));
        }
        else if (o instanceof VariableParameterValue){
            this.cache.isKeyInCache("VARIABLE_O_"+Integer.toString(o.hashCode()));
        }
        else if (o instanceof Feature){
            this.cache.isKeyInCache("FEATURE_O_"+Integer.toString(o.hashCode()));
        }
        return false;
    }

    @Override
    public void registerSubComplex(ModelledInteraction c) {
        this.complexes.add(c);
    }

    @Override
    public Set<ModelledInteraction> clearRegisteredSubComplexes() {
        Set<ModelledInteraction> complexes = new HashSet<ModelledInteraction>(this.complexes);
        this.complexes.clear();
        return complexes;
    }

    @Override
    public boolean hasRegisteredSubComplexes() {
        return !this.complexes.isEmpty();
    }

    @Override
    public void removeObject(Object o) {
       if (o instanceof String){
          this.cache.remove("AVAILABILITY_O_"+Integer.toString(o.hashCode()));
       }
       else if (o instanceof Experiment){
           this.cache.remove("EXPERIMENT_O_"+Integer.toString(o.hashCode()));
       }
       else if (o instanceof Interactor){
           this.cache.remove("INTERACTOR_O_"+Integer.toString(o.hashCode()));
       }
       else if (o instanceof Interaction){
           this.cache.remove("INTERACTION_O_"+Integer.toString(o.hashCode()));
       }
       else if (o instanceof Participant){
           this.cache.remove("PARTICIPANT_O_"+Integer.toString(o.hashCode()));
       }
       else if (o instanceof VariableParameterValue){
           this.cache.remove("VARIABLE_O_"+Integer.toString(o.hashCode()));
       }
       else if (o instanceof Feature){
           this.cache.remove("FEATURE_O_"+Integer.toString(o.hashCode()));
       }
    }

    private int nextId(){
        current++;
        return current;
    }

    private int extractIdFor(String key) {
        if (key == null){
            return 0;
        }
        Integer id = getFromCache(key);
        if (id == null){
            id = nextId();
            storeInCache(key, id);
        }
        return id;
    }

    private void storeInCache( String key, Object data ) {
        Element element = new Element( key, data );
        this.cache.put( element );
    }

    private Integer getFromCache( String key ) {
        Integer data = null;
        Element element = cache.get( key );
        if( element != null ){
            data = (Integer)element.getObjectValue();
        }
        return data;
    }
}
