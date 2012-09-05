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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.hupo.psi.calimocho.tab.util.MitabDocumentDefinitionFactory;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.search.TestHelper;

import static org.junit.Assert.assertEquals;

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

        ColumnBasedDocumentDefinition docDefinition = MitabDocumentDefinitionFactory.mitab25();

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
        IndexReader reader = IndexReader.open(indexDirectory);
        IndexSearcher is = new IndexSearcher(reader);
        QueryParser parser = new QueryParser(Version.LUCENE_36, "id", analyzer);
        Query query = parser.parse("P47077");
        TopDocs hits = is.search(query, 20);

        assertEquals(1, hits.totalHits);

        ScoreDoc[] docs = hits.scoreDocs;
        for (ScoreDoc hit : docs)
        {
            Document doc = is.getIndexReader().document(hit.doc);

            for (int i=0; i<docDefinition.getHighestColumnPosition(); i++) {
                ColumnDefinition colDef = docDefinition.getColumnByPosition(i);

                if (colDef.getKey().equals("detmethod") || colDef.getKey().equals("type")){
                    Assert.assertEquals(valuesExpectedForLine[i], doc.get(colDef.getKey()+"_exact"));
                }
                else {
                    Assert.assertEquals(valuesExpectedForLine[i], doc.get(colDef.getKey()));
                }
            }

        }
    }
}
