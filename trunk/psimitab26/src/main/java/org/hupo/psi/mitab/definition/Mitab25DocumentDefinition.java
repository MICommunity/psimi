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
public class Mitab25DocumentDefinition implements DocumentDefinition {

    public static final String KEY_ID_A = "idA";
    public static final String KEY_ID_B = "idB";
    public static final String KEY_ALTID_A = "altidA";
    public static final String KEY_ALTID_B = "altidB";
    public static final String KEY_ALIAS_A = "aliasA";
    public static final String KEY_ALIAS_B = "aliasB";
    public static final String KEY_DETMETHOD_EXACT = "detmethod_exact";
    public static final String KEY_PUBAUTH = "pubauth";
    public static final String KEY_PUBID = "pubid";
    public static final String KEY_TAXID_A = "taxidA";
    public static final String KEY_TAXID_B = "taxidB";
    public static final String KEY_TYPE_EXACT = "type_exact";
    public static final String KEY_SOURCE = "source";
    public static final String KEY_INTERACTION_ID = "interaction_id";
    public static final String KEY_CONFIDENCE = "confidence";

    public ColumnMetadata[] getColumns() {
        return new ColumnMetadata[] {
            new ColumnMetadata(KEY_ID_A, "ID(s) interactor A"),
            new ColumnMetadata(KEY_ID_B, "ID(s) interactor B"),
            new ColumnMetadata(KEY_ALTID_A, "Alt. ID(s) interactor A"),
            new ColumnMetadata(KEY_ALTID_B, "Alt. ID(s) interactor B"),
            new ColumnMetadata(KEY_ALIAS_A, "Alias(es) interactor A"),
            new ColumnMetadata(KEY_ALIAS_B, "Alias(es) interactor B"),
            new ColumnMetadata(KEY_DETMETHOD_EXACT, "Interaction detection method(s)"),
            new ColumnMetadata(KEY_PUBAUTH, "Publication 1st author(s)"),
            new ColumnMetadata(KEY_PUBID, "Publication Identifier(s)"),
            new ColumnMetadata(KEY_TAXID_A, "Taxid interactor A"),
            new ColumnMetadata(KEY_TAXID_B, "Taxid interactor B"),
            new ColumnMetadata(KEY_TYPE_EXACT, "Interaction type(s)"),
            new ColumnMetadata(KEY_SOURCE, "Source database(s)"),
            new ColumnMetadata(KEY_INTERACTION_ID, "Interaction identifier(s)"),
            new ColumnMetadata(KEY_CONFIDENCE, "Confidence value(s)")
        };
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

    public String getEmptyColumnContent() {
        return "-";
    }

    public String getCommentedLineStart() {
        return "#";
    }
}
