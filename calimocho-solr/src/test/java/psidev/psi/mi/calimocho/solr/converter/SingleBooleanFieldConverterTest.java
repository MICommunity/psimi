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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.DefaultRowReader;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.util.MitabDocumentDefinitionFactory;

/**
 *
 * @author kbreuer
 */
public class SingleBooleanFieldConverterTest extends TestCase {

    List<Row> rowList_mitab27, rowList_mitab26, rowList_mitab25;
    Converter converter;

    public SingleBooleanFieldConverterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SingleBooleanFieldConverterTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        converter = new Converter();

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

    public void testIndexFieldValues_negative_mitab27() throws Exception {
        System.out.println("SingleBooleanField: indexFieldValues - negative - mitab2.7");

        Assert.assertNotNull(rowList_mitab27);

        for (Row row:rowList_mitab27) {
            Assert.assertNotNull(row);
            SolrInputDocument origSolrDoc = converter.toSolrDocument(row);
            Assert.assertNotNull(origSolrDoc);

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_NEGATIVE);
            SolrFieldName fName = SolrFieldName.negative;
            SolrInputDocument solrDoc = new SolrInputDocument();
            SingleBooleanFieldConverter boolConverter = new SingleBooleanFieldConverter();

            for (Field field : fields) {
                Assert.assertNotSame(solrDoc.getField(fName.toString()+"_s"), origSolrDoc.getField(fName.toString()+"_s"));
                Assert.assertNotSame(solrDoc.getField(fName.toString()+"_o"), origSolrDoc.getField(fName.toString()+"_o"));
                Assert.assertNotSame(solrDoc.getFieldValue(fName.toString()), origSolrDoc.getFieldValue(fName.toString()));
                boolConverter.indexFieldValues(field, fName, solrDoc, new HashSet());
                Assert.assertNotNull(solrDoc.getField(fName.toString()+"_s"));
                Assert.assertNull(solrDoc.getField(fName.toString()+"_o"));
                Assert.assertNotNull(solrDoc.getField(fName.toString()));
                Assert.assertEquals(solrDoc.getFieldValue(fName.toString()), origSolrDoc.getFieldValue(fName.toString()));
                Assert.assertNotSame(solrDoc.getField(fName.toString()+"_o"), origSolrDoc.getField(fName.toString()+"_o"));
                Assert.assertEquals(solrDoc.getFieldValue(fName.toString()+"_s"), origSolrDoc.getFieldValue(fName.toString()+"_s"));
            }
        }
    }

    public void testIndexFieldValues_stc() {
    }

    public void testIndexFieldValues_param() {
    }

}
