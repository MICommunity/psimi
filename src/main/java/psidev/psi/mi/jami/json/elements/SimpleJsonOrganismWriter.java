package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Organism;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for organisms. It ignores celltype, tissue and compartment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonOrganismWriter implements JsonElementWriter<Organism>{

    private Writer writer;

    public SimpleJsonOrganismWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json organism writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(Organism object) throws IOException {
        MIJsonUtils.writeStartObject(writer);
        MIJsonUtils.writeProperty("taxid", Integer.toString(object.getTaxId()), writer);
        if (object.getCommonName() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("common", JSONValue.escape(object.getCommonName()), writer);
        }
        if (object.getScientificName() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writeProperty("scientific", JSONValue.escape(object.getScientificName()), writer);
        }

        writeOtherProperties(object);

        MIJsonUtils.writeEndObject(writer);
    }

    protected void writeOtherProperties(Organism object) throws IOException {
        // nothing to do here but can be overriden
    }

    protected Writer getWriter() {
        return writer;
    }
}
