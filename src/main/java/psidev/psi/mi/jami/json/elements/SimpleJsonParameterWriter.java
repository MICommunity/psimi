package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.utils.ParameterUtils;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for parameters
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonParameterWriter implements JsonElementWriter<Parameter>{

    private Writer writer;

    public SimpleJsonParameterWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json parameter writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(Parameter object) throws IOException {
        MIJsonUtils.writeStartObject(writer);
        MIJsonUtils.writeProperty("type", JSONValue.escape(object.getType().getShortName()), writer);
        MIJsonUtils.writeSeparator(writer);
        MIJsonUtils.writeProperty("value", JSONValue.escape(ParameterUtils.getParameterValueAsString(object)), writer);
        if (object.getUnit() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("unit", JSONValue.escape(object.getUnit().getShortName()), writer);
        }
        MIJsonUtils.writeEndObject(writer);
    }
}
