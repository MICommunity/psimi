package org.hupo.psi.calimocho.io.parser;

import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.tab.io.parser.LiteralFieldParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class LiteralFieldParserTest {

    @Test
    public void parse1() throws Exception {
        String value = "lala";

        LiteralFieldParser parser = new LiteralFieldParser();
        final Field field = parser.parse( value, null );

        Assert.assertEquals( 1, field.getEntries().size() );
        Assert.assertEquals( "lala", field.get( CalimochoKeys.VALUE ) );
    }

    @Test
    public void parse2() throws Exception {
        String value = "";

        LiteralFieldParser parser = new LiteralFieldParser();
        final Field field = parser.parse( value, null );

        Assert.assertEquals( 1, field.getEntries().size() );
        Assert.assertEquals( "", field.get( CalimochoKeys.VALUE ) );
    }
}
