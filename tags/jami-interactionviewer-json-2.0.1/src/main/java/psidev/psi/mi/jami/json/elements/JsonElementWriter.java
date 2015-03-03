package psidev.psi.mi.jami.json.elements;

import java.io.IOException;

/**
 * JSON writer for JAMI objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public interface JsonElementWriter<T extends Object>{

    public void write(T object) throws IOException;
}
