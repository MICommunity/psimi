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

import org.hupo.psi.mitab.definition.Mitab26DocumentDefinition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class RowFactory {

    private RowFactory() {}

    public static Row createRow(BinaryInteraction binaryInteraction) {
        List<Field> fields = new ArrayList<Field>();

        addInteractorFields(fields, binaryInteraction.getInteractorA());
        addInteractorFields(fields, binaryInteraction.getInteractorB());

        fields.addAll(binaryInteraction.getDetectionMethods());
        fields.addAll(binaryInteraction.getAuthors());
        fields.addAll(binaryInteraction.getPublications());
        fields.add(binaryInteraction.getInteractionType());
        fields.addAll(binaryInteraction.getSources());
        fields.addAll(binaryInteraction.getIdentifiers());
        fields.addAll(binaryInteraction.getConfidences());
        fields.add(binaryInteraction.getExpansionMethod());
        fields.addAll(binaryInteraction.getXrefs());
        fields.addAll(binaryInteraction.getAnnotations());
        fields.addAll(binaryInteraction.getHostOrganisms());
        fields.addAll(binaryInteraction.getParameters());

        if (binaryInteraction.getCreationDate() != null) {
            fields.add(dateField(Mitab26DocumentDefinition.KEY_CREATION_DATE, binaryInteraction.getCreationDate()));
        }

        if (binaryInteraction.getUpdateDate() != null) {
            fields.add(dateField(Mitab26DocumentDefinition.KEY_UPDATE_DATE, binaryInteraction.getUpdateDate()));
        }

        fields.add(binaryInteraction.getChecksum());
        fields.add(booleanField(Mitab26DocumentDefinition.KEY_NEGATIVE, binaryInteraction.isNegative()));

        return new Row(fields);
    }

    private static void addInteractorFields(List<Field> fields, Interactor interactor) {
        fields.addAll(interactor.getIdentifiers());
        fields.addAll(interactor.getAlternativeIdentifiers());
        fields.addAll(interactor.getAliases());
        fields.add(interactor.getTaxid());
        fields.addAll(interactor.getBiologicalRoles());
        fields.addAll(interactor.getExperimentalRoles());
        fields.add(interactor.getInteractorType());
        fields.addAll(interactor.getXrefs());
        fields.addAll(interactor.getAnnotations());
        fields.addAll(interactor.getParameters());
        fields.add(interactor.getChecksum());
    }

    private static Field dateField(String columnKey, Date date) {
        String value = new SimpleDateFormat("yyyy/MM/dd").format(date);
        Field field = new Field(columnKey);
        field.setValue(value);
        return field;
    }

    private static Field booleanField(String columnKey, boolean bool) {
        Field field = new Field(columnKey);
        field.setValue(String.valueOf(bool));
        return field;
    }
}
