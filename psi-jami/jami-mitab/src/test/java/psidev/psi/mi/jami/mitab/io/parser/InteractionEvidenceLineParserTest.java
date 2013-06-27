package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.tab.io.parser.InteractionEvidenceLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;

import java.io.InputStream;

/**
 * Unit tester for InteractionEvidenceLineParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class InteractionEvidenceLineParserTest {

    @Test
    public void test_read_valid_mitab27() throws ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_line.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        BinaryInteractionEvidence binary = parser.MitabLine();

        Assert.assertNotNull(binary);
        Assert.assertTrue(parser.hasFinished());
    }
}
