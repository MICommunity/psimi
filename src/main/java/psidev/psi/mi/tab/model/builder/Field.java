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

import psidev.psi.mi.tab.utils.MitabEscapeUtils;

/**
 * A MITAB Field ( eg. 'type:value(description)' ).
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.2
 */
@Deprecated
public final class Field {

    private static final String COLON = ":";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";

    private String type;
    private String value;
    private String description;


    public Field(String value) {
        this(null, value, null);
    }

    public Field(String type, String value) {
        this(type, value, null);
    }

    public Field(String type, String value, String description) {
        this.type = type;
        this.value = value;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (type != null) {
            sb.append(MitabEscapeUtils.escapeFieldElement(type));
            sb.append(COLON);
        }

        if (value != null) {
            if (type == null && description == null)  {
                sb.append(value);
            } else {
                sb.append(MitabEscapeUtils.escapeFieldElement(value));
            }
        }

        if (description != null) {
            sb.append(OPEN_BRACKET);
            sb.append(MitabEscapeUtils.escapeFieldElement(description));
            sb.append(CLOSE_BRACKET);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field that = ( Field ) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
