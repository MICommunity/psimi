package psidev.psi.mi.jami.xml;

import java.util.Map;

/**
 * A XML reference to an object having an id
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public interface XmlIdReference {

    /**
     * Register itself as an id to resolve later
     */
    public void registerForResolution();

    /**
     * Resolve the references given a map of parsed objects
     * @param parsedObjects
     * @return true if the reference was solved. false otherwise
     */
    public boolean resolve(Map<Integer,Object> parsedObjects);
}
