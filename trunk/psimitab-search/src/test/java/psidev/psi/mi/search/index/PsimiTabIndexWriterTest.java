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

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.junit.Test;
import org.junit.Assert;

import psidev.psi.mi.search.TestHelper;
import psidev.psi.mi.tab.model.builder.DocumentDefinition;
import psidev.psi.mi.tab.model.builder.MitabDocumentDefinition;
import psidev.psi.mi.tab.model.builder.ColumnDefinition;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */

public class PsimiTabIndexWriterTest
{
	@Test
    public void testIndex() throws Exception
    {
        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample.tsv");

        String matchedLine = "uniprotkb:P47077\tuniprotkb:P40069\t-\tgene name:KAP123\tlocus name:YJL010C|orf name:J1357\tgene name synonym:YRB4|locus name:YER110C\tpsi-mi:\"MI:0096\"(pull down)\t-\tpubmed:14690591\ttaxid:4932(yeast)\ttaxid:4932(yeast)\tpsi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-854045\t-";
        String[] valuesExpectedForLine = matchedLine.split("\t");

        DocumentDefinition docDefinition = new MitabDocumentDefinition();

        Analyzer analyzer = new StandardAnalyzer();
        IndexSearcher is = new IndexSearcher(indexDirectory);
        QueryParser parser = new QueryParser("id", analyzer);
        Query query = parser.parse("P47077");
        Hits hits = is.search(query);

        assertEquals(1, hits.length());

        Iterator<Hit> iter = hits.iterator();
        while (iter.hasNext())
        {
            Hit hit = iter.next();
            Document doc = hit.getDocument();

            for (int i=0; i<docDefinition.getColumnsCount(); i++) {
                ColumnDefinition colDef = docDefinition.getColumnDefinition(i);
                Assert.assertEquals(valuesExpectedForLine[i], doc.get(colDef.getShortName()));
            }

        }
    }
}
