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
import org.apache.solr.common.SolrInputField;
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
public class AnnotationFieldConverterTest extends TestCase {
    
    private List<Row> rowList_mitab26, rowList_mitab27, rowList_mitab28;
    private Converter converter;

    public AnnotationFieldConverterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(AnnotationFieldConverterTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        converter = new Converter();

        ColumnBasedDocumentDefinition documentDefinition_mitab26 = MitabDocumentDefinitionFactory.mitab26();
        DefaultRowReader rowReader_mitab26 = new DefaultRowReader( documentDefinition_mitab26 );
        rowList_mitab26 = rowReader_mitab26.read(File.class.getResourceAsStream("/samples/sampleFileMitab26.txt"));

        ColumnBasedDocumentDefinition documentDefinition_mitab27 = MitabDocumentDefinitionFactory.mitab27();
        DefaultRowReader rowReader_mitab27 = new DefaultRowReader( documentDefinition_mitab27 );
        rowList_mitab27 = rowReader_mitab27.read(File.class.getResourceAsStream("/samples/sampleFileMitab27.txt"));

        ColumnBasedDocumentDefinition documentDefinition_mitab28 = MitabDocumentDefinitionFactory.mitab28();
        DefaultRowReader rowReader_mitab28 = new DefaultRowReader( documentDefinition_mitab28 );
        rowList_mitab28 = rowReader_mitab28.read(File.class.getResourceAsStream("/samples/sampleFileMitab28.txt"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    //no annotation field in MITAB 2.5

    public void testIndexFieldValues_annotA_mitab26() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotA - mitab2.6");

        Assert.assertNotNull(rowList_mitab26);

        for (Row row:rowList_mitab26) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_A);
            SolrFieldName fName = SolrFieldName.annotA;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotB_mitab26() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotB - mitab2.6");

        Assert.assertNotNull(rowList_mitab26);

        for (Row row:rowList_mitab26) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_B);
            SolrFieldName fName = SolrFieldName.annotB;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotI_mitab26() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotI - mitab2.6");

        Assert.assertNotNull(rowList_mitab26);

        for (Row row:rowList_mitab26) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_I);
            SolrFieldName fName = SolrFieldName.annot;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotA_mitab27() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotA - mitab2.7");

        Assert.assertNotNull(rowList_mitab27);

        for (Row row:rowList_mitab27) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_A);
            SolrFieldName fName = SolrFieldName.annotA;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotB_mitab27() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotB - mitab2.7");

        Assert.assertNotNull(rowList_mitab27);

        for (Row row:rowList_mitab27) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_B);
            SolrFieldName fName = SolrFieldName.annotB;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotI_mitab27() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotI - mitab2.7");

        Assert.assertNotNull(rowList_mitab27);

        for (Row row:rowList_mitab27) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_I);
            SolrFieldName fName = SolrFieldName.annot;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotA_mitab28() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotA - mitab2.8");

        Assert.assertNotNull(rowList_mitab28);

        for (Row row:rowList_mitab28) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_A);
            SolrFieldName fName = SolrFieldName.annotA;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotB_mitab28() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotB - mitab2.8");

        Assert.assertNotNull(rowList_mitab28);

        for (Row row:rowList_mitab28) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_B);
            SolrFieldName fName = SolrFieldName.annotB;

            testIndexFieldValues(fName, fields, row);
        }
    }

    public void testIndexFieldValues_annotI_mitab28() throws Exception {
        System.out.println("AnnotationField: indexFieldValues - annotI - mitab2.8");

        Assert.assertNotNull(rowList_mitab28);

        for (Row row:rowList_mitab28) {

            Collection<Field> fields = row.getFields(InteractionKeys.KEY_ANNOTATIONS_I);
            SolrFieldName fName = SolrFieldName.annot;

            testIndexFieldValues(fName, fields, row);
        }
    }

    private void testIndexFieldValues(SolrFieldName fName, Collection<Field> fields, Row row) throws Exception {

        Assert.assertNotNull(row);
//      System.out.println("row: " + row.keySet().toString());

        SolrInputDocument origSolrDoc = converter.toSolrDocument(row);
        Assert.assertNotNull(origSolrDoc);

        SolrInputField origSolrDocFieldName = origSolrDoc.getField(fName.toString());
        SolrInputField origSolrDocFieldName_o = origSolrDoc.getField(fName.toString() + "_o");

        if (origSolrDocFieldName != null) {

//            System.out.println("\torigSolrDoc-field-name: " + fName);
//            System.out.println("\torigSolrDoc-field: " + origSolrDocFieldName.toString());
//            System.out.println("\torigSolrDoc-stored: " + origSolrDoc.getField(fName.toString() + "_s").toString());
//            System.out.println("\torigSolrDoc-original: " + origSolrDoc.getField(fName.toString() + "_o").toString());

            Assert.assertNotNull(origSolrDocFieldName);
            Assert.assertNotNull(origSolrDoc.getField(fName.toString() + "_s"));
            Assert.assertNotNull(origSolrDoc.getField(fName.toString() + "_o"));

            SolrInputDocument solrDoc = new SolrInputDocument();
            Set<String> uniques = new HashSet();
            AnnotationFieldConverter annotConverter = new AnnotationFieldConverter();

            for (Field field : fields) {
//                System.out.println("field: " + field);

                solrDoc = annotConverter.indexFieldValues(field, fName, solrDoc, uniques);

//                System.out.println("\tsolrDoc-field: " + solrDoc.getField(fName.toString()).toString());
//                System.out.println("\tsolrDoc-stored: " + solrDoc.getField(fName.toString() + "_s").toString());

                Assert.assertNotNull(solrDoc.getField(fName.toString()));
                String s1 = origSolrDocFieldName.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                String s2 = solrDoc.getField(fName.toString()).getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                Assert.assertTrue(s1.contains(s2));
                Assert.assertNotNull(solrDoc.getField(fName.toString() + "_s"));
                s1 = origSolrDoc.getField(fName.toString() + "_s").getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                s2 = solrDoc.getField(fName.toString() + "_s").getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                Assert.assertTrue(s1.contains(s2));
                Assert.assertNull(solrDoc.getField(fName.toString() + "_o")); //indexFieldValues-method doesn't write _o
            }
        } else if (origSolrDocFieldName_o != null) {
//          System.out.println(origSolrDocFieldName_o.getName());
            if (fName.toString().equals("annotA")) Assert.assertEquals(origSolrDocFieldName_o.getName(), "annotA_o");
            else if (fName.toString().equals("annotB")) Assert.assertEquals(origSolrDocFieldName_o.getName(), "annotB_o");
            else Assert.fail("\tField " + origSolrDocFieldName_o.getName() + " not found!");
        }
        else {
            // The Mitab2.6 and 2.7 don't have the fields annotA and annotB (just reporting, no test fail in here)
            System.out.println("\tField " + fName.toString() + " not found - it is empty (a dash) in the sample file!");
        }
    }

}
