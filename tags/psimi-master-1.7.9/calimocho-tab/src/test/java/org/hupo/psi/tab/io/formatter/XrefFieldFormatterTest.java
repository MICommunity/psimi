package org.hupo.psi.tab.io.formatter;

import org.hupo.psi.tab.io.FieldFormatter;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * XrefFieldFormatter Tester.
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class XrefFieldFormatterTest {

    static FieldFormatter fieldFormatter;

    @BeforeClass
    public static void initFieldFormatter(){
       fieldFormatter = new XrefFieldFormatter();
    }

    public Field getField(){
        Field field = new DefaultField();
        field.set( CalimochoKeys.KEY, "idA" );
        field.set( CalimochoKeys.DB, "uniprot");
        field.set( CalimochoKeys.VALUE, "P12345" );
        return field;
    }

    @Test
    public void format_withoutText() throws Exception {
        Field field = getField();

        String fieldText = fieldFormatter.format( field );
        String expected = "uniprot:P12345";
        Assert.assertEquals(expected, fieldText);
    }

    @Test
    public void format_withText() throws Exception {
        Field field = getField();
        field.set( CalimochoKeys.TEXT, "testText");

        String fieldText = fieldFormatter.format( field );
        String expected = "uniprot:P12345(testText)";
        Assert.assertEquals(expected, fieldText);
    }

    @Test
    public void format_escaping_withText() throws Exception {
        Field field = getField();
        field.set( CalimochoKeys.TEXT, "test)Text");
        field.set( CalimochoKeys.DB, "uni:prot" );
        field.set( CalimochoKeys.VALUE, "P|12345");

        String fieldText = fieldFormatter.format( field );
        String expected = "\"uni:prot\":\"P|12345\"(\"test)Text\")";
        Assert.assertEquals(expected, fieldText);
    }

    @Test(expected = IllegalFieldException.class)
    public void format_noDB() throws Exception {
        Field field = new DefaultField();
        field.set(CalimochoKeys.VALUE, "P1");

        fieldFormatter.format( field );

    }

    @Test(expected = IllegalFieldException.class)
    public void format_noValue() throws Exception {
        Field field = new DefaultField();
        field.set(CalimochoKeys.DB, "uniprot");

        fieldFormatter.format( field );

    }
}
