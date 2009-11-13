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

import psidev.psi.mi.tab.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabInteractionRowConverter extends AbstractInteractionRowConverter<BinaryInteraction> {

    protected Interactor newInteractor() {
        return new Interactor();
    }

    protected BinaryInteraction newBinaryInteraction(Interactor interactorA, Interactor interactorB) {
        return new BinaryInteractionImpl(interactorA, interactorB);
    }


    protected Interactor createInteractorA(Row row) {
        Column idACol = row.getColumnByIndex(MitabDocumentDefinition.ID_INTERACTOR_A);
        Column altidACol = row.getColumnByIndex(MitabDocumentDefinition.ALTID_INTERACTOR_A);
        Column aliasACol = row.getColumnByIndex(MitabDocumentDefinition.ALIAS_INTERACTOR_A);
        Column taxidACol = row.getColumnByIndex(MitabDocumentDefinition.TAXID_A);

        Interactor interactorA = new Interactor();
        interactorA.setIdentifiers(createCrossReferences(idACol));
        interactorA.setAlternativeIdentifiers(createCrossReferences(altidACol));
        interactorA.setAliases(createAliases(aliasACol));
        interactorA.setOrganism(createOrganism(taxidACol));

        return interactorA;
    }

    protected Interactor createInteractorB(Row row) {
        Column idBCol = row.getColumnByIndex(MitabDocumentDefinition.ID_INTERACTOR_B);
        Column altidBCol = row.getColumnByIndex(MitabDocumentDefinition.ALTID_INTERACTOR_B);
        Column aliasBCol = row.getColumnByIndex(MitabDocumentDefinition.ALIAS_INTERACTOR_B);
        Column taxidBCol = row.getColumnByIndex(MitabDocumentDefinition.TAXID_B);

        Interactor interactorB = new Interactor();

        if (idBCol != null) interactorB.setIdentifiers(createCrossReferences(idBCol));
        if (altidBCol != null) interactorB.setAlternativeIdentifiers(createCrossReferences(altidBCol));
        if (aliasBCol != null) interactorB.setAliases(createAliases(aliasBCol));
        if (taxidBCol != null) interactorB.setOrganism(createOrganism(taxidBCol));

        return interactorB;
    }

    protected void populateBinaryInteraction(BinaryInteraction binaryInteraction, Row row) {
        Column intDetMethodCol = row.getColumnByIndex(MitabDocumentDefinition.INT_DET_METHOD);
        Column pubAuthCol = row.getColumnByIndex(MitabDocumentDefinition.PUB_AUTH);
        Column pubIdCol = row.getColumnByIndex(MitabDocumentDefinition.PUB_ID);
        Column intTypeCol = row.getColumnByIndex(MitabDocumentDefinition.INT_TYPE);
        Column sourceDbCol = row.getColumnByIndex(MitabDocumentDefinition.SOURCE);
        Column intIdCol = row.getColumnByIndex(MitabDocumentDefinition.INTERACTION_ID);
        Column confCol = row.getColumnByIndex(MitabDocumentDefinition.CONFIDENCE);

        binaryInteraction.setDetectionMethods(createInteractionDetectionMethods(intDetMethodCol));
        binaryInteraction.setAuthors(createAuthors(pubAuthCol));
        binaryInteraction.setPublications(createCrossReferences(pubIdCol));
        binaryInteraction.setInteractionTypes(createInteractionTypes(intTypeCol));
        binaryInteraction.setSourceDatabases(createCrossReferences(sourceDbCol));
        binaryInteraction.setInteractionAcs(createCrossReferences(intIdCol));
        binaryInteraction.setConfidenceValues(createConfidenceValues(confCol));
    }

    protected List<CrossReference> createCrossReferences(Column column) {
        List<CrossReference> xrefs = new ArrayList<CrossReference>();

        for (Field field : column.getFields()) {
            if (field == null) throw new IllegalArgumentException("Column contains null fields: "+column);
            
            try {
                xrefs.add(createCrossReference(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating cross reference from field: " + field, e);
            }
        }

        return xrefs;
    }

    protected List<InteractionDetectionMethod> createInteractionDetectionMethods(Column column) {
        List<InteractionDetectionMethod> xrefs = new ArrayList<InteractionDetectionMethod>();

        for (Field field : column.getFields()) {
            try {
                xrefs.add(createInteractionDetectionMethod(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating interaction detection method from field: " + field);
            }
        }

        return xrefs;
    }

    protected List<InteractionType> createInteractionTypes(Column column) {
        List<InteractionType> xrefs = new ArrayList<InteractionType>();

        for (Field field : column.getFields()) {
            try {
                xrefs.add(createInteractionType(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating interaction type from field: " + field);
            }
        }

        return xrefs;
    }

    protected CrossReference createCrossReference(Field field) {
        CrossReference xref = new CrossReferenceImpl();
        populateCrossReference(field, xref);
        return xref;
    }

    protected InteractionDetectionMethod createInteractionDetectionMethod(Field field) {
        InteractionDetectionMethod xref = new InteractionDetectionMethodImpl();
        populateCrossReference(field, xref);
        return xref;
    }

    protected InteractionType createInteractionType(Field field) {
        InteractionType xref = new InteractionTypeImpl();
        populateCrossReference(field, xref);
        return xref;
    }

    private void populateCrossReference(Field field, CrossReference xref) {
        xref.setDatabase(field.getType());
        xref.setIdentifier(field.getValue());
        xref.setText(field.getDescription());
    }

    protected List<Alias> createAliases(Column column) {
        List<Alias> aliases = new ArrayList<Alias>();

        for (Field field : column.getFields()) {
            aliases.add(createAlias(field));
        }

        return aliases;
    }

    protected Alias createAlias(Field field) {
        Alias alias = new AliasImpl();
        alias.setDbSource(field.getType());
        alias.setName(field.getValue());
        alias.setAliasType(field.getDescription());

        return alias;
    }

    protected List<Author> createAuthors(Column column) {
        List<Author> authors = new ArrayList<Author>();

        for (Field field : column.getFields()) {
            authors.add(createAuthor(field));
        }

        return authors;
    }

    protected Author createAuthor(Field field) {
        return new AuthorImpl(field.getValue());
    }

    protected Organism createOrganism(Column column) {
        return new OrganismImpl(createCrossReferences(column));
    }

    protected List<Confidence> createConfidenceValues(Column column) {
        List<Confidence> confidences = new ArrayList<Confidence>();

        for (Field field : column.getFields()) {
            try {
                confidences.add(createConfidence(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating confidence from field: " + field);
            }
        }

        return confidences;
    }

    protected Confidence createConfidence(Field field) {
        return new ConfidenceImpl(field.getType(), field.getValue(), field.getDescription());
    }
}
