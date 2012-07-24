/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
package psidev.psi.mi.tab.model.builder;

import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@Deprecated
public class RowBuilder<T extends BinaryInteraction> {

    private static final Log log = LogFactory.getLog( RowBuilder.class );

    private DocumentDefinition documentDefinition;

    public RowBuilder(DocumentDefinition documentDefinition) {
        this.documentDefinition = documentDefinition;
    }

    public Row createRow(String strRow) {
        List<Column> columns = new LinkedList<Column>();

        String[] colStrs = ParseUtils.quoteAwareSplit(strRow, new char[]{'\t'}, false);

        final int columnDefinitionCount = documentDefinition.getColumnDefinitionCount();
        if( columnDefinitionCount > colStrs.length ) {
            if (log.isDebugEnabled()) log.debug( "Parsing row with less columns than those expected by the document definition. Found " + colStrs.length + " and expected: " + columnDefinitionCount );
        }

        // We parse the count of column defined in the document definition. If there were more than available,
        // an exception has been thrown previously.
        for (int i = 0; i < colStrs.length; i++) {
            String strCol = colStrs[i];

            String[] strFields = ParseUtils.quoteAwareSplit(strCol, new char[]{'|'}, false);

            if (i >= documentDefinition.getColumnsCount()) {
                break;
            }

            final ColumnDefinition columnDefinition = documentDefinition.getColumnDefinition(i);

            FieldBuilder builder = columnDefinition.getBuilder();

            List<Field> fields = new ArrayList<Field>(strFields.length);

            for (String strField : strFields) {
                Field field = builder.createField(strField);
                if( field != null ) {
                    fields.add(field);
                }
            }

            Column col = new Column(fields);
            columns.add(col);
        }

        return new Row(columns);
    }
}