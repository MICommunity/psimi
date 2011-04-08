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

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class IntactDocumentDefinition  {

    /*
    public static String KEY_DATASET = "dataset";

    private ColumnMetadata[] columns;

    public IntactDocumentDefinition() {
        ColumnMetadata[] mitab25Columns = mitab25DocumentDefinition.getColumns();

        ColumnMetadata checksumAColumn = new ColumnMetadata( Mitab26ColumnKeys.KEY_CHECKSUM_A, "Checksum A", "irefindex");
        ColumnMetadata checksumBColumn = new ColumnMetadata( Mitab26ColumnKeys.KEY_CHECKSUM_B, "Checksum B", "irefindex");
        ColumnMetadata checksumIColumn = new ColumnMetadata( Mitab26ColumnKeys.KEY_CHECKSUM_I, "Checksum Interaction", "irefindex");

        for (ColumnMetadata mitab25Col : mitab25Columns) {
            if ( Mitab25ColumnKeys.KEY_INTERACTION_ID.equals(mitab25Col.getKey())) {
                mitab25Col.getSynonymColumns().add(checksumIColumn);
            } else if ( Mitab25ColumnKeys.KEY_ALTID_A.equals(mitab25Col.getKey())) {
                mitab25Col.getSynonymColumns().add(checksumAColumn);
            } else if ( Mitab25ColumnKeys.KEY_ALTID_B.equals(mitab25Col.getKey())) {
                mitab25Col.getSynonymColumns().add(checksumBColumn);
            }
        }

        ColumnMetadata datasetColumn = new ColumnMetadata(KEY_DATASET, "Dataset");
        ColumnMetadata annotationsCol = new ColumnMetadata( Mitab26ColumnKeys.KEY_ANNOTATIONS_I, "Annotations Interaction");
        annotationsCol.setReadDefaultType("dataset");
        datasetColumn.getSynonymColumns().add(annotationsCol);

        ColumnMetadata[] additionalCols = new ColumnMetadata[]{
                new ColumnMetadata( Mitab26ColumnKeys.KEY_EXPROLE_A, "Experimental Role A"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_EXPROLE_B, "Experimental Role B"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_BIOROLE_A, "Biological Role A"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_BIOROLE_B, "Biological Role B"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_XREFS_A, "Xrefs A"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_XREFS_B, "Xrefs B"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_A, "Interactor type A"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_B, "Interactor type B"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_HOST_ORGANISM, "Host Organism"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_EXPANSION, "Expansion"),
                datasetColumn,
                new ColumnMetadata( Mitab26ColumnKeys.KEY_ANNOTATIONS_A, "Annotations A"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_ANNOTATIONS_B, "Annotations B"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_PARAMETERS_A, "Parameters A"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_PARAMETERS_B, "Parameters B"),
                new ColumnMetadata( Mitab26ColumnKeys.KEY_PARAMETERS_I, "Parameters Interaction")
        };

        columns = (ColumnMetadata[]) ArrayUtils.addAll(mitab25Columns, additionalCols);
    }

   */
}