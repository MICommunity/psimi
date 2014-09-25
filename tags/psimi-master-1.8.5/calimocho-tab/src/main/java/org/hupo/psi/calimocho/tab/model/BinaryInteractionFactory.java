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
package org.hupo.psi.calimocho.tab.model;

import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class BinaryInteractionFactory {

    private BinaryInteractionFactory() {}

    public static BinaryInteraction createBinaryInteraction(Row row) {
        throw new UnsupportedOperationException( "Not implemented in Calimocho" );
        /*
        Interactor interactorA = new Interactor();
        Interactor interactorB = new Interactor();
        BinaryInteraction binaryInteraction = new BinaryInteraction(interactorA, interactorB);

        interactorA.getIdentifiers().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_ID_A));
        interactorA.getAlternativeIdentifiers().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_ALTID_A));
        interactorA.getAliases().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_ALIAS_A));
        interactorA.setTaxid(firstField(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_TAXID_A)));
        interactorA.getBiologicalRoles().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_BIOROLE_A));
        interactorA.getExperimentalRoles().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_EXPROLE_A));
        interactorA.setInteractorType(firstField(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_A)));
        interactorA.getXrefs().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_XREFS_A));
        interactorA.getAnnotations().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_ANNOTATIONS_A));
        interactorA.getParameters().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_PARAMETERS_A));
        interactorA.setChecksum(firstField(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_CHECKSUM_A)));

        interactorB.getIdentifiers().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_ID_B));
        interactorB.getAlternativeIdentifiers().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_ALTID_B));
        interactorB.getAliases().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_ALIAS_B));
        interactorB.setTaxid(firstField(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_TAXID_B)));
        interactorB.getBiologicalRoles().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_BIOROLE_B));
        interactorB.getExperimentalRoles().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_EXPROLE_B));
        interactorB.setInteractorType(firstField(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_B)));
        interactorB.getXrefs().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_XREFS_B));
        interactorB.getAnnotations().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_ANNOTATIONS_B));
        interactorB.getParameters().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_PARAMETERS_B));
        interactorB.setChecksum(firstField(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_CHECKSUM_B)));

        binaryInteraction.getDetectionMethods().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_DETMETHOD));
        binaryInteraction.getAuthors().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_PUBAUTH));
        binaryInteraction.getPublications().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_PUBID));
        binaryInteraction.setInteractionType(firstField(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_INTERACTION_TYPE)));
        binaryInteraction.getSources().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_SOURCE));
        binaryInteraction.getIdentifiers().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_INTERACTION_ID));
        binaryInteraction.getConfidences().addAll(row.getFieldsByColumnKey( Mitab25ColumnKeys.KEY_CONFIDENCE));
        binaryInteraction.setExpansionMethod(firstField(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_EXPANSION)));
        binaryInteraction.getXrefs().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_XREFS_I));
        binaryInteraction.getHostOrganisms().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_HOST_ORGANISM));
        binaryInteraction.getAnnotations().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_ANNOTATIONS_I));
        binaryInteraction.getParameters().addAll(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_PARAMETERS_I));
        binaryInteraction.setCreationDate(date(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_CREATION_DATE)));
        binaryInteraction.setUpdateDate(date(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_UPDATE_DATE)));
        binaryInteraction.setChecksum(firstField(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_CHECKSUM_I)));
        binaryInteraction.setNegative(bool(row.getFieldsByColumnKey( Mitab26ColumnKeys.KEY_NEGATIVE)));

        return binaryInteraction;
        */

    }

    private static Field firstField(Collection<Field> fields) {
        if (fields == null || fields.size() == 0) return null;
        return fields.iterator().next();
    }

    private static Date date(Collection<Field> fields) {
        Field field = firstField(fields);

        if (field != null) {
            Date date;
            try {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(field.get( CalimochoKeys.VALUE ));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Date does not follow format yyyy/MM/dd: "+CalimochoKeys.VALUE, e);
            }
            return date;
        }
        return null;
    }

    private static boolean bool(Collection<Field> fields) {
        Field field = firstField(fields);

        if (field != null) {
            return Boolean.parseBoolean(field.get( CalimochoKeys.VALUE ));
        }

        return false;
    }
}
