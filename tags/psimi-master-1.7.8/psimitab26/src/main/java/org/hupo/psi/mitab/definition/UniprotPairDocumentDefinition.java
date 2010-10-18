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
package org.hupo.psi.mitab.definition;

import org.hupo.psi.mitab.model.ColumnMetadata;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class UniprotPairDocumentDefinition implements DocumentDefinition {

    private ColumnMetadata[] columns;

    public UniprotPairDocumentDefinition() {
        this.columns = new ColumnMetadata[] {
            new ColumnMetadata(Mitab25DocumentDefinition.KEY_INTERACTION_ID, "ID(s) interaction"),
            new ColumnMetadata(Mitab25DocumentDefinition.KEY_ID_A, "Uniprotkb A", "uniprotkb", true),
            new ColumnMetadata(Mitab25DocumentDefinition.KEY_ID_B, "Uniprotkb B", "uniprotkb", true)
        };
    }

    public ColumnMetadata[] getColumns() {
        return columns;
    }

    public String getColumnSeparator() {
        return ",";
    }

    public String getFieldSeparator() {
        return "|";
    }

    public String getColumnDelimiter() {
        return "\"";
    }

    public String getEmptyColumnValue() {
        return "-";
    }

    public String getCommentedLineStart() {
        return "#";
    }
}