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

    public void registerForResolution();
    public void resolve(Map<Integer,Object> parsedObjects);
}
