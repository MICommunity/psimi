package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.Row;

import java.io.*;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface RowWriter {
    void write( Writer writer, List<Row> rows ) throws IOException, IllegalRowException;

    String writeLine( Row row ) throws IllegalRowException, IllegalColumnException, IllegalFieldException;

    void write( OutputStream os, List<Row> rows ) throws IOException, IllegalRowException;
}
