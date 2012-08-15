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
package psidev.psi.mi.search.index.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import psidev.psi.mi.search.SearchResult;
import psidev.psi.mi.search.engine.SearchEngine;
import psidev.psi.mi.search.engine.impl.BinaryInteractionSearchEngine;
import psidev.psi.mi.search.index.PsimiIndexWriter;
import psidev.psi.mi.search.util.DefaultDocumentBuilder;
import psidev.psi.mi.search.util.DocumentBuilder;
import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.builder.MitabWriterUtils;
import psidev.psi.mi.tab.model.builder.PsimiTabVersion;
import psidev.psi.mi.tab.utils.AbstractBinaryInteractionHandler;
import psidev.psi.mi.tab.utils.OnlyOneInteractorHandler;

import java.io.IOException;

/**
 * Interactor index writer.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class InteractorIndexWriter extends PsimiIndexWriter{

    private static final Log log = LogFactory.getLog( InteractorIndexWriter.class );

    private AbstractBinaryInteractionHandler binaryInteractionHandler;

    public InteractorIndexWriter() {
        super(new DefaultDocumentBuilder());
        this.binaryInteractionHandler = new OnlyOneInteractorHandler();
    }

    public InteractorIndexWriter(DocumentBuilder documentBuilder, AbstractBinaryInteractionHandler binaryInteractionHandler) {
        super(documentBuilder);
        if( binaryInteractionHandler == null ) {
            throw new IllegalArgumentException( "You must give a non null binary interaction handler" );
        }
        this.binaryInteractionHandler = binaryInteractionHandler;
    }

    protected AbstractBinaryInteractionHandler getBinaryInteractionHandler() {
        return binaryInteractionHandler;
    }

    @Override
    public void addBinaryInteractionToIndex(IndexWriter indexWriter, BinaryInteraction binaryInteraction) throws IOException, MitabLineException {
        ColumnBasedDocumentDefinition docDefinition = getDocumentBuilder().getDocumentDefinition();

        BinaryInteraction copy1 = binaryInteractionHandler.cloneBinaryInteraction(binaryInteraction);
        BinaryInteraction copy2 = binaryInteractionHandler.cloneBinaryInteraction(binaryInteraction);

        SearchEngine searchEngine = createSearchEngine(indexWriter.getDirectory());
        // interactor A
        indexBinaryInteraction(indexWriter,searchEngine, copy1, docDefinition);
        //IndexSearcher sees index as it was when it was opened, if it gets modified
        // the searcher needs to be reopened:
        searchEngine.close();
        searchEngine = createSearchEngine(indexWriter.getDirectory());
        // invert interaction interactors
        invertInteractors(copy2);

        // interactor B
        indexBinaryInteraction(indexWriter,searchEngine, copy2, docDefinition);
        searchEngine.close();
    }

    @Override
    public void addBinaryInteractionToIndex(IndexWriter indexWriter, Row row) throws IOException {
        throw new UnsupportedOperationException( "For performance sake, use addBinaryInteractionToIndex(IndexWriter, BinaryInteraction) instead." );
    }

    private void invertInteractors(BinaryInteraction binaryInteraction) {
        Interactor a = binaryInteraction.getInteractorA();
        Interactor b = binaryInteraction.getInteractorB();
        binaryInteraction.setInteractorA(b);
        binaryInteraction.setInteractorB(a);
    }

    private void indexBinaryInteraction(IndexWriter indexWriter, SearchEngine searchEngine, BinaryInteraction binaryInteraction, ColumnBasedDocumentDefinition docDefinition) throws IOException, MitabLineException {
        final String idAColumnName = docDefinition.getColumnByPosition(0).getKey();

        final String identifier = getMainIdentifier(binaryInteraction.getInteractorA());

        SearchResult<BinaryInteraction> result = searchEngine.search(idAColumnName +":"+identifier.toLowerCase(), null, null);

        final BinaryInteraction interactionToIndex;

        boolean disableCVexpansion = false;

        if (result.getTotalCount() == 1) {
           // merge lines
            BinaryInteraction existingInteraction = result.getData().iterator().next();
            interactionToIndex = mergeBinaryInteractions(existingInteraction, binaryInteraction);

            if (log.isDebugEnabled()) log.debug("Deleting existing document for interactor: "+identifier);

            indexWriter.deleteDocuments(new Term(idAColumnName, identifier.toLowerCase()));
            indexWriter.flush();

            disableCVexpansion = true;

        } else if (result.getTotalCount() > 1) {
            StringBuilder sb = new StringBuilder(1024);

            final String newLine = System.getProperty("line.separator");

            for (BinaryInteraction bi : result.getData()) {
                sb.append(MitabWriterUtils.buildLine(bi, PsimiTabVersion.v2_5));
            }

            throw new IllegalStateException("More than one document existing for identifier A: " + identifier + newLine + sb.toString());
        } else {
            interactionToIndex = binaryInteraction;
        }

        if (log.isDebugEnabled()) log.debug("Adding document for interactor: "+identifier);

        getDocumentBuilder().setDisableExpandInteractorsProperties( disableCVexpansion );
        indexWriter.addDocument(getDocumentBuilder().createDocument(interactionToIndex));
        indexWriter.commit();
    }

    /**
     * Gets the main identifier for the interactor. The default implementation searches for
     * an identifier with database "intact", or the first one if it is not found.
     * @param interactor
     * @return
     */
    protected String getMainIdentifier(Interactor interactor) {

        for (CrossReference xref : interactor.getIdentifiers()) {
            if ("intact".equals(xref.getDatabase())) {
                return xref.getIdentifier();
            }
        }

        return interactor.getIdentifiers().iterator().next().getIdentifier();
    }

    protected BinaryInteraction mergeBinaryInteractions(BinaryInteraction source, BinaryInteraction target) {
        return binaryInteractionHandler.merge(source, target);
    }

    protected SearchEngine createSearchEngine(Directory directory) throws IOException{
        return new BinaryInteractionSearchEngine(directory);
    }
}
