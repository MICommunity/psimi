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

import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public abstract class DocumentDefinition<T extends BinaryInteraction> {

    private List<ColumnDefinition> columnDefinitions;

    public DocumentDefinition() {
        this.columnDefinitions = new LinkedList<ColumnDefinition>();
    }

    protected void addColumnDefinition(ColumnDefinition columnDefinition) {
        columnDefinitions.add(columnDefinition);
    }

    public ColumnDefinition getColumnDefinition(int i) {
        return columnDefinitions.get(i);
    }

    public int getColumnDefinitionCount() {
        return columnDefinitions.size();
    }

    public RowBuilder createRowBuilder() {
        return new RowBuilder(this);
    }

    public int getColumnsCount() {
        return columnDefinitions.size();
    }

    public abstract InteractionRowConverter<T> createInteractionRowConverter();

    public String interactionToString(T binaryInteraction) {
        return createInteractionRowConverter().createRow(binaryInteraction).toString();
    }

    public T interactionFromString(String str) {
        Row row = createRowBuilder().createRow(str);
        return createInteractionRowConverter().createBinaryInteraction(row);
    }

    public ColumnDefinition getColumnDefinitionByShortName(String shortName) {
        for (ColumnDefinition colDef : columnDefinitions) {
            if (shortName.equals(colDef.getShortName())) {
                return colDef;
            }
        }

        return null;
    }
}
