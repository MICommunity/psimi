package psidev.psi.mi.jami.xml.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.InteractionEvidence;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Unit tester for Xml25InteractionEvidenceParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25InteractionEvidenceParserTest {

    @Test
    public void test_read_valid_xml25() throws JAXBException, XMLStreamException, IOException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/10049915.xml");

        PsiXml25Parser<InteractionEvidence> parser = new Xml25InteractionEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
    }

    @Test
    public void test_read_valid_xml25_inferred() throws JAXBException, XMLStreamException, IOException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/21703451.xml");

        PsiXml25Parser<InteractionEvidence> parser = new Xml25InteractionEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
    }
}
