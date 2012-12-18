package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Abstract comparators for CvTerms.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public abstract class AbstractCvTermComparator<T extends AbstractExternalIdentifierComparator> implements Comparator<CvTerm> {

    protected T identifierComparator;

    public AbstractCvTermComparator(){
       instantiateOntologyIdentifierComparator();
    }

    /**
     * Create the ontology identifier comparator
     */
    protected abstract void instantiateOntologyIdentifierComparator();
}
