package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultModelledConfidence;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlModelledConfidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlModelledConfidenceWriterTest extends AbstractXmlWriterTest {
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

    private String confidence_bibref = "<confidence>\n" +
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
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "    <attributeList>\n" +
            "      <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "    </attributeList>\n"+
            "  </bibref>\n"+
            "</confidence>";

    @Test
    public void test_write_confidence_null() throws XMLStreamException, IOException {

        XmlModelledConfidenceWriter writer = new XmlModelledConfidenceWriter(createStreamWriter());
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_confidence() throws XMLStreamException, IOException {
        Confidence conf = new DefaultModelledConfidence(new DefaultCvTerm("intact-miscore"), "0.8");
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        XmlModelledConfidenceWriter writer = new XmlModelledConfidenceWriter(createStreamWriter());
        writer.write(conf);
        streamWriter.flush();

        Assert.assertEquals(confidence, output.toString());
    }

    @Test
    public void test_write_confidence_bibref() throws XMLStreamException, IOException {
        ModelledConfidence conf = new DefaultModelledConfidence(new DefaultCvTerm("intact-miscore"), "0.8");
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        conf.setPublication(new DefaultPublication("xxxxxx"));
        conf.getPublication().setTitle("test title");

        XmlModelledConfidenceWriter writer = new XmlModelledConfidenceWriter(createStreamWriter());
        writer.write(conf);
        streamWriter.flush();

        Assert.assertEquals(confidence_bibref, output.toString());
    }
}