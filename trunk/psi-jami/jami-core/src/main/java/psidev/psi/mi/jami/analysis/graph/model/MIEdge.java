package psidev.psi.mi.jami.analysis.graph.model;

/**
 * A MI edge is an edge joining two MI objects.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public interface MIEdge<T> {

    /**
     * The first node. It cannot be null.
     * @return the first node
     */
    public T getNodeA();

    /**
     * The second node. It cannot be null.
     * @return
     */
    public T getNodeB();
}
