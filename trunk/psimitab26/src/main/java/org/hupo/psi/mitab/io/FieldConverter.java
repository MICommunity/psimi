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
package org.hupo.psi.mitab.io;

import org.hupo.psi.mitab.definition.DocumentDefinition;
import org.hupo.psi.mitab.model.ColumnMetadata;
import org.hupo.psi.mitab.model.Field;
import org.hupo.psi.tab.util.MitabEscapeUtils;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class FieldConverter {

    private DocumentDefinition documentDefinition;

    public FieldConverter(DocumentDefinition documentDefinition) {
        this.documentDefinition = documentDefinition;
    }

    public String fieldToString(Field field) {
        StringBuilder sb = new StringBuilder();

        ColumnMetadata columnMetadata = findColumn(documentDefinition, field.getColumnKey());

        String type = field.getType();
        String value = field.getValue();
        String text = field.getText();

        if (type != null && !columnMetadata.isOnlyValues()) {
            sb.append(MitabEscapeUtils.escapeFieldElement(type)).append(":");
        }

        sb.append( MitabEscapeUtils.escapeFieldElement( value ));

        if (text != null && !columnMetadata.isOnlyValues()) {
            sb.append("(");
            sb.append(MitabEscapeUtils.escapeFieldElement(text));
            sb.append(")");
        }

        return sb.toString();
    }

    private ColumnMetadata findColumn(DocumentDefinition documentDefinition, String columnKey) {
        for (ColumnMetadata column : documentDefinition.getColumns()) {
            if (columnKey.equals(column.getKey())) {
                return column;
            }
        }

        return null;
    }
}
