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

public abstract class CvTermComparator<T extends Comparator<ExternalIdentifier>> implements Comparator<CvTerm> {

    protected T identifierComparator;

    public CvTermComparator(){
       instantiateOntologyIdentifierComparator();
    }

    /**
     * Create the ontology identifier comparator
     */
    protected abstract void instantiateOntologyIdentifierComparator();
}
