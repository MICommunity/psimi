package psidev.psi.mi.tab.directoryProcessor;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;

/**
 * IgnoreDirectory Tester.
 *
 * @author <Authors name>
 * @since <pre>01/03/2007</pre>
 * @version 1.0
 */
public class IgnoreDirectoryTest extends FileSelectionAbstractTest {

    @Test
    public void process() {
        InputDirectoryProcessorStrategy selector = new IgnoreDirectory();
        Collection<File> files = selector.process( null );
        assertTrue( files.isEmpty() );

        files = selector.process( root );
        assertTrue( files.isEmpty() );

        files = selector.process( f1 );
        assertTrue( files.isEmpty() );
    }
}
