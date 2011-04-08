package psidev.psi.mi.tab.model.builder;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Column Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 1.5.2
 * @version $Id$
 */
public class ColumnTest {

    @Test
    public void column_toString() throws Exception {
        FieldBuilder fieldBuilder = new CrossReferenceFieldBuilder();

        Assert.assertEquals("a:b(c)|x:y(z)", new Column(Arrays.asList(fieldBuilder.createField("a:b(c)"),
                                                                      fieldBuilder.createField("x:y(z)"))).toString());

        Assert.assertEquals("\"a(a:a)a\":\"b(b:b)b\"(\"c(c:c)c\")" +
                            "|" +
                            "\"a(\\\"a:a)a\":\"b(\\\"b:b\\\")b\"(\"c(c:c)\\\"c\")", 
                            new Column(Arrays.asList(fieldBuilder.createField("\"a(a:a)a\":\"b(b:b)b\"(\"c(c:c)c\")"),
                                                     fieldBuilder.createField( "\"a(\\\"a:a)a\":\"b(\\\"b:b\\\")b\"(\"c(c:c)\\\"c\")" ) )).toString() );

        Assert.assertEquals("type:\"(+-)-value\"(\"descr(i)ption\")",
                            new Column(Collections.singleton(new Field("type", "(+-)-value", "descr(i)ption"))).toString() );
    }
}
