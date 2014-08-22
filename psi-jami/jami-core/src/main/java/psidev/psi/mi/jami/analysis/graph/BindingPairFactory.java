package psidev.psi.mi.jami.analysis.graph;

import org.jgrapht.EdgeFactory;
import psidev.psi.mi.jami.analysis.graph.model.BindingPair;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.IdentityHashComparator;
import psidev.psi.mi.jami.utils.comparator.MIComparator;

/**
 * A feature edge factory is an edge factory generating FeatureBindingPairs
 *
 * The factory can be instantiated with a specific MIComparator if it wants to override the comparison and hashcodes of features A and B
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class BindingPairFactory<F extends Feature> implements EdgeFactory<F,BindingPair<F>>{

    private MIComparator<F> customFeatureComparator;

    public BindingPairFactory(){
        customFeatureComparator = new IdentityHashComparator<F>();
    }

    public BindingPairFactory(MIComparator<F> comparator){
        customFeatureComparator = comparator != null ? comparator : new IdentityHashComparator<F>();
    }

    public BindingPair<F> createEdge(F sourceVertex, F targetVertex) {
        try {
            return new BindingPair<F>(sourceVertex, targetVertex, this.customFeatureComparator);
        } catch (Exception ex) {
            throw new RuntimeException("Feature Edge factory failed", ex);
        }
    }
}
