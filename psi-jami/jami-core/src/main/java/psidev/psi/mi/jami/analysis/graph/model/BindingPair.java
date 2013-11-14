package psidev.psi.mi.jami.analysis.graph.model;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.IdentityHashComparator;

import java.util.Comparator;

/**
 * A feature binding pair is an undirected MI edge o features that are linked to each other.
 * By default, It will use an identityHashComparator to compare features.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class BindingPair<F extends Feature> extends AbstractMIEdge<F>{
    public BindingPair(F nodeA, F nodeB) {
        super(nodeA, nodeB, new IdentityHashComparator<F>());
    }

    public BindingPair(F nodeA, F nodeB, Comparator<F> nodeComparator) {
        super(nodeA, nodeB, nodeComparator);
    }
}
