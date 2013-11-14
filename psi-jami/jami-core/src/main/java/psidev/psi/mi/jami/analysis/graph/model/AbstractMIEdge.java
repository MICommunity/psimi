package psidev.psi.mi.jami.analysis.graph.model;

import java.util.Comparator;

/**
 * Abstract class for MIEdge.
 * Node A and node B are sorted according to a provided comparator.
 * Equals and hascode are overriden so an edge is equals if nodeA and nodeB are the same and mutable.
 * The comparator will be used to test if two objects are identical in the equals method.
 * The comparator has to be consistent with the equals method of the nodes A and B so the generated hashcode is also consistent
 * because it will be based on node A and node B hashcodes.
 * (A-B is the same as B-A)
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractMIEdge<T> implements MIEdge<T> {

    private T nodeA;
    private T nodeB;
    private Comparator<T> nodeComparator;

    public AbstractMIEdge(T nodeA, T nodeB, Comparator<T> nodeComparator){
         if (nodeA == null){
             throw new IllegalArgumentException("The nodeA cannot be null");
         }
        if (nodeB == null){
            throw new IllegalArgumentException("The nodeB cannot be null");
        }
        if (nodeComparator == null){
            throw new IllegalArgumentException("The node comparator cannot be null");
        }
        this.nodeComparator = nodeComparator;
        int comp1 = nodeComparator.compare(nodeA, nodeB);
        // node A is before node B
        if (comp1 < 0 || comp1 == 0){
            this.nodeA = nodeA;
            this.nodeB = nodeB;
        }
        else {
            this.nodeA = nodeB;
            this.nodeB = nodeA;
        }
    }

    public T getNodeA() {
        return nodeA;
    }

    public T getNodeB() {
        return nodeB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        if (!this.getClass().equals(o.getClass())){
            return false;
        }

        AbstractMIEdge edge = (AbstractMIEdge)o;
        return this.nodeComparator.compare(nodeA, (T)edge.getNodeA()) == 0
                && this.nodeComparator.compare(nodeB, (T)edge.getNodeB()) == 0;
    }

    @Override
    public int hashCode() {
        int hashcode = 31;
        hashcode = 31*hashcode + nodeA.hashCode();
        hashcode = 31 * hashcode + nodeB.hashCode();
        return hashcode;
    }
}
