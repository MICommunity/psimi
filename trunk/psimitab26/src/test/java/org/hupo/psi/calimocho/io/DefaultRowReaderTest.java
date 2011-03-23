package org.hupo.psi.calimocho.io;

import junit.framework.Assert;
import org.hupo.psi.calimocho.model.*;
import org.hupo.psi.calimocho.parser.*;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DefaultRowReaderTest {

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
                .setFieldParser( new KeyValueFieldParser(":") )
                .setFieldFormatter( new KeyValueFieldFormatter(":") )
                .build();

        ColumnDefinition authColDefinition = new ColumnDefinitionBuilder()
                .extendColumnDefinition( idColDefinition )
                .setKey( "auth" )
                .setPosition( 8 )
                .setFieldParser( new LiteralFieldParser() )
                .setFieldFormatter( new LiteralFieldFormatter() )
                .build();

         DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
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

        Field idField = idFields.iterator().next();

        final String db = idField.get( CalimochoKeys.DB );
        final String value = idField.get( CalimochoKeys.VALUE );

        Assert.assertEquals("uniprotkb", db);
        Assert.assertEquals( "Q9Y5J7", value );

        Collection<Field> authorFields = row.getFields( idColDefinition.getKey() );

        Assert.assertEquals( 2, authorFields.size() );

        Field field = authorFields.iterator().next();

        Assert.assertEquals( "Peter et al (2010)", field.get( CalimochoKeys.VALUE ) );
    }

    @Test
    public void readStream() throws Exception {
        InputStream is = RowReaderTest.class.getResourceAsStream( "/META-INF/mitab26/14726512.txt" );

        ColumnDefinition columnDefinition = new ColumnDefinitionBuilder()
                .setKey( "id" )
                .setPosition( 0 )
                .setFieldSeparator( "|" )
                .setFieldParser( new LiteralFieldParser() )
                .build();

        DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
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

        final String db = field.get( CalimochoKeys.DB );
        final String value = field.get( CalimochoKeys.VALUE );
        final String text = field.get( CalimochoKeys.TEXT );

        Assert.assertEquals("uniprotkb", db);
        Assert.assertEquals("Q9Y5J7", value);
        Assert.assertNull( text );
    }
}
