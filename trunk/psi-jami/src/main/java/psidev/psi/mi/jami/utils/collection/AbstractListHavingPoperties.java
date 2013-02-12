package psidev.psi.mi.jami.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract list which is updating some properties when adding/removing elements.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public abstract class AbstractListHavingPoperties<T> extends ArrayList<T> {

    public AbstractListHavingPoperties(){
        super();
    }

    @Override
    public boolean add(T object) {
        boolean added = super.add(object);

        if (added){
            processAddedObjectEvent(object);
            return true;
        }

        return false;
    }

    protected abstract void processAddedObjectEvent(T added);

    @Override
    public boolean remove(Object o) {
        if (super.remove(o)){
            // we have nothing left, reset standard values
            if (isEmpty()){
                clearProperties();
            }
            else {
                processRemovedObjectEvent((T) o);
            }

            return true;
        }
        return false;
    }

    protected abstract void processRemovedObjectEvent(T removed);

    @Override
    public void clear() {
        clearProperties();
        super.clear();
    }

    protected abstract void clearProperties();

    @Override
    public void add(int i, T t) {
        super.add(i, t);
        processAddedObjectEvent(t);
    }

    @Override
    public T remove(int i) {
        T removed = super.remove(i);
        processRemovedObjectEvent(removed);
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        List<T> existingObject = new ArrayList<T>(this);

        boolean removed = false;
        for (T o : existingObject){
            if (!objects.contains(o)){
                if (remove(o)){
                    removed = true;
                }
            }
        }

        return removed;
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        boolean removed = super.removeAll(objects);

        for (Object o : objects){
            processRemovedObjectEvent((T)o);
        }

        return removed;
    }

    @Override
    protected void removeRange(int i, int i2) {
        if (i != i2){
            for (int j = i; j < i2; j++){
                remove(j);
            }
        }
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> ts) {

        boolean added = super.addAll(i, ts);

        if (added){
            for (T object : ts){
                processAddedObjectEvent(object);
            }
        }

        return added;
    }

    @Override
    public boolean addAll(Collection<? extends T> ts) {
        boolean added = super.addAll(ts);

        if (added){
            for (T object : ts){
                processAddedObjectEvent(object);
            }
        }

        return added;
    }

    @Override
    public T set(int i, T t) {
        T removed = super.set(i, t);
        processRemovedObjectEvent(removed);
        processAddedObjectEvent(t);

        return removed;
    }

    public boolean addOnly(T object) {
        return super.add(object);
    }

    public boolean removeOnly(Object o) {
        return super.remove(o);
    }

    public void clearOnly() {
        super.clear();
    }
}
