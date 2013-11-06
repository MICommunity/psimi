package psidev.psi.mi.jami.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * PsiXml25IdIndex that stores objects in memory using a map
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/11/13</pre>
 */

public class InMemoryPsiXml25Index implements PsiXml25IdIndex {
    private Map<Integer, Object> mapOfReferencedObjects;

    public InMemoryPsiXml25Index(){
        this.mapOfReferencedObjects = new HashMap<Integer, Object>();
    }

    @Override
    public Object get(int id) {
        return this.mapOfReferencedObjects.get(id);
    }

    @Override
    public void put(int id, Object object) {
        this.mapOfReferencedObjects.put(id, object);
    }

    @Override
    public void clear() {
        this.mapOfReferencedObjects.clear();
    }

    @Override
    public boolean contains(int id) {
        return this.mapOfReferencedObjects.containsKey(id);
    }
}
