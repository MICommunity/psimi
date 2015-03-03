package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.CvTerm;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for cv terms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonCvTermWriter implements JsonElementWriter<CvTerm>{

    private Writer writer;

    public SimpleJsonCvTermWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json cv term writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(CvTerm object) throws IOException {
        MIJsonUtils.writeStartObject(writer);
        boolean hasId = false;
        if (object.getMIIdentifier() != null){
            MIJsonUtils.writeProperty("id", JSONValue.escape(object.getMIIdentifier()), writer);
            hasId = true;
        }
        else if (object.getMODIdentifier() != null){
            MIJsonUtils.writeProperty("id", JSONValue.escape(object.getMODIdentifier()), writer);
            hasId = true;
        }
        else if (object.getPARIdentifier() != null){
            MIJsonUtils.writeProperty("id", JSONValue.escape(object.getPARIdentifier()), writer);
            hasId = true;
        }
        else if (!object.getIdentifiers().isEmpty()){
            MIJsonUtils.writeProperty("id", JSONValue.escape(object.getIdentifiers().iterator().next().getId()), writer);
            hasId = true;
        }

        if (hasId){
            MIJsonUtils.writeSeparator(writer);
        }

        if (object.getFullName() != null){
            MIJsonUtils.writeProperty("name", JSONValue.escape(object.getFullName()), writer);
        }
        else {
            MIJsonUtils.writeProperty("name", JSONValue.escape(object.getShortName()), writer);
        }
        MIJsonUtils.writeEndObject(writer);
    }
}
