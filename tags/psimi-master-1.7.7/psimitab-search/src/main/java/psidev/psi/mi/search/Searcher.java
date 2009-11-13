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
package psidev.psi.mi.search;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import psidev.psi.mi.search.engine.SearchEngine;
import psidev.psi.mi.search.engine.SearchEngineException;
import psidev.psi.mi.search.engine.impl.BinaryInteractionSearchEngine;
import psidev.psi.mi.search.index.impl.BinaryInteractionIndexWriter;
import psidev.psi.mi.search.index.PsimiIndexWriter;
import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class with the most common methods to search
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 *
 */
public class Searcher
{
    private Searcher(){}

    public static Directory buildIndex(File indexDirectory, File psimiTabData, boolean createIndex, boolean hasHeader)
            throws IOException, ConverterException, MitabLineException {
        return buildIndex(FSDirectory.getDirectory(indexDirectory), new FileInputStream(psimiTabData), createIndex, hasHeader);
    }
    
    public static Directory buildIndex(File indexDirectory, File psimiTabData, boolean createIndex, boolean hasHeader, PsimiIndexWriter indexWriter)
    		throws IOException, ConverterException, MitabLineException {
    	return buildIndex(FSDirectory.getDirectory(indexDirectory), new FileInputStream(psimiTabData), createIndex, hasHeader, indexWriter);
    }

    public static Directory buildIndex(String indexDirectory, String psimiTabData, boolean createIndex, boolean hasHeader)
            throws IOException, ConverterException, MitabLineException {
        return buildIndex(FSDirectory.getDirectory(indexDirectory), new FileInputStream(new File(psimiTabData)), createIndex, hasHeader);
    }

    public static Directory buildIndex(String indexDirectory, InputStream psimiTabData, boolean createIndex, boolean hasHeader)
            throws IOException, ConverterException, MitabLineException {
        return buildIndex(FSDirectory.getDirectory(indexDirectory), psimiTabData, createIndex, hasHeader);
    }

    public static Directory buildIndex(File indexDirectory, InputStream psimiTabData, boolean createIndex, boolean hasHeader)
            throws IOException, ConverterException, MitabLineException {
        return buildIndex(FSDirectory.getDirectory(indexDirectory), psimiTabData, createIndex, hasHeader);
    }

    public static Directory buildIndexInMemory(InputStream psimiTabData, boolean createIndex, boolean hasHeader)
            throws IOException, ConverterException, MitabLineException {
        return buildIndex(new RAMDirectory(), psimiTabData, createIndex, hasHeader);
    }
    
    public static Directory buildIndexInMemory(InputStream psimiTabData, boolean createIndex, boolean hasHeader, PsimiIndexWriter indexWriter)
    		throws IOException, ConverterException, MitabLineException {
    	return buildIndex(new RAMDirectory(), psimiTabData, createIndex, hasHeader, indexWriter);
    }
    public static Directory buildIndex(Directory indexDirectory, InputStream psimiTabData, boolean createIndex, boolean hasHeader) 
    		throws IOException, ConverterException, MitabLineException {

        BinaryInteractionIndexWriter indexWriter = new BinaryInteractionIndexWriter();
        indexWriter.index(indexDirectory, psimiTabData, createIndex, hasHeader);

        return indexDirectory;
    }
    
    public static Directory buildIndex(Directory indexDirectory, InputStream psimiTabData, boolean createIndex, boolean hasHeader, PsimiIndexWriter indexWriter)
    		throws IOException, ConverterException, MitabLineException {
    	
    	indexWriter.index(indexDirectory, psimiTabData, createIndex, hasHeader);
    	
    	return indexDirectory;
    }

    public static SearchResult<BinaryInteraction> search(String query, Directory indexDirectory)
            throws SearchEngineException
    {
        return search(query, indexDirectory, null, null, null);
    }
    
