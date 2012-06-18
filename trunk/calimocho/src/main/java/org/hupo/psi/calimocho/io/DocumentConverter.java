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


import org.hupo.psi.calimocho.model.CalimochoDocument;
import org.hupo.psi.calimocho.model.DocumentDefinition;

import java.io.*;

/**
 * Converts documents from one format to another, by using DocumentDefinitions.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class DocumentConverter {

    private DocumentDefinition sourceDocumentDefinition;
    private DocumentDefinition destinationDocumentDefinition;

    public DocumentConverter( DocumentDefinition sourceDocumentDefinition, DocumentDefinition destinationDocumentDefinition ) {
        this.sourceDocumentDefinition = sourceDocumentDefinition;
        this.destinationDocumentDefinition = destinationDocumentDefinition;
    }

    /**
     * Converts the documents, using Streams
     * @param is the input stream with the document definition that matches the first argument in the constructor.
     * @param os the output stream where the document will be output using the second document definition
     * @throws IOException
     */
    public void convert(InputStream is, OutputStream os) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader( is ));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        try{
            convert( reader, writer);
        }
        finally {
            writer.close();
            reader.close();
        }
    }

    /**
     * Converts the documents, using a Reader and a Writer.
     * @param reader the reader with the document definition that matches the first argument in the constructor.
     * @param writer the writer where the document will be output using the second document definition
     * @throws IOException
     */
    public void convert(Reader reader, Writer writer) throws IOException {
        CalimochoDocument calimochoDocument;

        try {
            calimochoDocument = sourceDocumentDefinition.readDocument( reader );
        } catch ( IllegalRowException e ) {
            throw new IOException( "Problem while reading", e );
        }

        try {
            destinationDocumentDefinition.writeDocument( writer, calimochoDocument );
        } catch ( IllegalRowException e ) {
            throw new IOException( "Problem while writing", e );
        }
    }
}
