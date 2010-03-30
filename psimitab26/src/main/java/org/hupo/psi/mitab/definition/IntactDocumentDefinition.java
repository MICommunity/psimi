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

import org.apache.commons.lang.ArrayUtils;
import org.hupo.psi.mitab.model.ColumnMetadata;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class IntactDocumentDefinition implements DocumentDefinition {

    public static String KEY_DATASET = "dataset";

    private ColumnMetadata[] columns;

    public IntactDocumentDefinition() {
        Mitab25DocumentDefinition mitab25DocumentDefinition = new Mitab25DocumentDefinition();
        ColumnMetadata[] mitab25Columns = mitab25DocumentDefinition.getColumns();

        ColumnMetadata[] additionalCols = new ColumnMetadata[]{
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_EXPROLE_A, "Experimental Role A"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_EXPROLE_B, "Experimental Role B"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_BIOROLE_A, "Biological Role A"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_BIOROLE_B, "Biological Role B"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_XREFS_A, "Xrefs A"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_XREFS_B, "Xrefs B"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_INTERACTOR_TYPE_A, "Interactor type A"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_INTERACTOR_TYPE_B, "Interactor type B"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_HOST_ORGANISM, "Host Organism"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_EXPANSION, "Expansion"),
                new ColumnMetadata(KEY_DATASET, "Dataset"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_ANNOTATIONS_A, "Annotations A"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_ANNOTATIONS_B, "Annotations B"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_PARAMETERS_A, "Parameters A"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_PARAMETERS_B, "Parameters B"),
                new ColumnMetadata(Mitab26DocumentDefinition.KEY_PARAMETERS_I, "Parameters Interaction")
        };

        columns = (ColumnMetadata[]) ArrayUtils.addAll(mitab25Columns, additionalCols);
    }

    public ColumnMetadata[] getColumns() {
       return columns;
    }

    public String getColumnSeparator() {
        return "\t";
    }

    public String getFieldSeparator() {
        return "|";
    }

    public String getColumnDelimiter() {
        return "";
    }

    public String getEmptyColumnValue() {
        return "-";
    }

    public String getCommentedLineStart() {
        return "#";
    }
}