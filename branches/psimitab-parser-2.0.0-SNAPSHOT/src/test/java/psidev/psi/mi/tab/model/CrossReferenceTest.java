package psidev.psi.mi.tab.model;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * CrossReferenceImpl Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since <pre>01/15/2007</pre>
 * @version 1.0
 */
public class CrossReferenceTest {

    @Test public void setGetDatabase() throws Exception {
        CrossReference cr = new CrossReferenceImpl( "db", "id" );
        assertEquals( "db", cr.getDatabaseName() );

        cr.setDatabaseName("foo");
        assertEquals( "foo", cr.getDatabaseName() );

        try {
            cr.setDatabaseName(" ");
            fail();
        } catch ( Exception e ) {
            // ok
            assertEquals( "foo", cr.getDatabaseName() );
        }

        try {
            cr.setDatabaseName(null);
            fail();
        } catch ( Exception e ) {
            // ok
            assertEquals( "foo", cr.getDatabaseName() );
        }
    }

    @Test public void setGetIdentifier() throws Exception {
        CrossReference cr = new CrossReferenceImpl( "db", "id" );
        assertEquals( "id", cr.getIdentifier() );

        cr.setIdentifier( "foo" );
        assertEquals( "foo", cr.getIdentifier() );

        try {
            cr.setIdentifier( " " );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        try {
            cr.setIdentifier( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }

    @Test public void setGetText() throws Exception {
        CrossReference cr = new CrossReferenceImpl( "db", "id" );
        assertEquals( null, cr.getText() );

        cr = new CrossReferenceImpl( "db", "id", "text" );
        assertEquals( "text", cr.getText() );

        cr.setText( null );
        assertNull( cr.getText() );

        cr.setText( " " );
        assertNull( cr.getText() );
    }

    @Test public void equals() {
        CrossReference cr1 = new CrossReferenceImpl( "db", "id", "text" );
        CrossReference cr2 = new CrossReferenceImpl( "db ", "id ", "text" );
        CrossReference cr3 = new CrossReferenceImpl( "db2", "id", "text" );
        CrossReference cr4 = new CrossReferenceImpl( "db", "id3", "text" );
        CrossReference cr5 = new CrossReferenceImpl( "db", "id", "text123" );

        assertEquals( cr1, cr2 );
        assertEquals( cr1, cr5 );

        assertNotSame( cr1, cr3 );
        assertNotSame( cr1, cr4 );
        assertNotSame( cr2, cr3 );
        assertNotSame( cr2, cr4 );
    }
}
