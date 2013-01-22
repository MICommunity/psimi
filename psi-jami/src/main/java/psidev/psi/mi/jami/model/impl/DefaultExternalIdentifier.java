package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.io.Serializable;

/**
 * Default implementation for ExternalIdentifier
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultExternalIdentifier implements ExternalIdentifier, Serializable{

    private CvTerm database;
    private String id;
    private Integer version;

    public DefaultExternalIdentifier(CvTerm database, String id, Integer version){
        this(database, id);
        this.version = version;
    }

    public DefaultExternalIdentifier(CvTerm database, String id){
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

    @Override
    public boolean equals(Object o) {

        if (this == o){
            return true;
        }

        if (!(o instanceof ExternalIdentifier)){
            return false;
        }

        return UnambiguousExternalIdentifierComparator.areEquals(this, (ExternalIdentifier) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousExternalIdentifierComparator.hashCode(this);
    }
}
