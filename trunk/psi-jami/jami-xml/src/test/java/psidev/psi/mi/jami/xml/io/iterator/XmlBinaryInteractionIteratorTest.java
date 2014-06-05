package psidev.psi.mi.jami.xml.io.iterator;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.io.parser.LightXmlBinaryParser;
import psidev.psi.mi.jami.xml.io.parser.XmlEvidenceParserTest;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

/**
 * Unit tester for BinaryInteractionIterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class XmlBinaryInteractionIteratorTest {

    @Test
    public void test_read_valid_xml25_inferred() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/21703451.xml");

        Iterator<BinaryInteraction> iterator = new XmlBinaryInteractionIterator(new LightXmlBinaryParser(stream));
        int index = 0;
        while(iterator.hasNext()){
            BinaryInteraction interaction = iterator.next();
            Assert.assertNotNull(interaction);
            index++;
        }

        Assert.assertEquals(26, index);
    }

    @Test
    @Ignore
    public void test_read_valid_xml25_2() throws PsiXmlParserException, JAXBException, XMLStreamException, IOException {
        InputStream stream = new URL("ftp://ftp.ebi.ac.uk/pub/databases/intact/current/psi25/pmid/2011/19536198_gong-2009-1_01.xml").openStream();

        System.out.println("Start"+System.currentTimeMillis());
        Iterator<BinaryInteraction> iterator = new XmlBinaryInteractionIterator(new LightXmlBinaryParser(stream));        int index = 0;
        while(iterator.hasNext()){
            BinaryInteraction interaction = iterator.next();
            Assert.assertNotNull(interaction);
            Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
            index++;
        }
        System.out.println("End"+System.currentTimeMillis());

        Assert.assertEquals(2000,index);
    }

    @Test
    public void test_read_valid_xml25_nary() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/15144954.xml");

        Iterator<BinaryInteraction> iterator = new XmlBinaryInteractionIterator(new LightXmlBinaryParser(stream));
        int index = 0;
        int numberOfExpanded=0;
        while(iterator.hasNext()){
            BinaryInteraction interaction = iterator.next();
            Assert.assertNotNull(interaction);
            Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
            if (interaction.getComplexExpansion() != null && ComplexExpansionMethod.SPOKE_EXPANSION_MI.equals(interaction.getComplexExpansion().getMIIdentifier())){
                numberOfExpanded++;
            }
            index++;
        }

        Assert.assertEquals(15, index);
        Assert.assertEquals(5, numberOfExpanded);
    }
}
