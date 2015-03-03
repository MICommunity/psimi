package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Annotation;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonAnnotationWriter implements JsonElementWriter<Annotation>{

    private Writer writer;

    public SimpleJsonAnnotationWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json annotation writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(Annotation object) throws IOException {
        if (object.getValue() != null){
            MIJsonUtils.writePropertyValue(JSONValue.escape(object.getValue()), writer);
        }
    }
}
