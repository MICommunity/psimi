package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;

import java.util.Comparator;

/**
 * Abstract comparators for CvTerms.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public abstract class AbstractCvTermComparator implements Comparator<CvTerm> {

    protected Comparator<ExternalIdentifier> identifierComparator;
    /**
     * Creates a new CvTermComparator
     * @param comparator : external identifier comparator which is required to compare ontology identifiers
     */
    public AbstractCvTermComparator(Comparator<ExternalIdentifier> comparator){
        if (comparator == null){
            throw new IllegalArgumentException("The ExternalIdentifier comparator is required and cannot be null");
        }
        this.identifierComparator = comparator;
    }

    public Comparator<ExternalIdentifier> getIdentifierComparator(){
        return identifierComparator;
    }

    /**
     * Two Cv terms that are null/not null are equals.
     * The Cv term that is not null comes before the one that is null.
     * @param cvTerm1
     * @param cvTerm2
     * @return
     */
    public abstract int compare(CvTerm cvTerm1, CvTerm cvTerm2);
}
