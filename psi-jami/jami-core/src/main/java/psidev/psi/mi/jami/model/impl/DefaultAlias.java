package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;

import java.io.Serializable;

/**
 * Default implementation of Alias
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultAlias implements Alias, Serializable {

    private CvTerm type;
    private String name;

    public DefaultAlias(CvTerm type, String name) {
        this(name);
        this.type = type;
    }

    public DefaultAlias(String name) {
        if (name == null){
            throw new IllegalArgumentException("The alias name is required and cannot be null");
        }
        this.name = name;
    }

    public CvTerm getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Alias)){
            return false;
        }

        return UnambiguousAliasComparator.areEquals(this, (Alias) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousAliasComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return name + (type != null ? "("+type.toString()+")" : "");
    }
}
