package org.hupo.psi.calimocho.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
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

        KeyValueFieldParser parser = new KeyValueFieldParser();
        final Field field = parser.parse( value, null );
    }

    @Test
    public void parse4_defaultKey() throws Exception {
        String value = "value";

        KeyValueFieldParser parser = new KeyValueFieldParser(":", "defaultKey");
        final Field field = parser.parse( value, null );

        Assert.assertEquals( 2, field.getEntries().size() );
        Assert.assertEquals( "defaultKey", field.get( CalimochoKeys.KEY ) );
        Assert.assertEquals( "value", field.get( CalimochoKeys.VALUE ));
    }
}
