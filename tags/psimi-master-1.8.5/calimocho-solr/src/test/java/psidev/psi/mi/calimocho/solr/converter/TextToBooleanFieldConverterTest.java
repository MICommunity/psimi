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
public class TextToBooleanFieldConverterTest extends TestCase {

    List<Row> rowList_mitab27, rowList_mitab26, rowList_mitab25;
    Converter converter;
    
    public TextToBooleanFieldConverterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TextToBooleanFieldConverterTest.class);
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

    public void testIndexFieldValues_param_mitab27() throws Exception {
        System.out.println("TextField: indexFieldValues - param - mitab2.7");

        Assert.assertNotNull(rowList_mitab27);

        for (Row row:rowList_mitab27) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_PARAMETERS_I);
            SolrFieldName fName = SolrFieldName.param;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_param_mitab26() throws Exception {
        System.out.println("TextField: indexFieldValues - param - mitab2.6");

        Assert.assertNotNull(rowList_mitab26);

        for (Row row:rowList_mitab26) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_PARAMETERS_I);
            SolrFieldName fName = SolrFieldName.param;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_param_mitab25() throws Exception {
        System.out.println("TextField: indexFieldValues - param - mitab2.5");

        Assert.assertNotNull(rowList_mitab25);

        for (Row row:rowList_mitab25) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_PARAMETERS_I);
            SolrFieldName fName = SolrFieldName.param;

            testIndexFieldValues(fName, fields, row);
        }
    }

    private void testIndexFieldValues(SolrFieldName fName, Collection<Field> fields, Row row) throws Exception {

        Assert.assertNotNull(row);
//        System.out.println("row: "+row.keySet().toString());

        SolrInputDocument origSolrDoc = converter.toSolrDocument(row);
        Assert.assertNotNull(origSolrDoc);

        if (origSolrDoc.getField(fName.toString()) != null) {

//            System.out.println("\torigSolrDoc-field-name: " + fName);
//            System.out.println("\torigSolrDoc-field: " + origSolrDoc.getField(fName.toString()).toString());
//            System.out.println("\torigSolrDoc-stored: " + origSolrDoc.getField(fName.toString() + "_s").toString());
//            System.out.println("\torigSolrDoc-original: " + origSolrDoc.getField(fName.toString() + "_o").toString());

            Assert.assertNotNull(origSolrDoc.getField(fName.toString()));
            Assert.assertNotNull(origSolrDoc.getField(fName.toString() + "_s"));
            Assert.assertNotNull(origSolrDoc.getField(fName.toString() + "_o"));

            SolrInputDocument solrDoc = new SolrInputDocument();
            Set<String> uniques = new HashSet();
            TextToBooleanFieldConverter textToBoolConverter = new TextToBooleanFieldConverter();

            for (Field field : fields) {
//                System.out.println("field: " + field);

                solrDoc = textToBoolConverter.indexFieldValues(field, fName, solrDoc, uniques);

//                System.out.println("\tsolrDoc-field: " + solrDoc.getField(fName.toString()).toString());
//                System.out.println("\tsolrDoc-stored: " + solrDoc.getField(fName.toString() + "_s").toString());

                Assert.assertNotNull(solrDoc.getField(fName.toString()));
                String s1 = origSolrDoc.getField(fName.toString()).getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                String s2 = solrDoc.getField(fName.toString()).getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                Assert.assertTrue(s1.contains(s2));
                Assert.assertNotNull(solrDoc.getField(fName.toString() + "_s"));
                s1 = origSolrDoc.getField(fName.toString() + "_s").getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                s2 = solrDoc.getField(fName.toString() + "_s").getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                Assert.assertTrue(s1.contains(s2));
                Assert.assertNull(solrDoc.getField(fName.toString() + "_o")); //indexFieldValues-method doesn't write _o
            }
        } else {
            System.err.println("\tField "+fName.toString()+" not found!");
        }
    }

}
