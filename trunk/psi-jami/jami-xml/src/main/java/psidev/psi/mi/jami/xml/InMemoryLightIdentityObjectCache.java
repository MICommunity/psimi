package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;

import java.util.*;

/**
 * It keeps a in memory cache of objects having an id.
 * The cache is based on a in memory Identity map.
 *
 * It will only keep experiments, interactions, features and participants in memory.
 * It will ignore interactors and availability
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class InMemoryLightIdentityObjectCache implements PsiXml25ObjectCache {
    private int current;
    private Map<Object, Integer> identityMap;
    private Set<ModelledInteraction> complexes;

    public InMemoryLightIdentityObjectCache(){
        this.current = 0;
        this.identityMap = new IdentityHashMap<Object, Integer>();
        this.complexes = new HashSet<ModelledInteraction>();
    }

    @Override
    public int extractIdForAvailability(String av) {
        if (av == null){
            return 0;
        }
        return nextId();
    }

    @Override
    public int extractIdForExperiment(Experiment o) {
        return extractIdFor(o);
    }

    @Override
    public int extractIdForInteractor(Interactor o) {
        return nextId();
    }

    @Override
    public int extractIdForInteraction(Interaction o) {
        return extractIdFor(o);
    }

    @Override
    public int extractIdForParticipant(Entity o) {
        return extractIdFor(o);
    }

    @Override
    public int extractIdForFeature(Feature o) {
        return extractIdFor(o);
    }

    @Override
    public int extractIdForComplex(Complex o) {
        return nextId();
    }

    public void clear(){
        this.current = 0;
        this.identityMap.clear();
        this.complexes.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.identityMap.containsKey(o);
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
        if (o != null){
            this.identityMap.remove(o);
        }
    }

    private int nextId(){
        current++;
        return current;
    }

    private int extractIdFor(Object o) {
        if (o == null){
            return 0;
        }
        Integer id = this.identityMap.get(o);
        if (id == null){
            id = nextId();
            this.identityMap.put(o, id);
        }
        return id;
    }
}
