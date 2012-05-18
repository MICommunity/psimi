package org.hupo.psi.calimocho.model.definition;

import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.model.*;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class PlainDocumentDefinition extends AbstractDocumentDefinition {

    private static final String NEW_LINE = System.getProperty( "line.separator" );

    public CalimochoDocument readDocument( Reader reader ) throws IOException, IllegalRowException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void writeDocument( Writer writer, CalimochoDocument calimochoDocument ) throws IOException, IllegalRowException {

        for (Row row : calimochoDocument.getRows()) {

            writer.write( "Row {"+NEW_LINE );

            for (String key : row.keySet()) {
                writer.write("\t"+key+" {"+NEW_LINE);

                for (Field field : row.getFields( key )) {
                    writer.write("\t\tField {"+NEW_LINE);

                    for ( Map.Entry entry : field.getEntries().entrySet() ) {
                        writer.write("\t\t\t"+entry.getKey()+"="+entry.getValue()+NEW_LINE);
                    }
                    writer.write("\t\t}"+NEW_LINE);
                }
                writer.write("\t}"+NEW_LINE);

            }

            writer.write( "}"+NEW_LINE );
            writer.flush();
        }


    }


}
