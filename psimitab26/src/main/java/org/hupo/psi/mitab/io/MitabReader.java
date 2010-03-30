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
import org.hupo.psi.mitab.model.*;
import org.hupo.psi.mitab.util.ParseUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabReader {

    private DocumentDefinition documentDefinition;
    private boolean ignoreFirstLine;

    public MitabReader(DocumentDefinition documentDefinition) {
        this.documentDefinition = documentDefinition;
    }

    public MitabReader(DocumentDefinition documentDefinition, boolean ignoreFirstLine) {
        this(documentDefinition);
        this.ignoreFirstLine = ignoreFirstLine;
    }

    public Collection<Row> readRows(InputStream is) throws IOException {
        return readRows(new InputStreamReader(is));
    }

    public Collection<Row> readRows(Reader reader) throws IOException {
        List<Row> rows = new ArrayList<Row>();

        BufferedReader in = new BufferedReader(reader);
        String str;
        int i=0;

        while ((str = in.readLine()) != null) {
            if (i == 0 && ignoreFirstLine) {
                i++;
                continue;
            }

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

        for (int i=0; i<columns.length; i++) {
            ColumnMetadata columnMetadata = columns[i];

            String col = null;
            try {
                col = cols[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("Column out of bounds: "+columnMetadata.getKey());
            }

            //System.out.println("COL: "+columnMetadata.getKey()+" "+col);

            // strip column delimiters
            String colDelimiter = documentDefinition.getColumnDelimiter();
            if (colDelimiter != null && colDelimiter.length() > 0) {
                if (col.startsWith(colDelimiter) && col.endsWith(colDelimiter)) {
                    String[] colTokens = col.split(colDelimiter);
                    col = colTokens[1];
                }
            }

            String[] strFields = ParseUtils.columnSplit(col, documentDefinition.getFieldSeparator());

            for (String strField : strFields) {
                Field[] fieldArray = ParseUtils.createFields(columnMetadata, strField);

                if (fieldArray != null) {
                    fields.addAll(Arrays.asList(fieldArray));
                }
            }
        }


        return new Row(fields);
    }

    public Collection<BinaryInteraction> readBinaryInteractions(InputStream is) throws IOException {
        return readBinaryInteractions(new InputStreamReader(is));
    }

    public Collection<BinaryInteraction> readBinaryInteractions(Reader reader) throws IOException {
        Collection<Row> rows = readRows(reader);

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(rows.size());

        for (Row row : rows) {
            binaryInteractions.add(BinaryInteractionFactory.createBinaryInteraction(row));
        }

        return binaryInteractions;
    }

    public BinaryInteraction readBinaryInteractionLine(String line) throws Exception {
        Row row = readLine(line);
        return BinaryInteractionFactory.createBinaryInteraction(row);
    }

    public boolean isIgnoreFirstLine() {
        return ignoreFirstLine;
    }

    public void setIgnoreFirstLine(boolean ignoreFirstLine) {
        this.ignoreFirstLine = ignoreFirstLine;
    }
}
