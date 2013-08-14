package psidev.psi.mi.jami.mitab.io.iterator;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.tab.io.iterator.MitabModelledInteractionIterator;
import psidev.psi.mi.jami.tab.io.parser.ModelledInteractionLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;

import java.io.InputStream;

/**
 * Unit tester for MitabModelledInteractionIterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/07/13</pre>
 */

public class MitabModelledInteractionIteratorTest {

    @Test
    public void test_header_and_empty_lines() throws ParseException, java.text.ParseException {
        InputStream stream = MitabInteractionEvidenceIteratorTest.class.getResourceAsStream("/samples/mitab27_line_header.txt");
        MitabModelledInteractionIterator iterator = new MitabModelledInteractionIterator(new ModelledInteractionLineParser(stream));

        Assert.assertTrue(iterator.hasNext());

        // read first interaction
        ModelledInteraction line1 = iterator.next();
        Assert.assertNotNull(line1);
        Assert.assertTrue(iterator.hasNext());

        // read title
        ModelledInteraction line2 = iterator.next();
        Assert.assertNotNull(line2);
        Assert.assertFalse(iterator.hasNext());
    }
}
