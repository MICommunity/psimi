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

import org.apache.lucene.store.Directory;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import psidev.psi.mi.search.index.impl.BinaryInteractionIndexWriter;

import java.io.InputStream;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class SearcherTest
{
	@Test
    public void testBuildIndex() throws Exception
    {
        InputStream is = SearcherTest.class.getResourceAsStream("/mitab_samples/intact.sample.tsv");
        Directory indexDir = Searcher.buildIndexInMemory(is, true, true);

        SearchResult results = Searcher.search("*", indexDir);
        assertEquals(199, results.getInteractions().size());
    }
    
	@Test
    public void testBuildIndex2() throws Exception
    {
        InputStream is = SearcherTest.class.getResourceAsStream("/mitab_samples/intact.sample.tsv");
        Directory indexDir = Searcher.buildIndexInMemory(is, true, true, new BinaryInteractionIndexWriter());

        SearchResult results = Searcher.search("*", indexDir);
        assertEquals(199, results.getInteractions().size());
    }
    
	@Test
    public void testSearch() throws Exception
    {
        InputStream is = SearcherTest.class.getResourceAsStream("/mitab_samples/intact.sample.tsv");

        Assert.assertNotNull("Ensure that the above file is in the classpath and the compiler accepts *.tsv", is);

        Directory indexDir = Searcher.buildIndexInMemory(is, true, true);

        SearchResult results = Searcher.search("P1*", indexDir);

        assertEquals(19, results.getInteractions().size());
    }

    @Test
    public void testSearch_type() throws Exception
    {
        InputStream is = SearcherTest.class.getResourceAsStream("/mitab_samples/intact.sample.tsv");

        Assert.assertNotNull("Ensure that the above file is in the classpath and the compiler accepts *.tsv", is);

        Directory indexDir = Searcher.buildIndexInMemory(is, true, true);

        SearchResult results = Searcher.search("detmethod:\"two hybrid\"", indexDir);

        assertEquals(89, results.getData().size());
    }
}
