package psidev.psi.mi.jami.analysis.graph;

import org.jgrapht.Graph;
import psidev.psi.mi.jami.analysis.graph.model.BindingFeatureGraph;
import psidev.psi.mi.jami.analysis.graph.model.BindingPair;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class will create a graph from one to several interactions.
 * Each edge is a bindingPair and each node is a feature.
 * It will build a BindingFeatureGraph
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class FeatureGraphBuilder<I extends Interaction, F extends Feature> {

    public Graph<F,BindingPair<F>> buildGraphFrom(I interaction){
        if (interaction == null){
            return null;
        }
        Graph<F,BindingPair<F>> graph = new BindingFeatureGraph<F>();
        // collect all features having linked features from interaction and add to the graph
        collectFeaturesAndAddToGraph(interaction, graph);
        return graph;
    }

    public Graph<F,BindingPair<F>> buildGraphFrom(Collection<I> interaction){
        if (interaction == null){
            return null;
        }
        return buildGraphFrom(interaction.iterator());
    }

    public Graph<F,BindingPair<F>> buildGraphFrom(Iterator<I> interaction){
        if (interaction == null){
            return null;
        }
        Graph<F,BindingPair<F>> graph = new BindingFeatureGraph<F>();
        // add features to the graph
        while(interaction.hasNext()){
            collectFeaturesAndAddToGraph(interaction.next(), graph);
        }
        return graph;
    }

    private void collectFeaturesAndAddToGraph(I interaction, Graph<F, BindingPair<F>> graph) {
        for (Object o : interaction.getParticipants()){
            Participant participant = (Participant)o;
            for (Object o2 : participant.getFeatures()){
                F feature = (F)o2;
                if (!feature.getLinkedFeatures().isEmpty()){
                    graph.addVertex(feature);
                    for (Object o3 : feature.getLinkedFeatures()){
                        F feature2 = (F)o3;
                        graph.addVertex(feature2);
                        graph.addEdge(feature, feature2);
                    }
                }
            }
        }
    }
}
