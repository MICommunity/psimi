package psidev.psi.mi.jami.json.nary.elements;

import psidev.psi.mi.jami.model.Range;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for ranges
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class JsonRangeWriter implements JsonElementWriter<Range>{

    private Writer writer;

    public JsonRangeWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json range writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(Range object) throws IOException {

    }
}