    public static <T extends BinaryInteraction> SearchResult<T> search(String query, SearchEngine<T> searchEngine)
    	throws SearchEngineException
    {
    	return search(query, null, null, null, searchEngine);
    }

    public static SearchResult<BinaryInteraction> search(String query, Directory indexDirectory, Sort sort)
            throws SearchEngineException
    {
        return search(query, indexDirectory, null, null, sort);
    }
    

    public static SearchResult<BinaryInteraction> search(String query, String indexDirectory, Integer firstResult, Integer maxResults)
            throws SearchEngineException
    {
        try
        {
            return search(query, FSDirectory.getDirectory(indexDirectory), firstResult, maxResults, null);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }
    }

    public static SearchResult<BinaryInteraction> search(String query, String indexDirectory, Integer firstResult, Integer maxResults, Sort sort)
            throws SearchEngineException
    {
        try
        {
            return search(query, FSDirectory.getDirectory(indexDirectory), firstResult, maxResults, sort);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }
    }

    public static SearchResult<BinaryInteraction> search(String query, File indexDirectory, Integer firstResult, Integer maxResults)
            throws SearchEngineException
    {
        try
        {
            return search(query, FSDirectory.getDirectory(indexDirectory), firstResult, maxResults, null);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }
    }

    public static SearchResult<BinaryInteraction> search(Query query, File indexDirectory, Integer firstResult, Integer maxResults)
            throws SearchEngineException
    {
        try
        {
            return search(query, FSDirectory.getDirectory(indexDirectory), firstResult, maxResults, null);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }
    }

    public static SearchResult<BinaryInteraction> search(String query, File indexDirectory, Integer firstResult, Integer maxResults, Sort sort)
            throws SearchEngineException
    {
        try
        {
            return search(query, FSDirectory.getDirectory(indexDirectory), firstResult, maxResults, sort);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }
    }

    public static SearchResult<BinaryInteraction> search(Query query, File indexDirectory, Integer firstResult, Integer maxResults, Sort sort)
            throws SearchEngineException
    {
        try
        {
            return search(query, FSDirectory.getDirectory(indexDirectory), firstResult, maxResults, sort);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }
    }

    public static SearchResult<BinaryInteraction> search(String query, Directory indexDirectory, Integer firstResult, Integer maxResults)
            throws SearchEngineException
    {
        return search(query, indexDirectory, firstResult, maxResults, null);
    }

     public static SearchResult<BinaryInteraction> search(Query query, Directory indexDirectory, Integer firstResult, Integer maxResults)
            throws SearchEngineException
    {
        return search(query, indexDirectory, firstResult, maxResults, null);
    }

    public static SearchResult<BinaryInteraction> search(String query, Directory indexDirectory, Integer firstResult, Integer maxResults, Sort sort)
            throws SearchEngineException
    {
        SearchEngine<BinaryInteraction> engine;
        try
        {
            engine = new BinaryInteractionSearchEngine(indexDirectory);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }

        return engine.search(query, firstResult, maxResults, sort);
    }

    public static SearchResult<BinaryInteraction> search(Query query, Directory indexDirectory, Integer firstResult, Integer maxResults, Sort sort)
            throws SearchEngineException
    {
        SearchEngine<BinaryInteraction> engine;
        try
        {
            engine = new BinaryInteractionSearchEngine(indexDirectory);
        }
        catch (IOException e)
        {
            throw new SearchEngineException(e);
        }

        return engine.search(query, firstResult, maxResults, sort);
    }
    
    public static <T extends BinaryInteraction> SearchResult<T> search(String query, Integer firstResult, Integer maxResults, Sort sort, SearchEngine<T> engine)
    		throws SearchEngineException
    {
    	
    	return engine.search(query, firstResult, maxResults, sort);
    }

    public static <T extends BinaryInteraction> SearchResult<T> search(Query query, Integer firstResult, Integer maxResults, Sort sort, SearchEngine<T> engine)
    		throws SearchEngineException
    {

    	return engine.search(query, firstResult, maxResults, sort);
    }
}
