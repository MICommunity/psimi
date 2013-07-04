package psidev.psi.mi.jami.mitab.io.iterator;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.tab.io.iterator.MitabBinaryIterator;
import psidev.psi.mi.jami.tab.io.parser.BinaryLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;

import java.io.InputStream;

/**
 * Unit tester for MitabInteractionIterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/07/13</pre>
 */

public class MitabBinaryIteratorTest {

    @Test
    public void test_header_and_empty_lines() throws ParseException, java.text.ParseException {
        InputStream stream = MitabBinaryIteratorTest.class.getResourceAsStream("/samples/mitab27_line_header.txt");
        MitabBinaryIterator iterator = new MitabBinaryIterator(new BinaryLineParser(stream));

        Assert.assertTrue(iterator.hasNext());

        // read first interaction
        BinaryInteraction line1 = iterator.next();
        Assert.assertNotNull(line1);
        Assert.assertTrue(iterator.hasNext());

        // read title
        BinaryInteraction line2 = iterator.next();
        Assert.assertNotNull(line2);
        Assert.assertFalse(iterator.hasNext());
    }
}
