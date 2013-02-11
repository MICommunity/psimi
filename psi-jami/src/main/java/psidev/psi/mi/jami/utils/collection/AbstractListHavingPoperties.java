package psidev.psi.mi.jami.utils.collection;

import java.util.ArrayList;

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
        super.clear();
        clearProperties();
    }

    protected abstract void clearProperties();
}
