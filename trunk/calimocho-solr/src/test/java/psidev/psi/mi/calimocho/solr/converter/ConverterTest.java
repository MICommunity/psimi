/*
 *  Copyright 2012 kbreuer.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package psidev.psi.mi.calimocho.solr.converter;

import java.io.File;
import java.util.List;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.DefaultRowReader;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.util.MitabDocumentDefinitionFactory;

/**
 *
 * @author kbreuer
 */
public class ConverterTest extends TestCase {

    List<Row> rowList_mitab27, rowList_mitab26, rowList_mitab25;
    
    public ConverterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ConverterTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ColumnBasedDocumentDefinition documentDefinition_mitab27 = MitabDocumentDefinitionFactory.mitab27();
        DefaultRowReader rowReader_mitab27 = new DefaultRowReader( documentDefinition_mitab27 );
        rowList_mitab27 = rowReader_mitab27.read(File.class.getResourceAsStream("/samples/sampleFileMitab27.txt"));

        ColumnBasedDocumentDefinition documentDefinition_mitab26 = MitabDocumentDefinitionFactory.mitab26();
        DefaultRowReader rowReader_mitab26 = new DefaultRowReader( documentDefinition_mitab26 );
        rowList_mitab26 = rowReader_mitab26.read(File.class.getResourceAsStream("/samples/sampleFileMitab26.txt"));

        ColumnBasedDocumentDefinition documentDefinition_mitab25 = MitabDocumentDefinitionFactory.mitab25();
        DefaultRowReader rowReader_mitab25 = new DefaultRowReader( documentDefinition_mitab25 );
        rowList_mitab25 = rowReader_mitab25.read(File.class.getResourceAsStream("/samples/sampleFileMitab25.txt"));
        
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testToSolrDocument_27() throws Exception {
        System.out.println("Indexing MITAB 2.7");

        Assert.assertNotNull(rowList_mitab27);
        
        for (Row row:rowList_mitab27) {
            Assert.assertNotNull(row);
            System.out.println("MITAB 2.7 row - key set:"+row.keySet());
            Assert.assertEquals(42-8, row.keySet().size());

            Converter converter = new Converter();
            SolrInputDocument solrDoc = converter.toSolrDocument(row);
            Assert.assertNotNull(solrDoc);

            
        }
    }

    public void testToSolrDocument_26() throws Exception {
        System.out.println("Indexing MITAB 2.6");

        Assert.assertNotNull(rowList_mitab26);

        for (Row row:rowList_mitab26) {
            Assert.assertNotNull(row);
            System.out.println("MITAB 2.6 row - key set:"+row.keySet());
            Assert.assertEquals(36-8, row.keySet().size());
            System.out.println("MITAB 2.6 row - key set:"+row.keySet());

            Converter instance = new Converter();
            SolrInputDocument solrDoc = instance.toSolrDocument(row);
            Assert.assertNotNull(solrDoc);

            
        }
    }

    public void testToSolrDocument_25() throws Exception {
        System.out.println("Indexing MITAB 2.5");

        Assert.assertNotNull(rowList_mitab25);

        for (Row row:rowList_mitab25) {
            Assert.assertNotNull(row);
            System.out.println("MITAB 2.5 row - key set:"+row.keySet());
            Assert.assertEquals(15-2, row.keySet().size());

            Converter instance = new Converter();
            SolrInputDocument solrDoc = instance.toSolrDocument(row);
            Assert.assertNotNull(solrDoc);

            
        }
    }

}
