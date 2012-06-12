package psidev.psi.mi.tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;

import psidev.psi.mi.tab.model.BinaryInteraction;

/**
 * PsimiTabIterator Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>02/07/2007</pre>
 */
public class PsimiTabIteratorTest {

    @Test
    public void getInteractionsProcessedCount() throws Exception {
        PsimiTabReader reader = new PsimiTabReader( true );
        Iterator<BinaryInteraction> iterator = reader.iterate( TestHelper.HEADER_TAB_11585365);
        while ( iterator.hasNext() ) {
            iterator.next();
        }
        assertEquals( 6, ( ( PsimiTabIterator ) iterator ).getInteractionsProcessedCount() );
    }

    @Test
    public void next() throws Exception {
        PsimiTabReader reader = new PsimiTabReader( true );
        Iterator<BinaryInteraction> iterator = reader.iterate( TestHelper.HEADER_TAB_11585365);

        // next() should keep returning the next element even if hasNext() hasn't been called.
        BinaryInteraction previous = null;
        int count = 0;
        while ( count < 6 ) {
            // NOTE - we DO NOT call hasNext() on purpose here !!
            BinaryInteraction bi = iterator.next();
            if ( previous != null ) {
                assertFalse( "Repeated call to next keep returning the same object", previous == bi );
                assertFalse( "Repeated call to next keep returning similar object", bi.equals( previous ) );
            }
            previous = bi;
            count++;
        }
    }

    @Test
    public void hasNext() throws Exception {
        PsimiTabReader reader = new PsimiTabReader( true );
        Iterator<BinaryInteraction> iterator = reader.iterate(TestHelper.HEADER_TAB_11585365);

        // hasNext should not be gready.
        for ( int i = 0; i < 20; i++ ) {
            iterator.hasNext();
            assertEquals( 0, ( ( PsimiTabIterator ) iterator ).getInteractionsProcessedCount() );
        }

        int count = 0;
        while ( iterator.hasNext() ) {
            iterator.next();
            count++;
            assertEquals( count, ( ( PsimiTabIterator ) iterator ).getInteractionsProcessedCount() );
        }
    }
}
