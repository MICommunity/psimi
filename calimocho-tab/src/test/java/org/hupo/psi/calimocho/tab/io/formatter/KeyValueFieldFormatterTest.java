package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class KeyValueFieldFormatterTest {

    @Test
    public void format1() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.KEY, "key" );
        field.set( CalimochoKeys.VALUE, "value" );

        final KeyValueFieldFormatter formatter = new KeyValueFieldFormatter();
        final String str = formatter.format( field );

        Assert.assertEquals( "key=value", str );
    }

    @Test
    public void format2() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.KEY, "key" );
        field.set( CalimochoKeys.VALUE, "value" );

        final KeyValueFieldFormatter formatter = new KeyValueFieldFormatter();
        formatter.setSeparator( ":" );

        final String str = formatter.format( field );

        Assert.assertEquals( ":", formatter.getSeparator() );
        Assert.assertEquals( "key:value", str );
    }

    @Test(expected = IllegalFieldException.class)
    public void format3_noKey() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.VALUE, "value" );

        final KeyValueFieldFormatter formatter = new KeyValueFieldFormatter();
        final String str = formatter.format( field );
    }

    @Test
    public void format4_noKey_withDefault() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.VALUE, "value" );

        final KeyValueFieldFormatter formatter = new KeyValueFieldFormatter();
        formatter.setDefaultKey( "lalakey" );

        final String str = formatter.format( field );

        Assert.assertEquals( "=", formatter.getSeparator() );
        Assert.assertEquals( "lalakey", formatter.getDefaultKey() );
        Assert.assertEquals( "lalakey=value", str );
    }

    @Test
    public void format5_fullConstructor() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.VALUE, "value" );

        final KeyValueFieldFormatter formatter = new KeyValueFieldFormatter(":", "lalakey");
        final String str = formatter.format( field );

        Assert.assertEquals( ":", formatter.getSeparator() );
        Assert.assertEquals( "lalakey", formatter.getDefaultKey() );
        Assert.assertEquals( "lalakey:value", str );
    }

    @Test(expected = IllegalFieldException.class)
    public void format6_invalid_nullValue() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.KEY, "key" );

        final KeyValueFieldFormatter formatter = new KeyValueFieldFormatter(":", "lalakey");
        final String str = formatter.format( field );
    }
}
