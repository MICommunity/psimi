package psidev.psi.mi.jami.utils.collection;

import java.util.ListIterator;

/**
 * Iterator for list having properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/15</pre>
 */

public class ListIteratorHavingProperties<T> extends IteratorHavingProperties<T> implements ListIterator<T> {

    private T lastObject;

    public ListIteratorHavingProperties(AbstractListHavingProperties<T> originalCollection, ListIterator<T> originalIterator){
        super(originalCollection, originalIterator);
    }

    public boolean hasPrevious() {
        return getOriginalIterator().hasPrevious();
    }

    public T previous() {
        this.lastObject = getOriginalIterator().previous();
        return this.lastObject;
    }

    public int nextIndex() {
        return getOriginalIterator().nextIndex();
    }

    public int previousIndex() {
        return getOriginalIterator().previousIndex();
    }

    public void set(T t) {
        getOriginalCollection().processRemovedObjectEvent(this.lastObject);
        getOriginalIterator().set(t);
        getOriginalCollection().processAddedObjectEvent(this.lastObject);
    }

    public void add(T t) {
        getOriginalIterator().add(t);
        getOriginalCollection().processAddedObjectEvent(t);
    }

    protected ListIterator<T> getOriginalIterator() {
        return (ListIterator<T>)super.getOriginalIterator();
    }

    @Override
    public T next() {
        this.lastObject = super.next();
        return this.lastObject;
    }
}
