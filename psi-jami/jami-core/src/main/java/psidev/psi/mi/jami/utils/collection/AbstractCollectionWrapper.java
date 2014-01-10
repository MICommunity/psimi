package psidev.psi.mi.jami.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract list which is updating some properties when adding/removing elements.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public abstract class AbstractCollectionWrapper<T> extends ArrayList<T> {
    private Collection<T> wrappedList;

    public AbstractCollectionWrapper(Collection<T> list){
        super();
        if (list == null){
            this.wrappedList = new ArrayList<T>();
        }
        this.wrappedList = list;
    }

    public int size() {
        return this.wrappedList.size();
    }

    public boolean isEmpty() {
        return this.wrappedList.isEmpty();
    }

    public boolean contains(Object o) {
        return this.wrappedList.contains(o);
    }

    public Iterator<T> iterator() {
        return this.wrappedList.iterator();
    }

    public Object[] toArray() {
        return this.wrappedList.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return this.wrappedList.toArray(a);
    }

    public boolean add(T xref) {
        if (needToPreProcessElementToAdd(xref)){
            T clone = processOrWrapElementToAdd(xref);
            return this.wrappedList.add(clone);
        }
        else {
            return this.wrappedList.add(xref);
        }
    }

    public boolean remove(Object o) {
        return this.wrappedList.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return this.wrappedList.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        boolean hasChanged = false;
        for (T xref : c){
            if (add(xref)){
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    public boolean removeAll(Collection<?> c) {
        return this.wrappedList.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return this.wrappedList.retainAll(c);
    }

    public void clear() {
        this.wrappedList.clear();
    }

    public Collection<T> getWrappedList() {
        return wrappedList;
    }

    /**
     * Method to know if an element to add needs some processing or being wrapped
     * @param added
     * @return true if we need to process/ wrap the element that will be added
     */
    protected abstract boolean needToPreProcessElementToAdd(T added);

    /**
     * Method to process or wrap an element to be added to the list
     * @param added
     * @return the processed/wrapped element that will be added
     */
    protected abstract T processOrWrapElementToAdd(T added);
}
