package org.hupo.psi.calimocho.io;

import junit.framework.Assert;
import org.hupo.psi.calimocho.AbstractCalimochoTest;
import org.hupo.psi.calimocho.model.*;
import org.hupo.psi.tab.io.DefaultRowReader;
import org.hupo.psi.tab.io.IllegalColumnException;
import org.hupo.psi.tab.io.RowReader;
import org.hupo.psi.tab.io.formatter.KeyValueFieldFormatter;
import org.hupo.psi.tab.io.formatter.LiteralFieldFormatter;
import org.hupo.psi.tab.io.parser.KeyValueFieldParser;
import org.hupo.psi.tab.io.parser.LiteralFieldParser;
import org.hupo.psi.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.tab.model.ColumnBasedDocumentDefinitionBuilder;
import org.hupo.psi.tab.model.ColumnDefinition;
import org.hupo.psi.tab.model.ColumnDefinitionBuilder;
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
public class DefaultRowReaderTest extends AbstractCalimochoTest {

    @Test
    public void readLine() throws Exception {
        String mitab26Line = "uniprotkb:Q9Y5J7|intact:EBI-123456\tuniprotkb:Q9Y584\tuniprotkb:TIMM9(gene name)\tuniprotkb:TIMM22(gene name)\t" +
                "uniprotkb:TIM9\tuniprotkb:TEX4\t" +
                "psi-mi:\"MI:0006\"(anti bait coip)\tPeter et al (2010)\tpubmed:14726512\ttaxid:9606(human)\ttaxid:9606(human)\t" +
                "psi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-1200556\t-\t" +
                "psi-mi:\"MI:xxxx\"(spoke)\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499\"(unspecified role)\t" +
                "psi-mi:\"MI:0497\"(bait)\tpsi-mi:\"MI:0498\"(prey)\tpsi-mi:\"MI:0326\"(protein)\tpsi-mi:\"MI:0326\"(protein)\t" +
                "interpro:IPR004046(GST_C)\t" +
                "go:\"GO:0004709\"(\"F:MAP kinase kinase kinase act\")\tgo:\"GO:xxxxx\"\t" +
                "caution:AnnotA\tcaution:AnnotB\tdataset:Test\ttaxid:9606(human-293t)\t-\t-\tkd:2\t2009/03/09\t2010/03/30\t" +
                "seguid:checksumA\tseguid:checksumB\tseguid:checksumI\tfalse";

        ColumnDefinition idColDefinition = new ColumnDefinitionBuilder()
                .setKey( "idA" )
                .setPosition( 0 )
                .setFieldSeparator( "|" )
                .setFieldParser( new KeyValueFieldParser( ":" ) )
                .setFieldFormatter( new KeyValueFieldFormatter( ":" ) )
                .build();

        ColumnDefinition authColDefinition = new ColumnDefinitionBuilder()
                .extendColumnDefinition( idColDefinition )
                .setKey( "auth" )
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
    public void readStream() throws Exception {
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

    @Test()
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
