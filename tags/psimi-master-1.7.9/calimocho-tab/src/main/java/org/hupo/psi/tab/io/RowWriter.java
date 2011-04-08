package org.hupo.psi.tab.io;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.model.Row;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;

/**
 * TODO document this !
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface RowWriter {

    /**
     * Writes a collection of Row objects.
     * @param writer the writer to use
     * @param rows the rows to write
     * @throws IOException thrown if there is a problem writing the output
     * @throws org.hupo.psi.calimocho.io.IllegalRowException thrown if there is a problem understanding the Row
     */
    void write( Writer writer, Collection<Row> rows ) throws IOException, IllegalRowException;

    /**
     * Writes a collection of Row objects.
     * @param os the output stream to use
     * @param rows the rows to write
     * @throws IOException thrown if there is a problem writing the output
     * @throws IllegalRowException thrown if there is a problem understanding the Row
     */
    void write( OutputStream os, Collection<Row> rows ) throws IOException, IllegalRowException;

    /**
     * Writes a line using the Row object.
     * @param row the row to write
     * @throws IllegalRowException thrown if there is a problem understanding the Row
     * @return the line for the row
     */
    String writeLine( Row row ) throws IllegalRowException, IllegalColumnException, IllegalFieldException;
}
