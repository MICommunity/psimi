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

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public abstract class AbstractDocumentDefinition<T extends BinaryInteraction> extends DocumentDefinition<T> {

    public static final int ID_INTERACTOR_A = 0;
    public static final int ID_INTERACTOR_B = 1;
    public static final int ALTID_INTERACTOR_A = 2;
    public static final int ALTID_INTERACTOR_B = 3;
    public static final int ALIAS_INTERACTOR_A = 4;
    public static final int ALIAS_INTERACTOR_B = 5;
    public static final int INT_DET_METHOD = 6;
    public static final int PUB_AUTH = 7;
    public static final int PUB_ID = 8;
    public static final int TAXID_A = 9;
    public static final int TAXID_B = 10;
    public static final int INT_TYPE = 11;
    public static final int SOURCE = 12;
    public static final int INTERACTION_ID = 13;
    public static final int CONFIDENCE = 14;

    public AbstractDocumentDefinition() {
        addColumnDefinition(new ColumnDefinition("ID(s) interactor A", "idA", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("ID(s) interactor B", "idB", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Alt. ID(s) interactor A", "altidA", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Alt. ID(s) interactor B", "altidB", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Alias(es) interactor A", "aliasA", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Alias(es) interactor B", "aliasB", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Interaction detection method(s)", "detmethod_exact", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Publication 1st author(s)", "pubauth", new PlainTextFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Publication Identifier(s)", "pubid", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Taxid interactor A", "taxidA", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Taxid interactor B", "taxidB", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Interaction type(s)", "type_exact", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Source database(s)", "source", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Interaction identifier(s)", "interaction_id", new CrossReferenceFieldBuilder()));
        addColumnDefinition(new ColumnDefinition("Confidence value(s)", "confidence", new CrossReferenceFieldBuilder()));
    }

    public abstract InteractionRowConverter<T> createInteractionRowConverter();
}