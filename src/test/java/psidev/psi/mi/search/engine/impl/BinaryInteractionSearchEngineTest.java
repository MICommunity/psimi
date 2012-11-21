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

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.util.MitabDocumentDefinitionFactory;
import org.junit.Test;
import psidev.psi.mi.search.SearchResult;
import psidev.psi.mi.search.TestHelper;
import psidev.psi.mi.tab.model.BinaryInteraction;

import static org.junit.Assert.assertEquals;

/**
 * BinaryInteractionSearchEngine Tester.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class BinaryInteractionSearchEngineTest {

	@Test
    public void testSearch() throws Exception {
        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample.tsv");

        BinaryInteractionSearchEngine searchEngine = new BinaryInteractionSearchEngine(indexDirectory, null);

        SearchResult result = searchEngine.search("identifier:P47077", null, null);

        assertEquals(1, result.getInteractions().size());

        result = searchEngine.search("P*", 50, 10);
        assertEquals(10, result.getInteractions().size());
    }

	@Test
    public void testSearchSort() throws Exception {
        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample.tsv");

        BinaryInteractionSearchEngine searchEngine = new BinaryInteractionSearchEngine(indexDirectory, null);

        ColumnBasedDocumentDefinition documentDefinition = MitabDocumentDefinitionFactory.mitab25();

        Sort sort = new Sort(new SortField(documentDefinition.getColumnByPosition(0).getKey()+"_s", SortField.STRING));

        SearchResult<BinaryInteraction> result = searchEngine.search("id:P*", 50, 10, sort);
        assertEquals(10, result.getInteractions().size());

        assertEquals("P33594", result.getInteractions().iterator().next().getInteractorA().getIdentifiers().iterator().next().getIdentifier());
    }

    @Test
    public void testSearch2() throws Exception {
        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample2.txt");

        BinaryInteractionSearchEngine searchEngine = new BinaryInteractionSearchEngine(indexDirectory, null);

        SearchResult<BinaryInteraction> result = searchEngine.search("molindone", null, null);

        assertEquals(1, result.getData().size());
        assertEquals("(+-)-molindone", result.getData().iterator().next().getInteractorA().getAlternativeIdentifiers().iterator().next().getIdentifier());
    }

}
