package psidev.psi.mi.jami.utils.collection;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.AbstractIdentifierComparator;

import java.util.TreeSet;

/**
 * Abstract class for
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/02/13</pre>
 */

public abstract class AbstractXrefTreeSet extends TreeSet<Xref>{

    public AbstractXrefTreeSet(AbstractIdentifierComparator xrefComparator){
        super(xrefComparator);
    }

    @Override
    public boolean add(Xref ref) {
        boolean added = super.add(ref);

        if (added){
            processAddedXrefEvent();
            return true;
        }

        return false;
    }

    protected abstract void processAddedXrefEvent();

    @Override
    public boolean remove(Object o) {
        if (super.remove(o)){
            // we have nothing left in identifiers, reset standard values
            if (isEmpty()){
                clearProperties();
            }
            else {
                processRemovedXrefEvent();
            }

            return true;
        }
        return false;
    }

    protected abstract void processRemovedXrefEvent();

    @Override
    public void clear() {
        super.clear();
        clearProperties();
    }

    protected abstract void clearProperties();
}
