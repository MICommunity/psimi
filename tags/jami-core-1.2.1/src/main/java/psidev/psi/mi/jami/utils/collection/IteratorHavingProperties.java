package psidev.psi.mi.jami.utils.collection;

import java.util.Iterator;

/**
 * Iterator for list having properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/15</pre>
 */

public class IteratorHavingProperties<T> implements Iterator<T> {

    private Iterator<T> originalIterator;
    private AbstractListHavingProperties<T> originalCollection;

    private T currentObject;

    public IteratorHavingProperties(AbstractListHavingProperties<T> originalCollection, Iterator<T> originalIterator){
        if (originalCollection == null){
            throw new IllegalArgumentException("The original collection cannot be null");
        }
        if (originalIterator == null){
            throw new IllegalArgumentException("The original iterator cannot be null");
        }
        this.originalCollection = originalCollection;
        this.originalIterator = originalIterator;
    }

    public boolean hasNext() {
        return this.originalIterator.hasNext();
    }

    public T next() {
        this.currentObject = this.originalIterator.next();
        return currentObject;
    }

    public void remove() {
        this.originalIterator.remove();
        this.originalCollection.processRemovedObjectEvent(this.currentObject);
    }

    protected Iterator<T> getOriginalIterator() {
        return originalIterator;
    }

    protected AbstractListHavingProperties<T> getOriginalCollection() {
        return originalCollection;
    }

    protected T getCurrentObject() {
        return currentObject;
    }
}
