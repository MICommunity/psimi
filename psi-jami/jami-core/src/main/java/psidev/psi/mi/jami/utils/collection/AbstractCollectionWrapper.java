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

public abstract class AbstractCollectionWrapper<T> implements Collection<T> {
    private Collection<T> wrappedList;

    public AbstractCollectionWrapper(Collection<T> list){
        super();
        if (list == null){
            this.wrappedList = new ArrayList<T>();
        }
        else{
            this.wrappedList = list;
        }
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
        return new IteratorWrapper<T>(this, this.wrappedList.iterator());
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
        if (needToPreProcessElementToRemove(o)){
            processElementToRemove(o);
            return this.wrappedList.remove(o);
        }
        else {
            return this.wrappedList.remove(o);
        }
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
        boolean hasRemoved = false;
        for (Object xref : c){
            if (remove(xref)){
                hasRemoved = true;
            }
        }
        return hasRemoved;
    }

    public boolean retainAll(Collection<?> c) {
        Iterator<T> iterator = this.wrappedList.iterator();
        boolean hasChanged = false;
        while (iterator.hasNext()){
            if (!c.contains(iterator.next())){
               iterator.remove();
               hasChanged = true;
            }
        }
        return hasChanged;
    }

    public void clear() {
        for (T ref : this.wrappedList){
            if (needToPreProcessElementToRemove(ref)){
                processElementToRemove(ref);
            }
        }
        this.wrappedList.clear();
    }

    public Collection<T> getWrappedList() {
        return wrappedList;
    }

    /**
     * Method to know if an element to add needs some processing or being wrapped
     * @param added : element that will be added to the collection
     * @return true if we need to process/ wrap the element that will be added
     */
    protected abstract boolean needToPreProcessElementToAdd(T added);

    /**
     * Method to process or wrap an element to be added to the list
     * @param added : element that will be added to the collection
     * @return the processed/wrapped element that will be added
     */
    protected abstract T processOrWrapElementToAdd(T added);

    protected abstract void processElementToRemove(Object o);

    protected abstract boolean needToPreProcessElementToRemove(Object o);
}
