package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.XmlConfidence;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25ConfidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25ConfidenceWriterTest extends AbstractXml25WriterTest {
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
    private String confidenceExperiments = "<confidence>\n" +
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
            "  <experimentRefList>\n" +
            "    <experimentRef>1</experimentRef>\n"+
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</confidence>";
    private String confidenceExperiments2 = "<confidence>\n" +
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
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "    <experimentRef>3</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</confidence>";
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_confidence_null() throws XMLStreamException, IOException {

        Xml25ConfidenceWriter writer = new Xml25ConfidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_confidence() throws XMLStreamException, IOException {
        Confidence conf = new XmlConfidence(new DefaultCvTerm("intact-miscore"), "0.8");
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        Xml25ConfidenceWriter writer = new Xml25ConfidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(conf);
        streamWriter.flush();

        Assert.assertEquals(confidence, output.toString());
    }

    @Test
    public void test_write_confidence_experiments() throws XMLStreamException, IOException {
        XmlConfidence conf = new XmlConfidence(new DefaultCvTerm("intact-miscore"), "0.8");
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        conf.getExperiments().add(new DefaultExperiment(new DefaultPublication("P12345")));
        conf.getExperiments().add(new DefaultExperiment(new DefaultPublication("P123456")));
        this.elementCache.clear();

        Xml25ConfidenceWriter writer = new Xml25ConfidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(conf);
        streamWriter.flush();

        Assert.assertEquals(confidenceExperiments, output.toString());
    }

    @Test
    public void test_write_confidence_experimentsRegistered() throws XMLStreamException, IOException {
        XmlConfidence conf = new XmlConfidence(new DefaultCvTerm("intact-miscore"), "0.8");
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        conf.getType().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("P12345"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("P123456"));
        conf.getExperiments().add(exp1);
        conf.getExperiments().add(exp2);
        this.elementCache.clear();
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("P1234")));
        this.elementCache.extractIdForExperiment(exp1);
        this.elementCache.extractIdForExperiment(exp2);

        Xml25ConfidenceWriter writer = new Xml25ConfidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(conf);
        streamWriter.flush();

        Assert.assertEquals(confidenceExperiments2, output.toString());
    }
}