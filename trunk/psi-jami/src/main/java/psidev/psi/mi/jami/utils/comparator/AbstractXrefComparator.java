package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.Comparator;

/**
 * Abstract class for Xref comparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public abstract class AbstractXrefComparator<T extends AbstractExternalIdentifierComparator> implements Comparator<Xref> {

    protected T identifierComparator;

    public AbstractXrefComparator(){
        instantiateIdentifierComparator();
    }

    protected abstract void instantiateIdentifierComparator();

    public int compare(Xref xref1, Xref xref2) {

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        int comp = identifierComparator.compare(xref1, xref2);
        if (comp != 0){
           return comp;
        }

        CvTerm qualifier1 = xref1.getQualifier();
        CvTerm qualifier2 = xref2.getQualifier();

        return identifierComparator.getDatabaseComparator().compare(qualifier1, qualifier2);
    }
}
