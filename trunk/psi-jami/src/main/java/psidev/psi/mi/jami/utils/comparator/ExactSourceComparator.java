package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;

/**
 * Exact source comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class ExactSourceComparator extends UnambiguousCvTermComparator{

    public ExactSourceComparator() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int compare(CvTerm cvTerm1, CvTerm cvTerm2) {
        return super.compare(cvTerm1, cvTerm2);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
