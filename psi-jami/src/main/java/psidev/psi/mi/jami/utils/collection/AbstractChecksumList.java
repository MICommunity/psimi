package psidev.psi.mi.jami.utils.collection;

import psidev.psi.mi.jami.model.Checksum;

import java.util.ArrayList;

/**
 * Abstract checksum list
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/02/13</pre>
 */

public abstract class AbstractChecksumList extends ArrayList<Checksum>{

    public AbstractChecksumList(){
        super();
    }

    @Override
    public boolean add(Checksum checksum) {
        boolean added = super.add(checksum);

        if (added){
            processAddedChecksumEvent(checksum);
            return true;
        }

        return false;
    }

    protected abstract void processAddedChecksumEvent(Checksum added);

    @Override
    public boolean remove(Object o) {
        if (super.remove(o)){
            // we have nothing left in identifiers, reset standard values
            if (isEmpty()){
                clearProperties();
            }
            else {
                processRemovedChecksumEvent((Checksum) o);
            }

            return true;
        }
        return false;
    }

    protected abstract void processRemovedChecksumEvent(Checksum removed);

    @Override
    public void clear() {
        super.clear();
        clearProperties();
    }

    protected abstract void clearProperties();
}

