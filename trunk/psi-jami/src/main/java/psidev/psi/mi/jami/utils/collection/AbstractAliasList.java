package psidev.psi.mi.jami.utils.collection;

import psidev.psi.mi.jami.model.Alias;

import java.util.ArrayList;

/**
 * Abstract list of aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/02/13</pre>
 */

public abstract class AbstractAliasList extends ArrayList<Alias> {

    public AbstractAliasList(){
        super();
    }

    @Override
    public boolean add(Alias alias) {
        boolean added = super.add(alias);

        if (added){
            processAddedAliasEvent(alias);
            return true;
        }

        return false;
    }

    protected abstract void processAddedAliasEvent(Alias added);

    @Override
    public boolean remove(Object o) {
        if (super.remove(o)){
            // we have nothing left in aliases, reset standard values
            if (isEmpty()){
                clearProperties();
            }
            else {
                processRemovedAliasEvent((Alias) o);
            }

            return true;
        }
        return false;
    }

    protected abstract void processRemovedAliasEvent(Alias removed);

    @Override
    public void clear() {
        super.clear();
        clearProperties();
    }

    protected abstract void clearProperties();
}
