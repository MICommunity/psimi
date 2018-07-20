package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.tab.io.FieldFormatter;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
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

    public Field getUniprotField(){
        Field field = new DefaultField();
        field.set( CalimochoKeys.KEY, "idA" );
        field.set( CalimochoKeys.DB, "uniprot");
        field.set( CalimochoKeys.VALUE, "P12345" );
        return field;
    }

    public Field getPsiMiField() {
        Field field = new DefaultField();
        field.set( CalimochoKeys.KEY, "psi-mi" );
        field.set( CalimochoKeys.DB, "MI");
        field.set( CalimochoKeys.VALUE, "0914" );
        return field;
    }

    public Field getGoField() {
        Field field = new DefaultField();
        field.set( CalimochoKeys.KEY, "go" );
        field.set( CalimochoKeys.DB, "GO");
        field.set( CalimochoKeys.VALUE, "0003824" );
        return field;
    }

    @Test
    public void format_withoutText() throws Exception {
        Field field = getUniprotField();

        String fieldText = fieldFormatter.format( field );
        String expected = "uniprot:P12345";
        Assert.assertEquals(expected, fieldText);
    }

    @Test
    public void format_uniprotField_withText() throws Exception {
        Field field = getUniprotField();
        field.set( CalimochoKeys.TEXT, "testText");

        String fieldText = fieldFormatter.format( field );
        String expected = "uniprot:P12345(testText)";
        Assert.assertEquals(expected, fieldText);
    }

    @Test
    public void format_psiMiField_withText() throws Exception {
        Field field = getPsiMiField();
        field.set( CalimochoKeys.TEXT, "association");

        String fieldText = fieldFormatter.format( field );
        String expected = "MI:0914(association)";
        Assert.assertEquals(expected, fieldText);
    }

    @Test
    public void format_goField_withText() throws Exception {
        Field field = getGoField();
        field.set( CalimochoKeys.TEXT, "catalytic activity");

        String fieldText = fieldFormatter.format( field );
        String expected = "GO:0003824(catalytic activity)";
        Assert.assertEquals(expected, fieldText);
    }

    @Test
    public void format_escaping_withText() throws Exception {
        Field field = getUniprotField();
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
