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
package org.hupo.psi.calimocho.xml.model;

import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.model.*;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interaction;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PsimiXmlDocumentDefinition extends AbstractDocumentDefinition {

    public PsimiXmlDocumentDefinition() {
        setName("PSI-XML 2.5");
    }

    public CalimochoDocument readDocument(Reader reader) throws IOException, IllegalRowException {
        PsimiXmlReader xmlReader = new PsimiXmlReader();

        EntrySet entrySet = null;
        try {
            entrySet = xmlReader.read(reader);
        } catch (PsimiXmlReaderException e) {
            throw new IOException("Problem reading PSI-MI XML", e);
        }

        return readEntrySet(entrySet);
    }

    public CalimochoDocument readEntrySet(EntrySet entrySet) throws IllegalRowException {
        CalimochoDocument calimochoDocument = new DefaultCalimochoDocument();

        for (Entry entry : entrySet.getEntries()) {
            for (Interaction interaction : entry.getInteractions()) {
                Row row = createRow(interaction);
                calimochoDocument.getRows().add(row);
            }
        }

        return calimochoDocument;
    }

    protected Row createRow(Interaction interaction) {
        Row row = new DefaultRow();

//        Field interactionAcField = new DefaultField();
//        interactionAcField.set(CalimochoKeys.);
//        row.addField()

        return row;
    }

    public void writeDocument(Writer writer, CalimochoDocument calimochoDocument) throws IOException, IllegalRowException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
