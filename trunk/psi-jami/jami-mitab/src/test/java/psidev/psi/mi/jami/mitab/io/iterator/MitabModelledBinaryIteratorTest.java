package psidev.psi.mi.jami.mitab.io.iterator;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.tab.io.iterator.MitabModelledBinaryIterator;
import psidev.psi.mi.jami.tab.io.parser.ModelledBinaryLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;

import java.io.InputStream;

/**
 * Unit tester for MitabModelledInteractionIterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/07/13</pre>
 */

public class MitabModelledBinaryIteratorTest {

    @Test
    public void test_header_and_empty_lines() throws ParseException, java.text.ParseException {
        InputStream stream = MitabModelledBinaryIteratorTest.class.getResourceAsStream("/samples/mitab27_line_header.txt");
        MitabModelledBinaryIterator iterator = new MitabModelledBinaryIterator(new ModelledBinaryLineParser(stream));

        Assert.assertTrue(iterator.hasNext());

        // read first interaction
        ModelledBinaryInteraction line1 = iterator.next();
        Assert.assertNotNull(line1);
        Assert.assertTrue(iterator.hasNext());

        // read title
        ModelledBinaryInteraction line2 = iterator.next();
        Assert.assertNotNull(line2);
        Assert.assertFalse(iterator.hasNext());
    }
}
