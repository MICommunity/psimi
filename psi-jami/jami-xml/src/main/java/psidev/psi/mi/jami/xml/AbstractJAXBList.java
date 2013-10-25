package psidev.psi.mi.jami.xml;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A list for annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/10/13</pre>
 */

public abstract class AbstractJAXBList<T extends Object, J extends Object> extends ArrayList<J> {

    private T parent;

    public AbstractJAXBList(){
    }

    public AbstractJAXBList(int initialCapacity) {
        super(initialCapacity);
    }

    public AbstractJAXBList(Collection<? extends J> c) {
        super(c);
    }

    @Override
    public boolean add(J annotation) {
        if (annotation == null){
            return false;
        }
        return addElement(annotation);

    }

    @Override
    public boolean addAll(Collection<? extends J> c) {
        if (c == null){
            return false;
        }
        boolean added = false;

        for (J a : c){
            if (add(a)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public void add(int index, J element) {
        addToSpecificIndex(index, element);
    }

    @Override
    public boolean addAll(int index, Collection<? extends J> c) {
        int newIndex = index;
        if (c == null){
            return false;
        }
        boolean add = false;
        for (J a : c){
            if (addToSpecificIndex(newIndex, a)){
                newIndex++;
                add = true;
            }
        }
        return add;
    }

    public void setParent(T object) {
        this.parent = object;
    }

    protected T getParent() {
        return parent;
    }

    protected abstract boolean addToSpecificIndex(int index, J element);

    protected abstract boolean addElement(J annotation);
}
