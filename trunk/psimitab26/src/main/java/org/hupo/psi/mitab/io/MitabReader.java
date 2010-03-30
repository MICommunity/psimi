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
import org.hupo.psi.mitab.util.ParseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabReader {

    private DocumentDefinition documentDefinition;

    public MitabReader(DocumentDefinition documentDefinition) {
        this.documentDefinition = documentDefinition;
    }

    public Collection<Row> readRows(InputStream is) throws IOException {
        List<Row> rows = new ArrayList<Row>();

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String str;
        while ((str = in.readLine()) != null) {
            if (!str.trim().startsWith(documentDefinition.getCommentedLineStart())) {
                rows.add(readLine(str));
            }
        }
        in.close();

        return rows;
    }

    public Row readLine(String line) {
        List<Field> fields = new ArrayList<Field>();

        String[] cols = ParseUtils.quoteAwareSplit(line, new String[] { documentDefinition.getColumnSeparator() }, false);

        ColumnMetadata[] columns = documentDefinition.getColumns();

        for (int i=0; i<cols.length && i<columns.length; i++) {
            String col = cols[i];
            ColumnMetadata columnMetadata = columns[i];

            String[] strFields = ParseUtils.columnSplit(col, documentDefinition.getFieldSeparator());

            for (String strField : strFields) {
                Field field = ParseUtils.createField(columnMetadata, strField);

                if (field != null) {
                    fields.add(field);
                }
            }
        }

        return new Row(fields);
    }
}
