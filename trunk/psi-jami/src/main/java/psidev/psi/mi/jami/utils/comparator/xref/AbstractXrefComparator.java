package psidev.psi.mi.jami.utils.comparator.xref;

import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Xref;

import java.util.Comparator;

/**
 * Simple Xref comparator.
 * It will first compare the external identifier composed of database and id using ExternalIdentifierComparator and then it will
 * compare the qualifier with the same CvTermComparator used by ExternalIdentifierComparator.
 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public abstract class AbstractXrefComparator implements Comparator<Xref> {

    protected Comparator<ExternalIdentifier> identifierComparator;

    /**
     * Creates a new AbstractXrefComparator
     * @param identifierComparator : the ExternalIdentifierComparator is required to compare database, id and qualifier
     */
    public AbstractXrefComparator(Comparator<ExternalIdentifier> identifierComparator){
        if(identifierComparator == null){
            throw new IllegalArgumentException("The ExternalIdentifierComparator is required to compare the database, id and qualifier. It cannot be null");
        }
        this.identifierComparator = identifierComparator;
    }

    public Comparator<ExternalIdentifier> getIdentifierComparator() {
        return identifierComparator;
    }

    /**
     * It will first compare the external identifier composed of database and id using ExternalIdentifierComparator and then it will
     * compare the qualifier with the same CvTermComparator used by ExternalIdentifierComparator.
     * - Two aliases which are null are equals
     * - The alias which is not null is before null.
     * @param xref1
     * @param xref2
     * @return
     */
    public abstract int compare(Xref xref1, Xref xref2);
}
