package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25ChecksumWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25ChecksumWriterTest extends AbstractXml25WriterTest {
    private String attribute_not_topic_ac ="<attribute name=\"rogid\">xxxxxxxxx1</attribute>";
    private String attribute ="<attribute name=\"rogid\" nameAc=\"MI:xxxx\">xxxxxxxxx1</attribute>";

    @Test
    public void test_write_checksum_null() throws XMLStreamException, IOException {

        Xml25ChecksumWriter writer = new Xml25ChecksumWriter(createStreamWriter());
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_checksum_no_topic_ac() throws XMLStreamException, IOException {
        Checksum annot = new DefaultChecksum(new DefaultCvTerm("rogid"), "xxxxxxxxx1");

        Xml25ChecksumWriter writer = new Xml25ChecksumWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        Assert.assertEquals(attribute_not_topic_ac, output.toString());
    }

    @Test
    public void test_write_checksum() throws XMLStreamException, IOException {
        Checksum annot = new DefaultChecksum(new DefaultCvTerm("rogid", "MI:xxxx"), "xxxxxxxxx1");

        Xml25ChecksumWriter writer = new Xml25ChecksumWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        Assert.assertEquals(attribute, output.toString());
    }
}
