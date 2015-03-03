package psidev.psi.mi.jami.json;

/**
 * Id generator that will increment ids
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/14</pre>
 */

public class IncrementalIdGenerator {

    private int currentId=0;

    public int getCurrentId() {
        return currentId;
    }

    public int nextId() {
        currentId++;
        return currentId;
    }
}
