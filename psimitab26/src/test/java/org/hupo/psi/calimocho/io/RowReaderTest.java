package org.hupo.psi.calimocho.io;

import junit.framework.Assert;
import org.hupo.psi.calimocho.model.*;
import org.hupo.psi.tab.XrefKeys;
import org.hupo.psi.tab.parser.XrefFieldParser;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class RowReaderTest {

    @Test
    public void read() throws Exception {
        InputStream is = RowReaderTest.class.getResourceAsStream( "/META-INF/mitab26/14726512.txt" );

        //DocumentDefinition docDefinition = DocumentDefinitionFactory.create( , "\t" );
        //docDefinition.setCommentPrefix( "#" );


        DefaultColumnDefinition columnDefinition = new DefaultColumnDefinition( "idA", 0 );
        columnDefinition.setFieldSeparator( "|" );
//        columnDefinition.setFieldClassName( XrefField.class.getName() );
        columnDefinition.setFieldParserClassName( XrefFieldParser.class.getName() );

        DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
                .addColumnDefinition( columnDefinition )
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        RowReader reader = new RowReader(docDefinition);

        List<Row> rows = reader.read(is);

        Assert.assertEquals( 1, rows.size() );

        Row row = rows.get( 0 );

        Collection<Field> fields = row.getFields( columnDefinition.getKey() );

        Assert.assertEquals( 1, fields.size() );

        Field field = fields.iterator().next();

        final String db = field.get( XrefKeys.DB );
        final String value = field.get( XrefKeys.VALUE );
        final String text = field.get( XrefKeys.TEXT );

        Assert.assertEquals("uniprotkb", db);
        Assert.assertEquals("Q9Y5J7", value);
        Assert.assertNull( text );
    }

}
