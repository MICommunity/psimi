package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

/**
 * Default implementation for ExternalIdentifier
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultExternalIdentifier extends DefaultXref implements ExternalIdentifier{

    public DefaultExternalIdentifier(CvTerm database, String id, Integer version){
        super(database, id, version, new DefaultCvTerm(Xref.IDENTITY, new DefaultExternalIdentifier(new DefaultCvTerm(CvTerm.PSI_MI), Xref.IDENTITY_MI)));
    }

    public DefaultExternalIdentifier(CvTerm database, String id){
        super(database, id, new DefaultCvTerm(Xref.IDENTITY, new DefaultExternalIdentifier(new DefaultCvTerm(CvTerm.PSI_MI), Xref.IDENTITY_MI)));

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

    @Override
    public String toString() {
        return getDatabase().toString() + ":" + getId().toString();
    }
}
