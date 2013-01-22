package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousXrefComparator;

/**
 * Default implementation for Xref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultXref implements Xref {

    private CvTerm database;
    private String id;
    private Integer version;
    private CvTerm qualifier;

    public DefaultXref(CvTerm database, String id, CvTerm qualifier){
        this(database, id);
        this.qualifier = qualifier;
    }

    public DefaultXref(CvTerm database, String id, Integer version, CvTerm qualifier){
        this(database, id, version);
        this.qualifier = qualifier;
    }

    public DefaultXref(CvTerm database, String id, Integer version){
        this(database, id);
        this.version = version;
    }

    public DefaultXref(CvTerm database, String id){
        if (database == null){
            throw new IllegalArgumentException("The database is required and cannot be null");
        }
        this.database = database;

        if (id == null || (id != null && id.length() == 0)){
            throw new IllegalArgumentException("The id is required and cannot be null or empty");
        }
        this.id = id;
    }

    public CvTerm getDatabase() {
        return database;
    }

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public CvTerm getQualifier() {
        return this.qualifier;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o){
            return true;
        }

        // Xrefs are different and it has to be ExternalIdentifier
        if (!(o instanceof Xref)){
            return false;
        }

        return UnambiguousXrefComparator.areEquals(this, (Xref) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousXrefComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return database.toString() + ":" + id.toString() + (qualifier != null ? " (" + qualifier.toString() + ")" : "");
    }
}
