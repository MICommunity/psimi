package psidev.psi.mi.jami.xml;

import java.util.IdentityHashMap;
import java.util.Map;

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

    public InMemoryIdentityObjectIndex(){
        this.current = 0;
        this.identityMap = new IdentityHashMap<Object, Integer>();
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
    }

    private int nextId(){
        current++;
        return current;
    }
}
