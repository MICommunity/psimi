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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import psidev.psi.mi.search.SearchResult;
import psidev.psi.mi.search.engine.SearchEngine;
import psidev.psi.mi.search.engine.SearchEngineException;
import psidev.psi.mi.search.util.DefaultDocumentBuilder;
import psidev.psi.mi.search.util.DocumentBuilder;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Search Engine based on lucene
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public abstract class AbstractSearchEngine<T extends BinaryInteraction> implements SearchEngine<T>
{
    private static final Log log = LogFactory.getLog(AbstractSearchEngine.class);

    private static final String WILDCARD = "*";

    private Directory indexDirectory;

    public AbstractSearchEngine(Directory indexDirectory) throws IOException
    {
        if (indexDirectory == null) {
            throw new NullPointerException("indexDirectory cannot be null");
        }

        this.indexDirectory = indexDirectory;
    }

    public AbstractSearchEngine(String indexDirectory) throws IOException
    {
        this(FSDirectory.getDirectory(indexDirectory));
    }


    public AbstractSearchEngine(File indexDirectory) throws IOException
    {
        this(FSDirectory.getDirectory(indexDirectory));
    }

    public void closeIndexSearcher(IndexSearcher indexSearcher) {
        try {
            indexSearcher.close();
        } catch (IOException e) {
            throw new SearchEngineException(e);
        }
    }

    public void closeIndexReader(IndexReader indexReader) {
        try {
            indexReader.close();
        } catch (IOException e) {
            throw new SearchEngineException(e);
        }
    }

    public SearchResult<T> search(String searchQuery, Integer firstResult, Integer maxResults) throws SearchEngineException
    {
        return search(searchQuery, firstResult, maxResults, null);
    }

    public SearchResult<T> search(Query searchQuery, Integer firstResult, Integer maxResults) throws SearchEngineException
    {
        return search(searchQuery, firstResult, maxResults, null);
    }

    public SearchResult<T> search(String searchQuery, Integer firstResult, Integer maxResults, Sort sort) throws SearchEngineException
    {
        if (searchQuery == null)
        {
            throw new NullPointerException("searchQuery cannot be null");
        }

        if (searchQuery.trim().equals(WILDCARD))
        {
            return searchAll(firstResult, maxResults);
        }

        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new MultiFieldQueryParser(getSearchFields(), analyzer);
        Query query = null;
        try {
            query = parser.parse(searchQuery);
        } catch (ParseException e) {
            throw new SearchEngineException("Problem creating lucene query from string: "+searchQuery, e);
        }

        return search(query, firstResult, maxResults, sort);
    }

    public SearchResult<T> search(Query query, Integer firstResult, Integer maxResults, Sort sort) throws SearchEngineException
    {
        if (log.isDebugEnabled()) log.debug("Searching=\""+query+"\" (first="+firstResult+"/max="+maxResults+")");

        IndexSearcher indexSearcher;
        try {
            indexSearcher = new IndexSearcher(indexDirectory);
        } catch (Exception e) {
            throw new SearchEngineException("Problem creating index searcher", e);
        }

        if (firstResult == null) firstResult = 0;
        if (maxResults == null) maxResults = Integer.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        Hits hits;

        try {
            if (sort != null) {
                hits = indexSearcher.search(query, sort);
            } else {
                hits = indexSearcher.search(query);
            }

            if (log.isDebugEnabled()) log.debug("\tTime: " + (System.currentTimeMillis() - startTime) + "ms");
        }
        catch (Exception e) {
            closeIndexSearcher(indexSearcher);
            throw new SearchEngineException(e);
        }

        int totalCount = hits.length();

        if (totalCount < firstResult)
        {
            if (log.isDebugEnabled()) log.debug("\tNo hits. No results returned");

            closeIndexSearcher(indexSearcher);

            return new SearchResult(Collections.EMPTY_LIST, totalCount, firstResult, maxResults, query);
        }

        int maxIndex = Math.min(totalCount, firstResult+maxResults);

        if (log.isDebugEnabled()) log.debug("\tHits: "+hits.length()+". Will return from "+firstResult+" to "+maxIndex);

        List<T> dataObjects = new ArrayList<T>();

        for (int i=firstResult; i<maxIndex; i++)
        {
            try
            {
                Document doc = hits.doc(i);
                T data = (T) createDocumentBuilder().createData(doc);
                dataObjects.add(data);
            }
            catch (Exception e)
            {
                closeIndexSearcher(indexSearcher);
                throw new SearchEngineException(e);
            }
        }

        closeIndexSearcher(indexSearcher);

         return new SearchResult<T>(dataObjects, totalCount, firstResult, maxResults, query);
    }

    protected DocumentBuilder createDocumentBuilder() {
        return new DefaultDocumentBuilder();
    }

    public SearchResult<T> searchAll(Integer firstResult, Integer maxResults) throws SearchEngineException
    {
        if (firstResult == null) firstResult = 0;
        if (maxResults == null) maxResults = Integer.MAX_VALUE;

        IndexSearcher indexSearcher = null;
        try {
            indexSearcher = new IndexSearcher(indexDirectory);
        } catch (Exception e) {
            throw new SearchEngineException(e);
        }

        IndexReader reader = indexSearcher.getIndexReader();

        int totalCount = reader.maxDoc();

        // this is a hack to ignore any header introduced in the index by mistake (first development versions)
        if (reader.isDeleted(0))
        {
            firstResult++;
            totalCount--;
        }

        if (firstResult > totalCount)
        {
            closeIndexReader(reader);
            closeIndexSearcher(indexSearcher);
            return new SearchResult(Collections.EMPTY_LIST, totalCount, firstResult, maxResults, new WildcardQuery(new Term("", "*")));
        }

        int maxIndex = Math.min(totalCount, firstResult+maxResults);

        List<T> dataObjects = new ArrayList<T>();

        for (int i=firstResult; i<maxIndex; i++)
        {
            try
            {
                Document doc = reader.document(i);
                T data = (T) createDocumentBuilder().createData(doc);
                dataObjects.add(data);
            }
            catch (Exception e)
            {
                closeIndexReader(reader);
                closeIndexSearcher(indexSearcher);
                throw new SearchEngineException(e);
            }
        }

        closeIndexReader(reader);
        closeIndexSearcher(indexSearcher);
        return new SearchResult(dataObjects, totalCount, firstResult, maxResults, new WildcardQuery( new Term("", "*")));
    }

}