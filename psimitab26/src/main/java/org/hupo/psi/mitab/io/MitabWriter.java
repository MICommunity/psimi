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
import org.hupo.psi.mitab.model.Row;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabWriter {

    private static final String NEW_LINE = System.getProperty("line.separator");

    private DocumentDefinition documentDefinition;

    public MitabWriter(DocumentDefinition documentDefinition) {
        this.documentDefinition = documentDefinition;
    }

    public void write(Writer writer, Collection<Row> rows) throws IOException {
        for (Row row : rows) {
            write(writer, row);
            writer.write(NEW_LINE);
        }
    }

    public void write(Writer writer, Row row) throws IOException {
        ColumnMetadata[] columns = documentDefinition.getColumns();

        FieldConverter fieldConverter = new FieldConverter(documentDefinition);

        for (int i=0; i<columns.length; i++) {
            ColumnMetadata columnMetadata = columns[i];

            writer.write(documentDefinition.getColumnDelimiter());

            Collection<Field> fields = row.getFieldsByColumnKey(columnMetadata.getKey());

            if (fields.isEmpty()) {
                if (columnMetadata.getDefaultValue() != null) {
                    writer.write(columnMetadata.getDefaultValue());
                } else {
                    writer.write(documentDefinition.getEmptyColumnValue());
                }
            } else {
                Iterator<Field> fieldIterator = fields.iterator();

                while (fieldIterator.hasNext()) {
                    Field field = fieldIterator.next();

                    if (field.getType() == null && columnMetadata.getWriteDefaultType() != null) {
                        field.setType(columnMetadata.getWriteDefaultType());
                    }

                    writer.write(fieldConverter.fieldToString(field));

                    if (fieldIterator.hasNext()) {
                        writer.write(documentDefinition.getFieldSeparator());
                    }
                }
            }

            writer.write(documentDefinition.getColumnDelimiter());

            if (i != columns.length-1) {
                writer.write(documentDefinition.getColumnSeparator());
            }
        }
    }
}
