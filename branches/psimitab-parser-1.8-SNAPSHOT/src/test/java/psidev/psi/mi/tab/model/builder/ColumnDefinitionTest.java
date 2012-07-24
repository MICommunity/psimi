package psidev.psi.mi.tab.model.builder;

import org.junit.*;

/**
 * ColumnDefinition Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since TODO artifact version
 * @version $Id$
 */
public class ColumnDefinitionTest {

    @Test
    public void getColumnName() throws Exception {
        final ColumnDefinition cd = new ColumnDefinition( "long", "short", new PlainTextFieldBuilder() );
        Assert.assertNotNull( cd );
        Assert.assertEquals("short", cd.getShortName());
        Assert.assertEquals("long", cd.getColumnName());
        Assert.assertNotNull( cd.getBuilder() );
        Assert.assertTrue( cd.getBuilder() instanceof PlainTextFieldBuilder );
    }

    @Test(expected = NullPointerException.class)
    public void constructor_noName() throws Exception {
        new ColumnDefinition( null, "short", new PlainTextFieldBuilder() );
    }    

    @Test(expected = NullPointerException.class)
    public void constructor_noShortName() throws Exception {
        new ColumnDefinition( "lala", null, new PlainTextFieldBuilder() );
    }

    @Test(expected = NullPointerException.class)
    public void constructor_noBuilder() throws Exception {
        new ColumnDefinition( "foo", "short", null );
    }
}
