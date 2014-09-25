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
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.RowReader;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import psidev.psi.mi.tab.PsimiTabException;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;

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

    T createData(Document doc) throws PsimiTabException;

    ColumnBasedDocumentDefinition getDocumentDefinition();

    Document createDocument(T binaryInteraction) throws MitabLineException;

    Document createDocument(Row row) throws IllegalFieldException;

    void setDisableExpandInteractorsProperties( boolean disable );

    boolean hasDisableExpandInteractorsProperties();

    public RowReader getRowReader();

    public PsimiTabReader getMitabReader();
}
