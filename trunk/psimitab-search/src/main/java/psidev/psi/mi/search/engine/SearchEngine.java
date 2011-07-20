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
package psidev.psi.mi.search.engine;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import psidev.psi.mi.search.SearchResult;
import psidev.psi.mi.tab.model.BinaryInteraction;

/**
 * A Search Engine for the binary interactions
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface SearchEngine<T extends BinaryInteraction>
{
    SearchResult<T> search(String searchQuery, Integer firstResult, Integer maxResults) throws SearchEngineException;

    SearchResult<T> search(Query searchQuery, Integer firstResult, Integer maxResults) throws SearchEngineException;

    SearchResult<T> search(String searchQuery, Integer firstResult, Integer maxResults, Sort sort) throws SearchEngineException;

    SearchResult<T> search(Query searchQuery, Integer firstResult, Integer maxResults, Sort sort) throws SearchEngineException;

    String[] getSearchFields();

    void close();
}
