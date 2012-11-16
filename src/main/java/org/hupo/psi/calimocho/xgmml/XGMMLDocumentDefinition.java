/**
 * Copyright 2011 The European Bioinformatics Institute, and others.
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
package org.hupo.psi.calimocho.xgmml;

import calimocho.internal.xgmml.Graph;
import calimocho.internal.xgmml.ObjectFactory;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.model.AbstractDocumentDefinition;
import org.hupo.psi.calimocho.model.CalimochoDocument;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class XGMMLDocumentDefinition extends AbstractDocumentDefinition {

    private String title;
    private String description;
    private String source;

    private GraphBuilder graphBuilder;
    private NamespacePrefixMapper mapper;
    
    public XGMMLDocumentDefinition() {
        this("Graph", "Created using Calimocho", "not set");
    }

    public XGMMLDocumentDefinition(String title, String description, String source) {
        this.title = title;
        this.description = description;
        this.source = source;
        this.graphBuilder = new GraphBuilder(title, description, source);
        mapper = new NamespacePrefixMapper() {
            @Override
            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean b) {
                if ("http://www.cs.rpi.edu/XGMML".equals(namespaceUri)) {
                    return "";
                } else if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(namespaceUri)) {
                    return "rdf";
                } else if ("http://purl.org/dc/elements/1.1/".equals(namespaceUri)) {
                    return "dc";
                } else if ("http://www.w3.org/1999/xlink".equals(namespaceUri)) {
                    return "xlink";
                }
                return suggestion;
            }
        };
    }

    public CalimochoDocument readDocument(Reader reader) throws IOException, IllegalRowException {
        throw new UnsupportedOperationException();
    }

    public void writeDocument(Writer writer, CalimochoDocument calimochoDocument) throws IOException, IllegalRowException {
        Graph graph = graphBuilder.createGraph(calimochoDocument);

        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller marshaller = jc.createMarshaller();


            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            marshaller.marshal(graph, writer);
        } catch (JAXBException e) {
            throw new IOException("Problem marshalling graph", e);
        }
    }

    public void writeDocument(Writer writer, InputStream mitabStream, ColumnBasedDocumentDefinition docDefinition) throws IOException, IllegalRowException {
        Graph graph = graphBuilder.createGraphFromMitab(mitabStream, docDefinition);

        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller marshaller = jc.createMarshaller();


            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            marshaller.marshal(graph, writer);
        } catch (JAXBException e) {
            throw new IOException("Problem marshalling graph", e);
        }
    }

    public String getName() {
        return "XGMML";
    }

    public String getDefinition() {
        return "XGMML Format";
    }
}
