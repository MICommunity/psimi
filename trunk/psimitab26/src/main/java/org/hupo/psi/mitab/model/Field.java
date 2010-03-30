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
package org.hupo.psi.mitab.model;

import org.hupo.psi.mitab.util.MitabEscapeUtils;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class Field {

    private ColumnMetadata columnMetadata;
    private String type;
    private String value;
    private String text;

    public Field(ColumnMetadata columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

    public Field(ColumnMetadata columnMetadata, String type, String value, String text) {
        this.columnMetadata = columnMetadata;
        this.type = type;
        this.value = value;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    public void setColumnMetadata(ColumnMetadata columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (type != null) {
            sb.append(MitabEscapeUtils.escapeFieldElement(type)+":");
        }

        sb.append(MitabEscapeUtils.escapeFieldElement(value));

        if (text != null) {
            sb.append("(");
            sb.append(MitabEscapeUtils.escapeFieldElement(text));
            sb.append(")");
        }

        return sb.toString();
    }
}
