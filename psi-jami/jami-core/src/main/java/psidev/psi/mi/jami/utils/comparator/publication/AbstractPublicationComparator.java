package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.util.*;

/**
 * Abstract Publication comparator.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public abstract class AbstractPublicationComparator implements Comparator<Publication> {

    protected Comparator<Xref> identifierComparator;

    /**
     * Creates a new AbstractPublicationComparator.
     * @param identifierComparator : the comparator for identifiers. It is required
     */
    public AbstractPublicationComparator(Comparator<Xref> identifierComparator){
        if (identifierComparator == null){
            throw new IllegalArgumentException("The ExternalIdentifier comparator is required to compare publication identifiers. It cannot be null");
        }
        this.identifierComparator = identifierComparator;
    }

    public Comparator<Xref> getIdentifierComparator() {
        return identifierComparator;
    }

    /**
     * @param publication1
     * @param publication2
     * @return
     */
    public abstract int compare(Publication publication1, Publication publication2);
}
