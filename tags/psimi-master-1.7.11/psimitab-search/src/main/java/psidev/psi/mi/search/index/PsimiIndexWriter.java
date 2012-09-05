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
package psidev.psi.mi.search.index;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import psidev.psi.mi.search.util.DocumentBuilder;
import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.Row;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.*;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PsimiIndexWriter {

    private static final Log log = LogFactory.getLog( PsimiIndexWriter.class );
    
    /**
    * Determines how often segment indices are merged by addDocument().
    * With smaller values, less RAM is used while indexing, and searches
    * on unoptimized indices are faster, but indexing speed is slower.
    * With larger values, more RAM is used during indexing, and while
    * searches on unoptimized indices are slower, indexing is faster.
    * Thus larger values (> 10) are best for batch index creation, and
    * smaller values (< 10) for indices that are interactively maintained.
    * This must never be less than 2. The default value is 10.
    */
    public static final int MERGE_FACTOR = 30;

    private DocumentBuilder documentBuilder;

    public PsimiIndexWriter(DocumentBuilder documentBuilder) {
        this.documentBuilder = documentBuilder;
    }

    public void index(String indexDir, InputStream is, boolean createIndex, boolean hasHeaderLine) throws IOException, ConverterException, MitabLineException {
        Directory directory = FSDirectory.getDirectory(indexDir);
        index(directory, is, createIndex, hasHeaderLine);
    }

    public void index(File indexDir, InputStream is, boolean createIndex, boolean hasHeaderLine) throws IOException, ConverterException, MitabLineException {
        Directory directory = FSDirectory.getDirectory(indexDir);
        index(directory, is, createIndex, hasHeaderLine);
    }

    public void index(Directory directory, InputStream is, boolean createIndex, boolean hasHeaderLine) throws IOException, ConverterException, MitabLineException {
        IndexWriter indexWriter = new IndexWriter(directory, new StandardAnalyzer(), createIndex);
        indexWriter.setMergeFactor(MERGE_FACTOR);
        indexWriter.setMaxMergeDocs(Integer.MAX_VALUE);
        index(indexWriter, is, hasHeaderLine);
        indexWriter.optimize();
        indexWriter.close();
    }

    public  void index(IndexWriter indexModifier, InputStream is, boolean hasHeaderLine) throws IOException, ConverterException, MitabLineException {
        if (log.isInfoEnabled()) log.info("Starting index creation: "+indexModifier);
        long startTime = System.currentTimeMillis();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if (hasHeaderLine)
        {
            reader.readLine();
        }

        String line;
        while ((line = reader.readLine()) != null)
        {
            if (log.isTraceEnabled()) log.trace("\tIndexing: "+line);

            addLineToIndex(indexModifier, line);
        }

        // close reader
        reader.close();

        if (log.isInfoEnabled())
        {
            long elapsedTime = (System.currentTimeMillis()-startTime)/1000;
            log.info("Index created. Time: "+elapsedTime+"s");
        }
    }

    /**
     * @deprecated Use addBinaryInteractionToIndex(IndexWriter, BinaryInteraction) method instead
     */
    @Deprecated
    public void addLineToIndex(IndexWriter indexWriter, String line) throws IOException, MitabLineException {
        addBinaryInteractionToIndex(indexWriter, documentBuilder.getDocumentDefinition().interactionFromString(line));
    }

    public void addBinaryInteractionToIndex(IndexWriter indexWriter, BinaryInteraction binaryInteraction) throws IOException {
        indexWriter.addDocument(documentBuilder.createDocument(binaryInteraction));
    }

    public void addBinaryInteractionToIndex(IndexWriter indexWriter, Row row) throws IOException {
        indexWriter.addDocument(documentBuilder.createDocument(row));
    }

    public DocumentBuilder getDocumentBuilder() {
        return documentBuilder;
    }
}
