package org.hupo.psi.calimocho.tab.io;

import junit.framework.Assert;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.tab.AbstractCalimochoTabTest;
import org.hupo.psi.calimocho.model.*;
import org.hupo.psi.calimocho.tab.io.formatter.KeyValueFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.LiteralFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.XrefFieldFormatter;
import org.hupo.psi.calimocho.tab.io.parser.KeyValueFieldParser;
import org.hupo.psi.calimocho.tab.io.parser.LiteralFieldParser;
import org.hupo.psi.calimocho.tab.io.parser.XrefFieldParser;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinitionBuilder;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnDefinitionBuilder;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DefaultRowReaderTest extends AbstractCalimochoTabTest {

    private static String mitab26Line;
    private static String mitab27Line;
    private static String mitab28Line;

    @BeforeClass
    public static void setUp() {
        mitab26Line = "uniprotkb:Q9Y5J7|intact:EBI-123456\tuniprotkb:Q9Y584\tuniprotkb:TIMM9(gene name)\tuniprotkb:TIMM22(gene name)\t" +
                "uniprotkb:TIM9\tuniprotkb:TEX4\t" +
                "psi-mi:\"MI:0006\"(anti bait coip)\tPeter et al (2010)\tpubmed:14726512\ttaxid:9606(human)\ttaxid:9606(human)\t" +
                "psi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-1200556\t-\t" +
                "psi-mi:\"MI:xxxx\"(spoke)\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499\"(unspecified role)\t" +
                "psi-mi:\"MI:0497\"(bait)\tpsi-mi:\"MI:0498\"(prey)\tpsi-mi:\"MI:0326\"(protein)\tpsi-mi:\"MI:0326\"(protein)\t" +
                "interpro:IPR004046(GST_C)\t" +
                "go:\"GO:0004709\"(\"F:MAP kinase kinase kinase act\")\tgo:\"GO:xxxxx\"\t" +
                "caution:AnnotA\tcaution:AnnotB\tdataset:Test\ttaxid:9606(human-293t)\tkd:2\t2009/03/09\t2010/03/30\t" +
                "seguid:checksumA\tseguid:checksumB\tseguid:checksumI\tfalse";
        mitab27Line = mitab26Line + "\ttag:?-?\t-\t-\t-\tpsi-mi:\"MI:0102\"(sequence tag identification)\tpsi-mi:\"MI:0102\"(sequence tag identification)";
        mitab28Line = mitab27Line + "\t-\t-\tpsi-mi:\"MI:2247\"(transcriptional regulation)\tpsi-mi:\"MI:2236\"(up-regulates activity)";
    }

    @Test
    public void readMitab26Line() throws Exception {

        ColumnDefinition idColDefinition = new ColumnDefinitionBuilder()
                .setKey( "idA" )
                .setPosition( 0 )
                .setFieldSeparator( "|" )
                .setFieldParser( new KeyValueFieldParser( ":" ) )
                .setFieldFormatter( new KeyValueFieldFormatter( ":" ) )
                .build();

        ColumnDefinition authColDefinition = new ColumnDefinitionBuilder()
                .extendColumnDefinition( idColDefinition )
                .setKey( "pubauth" )
                .setPosition( 7 )
                .setFieldParser( new LiteralFieldParser() )
                .setFieldFormatter( new LiteralFieldFormatter() )
                .build();

         ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .addColumnDefinition( idColDefinition )
                .addColumnDefinition( authColDefinition )
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        RowReader rowReader = new DefaultRowReader( docDefinition );
        Row row = rowReader.readLine(mitab26Line);

        Assert.assertNotNull( row );

        Collection<Field> idFields = row.getFields( idColDefinition.getKey() );

        Assert.assertEquals( 2, idFields.size() );

        for (Field idField : idFields) {
            final String db = idField.get( CalimochoKeys.KEY );
            final String value = idField.get( CalimochoKeys.VALUE );

            if ("intact".equals(db)) {
                Assert.assertEquals("intact", db);
                Assert.assertEquals( "EBI-123456", value );
            } else {
                Assert.assertEquals("uniprotkb", db);
                Assert.assertEquals( "Q9Y5J7", value );
            }
        }

        Collection<Field> authorFields = row.getFields( authColDefinition.getKey() );

        Assert.assertEquals( 1, authorFields.size() );

        Field authField = authorFields.iterator().next();

        Assert.assertEquals( "Peter et al (2010)", authField.get( CalimochoKeys.VALUE ) );
    }

    @Test
    public void readMitab27Line() throws Exception {

        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        ColumnDefinition featuresInteractorADefinition = new ColumnDefinitionBuilder()
                .setKey( "ftypeA" )
                .setPosition( 36 )
                .setFieldSeparator("|")
                .setEmptyValue("-")
                .setFieldDelimiter("")
                .setIsAllowsEmpty(true)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition partDetectionMethodADefinition = new ColumnDefinitionBuilder()
                .extendColumnDefinition( featuresInteractorADefinition )
                .setKey( "pmethodA" )
                .setPosition( 40 )
                .build();

        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .addColumnDefinition( featuresInteractorADefinition )
                .addColumnDefinition( partDetectionMethodADefinition )
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        RowReader rowReader = new DefaultRowReader( docDefinition );
        Row row = rowReader.readLine(mitab27Line);

        Assert.assertNotNull( row );

        Collection<Field> fTypeAFields = row.getFields( featuresInteractorADefinition.getKey() );

        Assert.assertEquals( 1, fTypeAFields.size() );

        Field fTypeAField = fTypeAFields.iterator().next();

        final String fTypeA_key = fTypeAField.get( CalimochoKeys.KEY );
        final String fTypeA_value = fTypeAField.get( CalimochoKeys.VALUE );

        Assert.assertEquals("tag", fTypeA_key);
        Assert.assertEquals("?-?", fTypeA_value);

        Collection<Field> partDetMethodAFields = row.getFields( partDetectionMethodADefinition.getKey() );

        Assert.assertEquals(1, partDetMethodAFields.size());

        Field partDetMethodAField = partDetMethodAFields.iterator().next();

        final String partDetMethodA_key = partDetMethodAField.get( CalimochoKeys.KEY );
        final String partDetMethodA_value = partDetMethodAField.get( CalimochoKeys.VALUE );
        final String partDetMethodA_text = partDetMethodAField.get( CalimochoKeys.TEXT );

        Assert.assertEquals("psi-mi", partDetMethodA_key);
        Assert.assertEquals("MI:0102", partDetMethodA_value);
        Assert.assertEquals("sequence tag identification", partDetMethodA_text);
    }

    @Test
    public void readMitab28Line() throws Exception {

        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        ColumnDefinition biologicalEffectADefinition = new ColumnDefinitionBuilder()
                .setKey( "bioEffectA" )
                .setPosition( 42 )
                .setFieldSeparator("|")
                .setEmptyValue("-")
                .setFieldDelimiter("")
                .setIsAllowsEmpty(true)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition causalRegMechanismDefinition = new ColumnDefinitionBuilder()
                .extendColumnDefinition( biologicalEffectADefinition )
                .setKey( "causalmechanism" )
                .setPosition( 44 )
                .build();

        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .addColumnDefinition( biologicalEffectADefinition )
                .addColumnDefinition( causalRegMechanismDefinition )
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        RowReader rowReader = new DefaultRowReader( docDefinition );
        Row row = rowReader.readLine(mitab28Line);

        Assert.assertNotNull( row );

        Collection<Field> bioEffectAFields = row.getFields( biologicalEffectADefinition.getKey() );

        Assert.assertEquals( 0, bioEffectAFields.size() );

        Collection<Field> causalRegMechanismFields = row.getFields( causalRegMechanismDefinition.getKey() );

        Assert.assertEquals(1, causalRegMechanismFields.size());

        Field causalRegMechanismField = causalRegMechanismFields.iterator().next();

        final String key = causalRegMechanismField.get( CalimochoKeys.KEY );
        final String value = causalRegMechanismField.get( CalimochoKeys.VALUE );
        final String text = causalRegMechanismField.get( CalimochoKeys.TEXT );

        Assert.assertEquals("psi-mi", key);
        Assert.assertEquals("MI:2247", value);
        Assert.assertEquals("transcriptional regulation", text);
    }

    @Test
    public void readMitab26Stream() throws Exception {
        InputStream is = DefaultRowReaderTest.class.getResourceAsStream( "/META-INF/mitab26/14726512.txt" );

        ColumnDefinition columnDefinition = new ColumnDefinitionBuilder()
                .setKey( "id" )
                .setPosition( 0 )
                .setFieldSeparator( "|" )
                .setFieldParser( new KeyValueFieldParser( ":" ) )
                .setFieldFormatter( new KeyValueFieldFormatter( ":" ) )
                .build();

        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .addColumnDefinition( columnDefinition )
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        RowReader reader = new DefaultRowReader(docDefinition);

        List<Row> rows = reader.read(is);

        Assert.assertEquals( 1, rows.size() );

        Row row = rows.get( 0 );

        Collection<Field> fields = row.getFields( columnDefinition.getKey() );

        Assert.assertEquals( 1, fields.size() );

        Field field = fields.iterator().next();

        final String key = field.get( CalimochoKeys.KEY );
        final String value = field.get( CalimochoKeys.VALUE );
        final String text = field.get( CalimochoKeys.TEXT );

        Assert.assertEquals("uniprotkb", key);
        Assert.assertEquals("Q9Y5J7", value);
        Assert.assertNull( text );
    }

    @Test
    public void readMitab27Stream() throws Exception {

        InputStream is = DefaultRowReaderTest.class.getResourceAsStream( "/META-INF/mitab27/mitab27_example.txt" );

        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        ColumnDefinition hostOrganismDefinition = new ColumnDefinitionBuilder()
                .setKey("taxidHost")
                .setFieldSeparator("|")
                .setEmptyValue("-")
                .setFieldDelimiter("")
                .setIsAllowsEmpty(true)
                .setPosition(28)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition partDetectionMethodBDefinition = new ColumnDefinitionBuilder()
                .extendColumnDefinition(hostOrganismDefinition)
                .setKey("pmethodB")
                .setPosition(41)
                .build();

        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .addColumnDefinition(hostOrganismDefinition)
                .addColumnDefinition(partDetectionMethodBDefinition)
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        RowReader reader = new DefaultRowReader(docDefinition);

        List<Row> rows = reader.read(is);

        Assert.assertEquals( 1, rows.size() );

        Row row = rows.get( 0 );

        Collection<Field> hostOrganismFields = row.getFields( hostOrganismDefinition.getKey() );

        Assert.assertEquals( 2, hostOrganismFields.size() );

        for (Field hostOrganismField : hostOrganismFields) {
            final String key = hostOrganismField.get( CalimochoKeys.KEY );
            final String value = hostOrganismField.get( CalimochoKeys.VALUE );
            final String text = hostOrganismField.get( CalimochoKeys.TEXT );

            Assert.assertEquals("taxid", key);
            Assert.assertEquals("7227", value);
            Assert.assertTrue(text.equalsIgnoreCase("drome-embryo") || text.equalsIgnoreCase("drosophilla melanogaster embryo"));
        }

        Collection<Field> pmethodBFields = row.getFields( partDetectionMethodBDefinition.getKey() );

        Assert.assertEquals( 1, pmethodBFields.size() );

        Field pmethodBField = pmethodBFields.iterator().next();

        final String key = pmethodBField.get( CalimochoKeys.KEY );
        final String value = pmethodBField.get( CalimochoKeys.VALUE );
        final String text = pmethodBField.get( CalimochoKeys.TEXT );

        Assert.assertEquals("psi-mi", key);
        Assert.assertEquals("MI:0102", value);
        Assert.assertEquals("sequence tag identification", text);
    }

    @Test
    public void reamMitab28Stream() throws Exception {
        InputStream is = DefaultRowReaderTest.class.getResourceAsStream( "/META-INF/mitab28/mitab28_example.txt" );

        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        ColumnDefinition biologicalEffectBDefinition = new ColumnDefinitionBuilder()
                .setKey( "bioEffectB" )
                .setPosition( 43 )
                .setFieldSeparator("|")
                .setEmptyValue("-")
                .setFieldDelimiter("")
                .setIsAllowsEmpty(true)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition causalStatementDefinition = new ColumnDefinitionBuilder()
                .extendColumnDefinition( biologicalEffectBDefinition)
                .setKey( "causalstatement" )
                .setPosition( 45 )
                .build();

        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .addColumnDefinition(biologicalEffectBDefinition)
                .addColumnDefinition(causalStatementDefinition)
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        RowReader reader = new DefaultRowReader(docDefinition);

        List<Row> rows = reader.read(is);

        Assert.assertEquals( 1, rows.size() );

        Row row = rows.get( 0 );

        Collection<Field> bioEffectBFields = row.getFields( biologicalEffectBDefinition.getKey() );

        Assert.assertEquals(1, bioEffectBFields.size());

        Field bioEffectBField = bioEffectBFields.iterator().next();
        final String bioEffectBField_key = bioEffectBField.get( CalimochoKeys.KEY );
        final String bioEffectBField_value = bioEffectBField.get( CalimochoKeys.VALUE );
        final String bioEffectBField_text = bioEffectBField.get( CalimochoKeys.TEXT );

        Assert.assertEquals("go", bioEffectBField_key);
        Assert.assertEquals("GO:0016301", bioEffectBField_value);
        Assert.assertEquals("kinase activity", bioEffectBField_text);

        Collection<Field> causalStatementFields = row.getFields( causalStatementDefinition.getKey() );

        Assert.assertEquals(1, causalStatementFields.size());

        Field causalStatementField = causalStatementFields.iterator().next();
        final String causalStatementField_key = causalStatementField.get( CalimochoKeys.KEY );
        final String causalStatementField_value = causalStatementField.get( CalimochoKeys.VALUE );
        final String causalStatementField_text = causalStatementField.get( CalimochoKeys.TEXT );

        Assert.assertEquals("psi-mi", causalStatementField_key);
        Assert.assertEquals("MI:2240", causalStatementField_value);
        Assert.assertEquals("down regulates", causalStatementField_text);
    }

    @Test()
    @Ignore
    // we don't want this test anymore. If the number of columns is bigger/lower that the one expected in column definitions, we just ignore the supplementary columns.
    // this would help with parsing MITAB 2.5 files using 2.7 parser and 2.7 files using 2.5 parser
    public void invalidInputFile_columnCount() throws Exception {
        ColumnBasedDocumentDefinition dd = new ColumnBasedDocumentDefinitionBuilder().addColumnDefinition( new ColumnDefinitionBuilder()
                                              .setKey( "gene" )
                                              .setPosition( 1 )
                                              .setEmptyValue( "" )
                                              .setIsAllowsEmpty( false )
                                              .setFieldSeparator( "," )
                                              .setFieldDelimiter( "" )
                                              .setFieldParser( new LiteralFieldParser() )
                                              .setFieldFormatter( new LiteralFieldFormatter() )
                                              .build() )
                .addColumnDefinition( new ColumnDefinitionBuilder()
                                              .setKey( "taxid" )
                                              .setPosition( 2 )
                                              .setEmptyValue( "" )
                                              .setIsAllowsEmpty( false )
                                              .setFieldSeparator( "," )
                                              .setFieldDelimiter( "" )
                                              .setFieldParser( new LiteralFieldParser() )
                                              .setFieldFormatter( new LiteralFieldFormatter() )
                                              .build() )
                .setColumnSeparator( "|" )
                .build();

        String aLine = "LSM7|9606";

        RowReader reader = new DefaultRowReader( dd );
        try {
            reader.readLine( aLine );
            Assert.fail("Expected IllegalRowException to be thrown");
        } catch ( IllegalRowException e ) {
            // expected here !
            Assert.assertEquals( aLine, e.getLine() );
        } catch ( IllegalColumnException e ) {
            Assert.fail();
        } catch ( IllegalFieldException e ) {
            Assert.fail();
        }
    }

    @Test
    public void read_fieldKeyValue() throws Exception {

        final String aLine = "LSM7|9606\n";

        final ColumnBasedDocumentDefinition documentDefinition = buildGeneListDefinition();

        RowReader reader = new DefaultRowReader( documentDefinition );
        final List<Row> rows = reader.read( new ByteArrayInputStream( aLine.getBytes() ) );
        org.junit.Assert.assertNotNull( rows );
        org.junit.Assert.assertEquals( 1, rows.size() );

        Row row = rows.get( 0 );
        final Collection<Field> geneFields = row.getFields( "gene" );
        Assert.assertNotNull( geneFields );
        Assert.assertEquals(1, geneFields.size());
        final Field geneField = geneFields.iterator().next();
        final String gene = geneField.get( CalimochoKeys.VALUE );
        Assert.assertNotNull( gene );
        Assert.assertEquals("LSM7", gene);

        final Collection<Field> taxidFields = row.getFields( "taxid" );
        Assert.assertNotNull( taxidFields );
        Assert.assertEquals(1, taxidFields.size());
        final Field taxidField = taxidFields.iterator().next();
        final String taxid = taxidField.get( CalimochoKeys.VALUE );
        Assert.assertNotNull( taxid );
        Assert.assertEquals("9606", taxid);
    }
}
