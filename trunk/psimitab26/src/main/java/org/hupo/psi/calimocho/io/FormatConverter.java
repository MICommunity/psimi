/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hupo.psi.calimocho.io;


import org.hupo.psi.calimocho.model.DocumentDefinition;
import org.hupo.psi.calimocho.model.Row;

import java.io.*;
import java.util.Collection;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class FormatConverter {

    private DocumentDefinition sourceDocumentDefinition;
    private DocumentDefinition destinationDocumentDefinition;

    private boolean ignoreFirstLine;

    public FormatConverter( DocumentDefinition sourceDocumentDefinition, DocumentDefinition destinationDocumentDefinition ) {
        this.sourceDocumentDefinition = sourceDocumentDefinition;
        this.destinationDocumentDefinition = destinationDocumentDefinition;
    }

    public FormatConverter( DocumentDefinition sourceDocumentDefinition, DocumentDefinition destinationDocumentDefinition, boolean ignoreFirstLine ) {
        this(sourceDocumentDefinition, destinationDocumentDefinition);
        this.ignoreFirstLine = ignoreFirstLine;
    }

    public void convert(InputStream is, OutputStream os) throws IOException {
        OutputStreamWriter outputWriter = new OutputStreamWriter(os);
        convert(new InputStreamReader(is), outputWriter);
        outputWriter.close();
    }

    public void convert(Reader reader, Writer writer) throws IOException {
        RowReader rowReader = new DefaultRowReader(sourceDocumentDefinition);

        RowWriter rowWriter = new DefaultRowWriter(destinationDocumentDefinition);

        Collection<Row> rows;

        try {
            rows = rowReader.read( reader );
        } catch ( IllegalRowException e ) {
            throw new IOException( "Problem while reading", e );
        }

        try {
            rowWriter.write( writer, rows );
        } catch ( IllegalRowException e ) {
            throw new IOException( "Problem while writing", e );
        }
    }
}
