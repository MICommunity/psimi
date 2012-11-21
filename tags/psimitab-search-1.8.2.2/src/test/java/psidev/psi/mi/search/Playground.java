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

import org.apache.lucene.search.BooleanQuery;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class Playground
{
    public static void main(String[] args) throws Exception
    {
        //InputStream is = new FileInputStream("/ebi/sp/pro6/intact/local/data/released/current/psimitab/intact.txt");
        //String indexDir = "/homes/baranda/tmp_pub/intact-20070731";
        String indexDir = "/ebi/sp/pro6/intact/public-tomcat/psimitab-index/current";

        //Directory ramDir = new RAMDirectory();

        //IndexWriter indexWriter = new IndexWriter(indexDir, new StandardAnalyzer(), false);
        //indexWriter.optimize();

        //Searcher.buildIndex(indexDir, is, true, true);

        BooleanQuery.setMaxClauseCount(1024*150);

        //SearchResult result = Searcher.search("detmethod:\"MI:0018\"", indexDir, 0, 50);

        SearchResult result = Searcher.search("Q08641 NOT species:human", indexDir, 0, 1000);
        
        System.out.println(result.getLuceneQuery());

        System.out.println(result.getTotalCount());

    }
}
