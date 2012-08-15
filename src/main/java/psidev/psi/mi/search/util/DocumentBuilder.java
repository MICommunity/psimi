/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
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
 *  limitations under the License.
 */
package psidev.psi.mi.search.util;

import org.apache.lucene.document.Document;
import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;
import psidev.psi.mi.tab.model.builder.DocumentDefinition;
import psidev.psi.mi.tab.model.builder.Row;

/**
 * TODO comment that class header
 *
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id$
 * @since TODO specify the maven artifact version
 */
public interface DocumentBuilder<T> {

    /**
     * @deprecated Use createDocument() methods instead
     */
    @Deprecated
    Document createDocumentFromPsimiTabLine(String psiMiTabLine) throws MitabLineException;

    T createData(Document doc);

    DocumentDefinition getDocumentDefinition();

    Document createDocument(T binaryInteraction);

    Document createDocument(Row row);

    void setDisableExpandInteractorsProperties( boolean disable );

    boolean hasDisableExpandInteractorsProperties();
}
