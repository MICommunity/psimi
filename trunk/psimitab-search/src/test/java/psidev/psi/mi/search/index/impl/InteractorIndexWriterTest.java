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

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.search.SearchResult;
import psidev.psi.mi.search.Searcher;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceImpl;

import java.io.InputStream;

/**
 * InteractorIndexWriter Tester.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class InteractorIndexWriterTest {

    private InteractorIndexWriter indexWriter;
    private Directory directory;

    @Before
    public void before() throws Exception {
        indexWriter = new InteractorIndexWriter();
        directory = new RAMDirectory();
    }

    @After
    public void after() throws Exception {
        indexWriter = null;
        directory.close();
        directory = null;
    }

    @Test
    public void index1() throws Exception {
        InputStream is = InteractorIndexWriterTest.class.getResourceAsStream("/mitab_samples/imatinib.tsv");
        indexWriter.index(directory, is, true, true);

        assertSearchResultCount(5, "imatinib" );
    }
    
    @Test
    public void index2() throws Exception {
        InputStream is = InteractorIndexWriterTest.class.getResourceAsStream("/mitab_samples/DGI-5424.tsv");
        indexWriter.index(directory, is, true, true);

        assertSearchResultCount(8, "DGI-5424" );
    }

    @Test
    public void index3() throws Exception {
        InputStream is = InteractorIndexWriterTest.class.getResourceAsStream("/mitab_samples/DDR1.tsv");
        indexWriter.index(directory, is, true, true);

        assertSearchResultCount(2, "ddr1" );
    }

    @Test
    public void indexDDR1() throws Exception {
        InputStream is = InteractorIndexWriterTest.class.getResourceAsStream("/mitab_samples/DDR1.tsv");
        directory = FSDirectory.getDirectory("C:\\testDir");
        indexWriter.index(directory, is, true, true);

        // identifier
        assertSearchResultCount(2, "DB00619" );

        // alias
        assertSearchResultCount(2, "imatinib" );

        // pubmed id
        assertSearchResultCount(2, "18048412" );

        // taxid
        assertSearchResultCount(2, "9606" );

        // interaction ac
        assertSearchResultCount(2, "DGI-71899" );
    }

    @Test
    public void index4() throws Exception {
        InputStream is = InteractorIndexWriterTest.class.getResourceAsStream("/mitab_samples/aspirin.tsv");
        indexWriter.index(directory, is, true, true);

        assertSearchResultCount(5, "aspirine" );
    }

    @Test
    public void index5_noorganism() throws Exception {
        InputStream is = InteractorIndexWriterTest.class.getResourceAsStream("/mitab_samples/single_aspirin.tsv");
        indexWriter.index(directory, is, true, true);

        assertSearchResultCount(2, "Aneuxal" );
    }

    private void assertSearchResultCount( final int expectedCount, String searchQuery ){
        Assert.assertEquals( expectedCount, Searcher.search(searchQuery, directory).getTotalCount().intValue());
    }
}