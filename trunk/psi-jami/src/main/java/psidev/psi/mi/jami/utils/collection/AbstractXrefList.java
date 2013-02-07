package psidev.psi.mi.jami.utils.collection;

import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;

/**
 * Abstract class for identifiers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/02/13</pre>
 */

public abstract class AbstractXrefList extends ArrayList<Xref> {

    public AbstractXrefList(){
        super();
    }

    @Override
    public boolean add(Xref ref) {
        boolean added = super.add(ref);

        if (added){
            processAddedXrefEvent(ref);
            return true;
        }

        return false;
    }

    protected abstract void processAddedXrefEvent(Xref added);

    @Override
    public boolean remove(Object o) {
        if (super.remove(o)){
            // we have nothing left in identifiers, reset standard values
            if (isEmpty()){
                clearProperties();
            }
            else {
                processRemovedXrefEvent((Xref)o);
            }

            return true;
        }
        return false;
    }

    protected abstract void processRemovedXrefEvent(Xref removed);

    @Override
    public void clear() {
        super.clear();
        clearProperties();
    }

    protected abstract void clearProperties();
}
