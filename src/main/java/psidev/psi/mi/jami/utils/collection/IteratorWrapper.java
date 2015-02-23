package psidev.psi.mi.jami.utils.collection;

import java.util.Iterator;

/**
 * Iterator for list wrappers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/15</pre>
 */

public class IteratorWrapper<T> implements Iterator<T> {

    private Iterator<T> originalIterator;
    private AbstractCollectionWrapper<T> originalCollection;

    private T currentObject;

    public IteratorWrapper(AbstractCollectionWrapper<T> originalCollection, Iterator<T> originalIterator){
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
        if (this.originalCollection.needToPreProcessElementToRemove(this.currentObject)){
            this.originalCollection.processElementToRemove(this.currentObject);
        }
        this.originalIterator.remove();
    }

    protected Iterator<T> getOriginalIterator() {
        return originalIterator;
    }

    protected AbstractCollectionWrapper<T> getOriginalCollection() {
        return originalCollection;
    }

    protected T getCurrentObject() {
        return currentObject;
    }
}
