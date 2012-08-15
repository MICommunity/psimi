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
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
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
import java.util.Arrays;

/**
 * A Search Engine based on lucene
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 *
 * @deprecated use BinaryInteractionSearchEngine instead.
 */
@Deprecated
public class FastSearchEngine extends BinaryInteractionSearchEngine
{

    public FastSearchEngine(Directory indexDirectory) throws IOException {
        super(indexDirectory);
    }

    public FastSearchEngine(String indexDirectory) throws IOException {
        super(indexDirectory);
    }

    public FastSearchEngine(File indexDirectory) throws IOException {
        super(indexDirectory);
    }
}
