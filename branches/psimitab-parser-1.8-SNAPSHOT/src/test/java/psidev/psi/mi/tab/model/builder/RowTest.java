package psidev.psi.mi.tab.model.builder;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Row Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 1.6.0
 * @version $Id$
 */
public class RowTest {
    
    private Row row;

    @Before
    public void init() {
        row = new Row( );
        row.appendColumn( new Column( Arrays.asList( new Field("a"), new Field("b") )) );
        row.appendColumn( new Column( Arrays.asList( new Field("c"), new Field("d") )) );
        row.appendColumn( new Column( Arrays.asList( new Field("e") )) );
        row.appendColumn( new Column( Arrays.asList( new Field("f") )) );
    }

    @After
    public void cleanup() {
        row = null;
    }

    @Test
    public void getColumnCount() {
        Row row = new Row( );
        row.appendColumn( new Column( Arrays.asList( new Field("a"), new Field("b") )) );
        row.appendColumn( new Column( Arrays.asList( new Field("c"), new Field("d") )) );
        row.appendColumn( new Column( Arrays.asList( new Field("e") )) );
        row.appendColumn( new Column( Arrays.asList( new Field("f") )) );
        Assert.assertEquals( 4, row.getColumnCount() );
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumnByIndex_fail() {
        row.getColumnByIndex( 4 );
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getColumnByIndex_negative() {
        row.getColumnByIndex( -1 );
    }

    @Test
    public void getColumnByIndex() {
        final Column column = row.getColumnByIndex( 1 );
        Assert.assertNotNull( column );
        Assert.assertEquals( 2, column.getFields().size() );
    }

    @Test
    public void iterate() throws Exception {
        final Iterator<Column> iterator = row.iterator();
        Assert.assertNotNull( iterator );
        Assert.assertEquals( 2, iterator.next().getFields().size() );
        Assert.assertTrue( iterator.hasNext() );

        Assert.assertEquals( 2, iterator.next().getFields().size() );
        Assert.assertTrue( iterator.hasNext() );

        Assert.assertEquals( 1, iterator.next().getFields().size() );
        Assert.assertTrue( iterator.hasNext() );
        
        Assert.assertEquals( 1, iterator.next().getFields().size() );
        Assert.assertFalse( iterator.hasNext() );
    }
}
