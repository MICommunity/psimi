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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class Mitab26DocumentDefinition implements DocumentDefinition {

    public static String KEY_EXPANSION = "expansion";
    public static String KEY_BIOROLE_A = "bioRoleA";
    public static String KEY_BIOROLE_B = "bioRoleB";
    public static String KEY_EXPROLE_A = "expRoleA";
    public static String KEY_EXPROLE_B = "expRoleB";
    public static String KEY_INTERACTOR_TYPE_A = "typeA";
    public static String KEY_INTERACTOR_TYPE_B = "typeB";
    public static String KEY_XREFS_A = "xrefsA";
    public static String KEY_XREFS_B = "xrefsB";
    public static String KEY_XREFS_I = "xrefsI";
    public static String KEY_ANNOTATIONS_A = "annotationsA";
    public static String KEY_ANNOTATIONS_B = "annotationsB";
    public static String KEY_ANNOTATIONS_I = "annotationsI";
    public static String KEY_HOST_ORGANISM = "hostOrganism";
    public static String KEY_PARAMETERS_A = "parametersA";
    public static String KEY_PARAMETERS_B = "parametersB";
    public static String KEY_PARAMETERS_I = "parametersI";
    public static String KEY_CREATION_DATE = "creationDate";
    public static String KEY_UPDATE_DATE = "updateDate";
    public static String KEY_CHECKSUM_A = "checksumA";
    public static String KEY_CHECKSUM_B = "checksumB";
    public static String KEY_CHECKSUM_I = "checksumI";
    public static String KEY_NEGATIVE = "negative";

    private ColumnMetadata[] columns;

    public Mitab26DocumentDefinition() {
        Mitab25DocumentDefinition mitab25DocumentDefinition = new Mitab25DocumentDefinition();
        ColumnMetadata[] mitab25Columns = mitab25DocumentDefinition.getColumns();

        ColumnMetadata negativeColumn = new ColumnMetadata(KEY_NEGATIVE, "Negative");
        negativeColumn.setDefaultValue("false");

        String dateNow = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        ColumnMetadata creationDateColumn = new ColumnMetadata(KEY_CREATION_DATE, "Creation Date");
        creationDateColumn.setDefaultValue(dateNow);

        ColumnMetadata updateDateColumn = new ColumnMetadata(KEY_UPDATE_DATE, "Update Date");
        updateDateColumn.setDefaultValue(dateNow);

        ColumnMetadata[] additionalCols = new ColumnMetadata[] {
            new ColumnMetadata(KEY_EXPANSION, "Expansion"),
            new ColumnMetadata(KEY_BIOROLE_A, "Biological Role A"),
            new ColumnMetadata(KEY_BIOROLE_B, "Biological Role B"),
            new ColumnMetadata(KEY_EXPROLE_A, "Experimental Role A"),
            new ColumnMetadata(KEY_EXPROLE_B, "Experimental Role B"),
            new ColumnMetadata(KEY_INTERACTOR_TYPE_A, "Interactor type A"),
            new ColumnMetadata(KEY_INTERACTOR_TYPE_B, "Interactor type B"),
            new ColumnMetadata(KEY_XREFS_A, "Xrefs A"),
            new ColumnMetadata(KEY_XREFS_B, "Xrefs B"),
            new ColumnMetadata(KEY_XREFS_I, "Xrefs Interaction"),
            new ColumnMetadata(KEY_ANNOTATIONS_A, "Annotations A"),
            new ColumnMetadata(KEY_ANNOTATIONS_B, "Annotations B"),
            new ColumnMetadata(KEY_ANNOTATIONS_I, "Annotations Interaction"),
            new ColumnMetadata(KEY_HOST_ORGANISM, "Host Organism"),
            new ColumnMetadata(KEY_PARAMETERS_A, "Parameters A"),
            new ColumnMetadata(KEY_PARAMETERS_B, "Parameters B"),
            new ColumnMetadata(KEY_PARAMETERS_I, "Parameters Interaction"),
                creationDateColumn,
                updateDateColumn,
            new ColumnMetadata(KEY_CHECKSUM_A, "Checksum A"),
            new ColumnMetadata(KEY_CHECKSUM_B, "Checksum B"),
            new ColumnMetadata(KEY_CHECKSUM_I, "Checksum Interaction"),
            negativeColumn,
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