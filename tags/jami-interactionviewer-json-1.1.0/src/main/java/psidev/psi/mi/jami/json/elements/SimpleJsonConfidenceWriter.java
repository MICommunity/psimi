package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Confidence;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for confidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonConfidenceWriter implements JsonElementWriter<Confidence>{

    private Writer writer;

    public SimpleJsonConfidenceWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json confidence writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(Confidence object) throws IOException {
        MIJsonUtils.writeStartObject(writer);
        MIJsonUtils.writeProperty("type", JSONValue.escape(object.getType().getShortName()), writer);
        MIJsonUtils.writeSeparator(writer);
        MIJsonUtils.writeProperty("value", JSONValue.escape(object.getValue()), writer);
        MIJsonUtils.writeEndObject(writer);
    }
}
