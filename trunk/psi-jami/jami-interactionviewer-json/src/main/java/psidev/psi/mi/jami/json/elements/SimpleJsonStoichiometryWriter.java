package psidev.psi.mi.jami.json.elements;

import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Stoichiometry;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for stoichiometry
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonStoichiometryWriter implements JsonElementWriter<Stoichiometry>{

    private Writer writer;

    public SimpleJsonStoichiometryWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json stoichiometry writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(Stoichiometry object) throws IOException {
        if (object != null &&
                object.getMinValue() > 0 &&
                object.getMaxValue() > 0){
            if (object.getMaxValue() == object.getMinValue()){
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writeProperty("stoichiometry", Integer.toString(object.getMinValue()), writer);
            }
            else{
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writeProperty("minStoichiometry", Integer.toString(object.getMinValue()), writer);
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writeProperty("maxStoichiometry", Integer.toString(object.getMaxValue()), writer);
            }
        }
    }
}
