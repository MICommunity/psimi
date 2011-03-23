package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.Row;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DefaultRowWriter implements RowWriter{

    public void read( Writer writer, List<Row> rows ) throws IOException, IllegalRowException {
    }

    public String writeLine( Row row ) throws IllegalRowException, IllegalColumnException, IllegalFieldException {
        return null;
    }

    public void read( OutputStream os, List<Row> rows ) throws IOException, IllegalRowException {
    }
}
