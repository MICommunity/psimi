package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Comparator;

/**
 * Abstract Interaction comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public abstract class AbstractInteractionBaseComparator implements Comparator<Interaction> {

    protected AbstractCvTermComparator cvTermComparator;
    protected Comparator<Xref> identifierComparator;

    /**
     * @param identifierComparator : required to compare identifiers
     * @param cvTermComparator : required to compare interaction type
     */
    public AbstractInteractionBaseComparator(Comparator<Xref> identifierComparator, AbstractCvTermComparator cvTermComparator){
        if (identifierComparator == null){
            throw new IllegalArgumentException("The external identifier comparator is required to compares identifiers. It cannot be null");
        }
        this.identifierComparator = identifierComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare interaction types. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public Comparator<Xref> getIdentifierComparator() {
        return identifierComparator;
    }

    /**
     *
     * @param interaction1
     * @param interaction2
     * @return
     */
    public abstract int compare(Interaction interaction1, Interaction interaction2);
}
