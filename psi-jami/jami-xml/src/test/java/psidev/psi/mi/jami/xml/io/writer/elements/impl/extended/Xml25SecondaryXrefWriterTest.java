package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.extension.XmlXref;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25SecondaryXrefWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public class Xml25SecondaryXrefWriterTest extends AbstractXml25WriterTest {

    private String xref ="<secondaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>";
    private String xref_secondary ="<secondaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" refType=\"identity\" refTypeAc=\"MI:0356\" secondary=\"P12346\"/>";
    private String xref_attributes ="<secondaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" refType=\"identity\" refTypeAc=\"MI:0356\" secondary=\"P12346\">\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"caution\">test caution</attribute>\n" +
            "  </attributeList>\n" +
            "</secondaryRef>";
    @Test
    public void test_write_xref_null() throws XMLStreamException, IOException {

        Xml25SecondaryXrefWriter writer = new Xml25SecondaryXrefWriter(createStreamWriter());
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_xref() throws XMLStreamException, IOException {
        XmlXref ref = new XmlXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345", CvTermUtils.createIdentityQualifier());

        Xml25SecondaryXrefWriter writer = new Xml25SecondaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref, output.toString());
    }

    @Test
    public void test_write_xref_secondary() throws XMLStreamException, IOException {
        XmlXref ref = new XmlXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345", CvTermUtils.createIdentityQualifier());
        ref.setSecondary("P12346");
        Xml25SecondaryXrefWriter writer = new Xml25SecondaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref_secondary, output.toString());
    }

    @Test
    public void test_write_xref_attributes() throws XMLStreamException, IOException {
        XmlXref ref = new XmlXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345", CvTermUtils.createIdentityQualifier());
        ref.setSecondary("P12346");
        ref.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("caution"), "test caution"));
        Xml25SecondaryXrefWriter writer = new Xml25SecondaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref_attributes, output.toString());
    }
}
