package psidev.psi.mi.jami.analysis.graph;

import org.jgrapht.EdgeFactory;
import psidev.psi.mi.jami.analysis.graph.model.BindingPair;
import psidev.psi.mi.jami.model.Feature;

/**
 * A feature edge factory is an edge factory generating FeatureBindingPairs
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class BindingPairFactory<F extends Feature> implements EdgeFactory<F,BindingPair<F>>{
    public BindingPair<F> createEdge(F sourceVertex, F targetVertex) {
        try {
            return new BindingPair<F>(sourceVertex, targetVertex);
        } catch (Exception ex) {
            throw new RuntimeException("Feature Edge factory failed", ex);
        }
    }
}
