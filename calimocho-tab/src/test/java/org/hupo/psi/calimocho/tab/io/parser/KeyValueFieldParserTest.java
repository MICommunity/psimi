package org.hupo.psi.calimocho.tab.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnDefinitionBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class KeyValueFieldParserTest {

    @Test
    public void parse1() throws Exception {
        String value = "key=value";

        KeyValueFieldParser parser = new KeyValueFieldParser();
        final Field field = parser.parse( value, null );

        Assert.assertEquals( 2, field.getEntries().size() );
        Assert.assertEquals( "key", field.get( CalimochoKeys.KEY ) );
        Assert.assertEquals( "value", field.get( CalimochoKeys.VALUE ) );
    }

    @Test
    public void parse2() throws Exception {
        String value = "key:value";

        KeyValueFieldParser parser = new KeyValueFieldParser(":");
        final Field field = parser.parse( value, null );

        Assert.assertEquals( 2, field.getEntries().size() );
        Assert.assertEquals( "key", field.get( CalimochoKeys.KEY ) );
        Assert.assertEquals( "value", field.get( CalimochoKeys.VALUE ) );
    }

    @Test
    public void parse3() throws Exception {
        String value = "key:value:lala";

        KeyValueFieldParser parser = new KeyValueFieldParser(":");
        final Field field = parser.parse( value, null );

        Assert.assertEquals( 2, field.getEntries().size() );
        Assert.assertEquals( "key", field.get( CalimochoKeys.KEY ) );
        Assert.assertEquals( "value:lala", field.get( CalimochoKeys.VALUE ) );
    }

    @Test(expected = IllegalFieldException.class)
    public void parse4_invalid_defaultKey() throws Exception {
        String value = "value";

        ColumnDefinition columnDefinition = new ColumnDefinitionBuilder()
                .setKey( "key" )
                .setPosition( 1 )
                .build();

        KeyValueFieldParser parser = new KeyValueFieldParser();
        final Field field = parser.parse( value, columnDefinition );
    }

    @Test
    public void parse4_defaultKey() throws Exception {
        String value = "value";

        ColumnDefinition columnDefinition = new ColumnDefinitionBuilder()
                .setKey( "key" )
                .setPosition( 1 )
                .addDefaultValue( CalimochoKeys.KEY, "defaultKey" )
                .build();

        KeyValueFieldParser parser = new KeyValueFieldParser(":");
        final Field field = parser.parse( value, columnDefinition );

        Assert.assertEquals( 2, field.getEntries().size() );
        Assert.assertEquals( "defaultKey", field.get( CalimochoKeys.KEY ) );
        Assert.assertEquals( "value", field.get( CalimochoKeys.VALUE ));
    }
}
