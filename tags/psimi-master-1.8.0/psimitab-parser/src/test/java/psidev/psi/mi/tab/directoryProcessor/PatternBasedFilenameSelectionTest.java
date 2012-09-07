package psidev.psi.mi.tab.directoryProcessor;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * PatternBasedFilenameSelection Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/03/2007</pre>
 */
public class PatternBasedFilenameSelectionTest extends FileSelectionAbstractTest {

    //
    //  psimitab/patternBasedTest/
    //    |
    //    |-- a.xml
    //    |-- b.xml
    //    |-- c.doc
    //    |-- e.txt
    //    |
    //    |-- dir1/
    //    |     |
    //    |     |-- h1.xml
    //    |     |-- i1.xml
    //    |     |-- j1.doc
    //    |     \-- k1.txt
    //    |
    //    |-- dir2/
    //    |     |
    //    |     |-- x2.xml
    //    |     \-- y2.avi
    //    |
    //    \-- dir2/
    //

    ////////////////////
    // Tests

    @Test
    public void testNullParam() {
        InputDirectoryProcessorStrategy selector = new PatternBasedFilenameSelection( false, Pattern.compile( ".*\\.xml" ) );
        try {
            selector.process( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }

    @Test
    public void testProcessNonRecursive() {
        InputDirectoryProcessorStrategy selector = new PatternBasedFilenameSelection( false, Pattern.compile( ".*\\.xml" ) );

        Collection<File> files = selector.process( root );

        assertEquals( 2, files.size() );

        assertTrue( files.contains( f1 ) );
        assertTrue( files.contains( f2 ) );
    }

    @Test
    public void testProcessRecursive() {
        InputDirectoryProcessorStrategy selector = new PatternBasedFilenameSelection( true, Pattern.compile( ".*\\.xml" ) );

        Collection<File> files = selector.process( root );

        assertEquals( 5, files.size() );

        assertTrue( files.contains( f1 ) );
        assertTrue( files.contains( f2 ) );
        assertTrue( files.contains( f5 ) );
        assertTrue( files.contains( f6 ) );
        assertTrue( files.contains( f9 ) );
    }

    @Test
    public void testProcessAllNonRecursive() {
        InputDirectoryProcessorStrategy selector = new PatternBasedFilenameSelection( false, Pattern.compile( ".*" ) );

        Collection<File> files = selector.process( root );

        assertEquals( 4, files.size() );

        assertTrue( files.contains( f1 ) );
        assertTrue( files.contains( f2 ) );
        assertTrue( files.contains( f3 ) );
        assertTrue( files.contains( f4 ) );
    }

    @Test
    public void testProcessAllRecursive() {
        InputDirectoryProcessorStrategy selector = new PatternBasedFilenameSelection( true, Pattern.compile( ".*" ) );

        Collection<File> files = selector.process( root );

        assertEquals( 10, files.size() );

        assertTrue( files.contains( f1 ) );
        assertTrue( files.contains( f2 ) );
        assertTrue( files.contains( f3 ) );
        assertTrue( files.contains( f4 ) );
        assertTrue( files.contains( f5 ) );
        assertTrue( files.contains( f6 ) );
        assertTrue( files.contains( f7 ) );
        assertTrue( files.contains( f8 ) );
        assertTrue( files.contains( f9 ) );
        assertTrue( files.contains( f10 ) );
    }

    @Test
    public void testProcessAviRecursive() {
        InputDirectoryProcessorStrategy selector = new PatternBasedFilenameSelection( true, Pattern.compile( ".*\\.avi" ) );

        Collection<File> files = selector.process( root );

        assertEquals( 1, files.size() );

        assertTrue( files.contains( f10 ) );
    }

    @Test
    public void testProcessAviNonRecursive() {
        InputDirectoryProcessorStrategy selector = new PatternBasedFilenameSelection( false, Pattern.compile( ".*\\.avi" ) );

        Collection<File> files = selector.process( root );

        assertEquals( 0, files.size() );
    }
}