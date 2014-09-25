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

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.Interactor;

import java.io.Serializable;
import java.util.List;

import org.apache.lucene.search.Query;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class SearchResult<T extends BinaryInteraction> implements Serializable
{
    private List<T> data;
    private Integer totalCount;
    private Integer firstResult;
    private Integer maxResults;
    private Query luceneQuery;

    public SearchResult() {
    }

    public SearchResult(List<T> data, Integer totalCount, Integer firstResult, Integer maxResults, Query luceneQuery)
    {
        this.data = data;
        this.totalCount = totalCount;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.luceneQuery = luceneQuery;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    @Deprecated
    public List<T> getInteractions() {
        return data;
    }

    @Deprecated
    public void setInteractions(List<T> interactions) {
        this.data = interactions;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Query getLuceneQuery() {
        return luceneQuery;
    }

    public void setLuceneQuery(Query luceneQuery) {
        this.luceneQuery = luceneQuery;
    }
}
