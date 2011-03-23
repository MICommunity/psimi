package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.io.formatter.KeyValueFieldFormatter;
import org.hupo.psi.calimocho.io.parser.KeyValueFieldParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class ColumnDefinitionBuilderTest {

    @Test
    public void validate_valid() throws Exception {
        ColumnDefinition cd = new ColumnDefinitionBuilder()
                .setKey( "idA" )
                .setPosition( 0 )
                .setFieldSeparator( "|" )
                .setFieldParser( new KeyValueFieldParser(":") )
                .setFieldFormatter( new KeyValueFieldFormatter(":") )
                .build();

        Assert.assertEquals( "idA", cd.getKey() );
        Assert.assertEquals( 0, cd.getPosition() );
        Assert.assertEquals( "|", cd.getFieldSeparator() );
        Assert.assertNotNull( cd.getFieldParser() );
        Assert.assertTrue( cd.getFieldParser() instanceof KeyValueFieldParser );
        Assert.assertEquals( ":", ( ( KeyValueFieldParser ) cd.getFieldParser() ).getSeparator() );
        Assert.assertNotNull( cd.getFieldFormatter() );
        Assert.assertTrue( cd.getFieldFormatter() instanceof KeyValueFieldFormatter );
        Assert.assertEquals( ":", ( ( KeyValueFieldFormatter ) cd.getFieldFormatter() ).getSeparator() );
        Assert.assertNull( cd.getFieldDelimiter() );
        Assert.assertNull( cd.getEmptyValue() );
        Assert.assertTrue( cd.isAllowsEmpty() );

    }

    @Test(expected = DefinitionException.class)
    public void validate_invalid1() throws Exception {
        new ColumnDefinitionBuilder().build();
    }

    @Test(expected = DefinitionException.class)
    public void validate_invalid2() throws Exception {
        new ColumnDefinitionBuilder()
                .setPosition( 0 )
                .build();
    }

    @Test(expected = DefinitionException.class)
    public void validate_invalid3() throws Exception {
        new ColumnDefinitionBuilder()
                .setKey( "key" )
                .build();
    }

    @Test(expected = DefinitionException.class)
    public void validate_invalid4() throws Exception {
        new ColumnDefinitionBuilder()
                .setPosition( 0 )
                .setKey( "key" )
                .build();
    }
}
