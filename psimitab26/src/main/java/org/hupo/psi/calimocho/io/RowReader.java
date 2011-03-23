package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.Row;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface RowReader {
    List<Row> read( Reader reader ) throws IOException, IllegalRowException;

    Row readLine( String line ) throws IllegalRowException, IllegalColumnException, IllegalFieldException;

    List<Row> read( InputStream is ) throws IOException, IllegalRowException;
}
