package psidev.psi.mi.jami.analysis.graph;

import org.jgrapht.alg.BronKerboschCliqueFinder;
import psidev.psi.mi.jami.analysis.graph.model.BindingFeatureGraph;
import psidev.psi.mi.jami.analysis.graph.model.BindingPair;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * The BindingSiteClique finder is an extendion of BronKerboschCliqueFinder.
 *
 * It can find all maximal cliques in a graph of binding feature pairs (can retrieve complete graph of binding sites).
 * This BronKerboschCliqueFinder implements Bron-Kerbosch clique detection algorithm as it is described in
 * [Samudrala R.,Moult J.:A Graph-theoretic Algorithm for comparative Modeling of Protein Structure; J.Mol. Biol. (1998); vol 279; pp. 287-302]
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class BindingSiteCliqueFinder<I extends Interaction, F extends Feature> {

    private FeatureGraphBuilder<I,F> graphBuilder;
    private BronKerboschCliqueFinder<F,BindingPair<F>> cliqueFinder;
    /**
     * Creates a new clique finder.
     *
     * @param graph the graph in which cliques are to be found; graph must be
     *              simple
     */
    public BindingSiteCliqueFinder(BindingFeatureGraph<F> graph) {
        this.graphBuilder = new FeatureGraphBuilder<I, F>();
        this.cliqueFinder = new BronKerboschCliqueFinder<F, BindingPair<F>>(graph);
    }

    public BindingSiteCliqueFinder(I interaction) {
        this.graphBuilder = new FeatureGraphBuilder<I, F>();
        this.cliqueFinder = new BronKerboschCliqueFinder<F, BindingPair<F>>(graphBuilder.buildGraphFrom(interaction));
    }

    public BindingSiteCliqueFinder(Collection<I> interaction) {
        this.graphBuilder = new FeatureGraphBuilder<I, F>();
        this.cliqueFinder = new BronKerboschCliqueFinder<F, BindingPair<F>>(graphBuilder.buildGraphFrom(interaction));
    }

    public BindingSiteCliqueFinder(Iterator<I> interaction) {
        this.graphBuilder = new FeatureGraphBuilder<I, F>();
        this.cliqueFinder = new BronKerboschCliqueFinder<F, BindingPair<F>>(graphBuilder.buildGraphFrom(interaction));
    }

    public Collection<Set<F>> getAllMaximalCliques(){
        return this.cliqueFinder.getAllMaximalCliques();
    }

    public Collection<Set<F>> getBiggestMaximalCliques(){
        return this.cliqueFinder.getBiggestMaximalCliques();
    }
}
