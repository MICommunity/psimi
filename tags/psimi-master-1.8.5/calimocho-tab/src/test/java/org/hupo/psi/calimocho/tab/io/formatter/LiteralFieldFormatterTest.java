package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
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
public class LiteralFieldFormatterTest {

    @Test
    public void format1() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.VALUE, "value" );

        final LiteralFieldFormatter formatter = new LiteralFieldFormatter();
        final String str = formatter.format( field );

        Assert.assertEquals( "value", str );
    }

    @Test(expected = IllegalFieldException.class)
    public void format2() throws Exception {
        Field field = new DefaultField();

        final LiteralFieldFormatter formatter = new LiteralFieldFormatter();
        final String str = formatter.format( field );
    }

}
