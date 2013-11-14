package psidev.psi.mi.jami.analysis.graph.model;

import org.jgrapht.graph.Pseudograph;
import psidev.psi.mi.jami.analysis.graph.BindingPairFactory;
import psidev.psi.mi.jami.model.Feature;

/**
 * A binding feature graph is a pseudograph of binding features.
 * A pseudograph is a non-simple undirected graph in which both graph loops and multiple edges are permitted.
 * See: http://mathworld.wolfram.com/Pseudograph.html.
 *
 * It used a BindingPairFactory to generate edges between features.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class BindingFeatureGraph<T extends Feature> extends Pseudograph<T,BindingPair<T>>{

    public BindingFeatureGraph() {
        super(new BindingPairFactory<T>());
    }
}
