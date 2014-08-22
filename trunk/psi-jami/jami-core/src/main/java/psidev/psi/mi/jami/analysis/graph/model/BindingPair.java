package psidev.psi.mi.jami.analysis.graph.model;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.IdentityHashComparator;
import psidev.psi.mi.jami.utils.comparator.MIComparator;

/**
 * A feature binding pair is an undirected MI edge of features that are linked to each other.
 * By default, It will use an identityHashComparator to compare features and generate hashCodes for node A and B.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class BindingPair<F extends Feature> extends AbstractMIEdge<F>{
    public BindingPair(F nodeA, F nodeB) {
        super(nodeA, nodeB, new IdentityHashComparator<F>());
    }

    public BindingPair(F nodeA, F nodeB, MIComparator<F> nodeComparator) {
        super(nodeA, nodeB, nodeComparator);
    }
}
