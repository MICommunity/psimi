package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.io.IllegalRowException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface DocumentDefinition extends Defined {

    CalimochoDocument readDocument(Reader reader) throws IOException, IllegalRowException;

    void writeDocument(Writer writer, CalimochoDocument calimochoDocument) throws IOException, IllegalRowException;

}
