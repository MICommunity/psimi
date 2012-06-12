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

import psidev.psi.mi.tab.PsiMitabException;
import psidev.psi.mi.tab.RuntimePsiMitabException;
import psidev.psi.mi.tab.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public abstract class AbstractInteractionRowConverter<T extends BinaryInteraction> implements InteractionRowConverter<T> {

    protected abstract T newBinaryInteraction(Interactor interactorA, Interactor interactorB);

    protected abstract Interactor newInteractor();

    public T createBinaryInteraction(Row row) throws RuntimePsiMitabException {
        if (row.getColumnCount() < 15) {
            throw new IllegalArgumentException("At least 15 columns were expected in row: "+row);
        }

        T binaryInteraction = null;
        try {
            binaryInteraction = newBinaryInteraction(createInteractorA(row), createInteractorB(row));
            populateBinaryInteraction(binaryInteraction, row);
        } catch ( Exception e ) {
            throw new RuntimePsiMitabException( "Failed to create a BinaryInteraction from line: " + row, e );
        }

        return binaryInteraction;
    }

    public Row createRow( T interaction ) {
        final List<Column> columns = new LinkedList<Column>();

        final Interactor interactorA = interaction.getInteractorA();
        final Interactor interactorB = interaction.getInteractorB();

        columns.add( ParseUtils.createColumnFromCrossReferences( interactorA.getIdentifiers() ) );
        columns.add( ParseUtils.createColumnFromCrossReferences( interactorB.getIdentifiers() ) );
        columns.add( ParseUtils.createColumnFromCrossReferences( interactorA.getAlternativeIdentifiers() ) );
        columns.add( ParseUtils.createColumnFromCrossReferences( interactorB.getAlternativeIdentifiers() ) );
        columns.add( ParseUtils.createColumnFromAliases( interactorA.getAliases() ) );
        columns.add( ParseUtils.createColumnFromAliases( interactorB.getAliases() ) );

        columns.add( ParseUtils.createColumnFromDetectionMethods( interaction.getDetectionMethods() ) );
        columns.add( ParseUtils.createColumnFromAuthors( interaction.getAuthors() ) );
        columns.add( ParseUtils.createColumnFromCrossReferences( interaction.getPublications() ) );

        columns.add( ParseUtils.createColumnFromOrganism( interactorA.getOrganism() ) );
        columns.add( ParseUtils.createColumnFromOrganism( interactorB.getOrganism() ) );

        columns.add( ParseUtils.createColumnFromInteractionTypes( interaction.getInteractionTypes() ) );
        columns.add( ParseUtils.createColumnFromCrossReferences( interaction.getSourceDatabases() ) );
        columns.add( ParseUtils.createColumnFromCrossReferences( interaction.getInteractionAcs() ) );
        columns.add( ParseUtils.createColumnFromConfidences( interaction.getConfidenceValues() ) );

        return new Row( columns );
    }

    protected Interactor createInteractorA(Row row) {
        Column idACol = row.getColumnByIndex(0);
        Column altidACol = row.getColumnByIndex(2);
        Column aliasACol = row.getColumnByIndex(4);
        Column taxidACol = row.getColumnByIndex(9);

        Interactor interactorA = newInteractor();
        interactorA.setIdentifiers(ParseUtils.createCrossReferences(idACol));
        interactorA.setAlternativeIdentifiers(ParseUtils.createCrossReferences(altidACol));
        interactorA.setAliases(ParseUtils.createAliases(aliasACol));
        interactorA.setOrganism(ParseUtils.createOrganism(taxidACol));

        return interactorA;
    }

    protected Interactor createInteractorB(Row row) {
        Column idBCol = row.getColumnByIndex(1);
        Column altidBCol = row.getColumnByIndex(3);
        Column aliasBCol = row.getColumnByIndex(5);
        Column taxidBCol = row.getColumnByIndex(10);

        Interactor interactorB = newInteractor();
        interactorB.setIdentifiers(ParseUtils.createCrossReferences(idBCol));
        interactorB.setAlternativeIdentifiers(ParseUtils.createCrossReferences(altidBCol));
        interactorB.setAliases(ParseUtils.createAliases(aliasBCol));
        interactorB.setOrganism(ParseUtils.createOrganism(taxidBCol));

        return interactorB;
    }

    protected void populateBinaryInteraction(BinaryInteraction binaryInteraction, Row row) {
        Column intDetMethodCol = row.getColumnByIndex(6);
        Column pubAuthCol = row.getColumnByIndex(7);
        Column pubIdCol = row.getColumnByIndex(8);
        Column intTypeCol = row.getColumnByIndex(11);
        Column sourceDbCol = row.getColumnByIndex(12);
        Column intIdCol = row.getColumnByIndex(13);
        Column confCol = row.getColumnByIndex(14);

        binaryInteraction.setDetectionMethods(ParseUtils.createInteractionDetectionMethods(intDetMethodCol));
        binaryInteraction.setAuthors(ParseUtils.createAuthors(pubAuthCol));
        binaryInteraction.setPublications(ParseUtils.createCrossReferences(pubIdCol));
        binaryInteraction.setInteractionTypes(ParseUtils.createInteractionTypes(intTypeCol));
        binaryInteraction.setSourceDatabases(ParseUtils.createCrossReferences(sourceDbCol));
        binaryInteraction.setInteractionAcs(ParseUtils.createCrossReferences(intIdCol));
        binaryInteraction.setConfidenceValues(ParseUtils.createConfidenceValues(confCol));
    }
}