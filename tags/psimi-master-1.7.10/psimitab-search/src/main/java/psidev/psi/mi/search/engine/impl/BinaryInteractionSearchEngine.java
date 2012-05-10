/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
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
 *  limitations under the License.
 */
package psidev.psi.mi.search.engine.impl;

import org.apache.lucene.store.Directory;
import psidev.psi.mi.search.util.DefaultDocumentBuilder;
import psidev.psi.mi.search.util.DocumentBuilder;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabDocumentDefinition;

import java.io.File;
import java.io.IOException;

/**
 * A Search Engine based on lucene
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class BinaryInteractionSearchEngine extends AbstractSearchEngine<BinaryInteraction>
{

    public BinaryInteractionSearchEngine(Directory indexDirectory) throws IOException {
        super(indexDirectory);
    }

    public BinaryInteractionSearchEngine(String indexDirectory) throws IOException {
        super(indexDirectory);
    }

    public BinaryInteractionSearchEngine(File indexDirectory) throws IOException {
        super(indexDirectory);
    }

    protected DocumentBuilder createDocumentBuilder() {
        return new DefaultDocumentBuilder();
    }

    public String[] getSearchFields() {
        final MitabDocumentDefinition documentDefinition = new MitabDocumentDefinition();
        return new String[]{"identifier",
                            documentDefinition.getColumnDefinition(MitabDocumentDefinition.PUB_ID).getShortName(), // pub id
                            documentDefinition.getColumnDefinition(MitabDocumentDefinition.PUB_AUTH).getShortName(), // pub auth
                            "species",
                            "detmethod", // det method
                            "type", // int type
                            documentDefinition.getColumnDefinition(MitabDocumentDefinition.INTERACTION_ID).getShortName()}; // int ac
    }
}