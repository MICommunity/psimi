package psidev.psi.mi.jami.json.elements;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;

import java.io.IOException;

/**
 * JSON writer for Range objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public interface JsonRangeWriter extends JsonElementWriter<Range>{

    public void write(Range object, Feature parent) throws IOException;
}
