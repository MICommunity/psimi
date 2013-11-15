package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Complex;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * It keeps a in memory cache of objects having an id.
 * The cache is based on a in memory Identity map.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class InMemoryIdentityObjectIndex implements PsiXml25ObjectIndex{
    private int current;
    private Map<Object, Integer> identityMap;
    private Set<Complex> complexes;

    public InMemoryIdentityObjectIndex(){
        this.current = 0;
        this.identityMap = new IdentityHashMap<Object, Integer>();
        this.complexes = new HashSet<Complex>();
    }

    @Override
    public int extractIdFor(Object o) {
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

    public void clear(){
        this.current = 0;
        this.identityMap.clear();
        this.complexes.clear();
    }

    @Override
    public boolean contain(Object o) {
        return this.identityMap.containsKey(o);
    }

    @Override
    public void registerSubComplex(Complex c) {
        this.complexes.add(c);
    }

    @Override
    public Set<Complex> clearRegisteredComplexes() {
        Set<Complex> complexes = new HashSet<Complex>(this.complexes);
        this.complexes.clear();
        return complexes;
    }

    @Override
    public boolean hasRegisteredSubComplexes() {
        return !this.complexes.isEmpty();
    }

    private int nextId(){
        current++;
        return current;
    }
}
