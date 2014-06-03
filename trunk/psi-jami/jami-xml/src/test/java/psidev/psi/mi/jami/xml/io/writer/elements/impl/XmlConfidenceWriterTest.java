package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultConfidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlConfidenceWriter;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlConfidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlConfidenceWriterTest extends AbstractXmlWriterTest {
    private String confidence = "<confidence>\n" +
            "  <unit>\n" +
            "    <names>\n" +
            "      <shortLabel>intact-miscore</shortLabel>\n"+
            "    </names>\n"+
            "    <attributeList>\n" +
            "      <attribute name=\"test2\"/>\n"+
            "      <attribute name=\"test3\"/>\n"+
            "    </attributeList>\n"+
            "  </unit>\n" +
            "  <value>0.8</value>\n" +
            "</confidence>";

    @Test
    public void test_write_confidence_null() throws XMLStreamException, IOException {

        XmlConfidenceWriter writer = new XmlConfidenceWriter(createStreamWriter());
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_confidence() throws XMLStreamException, IOException {
        Confidence conf = new DefaultConfidence(new DefaultCvTerm("intact-miscore"), "0.8");
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        XmlConfidenceWriter writer = new XmlConfidenceWriter(createStreamWriter());
        writer.write(conf);
        streamWriter.flush();

        Assert.assertEquals(confidence, output.toString());
    }
}