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

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@Deprecated
public class ColumnDefinition {

    private String columnName;
    private String shortName;
    private FieldBuilder builder;

    public ColumnDefinition(String columnName, String shortName, FieldBuilder builder) {
        if (columnName == null) {
            throw new NullPointerException("You must give a non null columnName");
        }
        if (shortName == null) {
            throw new NullPointerException("You must give a non null shortName");
        }
        if (builder == null) {
            throw new NullPointerException("You must give a non null builder");
        }

        this.columnName = columnName;
        this.shortName = shortName;
        this.builder = builder;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getShortName() {
        return shortName;
    }

    public FieldBuilder getBuilder() {
        return builder;
    }

    public String getSortableColumnName() {
        return shortName+"_s";
    }
}
