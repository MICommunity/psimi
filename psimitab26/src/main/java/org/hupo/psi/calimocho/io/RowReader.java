package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.Row;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * Reads data generating list of Rows.
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface RowReader {

    /**
     * Reads data, creating a list of Rows
     * @param reader the reader with the data
     * @return a list of Rows representing the data read
     * @throws IOException thrown if there are problems reaching the read content
     * @throws IllegalRowException thrown if there are inconsistencies creating the Row object
     */
    List<Row> read( Reader reader ) throws IOException, IllegalRowException;

    /**
     * Reads data, creating a list of Rows
     * @param is input stream with the data
     * @return a list of Rows representing the data read
     * @throws IOException thrown if there are problems reaching the read content
     * @throws IllegalRowException thrown if there are inconsistencies creating the Row object
     */
    List<Row> read( InputStream is ) throws IOException, IllegalRowException;

    /**
     * Reads a line of text, creating a Row.
     * @param line the line to transform
     * @return the Row for the line
     * @throws IOException thrown if there are problems reaching the read content
     * @throws IllegalRowException thrown if there are inconsistencies creating the Row object
     * @throws IllegalColumnException thrown if there is a problem understanding the column structure
     */
    Row readLine( String line ) throws IllegalRowException, IllegalColumnException, IllegalFieldException;
}
