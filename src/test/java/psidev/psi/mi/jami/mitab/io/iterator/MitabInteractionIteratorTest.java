package psidev.psi.mi.jami.mitab.io.iterator;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.tab.io.iterator.MitabInteractionIterator;
import psidev.psi.mi.jami.tab.io.parser.InteractionLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;

import java.io.InputStream;

/**
 * Unit tester for MitabInteractionIterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/07/13</pre>
 */

public class MitabInteractionIteratorTest {

    @Test
    public void test_header_and_empty_lines() throws ParseException, java.text.ParseException {
        InputStream stream = MitabInteractionEvidenceIteratorTest.class.getResourceAsStream("/samples/mitab27_line_header.txt");
        MitabInteractionIterator iterator = new MitabInteractionIterator(new InteractionLineParser(stream));

        Assert.assertTrue(iterator.hasNext());

        // read first interaction
        Interaction line1 = iterator.next();
        Assert.assertNotNull(line1);
        Assert.assertTrue(iterator.hasNext());

        // read title
        Interaction line2 = iterator.next();
        Assert.assertNotNull(line2);
        Assert.assertFalse(iterator.hasNext());
    }
}
